package com.moneyapp;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import java.util.HashMap;
import java.util.Locale;

public class SpeechService extends Service implements TextToSpeech.OnInitListener
{
    private TextToSpeech textToSpeech;
    String textData;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        //Get data from the class calling text to speech.
        textData = intent.getStringExtra("textData");

        return super.onStartCommand(intent, flags, startId);
    }

    public void onCreate()
    {
        super.onCreate();
        textToSpeech = new TextToSpeech(getApplicationContext(), this);
        //OnUtteranceProgressListener to track speech progress.
        textToSpeech.setOnUtteranceProgressListener(speechProgressListener);
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
                //HashMap to store utterance id
                HashMap<String, String> map = new HashMap<String, String>();
                //Add Utterance id value.
                map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "messageID");
                //Start Speech
                textToSpeech.speak(textData, TextToSpeech.QUEUE_FLUSH, map);
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

    private UtteranceProgressListener speechProgressListener = new UtteranceProgressListener()
    {
        @Override
        public void onStart(String utteranceId) {

        }

        @Override
        public void onDone(String utteranceId)
        {
            stopSelf();
        }

        @Override
        public void onError(String utteranceId) {

        }
    };

}
