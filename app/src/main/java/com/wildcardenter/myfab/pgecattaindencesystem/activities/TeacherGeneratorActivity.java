package com.wildcardenter.myfab.pgecattaindencesystem.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.scottyab.aescrypt.AESCrypt;
import com.wildcardenter.myfab.pgecattaindencesystem.R;

import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static com.wildcardenter.myfab.pgecattaindencesystem.helpers.Constants.PASSWORD;

public class TeacherGeneratorActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private boolean close = false;
    FirebaseAuth auth;
    private String subCode="CS-301",genDate,genTime;
    ImageView qrCode;
    TextView subjectCode,qrDate,qrTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_generator);
        getWindow().setStatusBarColor(getResources().getColor(R.color.BarcodeActStatusBarColor));
        toolbar = findViewById(R.id.toolbar);
        auth = FirebaseAuth.getInstance();
        qrCode=findViewById(R.id.QRCodeImage);
        subjectCode=findViewById(R.id.QRSubjectCode);
        qrDate=findViewById(R.id.QRDate);
        qrTime=findViewById(R.id.QRTime);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            subCode = bundle.getString("subCode");
        }
        Date date = new Date();
        genDate=SimpleDateFormat.getDateInstance().format(date);
        genTime=SimpleDateFormat.getTimeInstance().format(date);
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
        createQRCode();
    }

    private void createQRCode() {
        try {

            String str = auth.getUid() + "@" + subCode + "@" + genDate
                    + "@" +genTime ;
            String cryptoString = AESCrypt.encrypt(PASSWORD, str);
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            BitMatrix bitMatrix = multiFormatWriter.encode(cryptoString, BarcodeFormat.QR_CODE, 600, 600);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrCode.setImageBitmap(bitmap);


        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
