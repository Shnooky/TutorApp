package com.example.xwc.tutorapp.Controllers;
import java.util.ArrayList;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.Manifest;
import com.example.xwc.tutorapp.Database.ClassProvider;
import com.example.xwc.tutorapp.Database.DBOpenHelper;
import com.example.xwc.tutorapp.Database.StudentProvider;
import com.example.xwc.tutorapp.Database.StudentTutorialProvider;
import com.example.xwc.tutorapp.Model.Class;
import com.example.xwc.tutorapp.R;
import android.content.pm.*;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.graphics.*;
import android.net.Uri;
import org.w3c.dom.Text;
import java.io.File;
import android.os.Environment;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.ByteArrayOutputStream;
import android.graphics.Bitmap.CompressFormat;

public class StudentProfile extends AppCompatActivity {
    private Spinner cboCurrentClass;
    private TextView lblGrade;
    private TextView gradeLabel;
    private EditText txtZID;
    private EditText txtFirstName;
    private EditText txtSurname;
    private Spinner cboSkill;

    private Button btnUpdate;
    private Button btnDelete;
    private ImageView imgStudent;
    private String updating_student = null;

    private Uri mImageCaptureUri;
    Bitmap student_photo = null;
    private static final int CAMERA_PIC_REQUEST = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_profile);

        // Get references to UI elements
        cboCurrentClass = (Spinner) findViewById(R.id.cboStudentClass);
        lblGrade = (TextView) findViewById(R.id.lblStudentGrade);
        gradeLabel = (TextView) findViewById(R.id.gradeLabel);

        txtZID = (EditText) findViewById(R.id.txtStudentZID);
        txtFirstName = (EditText) findViewById(R.id.txtStudentFirstname);
        txtSurname = (EditText) findViewById(R.id.txtStudentSurname);
        cboSkill = (Spinner) findViewById(R.id.cboCodingSkill);

        btnUpdate = (Button) findViewById(R.id.btnUpdateStudent);
        btnDelete = (Button) findViewById(R.id.btnDeleteStudent);

        imgStudent = (ImageView) findViewById(R.id.imgStudent);

        // Set up UI

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, new String[] {
                "Low", "Moderate", "High", "Exceptional"
        });
        cboSkill.setAdapter(adapter);

        ArrayList<String> classes = new ArrayList<>();
        Cursor c = getContentResolver().query(ClassProvider.CONTENT_URI,
                DBOpenHelper.CLASSES_ALL_COLUMNS, null,null, null);
        while (c!= null && c.moveToNext()) {
            classes.add(c.getString(c.getColumnIndex(DBOpenHelper.CLASSES_CLASS_ID)));
        }
        cboCurrentClass.setAdapter(
                new ArrayAdapter<String>(
                        this, android.R.layout.simple_spinner_item, classes.toArray(new String[classes.size()])));

        // Set up UI Events

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ContentValues insertValues = new ContentValues();
                insertValues.put(DBOpenHelper.STUDENTS_ZID, txtZID.getText().toString());
                insertValues.put(DBOpenHelper.STUDENTS_CLASS, cboCurrentClass.getSelectedItem().toString());
                insertValues.put(DBOpenHelper.STUDENTS_FIRSTNAME, txtFirstName.getText().toString());
                insertValues.put(DBOpenHelper.STUDENTS_SURNAME, txtSurname.getText().toString());
                insertValues.put(DBOpenHelper.STUDENTS_SKILL, cboSkill.getSelectedItem().toString());
                if (student_photo != null) {
                    insertValues.put(DBOpenHelper.STUDENTS_PICTURE, getBytes(student_photo));
                }

                if (updating_student != null) {
                    // update existing student
                    getContentResolver().update(StudentProvider.CONTENT_URI,
                            insertValues, DBOpenHelper.STUDENTS_ZID + " = ?", new String[] {updating_student});
                } else {
                    // add new student
                    getContentResolver().insert(StudentProvider.CONTENT_URI,
                            insertValues);
                }
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String studentToDelete = txtZID.getText().toString();
                getContentResolver().delete(StudentProvider.CONTENT_URI,DBOpenHelper.STUDENTS_ZID+"=?",new String[] {studentToDelete});
                getContentResolver().delete(StudentTutorialProvider.CONTENT_URI,DBOpenHelper.STUDENTS_TUTORIALS_ZID+"=?",new String[] {studentToDelete});
                Intent i = new Intent(getBaseContext(),StudentManager.class);
                startActivity(i);
            }
        });

        imgStudent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent cameraIntent =
                        new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);

            }
        });


        // Check if we are adding or updating a student

        Intent i = getIntent();
        if (i.hasExtra("ZID")) {
            txtFirstName.setText(i.getStringExtra("FIRSTNAME").toString());
            txtSurname.setText(i.getStringExtra("SURNAME").toString());
            txtZID.setText(i.getStringExtra("ZID").toString());
            selectSpinnerValue (cboCurrentClass, i.getStringExtra("CLASS").toString());
            txtFirstName.setText(i.getStringExtra("FIRSTNAME").toString());
            selectSpinnerValue(cboSkill, i.getStringExtra("SKILL").toString());
            updating_student = i.getStringExtra("ZID").toString();
            btnUpdate.setText("Update");
            btnDelete.setVisibility(View.VISIBLE);
            byte[] img = i.getByteArrayExtra("PICTURE");
            if (img != null) {
                imgStudent.setImageBitmap(getImage(img));
            }
        } else {
            updating_student = null;
            btnUpdate.setText("Add");
            btnDelete.setVisibility(View.INVISIBLE);
            lblGrade.setText("n/a");
        }

        gradeLabel.setVisibility(View.VISIBLE);
        lblGrade.setVisibility(View.VISIBLE);
        if(i.hasExtra("HideGrades")) {
            lblGrade.setVisibility(View.INVISIBLE);
            gradeLabel.setVisibility(View.INVISIBLE);
        }
    }

    private void selectSpinnerValue(Spinner spinner, String myString)
    {
        int index = 0;
        for(int i = 0; i < spinner.getCount(); i++){
            if(spinner.getItemAtPosition(i).toString().equals(myString)){
                spinner.setSelection(i);
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK) {
            student_photo = (Bitmap) data.getExtras().get("data");
            imgStudent.setImageBitmap(student_photo);
        }
    }

    // Bitmap -> Byte Array Utility Functions
    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }


}
