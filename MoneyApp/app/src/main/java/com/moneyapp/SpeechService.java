package com.moneyapp;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class SpeechService extends Service implements TextToSpeech.OnInitListener
{
    private TextToSpeech textToSpeech;
    String textData;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        textData = intent.getStringExtra("textData");
        return super.onStartCommand(intent, flags, startId);
    }

    public void onCreate()
    {
        super.onCreate();
        textToSpeech = new TextToSpeech(getApplicationContext(), this);

    }

    @Override
    public IBinder onBind(Intent intent)
    {
       return null;
    }

    @Override
    public void onInit(int status)
    {
        if (status == TextToSpeech.SUCCESS)
        {
            int ttsLang = textToSpeech.setLanguage(Locale.UK);
            if (ttsLang == TextToSpeech.LANG_MISSING_DATA || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Log.e("TTS", "The language is not supported");
            }
            else
            {
                textToSpeech.speak(textData, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }

    public void onDestroy()
    {
        if (textToSpeech != null)
        {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }

        super.onDestroy();
    }

    public void onUtteranceCompleted(String uttID)
    {
        stopSelf();
    }
}
