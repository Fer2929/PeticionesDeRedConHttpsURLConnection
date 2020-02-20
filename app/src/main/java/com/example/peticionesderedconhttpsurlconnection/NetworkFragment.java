package com.example.peticionesderedconhttpsurlconnection;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class NetworkFragment extends Fragment {


    public static final String TAG = "NetworkFragment";

    private static final String URL_KEY = "UrlKey";

    private DownloadCallback<String> callback;
    private DownloadTask downloadTask;
    private String urlString;

    public static NetworkFragment  getInstance(FragmentManager fragmentManager, String url) {

        // Recover NetworkFragment in case we are re-creating the Activity due to a config change.
        // This is necessary because NetworkFragment might have a task that began running before
        // the config change occurred and has not finished yet.
        // The NetworkFragment is recoverable because it calls setRetainInstance(true).
        NetworkFragment networkFragment = (NetworkFragment) fragmentManager
                .findFragmentByTag(NetworkFragment.TAG);
        if (networkFragment == null) {
            networkFragment = new NetworkFragment();
            Bundle args = new Bundle();
            args.putString(URL_KEY, url);
            networkFragment.setArguments(args);
            fragmentManager.beginTransaction().add(networkFragment, TAG).commit();
        }
        return networkFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        urlString = getArguments().getString(URL_KEY);
        setRetainInstance(true);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Host Activity will handle callbacks from task.
        callback = (DownloadCallback<String>) context;
    }

    public void startDownload() {
        cancelDownload();
        downloadTask = new DownloadTask(callback);
        downloadTask.execute(urlString);
    }

    /**
     * Cancel (and interrupt if necessary) any ongoing DownloadTask execution.
     */
    public void cancelDownload() {
        if (downloadTask != null) {
            downloadTask.cancel(true);
        }
    }
}
