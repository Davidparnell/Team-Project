package com.moneyapp.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.moneyapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HistoryAdapter extends ArrayAdapter<HistoryData>
{
    private List<HistoryData> historyList;
    private Context context;

    public HistoryAdapter(List<HistoryData> historyList, Context context)
    {
        super(context, R.layout.list_items, historyList);
        this.historyList = historyList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        //Custom view
        View listViewItem = inflater.inflate(R.layout.list_items, null, true);

        //Text view components
        TextView textDate = listViewItem.findViewById(R.id.DateView);
        TextView textBalance = listViewItem.findViewById(R.id.BalanceView);
        TextView textRegister = listViewItem.findViewById(R.id.RegisterView);
        ImageView imgType = listViewItem.findViewById(R.id.typeImg);

        HistoryData items = historyList.get(position);

        textDate.setText(items.getDate());
        textBalance.setText(items.getBalance());
        textRegister.setText(items.getRegister());
        imgType.setImageDrawable(items.getType());

        return listViewItem;
    }
}
