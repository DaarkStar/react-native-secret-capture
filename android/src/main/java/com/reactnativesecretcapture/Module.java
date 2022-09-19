package com.reactnativesecretcapture;

import static com.facebook.react.bridge.UiThreadUtil.runOnUiThread;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.reactnativesecretcapture.listeners.PictureCapturingListener;
import com.reactnativesecretcapture.services.APictureCapturingService;
import com.reactnativesecretcapture.services.PictureCapturingServiceImpl;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.TreeMap;

public class Module extends ReactContextBaseJavaModule implements ActivityEventListener,
        PictureCapturingListener {

    private APictureCapturingService pictureService;

    Context context;

    public Module(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "SecretCapture";
    }

    private void showToast(final String text) {
        runOnUiThread(() ->
                Toast.makeText(getReactApplicationContext(), text, Toast.LENGTH_SHORT).show()
        );
    }

    @ReactMethod
    public void captureImage( Callback callBack) {

        pictureService = PictureCapturingServiceImpl.getInstance(getReactApplicationContext()
                .getCurrentActivity(), encodedImage -> {
           this.encodedImage = encodedImage;
            Log.d("YYYYYY", "getBase64Image: "+this.encodedImage);
            getReactApplicationContext().runOnUiQueueThread(new Runnable() {
                @Override
                public void run() {
                    callBack.invoke(encodedImage);
                }
            });
        });
        showToast("Starting capture!");
        Log.d("Aditya", "Starting capture!");
        pictureService.startCapturing(this);
    }

    private String encodedImage;

    @ReactMethod
    public void getBase64Image(Promise promise) {
        Log.d("XXXXXX", "getBase64Image: "+this.encodedImage);
        try {
            promise.resolve(this.encodedImage);
        } catch (Exception e) {
            promise.reject("Error",e);
        }
    }

    @Override
    public void onActivityResult(Activity activity, int i, int i1, @Nullable Intent intent) {

    }

    @Override
    public void onNewIntent(Intent intent) {

    }

    @Override
    public void onCaptureDone(String pictureUrl, byte[] pictureData) {
        if (pictureData != null && pictureUrl != null) {
            runOnUiThread(() -> {
                final Bitmap bitmap = BitmapFactory.decodeByteArray(pictureData, 0, pictureData.length);
                final int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
                final Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                if (pictureUrl.contains("0_pic.jpg")) {
//          uploadBackPhoto.setImageBitmap(scaled);
                } else if (pictureUrl.contains("1_pic.jpg")) {
//          uploadFrontPhoto.setImageBitmap(scaled);
                }
            });
            //showToast("Picture saved to " + pictureUrl);
            Log.d("Picture saved to " , pictureUrl);
        }
    }

    @Override
    public void onDoneCapturingAllPhotos(TreeMap<String, byte[]> picturesTaken) {
        if (picturesTaken != null && !picturesTaken.isEmpty()) {
            //showToast("Done capturing all photos!");
            return;
        }
        Log.v("Picture",picturesTaken.toString());
        // showToast("No camera detected!");
    }
}
