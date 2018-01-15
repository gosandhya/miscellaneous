/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.gms.samples.vision.face.facetracker;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.samples.vision.face.facetracker.ui.camera.CameraSourcePreview;
import com.google.android.gms.samples.vision.face.facetracker.ui.camera.GraphicOverlay;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.Timer;
import java.util.UUID;
import android.media.MediaPlayer;


/**
 * Activity for the face tracker app.  This app detects faces with the rear facing camera, and draws
 * overlay graphics to indicate the position, size, and ID of each face.
 */

public final class FaceTrackerActivity extends AppCompatActivity {
    private static final String TAG = "FaceTracker";

    private CameraSource mCameraSource = null;

    private CameraSourcePreview mPreview;
    private GraphicOverlay mGraphicOverlay;

    private static final int RC_HANDLE_GMS = 9001;
    // permission request codes need to be < 256
    private static final int RC_HANDLE_CAMERA_PERM = 2;

    //====================================//
    private boolean megaCheck = false;
    private boolean megaCheck1 = false;
    private boolean megaCheck2 = false;

    private boolean megaCheckSelf = false;
    private boolean megaCheck1Self = false;
    private boolean megaCheck2Self = false;

    private boolean activate = true;
    private boolean firstFrame = true;
    private boolean secondFrame = false;
    private boolean thirdFrame = false;
    private boolean firstFrameSelf = true;
    private boolean secondFrameSelf = false;
    private boolean thirdFrameSelf = false;

    private ArrayList<Integer> inputGest = new ArrayList<Integer>();
    private int[] inGest = new int[2];
    public MediaPlayer mp;





    //    private boolean wait = false;

    private int faceCounter = 0;

    public SQLiteDatabase mydatabase;
    //==============================================================================================
    // Activity Methods
    //==============================================================================================
    ArrayAdapter<BluetoothDevice> pairedDeviceAdapter;

    BluetoothAdapter bluetoothAdapter;
    ArrayList<BluetoothDevice> pairedDeviceArrayList;
    private UUID myUUID;





    private final String UUID_STRING_WELL_KNOWN_SPP =
            "00001101-0000-1000-8000-00805F9B34FB";

    ThreadConnectBTdevice myThreadConnectBTdevice;
    ThreadConnected myThreadConnected;
    private static final int REQUEST_ENABLE_BT = 1;
    //============================================


    /**
     * Initializes the UI and initiates the creation of a face detector.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.main);


        mPreview = (CameraSourcePreview) findViewById(R.id.preview);
        mGraphicOverlay = (GraphicOverlay) findViewById(R.id.faceOverlay);
        mp = MediaPlayer.create(this,R.raw.beep);


        //Required for intent BT
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)){
            Toast.makeText(this,
                    "FEATURE_BLUETOOTH NOT support",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        myUUID = UUID.fromString(UUID_STRING_WELL_KNOWN_SPP);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this,
                    "Bluetooth is not supported on this hardware platform",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }

//        myUUID = UUID.fromString(UUID_STRING_WELL_KNOWN_SPP);



        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.
        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource();
        } else {
            requestCameraPermission();
        }


        // setup(); //required when the app re-opens after resume!
        //issue on my qmobile.

    }

    public void menuList(View view)
    {
        Intent i = new Intent(getApplicationContext(), MenuList.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public void goToinstruction(View view)
    {
        Intent i = new Intent(getApplicationContext(),Instruction_Controller.class);
        startActivity(i);
        //setContentView(R.layout.activity_db);
    }

    public void gotoBack (View view) {
//        onBackPressed();
        if (DbAct.storedGestures == 5) {
            DbAct.isCalibrated = true; //as on faceGraphic it will cause logical error!
        }
        else {
            DbAct.isCalibrated = false;
        }

        if(Chooser.selfCalib && DbAct.storedGestures!= 5)
        {
            Intent i = new Intent(getApplicationContext(),DbAct.class);
            startActivity(i);
            setContentView(R.layout.activity_db);
        }
        else
        {

        }
        }





    public void screenSh(View view) {
        Bitmap bitmap = takeScreenshot();
        saveBitmap(bitmap);
    }

//    private void takeScreenshot() {
//        Date now = new Date();
//        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
//
//        try {
//            // image naming and path  to include sd card  appending name you choose for file
//            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";
//
//            // create bitmap screen capture
//            View v1 = getWindow().getDecorView().getRootView();
//            v1.setDrawingCacheEnabled(true);
//            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
//            v1.setDrawingCacheEnabled(false);
//
//            File imageFile = new File(mPath);
//
//            FileOutputStream outputStream = new FileOutputStream(imageFile);
//            int quality = 100;
//            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
//            outputStream.flush();
//            outputStream.close();
//
//            openScreenshot(imageFile);
//        } catch (Throwable e) {
//            // Several error may come out with file handling or OOM
//            e.printStackTrace();
//        }
//    }
//
//    private void openScreenshot(File imageFile) {
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        Uri uri = Uri.fromFile(imageFile);
//        intent.setDataAndType(uri, "image/*");
//        startActivity(intent);
//    }

    //    Bitmap bitmap;
//    View v1 = findViewById(R.id.topLayout);// get ur root view id
//    v1.setDrawingCacheEnabled(true);
//    bitmap = Bitmap.createBitmap(v1.getDrawingCache());
//    v1.setDrawingCacheEnabled(false);
//
    public Bitmap takeScreenshot() {
        View rootView = findViewById(R.id.topLayout).getRootView(); //findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    public void saveBitmap(Bitmap bitmap) {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        File imagePath = new File(Environment.getExternalStorageDirectory() + "/" +now+ ".png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Turn ON BlueTooth if it is OFF
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);


            Log.w("for loop", "If not Enabled");
        }
        else
        {
            setup();
        }
    }

   /* @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(),DbAct.class);
        startActivity(i);
        setContentView(R.layout.activity_db);
    }*/


