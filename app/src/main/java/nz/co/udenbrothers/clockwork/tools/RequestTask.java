package nz.co.udenbrothers.clockwork.tools;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import nz.co.udenbrothers.clockwork.abstractions.AsynCallback;
import nz.co.udenbrothers.clockwork.serverObjects.Response;

public class RequestTask extends AsyncTask<String,String,Response>
{
    private AsynCallback asynCallback;
    private String uploadString;
    private String method;
    private String aus;
    private ProgressDialog progressDialog;
    private boolean block = true;

    public RequestTask(Context con, String meth, String content, String au, boolean block) {
        uploadString = content;
        method = meth;
        asynCallback = (AsynCallback) con;
        aus = au;
        progressDialog = new ProgressDialog(con);
        this.block = block;
    }


    public RequestTask(Context con, String meth, String content, String au) {
        uploadString = content;
        method = meth;
        asynCallback = (AsynCallback) con;
        aus = au;
        progressDialog = new ProgressDialog(con);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(block){
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }

    }

    @Override
    protected Response doInBackground(String... params) {
        return myHttpConnection(method, uploadString, params[0], aus);
    }

    @Override
    protected void onPostExecute(Response response) {

        if(block){
            progressDialog.dismiss();
        }

        asynCallback.postCallback(response);
    }

    public static Response myHttpConnection(String method, String content, String url, String aus){
        HttpURLConnection urlConnection = null;
        String result = "N/A";
        int statusCode = 900;
        try {
            urlConnection = (HttpURLConnection) ((new URL(url).openConnection()));
            if(content != null) urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            if(aus != null){
                urlConnection.setRequestProperty("Authorization", "Basic " + aus);
            }
            urlConnection.setRequestMethod(method);
            urlConnection.setConnectTimeout(10000);
            urlConnection.connect();
            if(content != null){
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                writer.write(content);
                writer.close();
            }

            try {
                statusCode = urlConnection.getResponseCode();
            } catch (IOException e) {
                statusCode = urlConnection.getResponseCode();
            }

            if (statusCode < 400 ) {
                InputStream is = urlConnection.getInputStream();
                if(is != null){
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    String line;
                    StringBuilder sb = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }
                    bufferedReader.close();
                    result = sb.toString();
                }
            }
        } catch (Exception e) {
            result = "Problem with connection or server. Try again later";
        }
        finally {
            if (urlConnection != null) urlConnection.disconnect();
        }
        return new Response(result, statusCode, url);
    }
}
