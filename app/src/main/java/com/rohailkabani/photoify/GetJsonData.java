package com.rohailkabani.photoify;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rohailkabani on 2018-02-04.
 */

class GetJsonData implements GetData.OnDownloadComplete {
    private static final String TAG = "GetJsonData";

    private List<Photo> photoList = null;
    private String baseURL;
    private String language;
    private boolean matchAll;

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
        String destinationUri = createUri(searchCriteria, language, matchAll);
        GetData getData = new GetData(this);
        getData.execute(destinationUri);
        Log.d(TAG, "executeOnSameThread: Ends.");
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
                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = jsonObject.getJSONArray("items");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonPhoto = jsonArray.getJSONObject(i);
                    String jsonTitle = jsonPhoto.getString("title");
                    String jsonAuthor = jsonPhoto.getString("author");
                    String jsonAuthorID = jsonPhoto.getString("author_id");
                    


                }
                
            }
        }
    }
}
