package com.example.xwc.tutorapp.Adapters;

/**
 * Created by Jacob on 15/10/2017.
 */

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.math.*;
import com.example.xwc.tutorapp.Model.Class;
import com.example.xwc.tutorapp.R;

import java.util.List;
import java.util.Random;

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
        gradeView.setText(Double.toString(Math.round(currentClass.getAverageGrade()*100)/100.0));

        TextView numStuView = (TextView) listItemView.findViewById(R.id.numberStudents);
        numStuView.setText(Integer.toString(currentClass.getNumStu()));

        ImageView image = (ImageView) listItemView.findViewById(R.id.classImage);
        Random rn = new Random();
        switch(rn.nextInt(3)+1) {
            case 1:
                image.setImageResource(R.drawable.mon);
                image.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorAccent));
                break;
            case 2:
                image.setImageResource(R.drawable.tue);
                image.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                break;
            case 3:
                image.setImageResource(R.drawable.wed);
                image.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimaryDark));
                break;
            default:
                break;

        }

        return listItemView;
    }


}