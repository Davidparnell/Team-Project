package com.moneyapp.transaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ProgressBar;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.moneyapp.R;
import com.moneyapp.SpeechService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

/*
Reads the value off the register in format 0.00 or 0-00
Scans 5 times and confirms by checking if the same value was read enough times
Also displays a progress bar while scanning in a separate thread
 */

public class Camera extends AppCompatActivity {
    private static final int requestID = 1;
    SurfaceView cameraSurface;
    CameraSource cameraSource;
    public int scanCounter;
    public HashMap<String, Integer> regList = new HashMap<String, Integer>();
    public String register = "";
	private final int maxScans = 5; //number of scans to check accuracy
	private final int minFreq = 3;  //minimum frequency to accept result is a ratio of maxScans eg.5:3
    ProgressBar progressBar;
    public ScanProgress progressTask = new ScanProgress();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        progressBar = findViewById(R.id.progressbar);
        cameraSurface = findViewById(R.id.cameraSurface);

        //Progress bar while scanning register
        progressTask.setProgressBar(progressBar);
        progressTask.execute();
        //camera setup and running image recognition
        cameraSource();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //if statement for if the camera permission is not as expected.
        if (requestCode != requestID) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        //if camera permission is granted, start the cameraSource
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            try {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                cameraSource.start(cameraSurface.getHolder());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void cameraSource() {
        TextRecognizer textRecognizer = new TextRecognizer.Builder(this).build();
        if(!textRecognizer.isOperational()) { return; }

        //Setting up camera
        cameraSource = new CameraSource.Builder(this, textRecognizer)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1280, 1024)
                .setAutoFocusEnabled(true)
                .setRequestedFps(2.0f)
                .build();

        cameraSurface.getHolder().addCallback(new SurfaceHolder.Callback() {
            //Camera permissions
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                        //If no permission found
                        ActivityCompat.requestPermissions(Camera.this,
                                new String[]{Manifest.permission.CAMERA},
                                requestID);
                        return;
                    }
                    cameraSource.start(cameraSurface.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            };

            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            };
        });

        //Text processing
        textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
            public void release(){}

            public void receiveDetections(Detector.Detections<TextBlock> detections) {
                SparseArray<TextBlock> items = detections.getDetectedItems();

                if (items.size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    //convert scanned text Block
                    for (int i=0; i<items.size(); i++) {
                        TextBlock item = items.valueAt(i);
                        stringBuilder.append(item.getValue());
                        stringBuilder.append("\n");
                    }

                    //Looking for a number in pattern 000.00 or 000-00
                    Pattern pattern = Pattern.compile("(\\d{1,3}\\.\\d{2}|\\d{1,3}\\-\\d{2})");
                    Matcher matcher = pattern.matcher(stringBuilder);

                    if(matcher.find()){
                        progressTask.setProgress(scanCounter);
                        String group = matcher.group(1).replace("-", ".");//for consistency with pattern

                        //Add scanned numbers to frequency map
                        if(scanCounter < maxScans){
                            if(regList.get(group) == null) {
                                regList.put(group, 1);
                            }
                            else{
                                regList.put(group, regList.get(group)+1);
                            }
                            scanCounter++;
                        }
                        else{
                            //check when enough scans have been made
                            scanCounter = 0;
                            int highestFreq = 0;
                            //find most frequent
                            for(Map.Entry<String, Integer> entry : regList.entrySet() ){
                                if(entry.getValue() >= highestFreq){
                                    highestFreq = entry.getValue();
                                    register = entry.getKey();
                                }
                            }
                            regList.clear();

                            //Intent for text to speech.
                            Intent speechIntent = new Intent(getApplicationContext(), SpeechService.class);
                            //Pass data to be spoken to the SpeechService class.
                            speechIntent.putExtra("textData", register);
                            //Start Text to speech.
                            getApplicationContext().startService(speechIntent);
                            
                            //minimal frequency allowed
                            if(highestFreq < minFreq){
                                return;
                            }
                            else{
                                progressTask.setProgress(6);//progress bar end do doInBackground
                                Intent intent = new Intent(Camera.this, PaySuggestion.class);
                                intent.putExtra("register", register);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                }
            }
        });
    }
}

