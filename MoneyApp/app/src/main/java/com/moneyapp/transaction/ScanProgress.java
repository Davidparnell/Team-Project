package com.moneyapp.transaction;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

class ScanProgress extends AsyncTask<Void, Integer, Void> {
    private ProgressBar progressBar;
    @Override
    protected Void doInBackground(Void... args) {
        publishProgress();

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
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progressBar.setProgress(values[0]);
    }

    public void setProgressBar(ProgressBar bar) {
        this.progressBar = bar;
    }
}
