package com.example.xwc.tutorapp.Adapters;

/**
 * Created by Jacob and James on 15/10/2017.
 * Custom adapter for Students, Tutorial-level
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xwc.tutorapp.Controllers.StudentProfile;
import com.example.xwc.tutorapp.Model.Class;
import com.example.xwc.tutorapp.Model.StudentTutorial;
import com.example.xwc.tutorapp.R;

import java.util.List;

public class TutorialStudentAdapter extends ArrayAdapter<StudentTutorial> {

    public TutorialStudentAdapter(Context context, List<StudentTutorial> classes) {
        super(context, 0, classes);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.view_students, parent, false);
        }

        StudentTutorial currentST = getItem(position);

        TextView lblStudent = (TextView) listItemView.findViewById(R.id.v_studentName);
        TextView lblStatus = (TextView) listItemView.findViewById(R.id.v_studentStatus);
        lblStudent.setText(currentST.getFirstName() + " " + currentST.getLastName() + " - " + currentST.getMzID());
        LinearLayout itemLayout = (LinearLayout) listItemView.findViewById(R.id.studentLLayout);
        ImageView imgStudent = listItemView.findViewById(R.id.imgStudent_LV);
        if (currentST.ismAbsent()) {
            lblStatus.setText("Absent");
            itemLayout.setBackgroundResource(R.drawable.layout_absent);
        } else if (currentST.ismLate()) {
            if (currentST.getParticipation() > 0) {
                lblStatus.setText("Mark: " + currentST.getmMark() + " (Participated & Late)");
                itemLayout.setBackgroundResource(R.drawable.layout_part);
            } else {
                lblStatus.setText("Mark: " + currentST.getmMark() + " (Late)");
            }
            itemLayout.setBackgroundResource(R.drawable.layout_late);
        } else {
            if (currentST.getParticipation() > 0) {
                lblStatus.setText("Mark: " + currentST.getmMark() + " (Participated)");
                itemLayout.setBackgroundResource(R.drawable.layout_part);
            } else {
                lblStatus.setText("Mark: " + currentST.getmMark());
            }
        }

        imgStudent.setImageBitmap(StudentProfile.getImage(currentST.getmImg()));

        return listItemView;
    }


}