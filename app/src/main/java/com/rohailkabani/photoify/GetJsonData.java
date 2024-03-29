package com.rohailkabani.photoify;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class GetJsonData extends AsyncTask<String, Void, List<Photo>> implements GetData.OnDownloadComplete {
    private static final String TAG = "GetJsonData";

    private List<Photo> photoList = null;
    private String baseURL;
    private String language;
    private boolean matchAll;
    private boolean runningOnSameThread = false;

    private final OnDataAvailable callback;

    interface OnDataAvailable {
        void onDataAvailable (List<Photo> data, DOWNLOAD_STATUS status);
    }

    public GetJsonData(OnDataAvailable callback, String baseURL, String language, boolean matchAll) {
        Log.d(TAG, "GetJsonData: constructor initalized.");
        this.baseURL = baseURL;
        this.language = language;
        this.matchAll = matchAll;
        this.callback = callback;
    }

    void executeOnSameThread (String searchCriteria) {
        Log.d(TAG, "executeOnSameThread: Start.");
        runningOnSameThread = true;
        String destinationUri = createUri(searchCriteria, language, matchAll);
        GetData getData = new GetData(this);
        getData.execute(destinationUri);
        Log.d(TAG, "executeOnSameThread: Ends.");
    }

    @Override
    protected void onPostExecute(List<Photo> photos) {
        Log.d(TAG, "onPostExecute: Starts.");

        if (callback != null) {
            callback.onDataAvailable(photoList, DOWNLOAD_STATUS.OK);
        }

        Log.d(TAG, "onPostExecute: Ends.");
    }

    @Override
    protected List<Photo> doInBackground(String... strings) {
        Log.d(TAG, "doInBackground: Starts.");
        String destinationUri = createUri(strings[0], language, matchAll);

        GetData getData = new GetData(this);
        getData.runInSameThread(destinationUri);
        Log.d(TAG, "doInBackground: Ends.");

        return photoList;
    }

    private String createUri (String searchCriteria, String lang, boolean matchAll) {
        Log.d(TAG, "createUri: Starts.");
        return Uri.parse(baseURL).buildUpon()
                .appendQueryParameter("tags", searchCriteria)
                .appendQueryParameter("tagmode", matchAll ? "ALL" : "ANY")
                .appendQueryParameter("lang", lang)
                .appendQueryParameter("format", "json")
                .appendQueryParameter("nojsoncallback", "1")
                .build().toString();
    }

    @Override
    public void OnDownloadComplete(String data, DOWNLOAD_STATUS status) {
        Log.d(TAG, "OnDownloadComplete: Starting.");

        if (status == DOWNLOAD_STATUS.OK) {
            photoList = new ArrayList<>();

            try {
                JSONObject jsonObject = new JSONObject(data);
                JSONArray jsonArray = jsonObject.getJSONArray("items");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonPhoto = jsonArray.getJSONObject(i);
                    String jsonTitle = jsonPhoto.getString("title");
                    String jsonAuthor = jsonPhoto.getString("author");
                    String jsonAuthorID = jsonPhoto.getString("author_id");
                    String jsonTags = jsonPhoto.getString("tags");

                    JSONObject jsonMedia = jsonPhoto.getJSONObject("media");
                    String jsonPhotoURL = jsonMedia.getString("m");
                    String link = jsonPhotoURL.replaceFirst("_m.", "_b.");

                    Photo photoObject = new Photo(jsonTitle, jsonAuthor, jsonAuthorID, link, jsonTags, jsonPhotoURL);
                    photoList.add(photoObject);

                    Log.d(TAG, "OnDownloadComplete " + photoObject.toString());
                }
            } catch (JSONException e) {
                Log.e(TAG, "OnDownloadComplete: Error processing JSON data. " + e.getMessage());
                status = DOWNLOAD_STATUS.FAILED_OR_EMPTY;
            }
        }

        if (runningOnSameThread && callback != null) {
            //now inform the caller that processing is done (possibly returning null if there was an error)
            callback.onDataAvailable(photoList, status);
        }

        Log.d(TAG, "OnDownloadComplete: Finished.");
    }
}
