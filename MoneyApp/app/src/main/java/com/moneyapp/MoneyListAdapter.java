package com.moneyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MoneyListAdapter extends ArrayAdapter<MoneyListData>
{
    private List<MoneyListData> moneyList;
    private Context context;

    public MoneyListAdapter(List<MoneyListData> moneyList, Context context)
    {
        super(context, R.layout.money_list, moneyList);
        this.moneyList = moneyList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        //Custom view
        View listViewItem = inflater.inflate(R.layout.money_list, null, true);

        ImageView imgCash = listViewItem.findViewById(R.id.MoneyView);

        MoneyListData items = moneyList.get(position);

        imgCash.setImageDrawable(items.getCash());

        return listViewItem;
    }
}
