package com.imperial.project.roadtrip.workers;

import com.loopj.android.http.*;

public class TrippinHttpClient {
    public static final String BASE_URL = "http://146.169.47.204:5000/";
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String URLext, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteURL(URLext), params, responseHandler);
    }

    public static void post(String URLext, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteURL(URLext), params, responseHandler);
    }

    public static void delete(String URLext, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.delete(getAbsoluteURL(URLext), params, responseHandler);
    }

    public static void put(String URLext, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.put(getAbsoluteURL(URLext), params, responseHandler);
    }

    private static String getAbsoluteURL(String URLext) {
        return BASE_URL + URLext;
    }
}