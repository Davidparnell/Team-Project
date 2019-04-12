package com.moneyapp.transaction;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.moneyapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SuggestionAdapter extends ArrayAdapter<SuggestionData>
{
    private List<SuggestionData> suggestionList;
    private Context context;

    public SuggestionAdapter(List<SuggestionData> suggestionList, Context context)
    {
        super(context, R.layout.suggestion_list_items, suggestionList);
        this.suggestionList = suggestionList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        //Custom view
        View listViewItem = inflater.inflate(R.layout.suggestion_list_items, null, true);

        ImageView imgCash = listViewItem.findViewById(R.id.NoteView);

        SuggestionData items = suggestionList.get(position);

        imgCash.setImageDrawable(items.getCash());

        return listViewItem;
    }
}