    private void SendSms() {
        String phoneNo = SetContact.numToFt;
        String sms = SetContact.msgToFt;

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, sms, null, null);
//            Toast.makeText(getApplicationContext(), "SMS Sent!",
//                    Toast.LENGTH_LONG).show();

        } catch (Exception e) {
//            Toast.makeText(getApplicationContext(),
//                    "SMS failed, please try again later!",
//                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    private void test() {
        Log.d("test", "test: first component ");
        megaCheck = true;
        algoTime();
    }


    private void test2() {
        Log.d("test", "second component");
        megaCheck = true;
        algoTime();
    }

    private void algoTime () {
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        // your code here
                        megaCheck = false;
                        Log.d("test", "algoTime");
                    }
                },
                10 * 1000
        );

    }

    private void facee () {
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                FaceTrackerActivity.this);
        alertDialog2.setTitle("Error!");
        alertDialog2.setMessage("More than one face detected");

        alertDialog2.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog

                    }
                });

        alertDialog2.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog

                        dialog.cancel();
                    }
                });


        alertDialog2.show();

    }


    private void call() {

        Intent in=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+SetContact.numToFt));
        try{
            startActivity(in);
            AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
            // audioManager.setMode(AudioManager.MODE_IN_CALL)
            if (!audioManager.isSpeakerphoneOn()){
                audioManager.setSpeakerphoneOn(true);
            }


        }

        catch (android.content.ActivityNotFoundException ex){
            Toast.makeText(getApplicationContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
        }
    }

