package com.wildcardenter.myfab.pgecattaindencesystem.activities;

import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseExceptionMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.kernel.colors.Color;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.scottyab.aescrypt.AESCrypt;
import com.wildcardenter.myfab.pgecattaindencesystem.R;
import com.wildcardenter.myfab.pgecattaindencesystem.models.Attendance;
import com.wildcardenter.myfab.pgecattaindencesystem.utils.NetworkStateReceiver;

import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.wildcardenter.myfab.pgecattaindencesystem.helpers.Constants.PASSWORD;

public class TeacherGeneratorActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private boolean close = false;
    FirebaseAuth auth;
    private String subCode, genDate, genTime;
    ImageView qrCode;
    TextView subjectCode, qrDate, qrTime;
    String fullpath;
    private DatabaseReference reference;
    private DocumentReference documentReference;
    private NetworkStateReceiver receiver;
    private AlertDialog dialog;
    private IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_generator);
        getWindow().setStatusBarColor(getResources().getColor(R.color.BarcodeActStatusBarColor));
        toolbar = findViewById(R.id.toolbar);
        auth = FirebaseAuth.getInstance();
        qrCode = findViewById(R.id.QRCodeImage);
        subjectCode = findViewById(R.id.QRSubjectCode);
        qrDate = findViewById(R.id.QRDate);
        qrTime = findViewById(R.id.QRTime);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            subCode = bundle.getString("Sub_code");
        }
        Date date = new Date();
        genDate = SimpleDateFormat.getDateInstance().format(date);
        genTime = SimpleDateFormat.getTimeInstance().format(date);
        qrDate.setText(genDate);
        qrTime.setText(genTime);
        subjectCode.setText(subCode);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(v -> {
            if (!close) {
                Toast.makeText(this, "Click Again Exit This Screen", Toast.LENGTH_SHORT).show();
                close = true;
            } else {
                finish();
            }
        });
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        dialog=new AlertDialog.Builder(this, R.style.dialog_anim)
                .setTitle("No Internet Connection!!!")
                .setMessage("Make sure that you're connected to the internet.")
                .setCancelable(false).create();
        receiver=new NetworkStateReceiver(dialog);
        filter=new IntentFilter();
        createQRCode();
    }

    private void createQRCode() {
        try {

            String str = auth.getUid() + "@" + subCode + "@" + genDate
                    + "@" + genTime;
            String cryptoString = AESCrypt.encrypt(PASSWORD, str);
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            BitMatrix bitMatrix = multiFormatWriter.encode(cryptoString, BarcodeFormat.QR_CODE, 600, 600);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrCode.setImageBitmap(bitmap);
            entryItOnDatabase();


        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void entryItOnDatabase() {
        fullpath = "attendance/" + subCode + "/" + genDate + "/" + genTime;
        Attendance attendance = new Attendance(ServerValue.TIMESTAMP, null, genDate + " " + genTime);
        reference = FirebaseDatabase.getInstance().getReference(fullpath);
        reference.setValue(attendance).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                documentReference = FirebaseFirestore.getInstance()
                        .collection("classCount").document("countDocument");
                documentReference.update(subCode, FieldValue.increment(1)).addOnSuccessListener(this,task1 -> {
                        Toast.makeText(TeacherGeneratorActivity.this, "successful",
                                Toast.LENGTH_SHORT).show();
                });

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.qr_code_share) {
            Toast.makeText(this, "To Be Developed", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.delete_qr_code) {
            AlertDialog builder = new AlertDialog.Builder(this
                    , R.style.Theme_AppCompat_Light_Dialog_Alert)
                    .setTitle("Delete This QR Code?")
                    .setMessage("Delete this QR code and it's reference on the database.")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Delete", (dialog, which) -> {
                        reference.removeValue().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                documentReference.update(subCode, FieldValue.increment(-1));
                                Toast.makeText(this, "Successfully deleted", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(this, "Something Went Wrong.Please Delete Again.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }).create();
            builder.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    protected void onResume() {
        super.onResume();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver,filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}
