package com.example.peticionesderedconhttpsurlconnection;

import androidx.fragment.app.FragmentActivity;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;

public class MainActivity  extends FragmentActivity implements DownloadCallback {


    // Keep a reference to the NetworkFragment, which owns the AsyncTask object
    // that is used to execute network ops.
    private NetworkFragment networkFragment;

    // Boolean telling us whether a download is in progress, so we don't trigger overlapping
    // downloads with consecutive button clicks.
    private boolean downloading = false;


    Button btnPrueba1 ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       networkFragment= NetworkFragment.getInstance(getSupportFragmentManager(), "https://www.google.com");

        InputStream inputStream = null;
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        ImageView imageView = (ImageView) findViewById(R.id.image_view);
        imageView.setImageBitmap(bitmap);
          btnPrueba1=  findViewById(R.id.btnPrueba);



    }

    private void startDownload() {
        if (!downloading && networkFragment != null) {
            // Execute the async download.
            networkFragment.startDownload();
            downloading = true;
        }
    }

    @Override
    public void updateFromDownload(Object result) {
        // Update your UI here based on result of download.
        Toast.makeText(this , result.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }

    @Override
    public void onProgressUpdate(int progressCode, int percentComplete) {
        switch(progressCode) {
            // You can add UI behavior for progress updates here.
            case Progress.ERROR:

                break;
            case Progress.CONNECT_SUCCESS:

                break;
            case Progress.GET_INPUT_STREAM_SUCCESS:

                break;
            case Progress.PROCESS_INPUT_STREAM_IN_PROGRESS:

                break;
            case Progress.PROCESS_INPUT_STREAM_SUCCESS:

                break;
        }


    }

    @Override
    public void finishDownloading() {
        downloading = false;
        if (networkFragment != null) {
            networkFragment.cancelDownload();
        }
    }

    public void btnIniciar(View view) {
        startDownload();
    }
}
