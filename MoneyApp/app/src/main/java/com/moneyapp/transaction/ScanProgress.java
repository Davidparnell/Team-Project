package com.moneyapp.transaction;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

/*
Progress bar for the camera scan in a separate thread not interrupt the UI thread
 */

class ScanProgress extends AsyncTask<Void, Integer, Void> {
    ProgressBar progressBar;
    int progress = 0;

    @Override
    protected Void doInBackground(Void... args) {
        while(progress < 6){
            publishProgress(progress);
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

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
