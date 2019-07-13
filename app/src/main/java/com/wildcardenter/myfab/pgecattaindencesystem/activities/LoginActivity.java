package com.wildcardenter.myfab.pgecattaindencesystem.activities;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.util.IOUtils;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.ListNumberingType;
import com.itextpdf.layout.property.TextAlignment;
import com.wildcardenter.myfab.pgecattaindencesystem.R;
import com.wildcardenter.myfab.pgecattaindencesystem.fragments.login.AdminLoginFragment;
import com.wildcardenter.myfab.pgecattaindencesystem.fragments.login.StudentLoginFragment;
import com.wildcardenter.myfab.pgecattaindencesystem.fragments.login.TeacherLoginFragment;
import com.wildcardenter.myfab.pgecattaindencesystem.models.Attendance;
import com.wildcardenter.myfab.pgecattaindencesystem.models.Resposnse;
import com.wildcardenter.myfab.pgecattaindencesystem.utils.NetworkStateReceiver;

import java.io.File;
import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private NetworkStateReceiver receiver;
    private AlertDialog dialog;
    private IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setStatusBarColor(getResources().getColor(R.color.lightBlue));
        dialog = new AlertDialog.Builder(this, R.style.dialog_anim)
                .setTitle("No Internet Connection!!!")
                .setMessage("Make sure that you're connected to the internet.")
                .setCancelable(false).create();
        receiver = new NetworkStateReceiver(dialog);
        filter = new IntentFilter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    public void openTeacherLoginFragment(View view) {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.loginFragContainer, new TeacherLoginFragment())
                .commit();
    }

    public void openAdminLoginFragment(View view) {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.loginFragContainer, new AdminLoginFragment()).commit();

    }

    public void openStudentLoginFragment(View view) {
        getSupportFragmentManager().beginTransaction().addToBackStack(null)
                .replace(R.id.loginFragContainer, new StudentLoginFragment()).commit();
    }

    public void generatePdf(View view) {
       /* ArrayList<Attendance> attendances = new ArrayList<>();
        HashMap<String, Resposnse> hashMap = new HashMap<>();
        Resposnse resposnsee = new Resposnse("35000116047", "vcvbcvbvbvbvcv"
                , System.currentTimeMillis());
        for (int i = 0; i < 10; i++) {
            hashMap.put("no " + i, resposnsee);
        }

        Attendance attendance = new Attendance(System.currentTimeMillis(),
                hashMap, SimpleDateFormat.getDateInstance().format(new Date()));

        for (int i = 0; i < 15; i++) {
            attendances.add(attendance);
        }
        new ExecutorThread(attendances).start();*/

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.dialog_anim);
        builder.setView(R.layout.dialog_successful).setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
        View v = getLayoutInflater().inflate(R.layout.dialog_successful, null);
        v.findViewById(R.id.success_dialog_btn).setOnClickListener(vw -> {
            dialog.dismiss();
            finish();
        });
    }

    private class ExecutorThread extends Thread {
        private ArrayList<Attendance> list;

        ExecutorThread(ArrayList<Attendance> list) {
            this.list = list;
        }

        private int count = 1;

        @Override
        public void run() {

            try {

                String filename = Environment.getExternalStorageDirectory() + "/" + System.currentTimeMillis() + ".pdf";
                File file = new File(filename);
                PdfWriter writer = new PdfWriter(file);

                PdfDocument document = new PdfDocument(writer);
                Document pdf = new Document(document);
                ImageData data = ImageDataFactory.create(IOUtils.toByteArray(getResources().openRawResource(R.raw.rkmgec)));
                Image image = new Image(data);
                image.setMarginTop(50f);
                image.setHorizontalAlignment(HorizontalAlignment.CENTER);
                pdf.add(image);
                Paragraph paragraph = new Paragraph("Sub : Attendance Sheet for CS301");
                paragraph.setMarginTop(50f);
                paragraph.setTextAlignment(TextAlignment.CENTER);
                pdf.add(paragraph);
                float[] colomWidth = {50f, 100f, 50f, 250f};
                Table table = new Table(colomWidth, true);
                table.setMarginTop(60f);
                table.setHorizontalAlignment(HorizontalAlignment.CENTER);
                table.addHeaderCell(new Cell().add(new Paragraph("Index")).setBackgroundColor(DeviceGray.GRAY)
                        .setTextAlignment(TextAlignment.CENTER));
                table.addHeaderCell(new Cell().add(new Paragraph("Date")).setBackgroundColor(DeviceGray.GRAY)
                        .setTextAlignment(TextAlignment.CENTER));
                table.addHeaderCell(new Cell().add(new Paragraph("Number Of Student Present")).setBackgroundColor(DeviceGray.GRAY)
                        .setTextAlignment(TextAlignment.CENTER));
                table.addHeaderCell(new Cell().add(new Paragraph("List Of Student Present")).setBackgroundColor(DeviceGray.GRAY)
                        .setTextAlignment(TextAlignment.CENTER));
                for (Attendance attendance : list) {
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(count))));
                    table.addCell(new Cell().add(new Paragraph(attendance.getDate())));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(30))));
                    List listt = new List();
                    for (Resposnse resposnse : attendance.getPresentStudentList().values()) {
                        listt.add(new ListItem(resposnse.getRoll_no())).setListSymbol(ListNumberingType.ROMAN_UPPER);
                    }
                    table.addCell(listt);
                    count++;

                }
                pdf.add(table);
                pdf.close();
                Log.d(TAG, "run: Successfully stored on " + filename);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}


