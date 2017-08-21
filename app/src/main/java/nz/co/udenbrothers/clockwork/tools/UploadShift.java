package nz.co.udenbrothers.clockwork.tools;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import nz.co.udenbrothers.clockwork.models.Shift;
import nz.co.udenbrothers.clockwork.serverObjects.Response;


public class UploadShift extends AsyncTask<String,String,Response> {

    private Context context;
    private Gson gson;
    private Pref pref;
    private ArrayList<Shift> shifts;

    public UploadShift(Context context){
        this.context = context;
        gson = new Gson();
        pref = new  Pref(context);
    }

    public void upload(Shift shift){
        shifts = new ArrayList<>();
        shifts.add(shift);
    }

    public void upload(ArrayList<Shift> shiftys){
        shifts = shiftys;
    }

    @Override
    protected void onPostExecute(Response response) {
        if(response.statusCode == 204){
            for (Shift shift: shifts){
                shift.uploaded = 1;
                shift.save(context);
            }
        }
        else {
            Kit.show(context,"shifts falied to upload");
        }
    }

    private Response myHttpConnection(String method, String content, String url, String aus){
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

    @Override
    protected Response doInBackground(String... params) {
        return myHttpConnection("POST", gson.toJson(shifts), "https://clockwork-api.azurewebsites.net/v1/projects/shifts/save", pref.getStr("token"));
    }
}
