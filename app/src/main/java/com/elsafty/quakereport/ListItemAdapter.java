package com.elsafty.quakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class ListItemAdapter extends ArrayAdapter {
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d,yyyy");
    SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
    Date date;
    String upperTitle, lowerTitle;

    public ListItemAdapter(Activity context, int resource, ArrayList<ListItem> items) {
        super(context, resource, items);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        ListItem currentItem = (ListItem) getItem(position);

        View magnitudeView = listItemView.findViewById(R.id.magnitude);
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();
        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentItem.getmMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);
        if (currentItem.getmCity().contains("of")) {
            String[] s = currentItem.getmCity().split("of ");
            upperTitle = s[0] + "of";
            lowerTitle = s[1];
        } else {
            upperTitle = "NEAR THE";
            lowerTitle = currentItem.getmCity();
        }

        date = new Date(currentItem.getmDate());
        TextView magnitude = (TextView) listItemView.findViewById(R.id.magnitude);
        magnitude.setText("" + String.format("%.1f", currentItem.getmMagnitude()));
        TextView city = (TextView) listItemView.findViewById(R.id.city_textView);
        city.setText(lowerTitle);
        TextView near = (TextView) listItemView.findViewById(R.id.near);
        near.setText(upperTitle);
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        dateTextView.setText(dateFormat.format(date));
        TextView time = (TextView) listItemView.findViewById(R.id.time);
        time.setText(timeFormat.format(date));
        return listItemView;
    }

    private int getMagnitudeColor(float colorId) {
        int magnitude1Color;
        switch ((int) colorId) {
            case 0:
            case 1:
                magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude1);
                break;
            case 2:
                magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude2);
                break;
            case 3:
                magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude3);
                break;
            case 4:
                magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude4);
                break;
            case 5:
                magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude5);
                break;
            case 6:
                magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude6);
                break;
            case 7:
                magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude7);
                break;
            case 8:
                magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude8);
                break;
            case 9:
                magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude9);
                break;
            default:
                magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude10plus);
                break;
        }


        return magnitude1Color;
    }
}
