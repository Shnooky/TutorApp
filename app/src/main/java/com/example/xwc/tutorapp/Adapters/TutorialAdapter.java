package com.example.xwc.tutorapp.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.xwc.tutorapp.Model.Tutorial;
import com.example.xwc.tutorapp.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Jacob on 15/10/2017.
 */

public class TutorialAdapter extends ArrayAdapter<Tutorial> {

    public TutorialAdapter(Context context, List<Tutorial> tutorials) {
        super(context,0,tutorials);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.view_tutorials, parent, false);
        }

        Tutorial currentTutorial = getItem(position);

        TextView tutorialView = (TextView) listItemView.findViewById(R.id.tutName);
        tutorialView.setText(currentTutorial.getTutorialId()+" "+currentTutorial.getTutorialDate());

        TextView absenteeView = (TextView) listItemView.findViewById(R.id.numAbsent);
        absenteeView.setText(currentTutorial.getAbsentees());

        TextView latesView = (TextView) listItemView.findViewById(R.id.numLates);
        latesView.setText(currentTutorial.getLates());
        


        return listItemView;
    }
}
