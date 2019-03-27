package com.moneyapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class Camera extends AppCompatActivity {
    private TextRecognizer textRecognizer;
    private static final int requestID = 1;
    SurfaceView cameraSurface;
    CameraSource cameraSource;
    public int maxDetections;
    public HashMap<String, Integer> regList = new HashMap<String, Integer>();
    public String register = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);

        cameraSurface = findViewById(R.id.cameraSurface);

        cameraSource();
    }

    private void cameraSource() {
        textRecognizer = new TextRecognizer.Builder(this).build();
        if (!textRecognizer.isOperational()) {
            return;
        }

        //Setting up camera
        cameraSource = new CameraSource.Builder(this, textRecognizer)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1280, 1024)
                .setAutoFocusEnabled(true)
                .setRequestedFps(2.0f)
                .build();

        //Camera permissions
        cameraSurface.getHolder().addCallback(new SurfaceHolder.Callback() {
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
                    for (int i=0; i<items.size(); i++) {
                        TextBlock item = items.valueAt(i);
                        stringBuilder.append(item.getValue());
                        stringBuilder.append("\n");
                    }
                    String block = stringBuilder.toString();

                    //Looking for a number in pattern 000.00 or 000-00
                    Pattern pattern = Pattern.compile("(\\d{1,3}\\.\\d{2}|\\d{1,3}\\-\\d{2})");
                    Matcher matcher = pattern.matcher(stringBuilder);

                    if(matcher.find()){
                        String group = matcher.group(1).replace("-", ".");

                        //Add numbers to frequency map
                        if(maxDetections < 5){
                            if(regList.get(group) == null) {
                                regList.put(group, 1);
                            }
                            else{
                                regList.put(group, regList.get(group)+1);
                            }
                            maxDetections++;
                        }
                        else{
                            //check when enough scans have been made
                            maxDetections = 0;
                            int i = 0;
                            //find most frequent
                            for(Map.Entry<String, Integer> entry : regList.entrySet() ){
                                if(entry.getValue() >= i){
                                    i = entry.getValue();
                                    register = entry.getKey();
                                }
                            }
                            regList.clear();
                            //minimal frequency allowed
                            if(i < 3){
                                return;
                            }
                            else{
                                cameraSource.stop();
                            }
                        }

                        Log.d("REG", regList.toString());
                        Log.d("REG", register);
                    }
                }
            }
        });
    }
}

