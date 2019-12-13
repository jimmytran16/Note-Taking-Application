package com.example.androidnotetakingproject;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.androidnotetakingproject.Document;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DocumentListAdapter extends ArrayAdapter<Document> implements View.OnClickListener {

    private ArrayList<Document> dataSet;
    private Context mContext;
    private int lastPosition = -1;
    private SimpleDateFormat dateFormat;
    DateFormat df;


    // View lookup cache
    private static class ViewHolder {
        TextView mainText;
        TextView subText;
    }

    public DocumentListAdapter(Context context, int resource, List list) {
        super(context, R.layout.list_layout, list);
        df = new java.text.SimpleDateFormat("MM/dd/yyyy: hh:mm:ss");

//        dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Document currentDoc = getItem(position);

        ViewHolder viewHolder;

        View result;

        //We are checking if the UI view (convertView) for the item has already been made
        if(convertView == null){
            /*
             * When convertView == null we must make it our selves, we do this with the inflater
             * in the code below
             */
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.list_layout, parent, false);

            /*
             * Next we assign the values from of the viewHolder to the values from UI elements in
             * the item we are creating.
             */
            viewHolder.mainText = (TextView)convertView.findViewById(R.id.item_main_title);

            viewHolder.subText = (TextView)convertView.findViewById(R.id.item_sub_title);

            result = convertView;

            /*
             * Next we associate viewHolder to the tag of the view. The tag can be an object that
             * hold extra descriptive data about the view.
             */
            convertView.setTag(viewHolder);
        }else {
            /*
             * If the view is already created we simply return the tag which we know will be a
             * ViewHolder b/c thats what we assigned it to be in the code above
             */

            viewHolder = (ViewHolder) convertView.getTag();

            result = convertView;
        }

            viewHolder.mainText.setText(currentDoc.getName());

            viewHolder.subText.setText(df.format(new Date(currentDoc.getCreationDate())));

        return result;

//        return super.getView(position, convertView, parent);
    }

    @Override
    public void onClick(View v) {

    }
}
