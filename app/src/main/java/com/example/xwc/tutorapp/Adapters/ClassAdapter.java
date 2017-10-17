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
import com.example.xwc.tutorapp.R;

import java.util.List;

public class ClassAdapter extends ArrayAdapter<Class> {

    public ClassAdapter(Context context, List<Class> classes) {
        super(context, 0, classes);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.view_classes, parent, false);
        }

        Class currentClass = getItem(position);

        TextView classView = (TextView) listItemView.findViewById(R.id.className);
        classView.setText(currentClass.getClassId() + " " + currentClass.getDay() + " " + currentClass.getStartTime() +
                "-" + currentClass.getEndTime() + " " + currentClass.getLocation());


        TextView gradeView = (TextView) listItemView.findViewById(R.id.avGrade);
        gradeView.setText(Double.toString(currentClass.getAverageGrade()));

        TextView numStuView = (TextView) listItemView.findViewById(R.id.numberStudents);
        gradeView.setText(Integer.toString(currentClass.getNumStu()));

        return listItemView;
    }


}