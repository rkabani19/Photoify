package com.rohailkabani.photoify;

import android.util.Log;

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
    }
}
