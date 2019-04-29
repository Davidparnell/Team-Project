package com.moneyapp.transaction;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.moneyapp.R;

import static com.google.android.gms.flags.FlagSource.G;

class ScanProgress extends AsyncTask<Void, Integer, Void> {
    ProgressBar progressBar;
    int i = 0;
    @Override
    protected Void doInBackground(Void... args) {
        while(i < 6){
            publishProgress(i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // code where data is processing
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressBar.setProgress(values[0]*25);
    }

    public void setProgressBar(ProgressBar bar) {
        this.progressBar = bar;
    }

    public void setI(int i) {
        this.i = i;
    }
}