/////////////////////////////



    private void SendToBluetooth(int [] a) {

        String toSend = null;
        Log.d("20May", "SendToBt");

        Log.d("20May", " "+a[0]);
        Log.d("20May", " "+a[1]);


        if (a[0] == 2) { //SCB
            if (a[1] == 4) {
                //sms
                SendSms();

//                destroyAl();
            }
            if (a[1] == 2) {
                toSend = "h";
                sending(toSend);
//                destroyAl();
            }
            if (a[1] == 3) {
                //call
                call();
//                destroyAl();
            }
        }
        else if (a[0] == 3) { //TV
            if (a[1] == 1) {
                toSend = "6";
                sending(toSend);
//                destroyAl();
            }
            if (a[1] == 2) {
                toSend = "f";
                sending(toSend);
//                destroyAl();
            }
            if (a[1] == 3) {
                toSend = "7";
                sending(toSend);
//                destroyAl();
            }
            if (a[1] == 4) {
                toSend = "g";
                sending(toSend);
//                destroyAl();
            }
            if (a[1] == 6) {
                toSend = "e"; //on/off
                sending(toSend);
//                destroyAl();
            }
        }
        else if (a[0] == 4) { //light/fan
            if (a[1] == 1) {
                toSend = "a";
                sending(toSend);
//                destroyAl();
            }
            if (a[1] == 2) {
                toSend = "b";
                sending(toSend);
//                destroyAl();
            }
            if (a[1] == 3) {
                toSend = "c";
                sending(toSend);
//                destroyAl();
            }
            if (a[1] == 4) {
                toSend = "d";
                sending(toSend);
//                destroyAl();
            }
        }
        else {
            toSend = "0";
        }


        if (toSend == null) {
            Log.d("20May", "null string");
        }
    }

    private void sending (String toSend) {
        Log.d("20May", "conversion to bytes");
        Log.w("for loop", "Replacement of SEND BUTTON");
        if (myThreadConnected != null) {
            Log.w("for loop", "Duplicate myThreadConnected!=null");
            //byte[] bytesToSend = inputField.getText().toString().getBytes();
            byte[] bytesToSend = toSend.getBytes();
            myThreadConnected.write(bytesToSend);
        }
    }

    private void setup() {
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            pairedDeviceArrayList = new ArrayList<BluetoothDevice>();
            for (BluetoothDevice device : pairedDevices)
            {
                pairedDeviceArrayList.add(device);
            }
            for(int i = 0; i<=pairedDeviceArrayList.size()-1; i++) {
                BluetoothDevice Device = pairedDeviceArrayList.get(i);
                if (Device.getName().contentEquals("HC-05")) {
                    myThreadConnectBTdevice = new ThreadConnectBTdevice(pairedDeviceArrayList.get(i));
                    myThreadConnectBTdevice.start();
                }
            }
        }

    }



    public void cancel() {

        Toast.makeText(getApplicationContext(),
                "close bluetoothSocket",
                Toast.LENGTH_LONG).show();

        try {
            myThreadConnectBTdevice.bluetoothSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_ENABLE_BT){
            if(resultCode == Activity.RESULT_OK){
                setup();
            }else{
                Toast.makeText(this,
                        "BlueTooth NOT enabled",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void startThreadConnected(BluetoothSocket socket){
        myThreadConnected = new ThreadConnected(socket);
        Log.w("for loop", "new ThreadConnected(socket)");
        myThreadConnected.start();
        Log.w("for loop", "myThreadConnected.start()");
    }





    //============================================
//HERE bt
//=================
    private class ThreadConnectBTdevice extends Thread {

        private BluetoothSocket bluetoothSocket = null;
        private final BluetoothDevice bluetoothDevice;


        private ThreadConnectBTdevice(BluetoothDevice device) {
            bluetoothDevice = device;

            try {
                bluetoothSocket = device.createRfcommSocketToServiceRecord(myUUID);
                //textStatus.setText("bluetoothSocket: \n" + bluetoothSocket);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        public void run() {

            boolean success = false;
            try {
                bluetoothSocket.connect();
                success = true;
            } catch (IOException e) {
                e.printStackTrace();

                try {
                    bluetoothSocket.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }

            if(success){
                //connect successful
                startThreadConnected(bluetoothSocket);
//            SendToBluetooth();
            }else{
                //fail
            }
        }



    }

    private class ThreadConnected extends Thread {
        private final BluetoothSocket connectedBluetoothSocket;
        private final OutputStream connectedOutputStream;

        public ThreadConnected(BluetoothSocket socket) {
            connectedBluetoothSocket = socket;
            OutputStream out = null;
            try {
                out = socket.getOutputStream();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            connectedOutputStream = out;
        }

        public void write(byte[] buffer) {
            try {
                connectedOutputStream.write(buffer);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }




//================================




//    @Override
//    protected void onStart() {
//        super.onStart();
//
//
////        new java.util.Timer().schedule(
////                new java.util.TimerTask() {
////                    @Override
////                    public void run() {
////                        // your code here
////                        int ma = Tweak.ivar1;
////
////                        if (ma == 1) {
////                            //sendData("b");
////                            Log.d("SmileSMS", "chal gaya");
////                            SendSms();
////                            //setup();
////                            //qwerty();
////                        } else {
////                            Log.w("PLIS", "nhi chala");
////                        }
////
////                    }
////                },
////                5000
////        );
//
//
//    }
    /**
     * Handles the requesting of the camera permission.  This includes
     * showing a "Snackbar" message of why the permission is needed then
     * sending the request.
     */
    private void requestCameraPermission() {
        Log.w(TAG, "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM);
            return;
        }

        final Activity thisActivity = this;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(thisActivity, permissions,
                        RC_HANDLE_CAMERA_PERM);
            }
        };

        Snackbar.make(mGraphicOverlay, R.string.permission_camera_rationale,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.ok, listener)
                .show();
    }

    /**
     * Creates and starts the camera.  Note that this uses a higher resolution in comparison
     * to other detection examples to enable the barcode detector to detect small barcodes
     * at long distances.
     */
    private void createCameraSource() {

        Context context = getApplicationContext();
        FaceDetector detector = new FaceDetector.Builder(context)
                .setClassificationType(FaceDetector.ACCURATE_MODE) //sandhyaIsGenius
                .setProminentFaceOnly(true)
                .build();


        detector.setProcessor(
                new MultiProcessor.Builder<>(new GraphicFaceTrackerFactory())
                        .build());

        if (!detector.isOperational()) {
            // Note: The first time that an app using face API is installed on a device, GMS will
            // download a native library to the device in order to do detection.  Usually this
            // completes before the app is run for the first time.  But if that download has not yet
            // completed, then the above call will not detect any faces.
            //
            // isOperational() can be used to check if the required native library is currently
            // available.  The detector will automatically become operational once the library
            // download completes on device.
            Log.w(TAG, "Face detector dependencies are not yet available.");
        }
        if (!MenuList.camm) {
            mCameraSource = new CameraSource.Builder(context, detector)
                    .setRequestedPreviewSize(640, 480)
                    .setFacing(CameraSource.CAMERA_FACING_FRONT) //front and back cam view
                    .setRequestedFps(30.0f)
                    .build();
        }
        else {
            mCameraSource = new CameraSource.Builder(context, detector)
                    .setRequestedPreviewSize(640, 480)
                    .setFacing(CameraSource.CAMERA_FACING_BACK) //front and back cam view
                    .setRequestedFps(30.0f)
                    .build();
        }
    }

    /**
     * Restarts the camera.
     */
    @Override
    protected void onResume() {
        super.onResume();

        startCameraSource();
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        mPreview.stop();
    }

    /**
     * Releases the resources associated with the camera source, the associated detector, and the
     * rest of the processing pipeline.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCameraSource != null) {
            mCameraSource.release();
        }

        if(myThreadConnectBTdevice!=null){
            cancel();
        }
    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on {@link #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link PackageManager#PERMISSION_GRANTED}
     *                     or {@link PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            Log.d(TAG, "Got unexpected permission result: " + requestCode);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Camera permission granted - initialize the camera source");
            // we have permission, so create the camerasource
            createCameraSource();
            return;
        }

        Log.e(TAG, "Permission not granted: results len = " + grantResults.length +
                " Result code = " + (grantResults.length > 0 ? grantResults[0] : "(empty)"));

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Face Tracker sample")
                .setMessage(R.string.no_camera_permission)
                .setPositiveButton(R.string.ok, listener)
                .show();
    }

    //==============================================================================================
    // Camera Source Preview
    //==============================================================================================

    /**
     * Starts or restarts the camera source, if it exists.  If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private void startCameraSource() {

        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getApplicationContext());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS);
            dlg.show();
        }

        if (mCameraSource != null) {
            try {
                mPreview.start(mCameraSource, mGraphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }

    //==============================================================================================
    // Graphic Face Tracker
    //==============================================================================================

    /**
     * Factory for creating a face tracker to be associated with a new face.  The multiprocessor
     * uses this factory to create face trackers as needed -- one for each individual.
     */
    private class GraphicFaceTrackerFactory implements MultiProcessor.Factory<Face> {
        @Override
        public Tracker<Face> create(Face face) {
            return new GraphicFaceTracker(mGraphicOverlay);
        }
    }

    /**
     * Face tracker for each detected individual. This maintains a face graphic within the app's
     * associated face overlay.
     */
    private class GraphicFaceTracker extends Tracker<Face> {
        private GraphicOverlay mOverlay;
        private FaceGraphic mFaceGraphic;

        GraphicFaceTracker(GraphicOverlay overlay) {
            mOverlay = overlay;
            mFaceGraphic = new FaceGraphic(overlay);
        }

        /**
         * Start tracking the detected face instance within the face overlay.
         */
        @Override
        public void onNewItem(int faceId, Face item) {
            mFaceGraphic.setId(faceId);
            faceCounter += 1;
            if (faceCounter > 1) {
                //facee();
            }
            String c = String.format("%d",faceCounter);
            Log.d("test", c);


//without timer
//            int ma = Tweak.ivar1;
//
//                        if (ma == 1) {
//                            //sendData("b");
//                            Log.d("SmileSMS", "chal gaya");
//                            //SendSms();
//                            test();
//                            //setup();
//                            //qwerty();
//                        }
//                        else if (ma == 2) {
//                            Log.d("SmileSMS", "calling");
//                            //call();
//                            test2();
//                        }
//                        else {
//                            Log.w("PLIS", "nhi chala");
//                        }






        }

        /**
         * Update the position/characteristics of the face within the overlay.
         */
        @Override
        public void onUpdate(FaceDetector.Detections<Face> detectionResults, Face face) {
            //THIS SHOULD WORK!!!!
            //DO THIS AND THEN IF NOT THEN TRY REMOVING UPDATEFACE AND WORK WITH IT NORMALLY!
            mOverlay.add(mFaceGraphic);
            mFaceGraphic.updateFace(face);
//            final Face inFace = face;
            if (!Chooser.selfCalib) {
                if (activate) {
//                mFaceGraphic.updateFace(face); //chances are that it might start calling itself over and over again
                }

                if (firstFrame) { //if its a first activating gesture frame
//                mFaceGraphic.updateFace(face);
                    new java.util.Timer().schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    // your code here
                                    final int ma = Tweak.ivar1; // obtaining the gesture value from FaceGraphic
//                            mFaceGraphic.updateFace(face);
                                    if (ma == 6 && !megaCheck) { // if the activating gesture is received so TRUE
                                        Log.d("20May", "First frame");
                                        mp.start();
                                        activate = false;
                                        firstFrame = false;
                                        secondFrame = true;
//                                    SendToBluetooth(ma);
                                        megaCheck = true;
//                                    mFaceGraphic.updateFace(inFace);
                                        Log.d("28April", "First frame");
                                    } else {

                                    }
                                }
                            },
                            3000
                    );

                }
                if (secondFrame) { //recieving the next frame soon after the activating gesture
//                    firstFrame = true;

//                mFaceGraphic.updateFace(face);
//                megaCheck = false;
                    new java.util.Timer().schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    final int ma = Tweak.ivar1; // obtaining a new gesture value
                                    if ((ma == 2 || ma == 3 || ma == 4) && !megaCheck1) {
//                                    activate = true;
                                        mp.start();
                                        Log.d("28April", "active Now");
                                        Log.d("20May", "Second frame");
                                        thirdFrame = true;
                                        secondFrame = false;
                                        megaCheck1 = true;
                                        megaCheck2 = false;
//                                        inputGest.add(ma);
//                                        SendToBluetooth(ma); //sending the gesture to BT signal
                                        inGest[0] = ma;
                                    } else {

                                    }
                                }
                            },
                            5000
                    );


                }
                if (thirdFrame) {
                    new java.util.Timer().schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    final int ma = Tweak.ivar1; // obtaining a new gesture value
                                    if ((ma != 0 && ma != 5 && ma != 7 && ma != 8) && !megaCheck2) {
//                                    activate = true;
                                        mp.start();
                                        Log.d("28April", "active Now");
                                        Log.d("20May", "Third frame");

                                        megaCheck2 = true;
//                                        SendToBluetooth(ma); //sending the gesture to BT signal
                                        megaCheck = false;
                                        megaCheck1 = false;
                                        thirdFrame = false;
                                        firstFrame = true;
                                        inGest[1] = ma;
                                        SendToBluetooth(inGest);

                                    } else {

                                    }
                                }
                            },
                            5000
                    );

                }

            }//selfCalib IF
            else { //selfCalib ELSE
                Log.d("1May", "inside self calib FT");
                if (DbAct.nowValRet) {
                    if (activate) {
//                mFaceGraphic.updateFace(face); //chances are that it might start calling itself over and over again
                    }

                    if (firstFrameSelf) { //if its a first activating gesture frame
//                mFaceGraphic.updateFace(face);
                        new java.util.Timer().schedule(
                                new java.util.TimerTask() {
                                    @Override
                                    public void run() {
                                        // your code here
                                        final int ma = Tweak.ivar2; // obtaining the gesture value from FaceGraphic
//                            mFaceGraphic.updateFace(face);
                                        if (ma == 6 && !megaCheckSelf) { // if the activating gesture is received so TRUE
                                            Log.d("20May", "First frame");
                                            mp.start();
                                            activate = false;
                                            firstFrameSelf = false;
                                            secondFrameSelf = true;
//                                    SendToBluetooth(ma);
                                            megaCheckSelf = true;
//                                    mFaceGraphic.updateFace(inFace);
                                            Log.d("28April", "First frame");
                                        } else {

                                        }
                                    }
                                },
                                3000
                        );

                    }
                    if (secondFrameSelf) { //recieving the next frame soon after the activating gesture
//                    firstFrame = true;

//                mFaceGraphic.updateFace(face);
//                megaCheck = false;
                        new java.util.Timer().schedule(
                                new java.util.TimerTask() {
                                    @Override
                                    public void run() {
                                        final int ma = Tweak.ivar2; // obtaining a new gesture value
                                        if ((ma == 2 || ma == 3 || ma == 4) && !megaCheck1Self) {
//                                    activate = true;
                                            mp.start();
                                            Log.d("28April", "active Now");
                                            Log.d("20May", "Second frame");
                                            thirdFrameSelf = true;
                                            secondFrameSelf = false;
                                            megaCheck1Self = true;
                                            megaCheck2Self = false;
//                                        inputGest.add(ma);
//                                        SendToBluetooth(ma); //sending the gesture to BT signal
                                            inGest[0] = ma;
                                        } else {

                                        }
                                    }
                                },
                                5000
                        );


                    }
                    if (thirdFrameSelf) {
                        new java.util.Timer().schedule(
                                new java.util.TimerTask() {
                                    @Override
                                    public void run() {
                                        final int ma = Tweak.ivar2; // obtaining a new gesture value
                                        if ((ma != 0 && ma != 5 && ma != 7 && ma != 8) && !megaCheck2Self) {
//                                    activate = true;
                                            mp.start();
                                            Log.d("28April", "active Now");
                                            Log.d("20May", "Third frame");

                                            megaCheck2Self = true;
//                                        SendToBluetooth(ma); //sending the gesture to BT signal
                                            megaCheckSelf = false;
                                            megaCheck1Self = false;
                                            thirdFrameSelf = false;
                                            firstFrameSelf = true;
                                            inGest[1] = ma;
                                            SendToBluetooth(inGest);

                                        } else {

                                        }
                                    }
                                },
                                5000
                        );

                    }
                }


            } //selfCalib ELSE
        }

        /**
         * Hide the graphic when the corresponding face was not detected.  This can happen for
         * intermediate frames temporarily (e.g., if the face was momentarily blocked from
         * view).
         */
        @Override
        public void onMissing(FaceDetector.Detections<Face> detectionResults) {
            mOverlay.remove(mFaceGraphic);
        }

        /**
         * Called when the face is assumed to be gone for good. Remove the graphic annotation from
         * the overlay.
         */
        @Override
        public void onDone() {
            mOverlay.remove(mFaceGraphic);
        }
    }
}
