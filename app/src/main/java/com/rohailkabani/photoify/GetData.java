package com.rohailkabani.photoify;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

enum DOWNLOAD_STATUS {IDLE, PROCESSING, NOT_INITIALIZED, FAILED_OR_EMPTY, OK};


class GetData extends AsyncTask<String, Void, String> {
    private static final String TAG = "GetData";

    private DOWNLOAD_STATUS downloadStatus;
    private final OnDownloadComplete callback;

    //make sure whatever class implements this, must have a onDownloadComplete method
    interface OnDownloadComplete {
        void OnDownloadComplete(String data, DOWNLOAD_STATUS status);
    }

    public GetData(OnDownloadComplete callback) {
        this.downloadStatus = DOWNLOAD_STATUS.IDLE;
        this.callback = callback;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "onPostExecute: " + s);
        if (this.callback != null) {
            callback.OnDownloadComplete(s, downloadStatus);
        }
        Log.d(TAG, "onPostExecute: ends.");
//        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        if (strings == null) {
            downloadStatus = DOWNLOAD_STATUS.NOT_INITIALIZED;
        }

        try {
            downloadStatus = DOWNLOAD_STATUS.PROCESSING;
            URL url = new URL(strings[0]);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();
            Log.d(TAG, "doInBackground: Response Code - " + responseCode);

            StringBuilder result = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

//            String line;
//            while ((line = reader.readLine()) != null) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                result.append(line).append("\n");
            }

            downloadStatus = DOWNLOAD_STATUS.OK;

            return result.toString();

        } catch (MalformedURLException e) {
            Log.e(TAG, "doInBackground: Invalid URL. " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "doInBackground: IOException. " + e.getMessage());
        } catch (SecurityException e) {
            Log.e(TAG, "doInBackground: Security Exception. " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "doInBackground: Error closing stream. " + e.getMessage() );
                }
            }
        }

        downloadStatus = DOWNLOAD_STATUS.FAILED_OR_EMPTY;
        return null;
    }
}
