package com.cookandroid.bus;

import android.content.ContentValues;
import android.os.AsyncTask;

class RouteTask extends AsyncTask<Void, Void, String>{
    private String url;
    private ContentValues values;

    public RouteTask(String url, ContentValues values){
        this.url = url;
        this.values = values;
    }

    @Override
    protected String doInBackground(Void... params) {
        String result; //요청결과 저장변수
        RequestHttpUrlConnection requestHttpUrlConnection = new RequestHttpUrlConnection();
        result = requestHttpUrlConnection.request(url,values);

        return result; //onPostExecute로 보냄
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}