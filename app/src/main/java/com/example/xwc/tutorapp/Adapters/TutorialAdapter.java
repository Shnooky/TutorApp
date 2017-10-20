package com.example.xwc.tutorapp.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xwc.tutorapp.Model.Tutorial;
import com.example.xwc.tutorapp.R;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Random;

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
        absenteeView.setText(Integer.toString(currentTutorial.getAbsentees()));

        TextView latesView = (TextView) listItemView.findViewById(R.id.numLates);
        latesView.setText(Integer.toString(currentTutorial.getLates()));

        ImageView image = (ImageView) listItemView.findViewById(R.id.tutImage);
        Random rn = new Random();
        switch(rn.nextInt(3-1+1)+1) {
            case 1:
                image.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorAccent));
                break;
            case 2:
                image.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                break;
            case 3:
                image.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimaryDark));
                break;
            default:
                break;

        }
        


        return listItemView;
    }
}
