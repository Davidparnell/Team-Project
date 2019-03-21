package com.moneyapp;

import android.Manifest;
import android.content.pm.PackageManager;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class Camera extends AppCompatActivity {

    private TextRecognizer textRecognizer;
    private static final int requestPermissionID = 101;
    SurfaceView cameraSurface;
    TextView cameraText;
    CameraSource cameraSource;
    public int detNum;
    public HashMap<String, Integer> regList = new HashMap<String, Integer>();
    public String register = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);

        cameraSurface = findViewById(R.id.cameraSurface);
        cameraText = findViewById(R.id.cameraText);

        cameraSource();
    }

    private void cameraSource() {
        textRecognizer = new TextRecognizer.Builder(this).build();
        if (!textRecognizer.isOperational()) {
            //Log.d("CAM", "Dependencies are downloading....try after few moment");
            return;
        }

        cameraSource = new CameraSource.Builder(this, textRecognizer)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1280, 1024)
                .setAutoFocusEnabled(true)
                .setRequestedFps(2.0f)
                .build();

        cameraSurface.getHolder().addCallback(new SurfaceHolder.Callback() {
            //@RequiresApi(api = Build.VERSION_CODES.M)
            public void surfaceCreated(SurfaceHolder holder) {
                try {

                    if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(Camera.this,
                                new String[]{Manifest.permission.CAMERA},
                                requestPermissionID);
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

                    Pattern pattern = Pattern.compile("(\\d{1,3}\\.\\d{2}|\\d{1,3}\\,\\d{2})");
                    Matcher matcher = pattern.matcher(block);

                    if(matcher.find()){
                        String group = matcher.group(1).replace(",", ".");

                        if(detNum < 10){
                            if(regList.get(group) == null) {
                                regList.put(group, 1);
                            }
                            else{
                                regList.put(group, regList.get(group)+1);
                            }
                            detNum++;
                        }
                        else{//exit
                            detNum = 0;
                            int i = 0;
                            //find most frequent
                            for(Map.Entry<String, Integer> entry : regList.entrySet() ){
                                if(entry.getValue() >= i){
                                    i = entry.getValue();
                                    register = entry.getKey();
                                }
                            }
                            regList.clear();

                            if(i < 3){
                                return;
                            }
                            else{
                                finish();
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

