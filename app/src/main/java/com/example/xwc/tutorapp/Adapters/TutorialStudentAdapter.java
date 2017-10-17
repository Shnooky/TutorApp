package com.example.xwc.tutorapp.Adapters;

/**
 * Created by Jacob on 15/10/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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

        if (currentST.ismAbsent()) {
            lblStatus.setText("Absent");
        } else if (currentST.ismLate()) {
            lblStatus.setText("Current Mark: " + currentST.getmMark() + " (Late)");
        } else {
            lblStatus.setText("Current Mark: " + currentST.getmMark());
        }

        return listItemView;
    }


}