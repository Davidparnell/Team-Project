package com.moneyapp.wallet;

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

public class ContentsAdapter extends ArrayAdapter<ContentsData>{

    private List<ContentsData> contentlist;
    private Context context;

    public ContentsAdapter(List<ContentsData> contentlist, Context context)
    {
        super(context, R.layout.wallet_contents, contentlist);
        this.contentlist = contentlist;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from( context );

        //Custom view
        View listViewItem = inflater.inflate(R.layout.wallet_contents, null, true);

        ImageView imgCash = listViewItem.findViewById(R.id.MoneyView);

        ContentsData items = contentlist.get(position);

        imgCash.setImageDrawable(items.getCash());

        return listViewItem;
    }
}
