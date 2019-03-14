package com.moneyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Locale;

public class Text2SpeechTest extends AppCompatActivity implements View.OnClickListener
{
    TextToSpeech textToSpeech;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text2_speech_test);
        ImageView t2sbtn = findViewById(R.id.t2sbutton);
        editText = findViewById(R.id.et);
        t2sbtn.setOnClickListener(this);
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
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
                        Log.i("TTS", "The language is supported");
                    }

                    Log.i("TTS", "Init. Successful");
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "TTS Initialisation failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onClick(View v)
    {
        String textData = editText.getText().toString();
        int speechStatus = textToSpeech.speak(textData, TextToSpeech.QUEUE_FLUSH, null);

        if (speechStatus == TextToSpeech.ERROR)
        {
            Log.e("TTS", "Error converting tts");
        }
    }

}
