package com.example.xwc.tutorapp.Controllers;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import com.example.xwc.tutorapp.Database.ClassProvider;
import com.example.xwc.tutorapp.Database.DBOpenHelper;
import com.example.xwc.tutorapp.Database.StudentProvider;
import com.example.xwc.tutorapp.Database.StudentTutorialProvider;
import com.example.xwc.tutorapp.R;
import android.view.View;
import android.widget.*;
import android.graphics.*;
import android.net.Uri;
import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap.CompressFormat;
/*
Created by: Jacob and James on 15/10/2017
For Student adding, browsing and editing.
 */
public class StudentProfile extends AppCompatActivity {
    private Spinner cboCurrentClass;
    private TextView lblGrade;
    private TextView gradeLabel;
    private EditText txtZID;
    private EditText txtFirstName;
    private EditText txtSurname;
    private Spinner cboSkill;
    private TextView lblPart;
    private TextView lblPartLabel;
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
        lblPart = (TextView) findViewById(R.id.lblPart);
        lblPartLabel = (TextView) findViewById(R.id.lblPartLabel);
        btnUpdate = (Button) findViewById(R.id.btnUpdateStudent);
        btnDelete = (Button) findViewById(R.id.btnDeleteStudent);
        imgStudent = (ImageView) findViewById(R.id.imgStudent);

        //Creating Spinner and adding drop-down options
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, new String[]{
                "Low", "Moderate", "High", "Exceptional"
        });
        cboSkill.setAdapter(adapter);
        ArrayList<String> classes = new ArrayList<>();
        Cursor c = getContentResolver().query(ClassProvider.CONTENT_URI,
                DBOpenHelper.CLASSES_ALL_COLUMNS, null, null, null);
        while (c != null && c.moveToNext()) {
            classes.add(c.getString(c.getColumnIndex(DBOpenHelper.CLASSES_CLASS_ID)));
        }
        cboCurrentClass.setAdapter(
                new ArrayAdapter<String>(
                        this, android.R.layout.simple_spinner_item, classes.toArray(new String[classes.size()])));

       /*
       Set OnClickListener for Update Button - validates input before adding data to the database.
        Different ContentProvider methods are called depending on whether the student is being updated or created.
        */
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String error = validateInput();
                if (error.isEmpty()) {
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
                                insertValues, DBOpenHelper.STUDENTS_ZID + " = ?", new String[]{updating_student});
                    } else {
                        // add new student
                        getContentResolver().insert(StudentProvider.CONTENT_URI,
                                insertValues);
                    }
                    Intent i = new Intent();
                    i.putExtra("IMG", student_photo != null ? getBytes(student_photo): (updating_student != null ? getIntent().getByteArrayExtra("PICTURE"):null));
                    i.putExtra("NAME", txtFirstName.getText().toString() + " " + txtSurname.getText().toString());
                    setResult(TutorialStudentEditor.UPDATE_STUDENT, i);
                    CommonMethods.showToast("Student successfully added/modified");
                    finish();
                } else {
                    CommonMethods.showToast(error);
                }

            }
        });
/* Set OnClickListener for Delete button
         */
        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String studentToDelete = txtZID.getText().toString();
                getContentResolver().delete(StudentProvider.CONTENT_URI, DBOpenHelper.STUDENTS_ZID + "=?", new String[]{studentToDelete});
                getContentResolver().delete(StudentTutorialProvider.CONTENT_URI, DBOpenHelper.STUDENTS_TUTORIALS_ZID + "=?", new String[]{studentToDelete});
                setResult(TutorialStudentEditor.DELETE_STUDENT, null);
                finish();
            }
        });
/*
Set up on Click listener for ImageView - creates an explicit intent to the Camera app.
As different Photography applications communicate in different ways, it was best to use the standard camera app.
 */
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
            selectSpinnerValue(cboCurrentClass, i.getStringExtra("CLASS").toString());
            txtFirstName.setText(i.getStringExtra("FIRSTNAME").toString());
            selectSpinnerValue(cboSkill, i.getStringExtra("SKILL").toString());
            updating_student = i.getStringExtra("ZID").toString();
            btnUpdate.setText("Update");
            btnDelete.setVisibility(View.VISIBLE);
            byte[] img = i.getByteArrayExtra("PICTURE");
            imgStudent.setImageBitmap(getImage(img));
            txtZID.setEnabled(false);
            // Class cannot be changed
            cboCurrentClass.setEnabled(false);
        } else {
            updating_student = null;
            btnUpdate.setText("Add");
            btnDelete.setVisibility(View.INVISIBLE);
            lblGrade.setText("n/a");
            imgStudent.setImageBitmap(getImage(null));
        }

//If we are adding a student, we don't need to show their total grade or total participation mark
        if (i.hasExtra("HideGrades")) {
            lblGrade.setVisibility(View.INVISIBLE);
            gradeLabel.setVisibility(View.INVISIBLE);
            lblPart.setVisibility(View.INVISIBLE);
            lblPartLabel.setVisibility(View.INVISIBLE);
        } else {
            gradeLabel.setVisibility(View.VISIBLE);
            lblGrade.setVisibility(View.VISIBLE);
            lblPart.setVisibility(View.VISIBLE);
            lblPartLabel.setVisibility(View.VISIBLE);
            // Calculate total grade
            Cursor cc = DBOpenHelper.runSQL("select SUM(STUDENT_TUTORIALS.MARK) AS 'AVGMARK'" +
                    ", SUM(STUDENT_TUTORIALS.PARTICIPATION) AS 'AVGPART' FROM STUDENT_TUTORIALS" +
                    " INNER JOIN STUDENTS ON (STUDENTS.ZID = STUDENT_TUTORIALS.ZID) WHERE " +
                    "STUDENTS.ZID = ?", new String[]{i.getStringExtra("ZID")});
            if (cc != null && cc.moveToNext()) {
                lblGrade.setText(cc.getString(cc.getColumnIndex("AVGMARK")));
                lblPart.setText(cc.getString(cc.getColumnIndex("AVGPART")));

            }
        }
    }

    /* Validate input by ensuring that there are no blank fields and that the ZID is entered correctly.
    Validation on the Class and Coding Level fields are not necessary as the Spinners ensure that data entry is valid.
    If the data entered is invalid, the caller method creates a Toast to alert the user.
     */
    private String validateInput() {
        String error = "";
        String checkZID = txtZID.getText().toString();
        String checkFirstname = txtFirstName.getText().toString();
        String checkSurname = txtSurname.getText().toString();
        if (checkZID.length()!=8 || (!Character.toString(checkZID.charAt(0)).equals("Z") && !Character.toString(checkZID.charAt(0)).equals("z"))) {
            error = "Please ensure a valid ZID is entered (z#######)";
        } else if (checkFirstname.isEmpty() || checkSurname.isEmpty()) {
            error = "Please ensure a firstname and surname is entered";
        } else if (CommonMethods.studentExists(checkZID) && updating_student == null) {
            error = "Student with the ZID " + checkZID + " already exists in the system";
        }
        return error;
    }

    /*
    If updating a student's detials, set spinner according to what is contained in the database for that student.
     */
    private void selectSpinnerValue(Spinner spinner, String myString) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equals(myString)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    /*
    Waits for a photo to be taken. Once taken, data is sent back to the Smart Tutor app (hence onActivityResult)
     */
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
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        if (image == null || image.length < 10) {
            return BitmapFactory.decodeResource(MainActivity.appContext.getResources(),
                        R.drawable.smarttutor);
        } else {
            return BitmapFactory.decodeByteArray(image, 0, image.length);
        }

    }


}
