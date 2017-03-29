package com.freelance.jptalusan.caretaker.http;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Iterator;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by JPTalusan on 30/03/2017.
 */

public class SendPostRequest extends AsyncTask<String, Void, String> {
    private Context mContext;
    private OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public SendPostRequest(Context context) {
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        //Working too
//        try {
//            URL url = new URL(Config.URL);
//            JSONObject postDataParams = new JSONObject();
//            postDataParams.put("name", "abc");
//            postDataParams.put("email", "abc@gmail.com");
//            Log.e("params",postDataParams.toString());
//
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setReadTimeout(15000 /* milliseconds */);
//            conn.setConnectTimeout(15000 /* milliseconds */);
//            conn.setRequestMethod("POST");
//            conn.setDoInput(true);
//            conn.setDoOutput(true);
//
//            OutputStream os = conn.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(
//                    new OutputStreamWriter(os, "UTF-8"));
//            writer.write(getPostDataString(postDataParams));
//
//            writer.flush();
//            writer.close();
//            os.close();
//
//            int responseCode=conn.getResponseCode();
//
//            if (responseCode == HttpsURLConnection.HTTP_OK) {
//                System.out.println("Success");
//                BufferedReader in=new BufferedReader(
//                        new InputStreamReader(
//                                conn.getInputStream()));
//                StringBuffer sb = new StringBuffer("");
//                String line="";
//
//                while((line = in.readLine()) != null) {
//
//                    sb.append(line);
//                    break;
//                }
//
//                in.close();
//                return sb.toString();
//
//            }
//            else {
//                return new String("false : "+responseCode);
//            }
//        } catch (Exception e) {
//            return new String("Exception: " + e.getMessage());
//        }

        //Working, no json sent
//        try {
//            URL url = new URL("http://ip.jsontest.com/");
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            try {
//                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//                System.out.print(convertStreamToString(in));
//            } finally {
//                urlConnection.disconnect();
//            }
//        } catch (Exception e) {
//            return new String("Exception: " + e.getMessage());
//        }
//        return null;

        //Trying a library (not working)

        try {
            String json = jsonString();
            System.out.println(json);
            //Not working
//            String result = post(Config.URL, json);
            String result = post("http://www.roundsapp.com/post", bowlingJson("JP", "Elaine"));
            System.out.println(result);

            //This is working
            System.out.println(run("http://ip.jsontest.com"));
            return result;
        } catch (Exception e) {
            return "";
        }

    }

    String bowlingJson(String player1, String player2) {
        return "{'winCondition':'HIGH_SCORE',"
                + "'name':'Bowling',"
                + "'round':4,"
                + "'lastSaved':1367702411696,"
                + "'dateStarted':1367702378785,"
                + "'players':["
                + "{'name':'" + player1 + "','history':[10,8,6,7,8],'color':-13388315,'total':39},"
                + "{'name':'" + player2 + "','history':[6,10,5,10,10],'color':-48060,'total':41}"
                + "]}";
    }


    String jsonString() {
        return "{'name':'abc',"
                + "'email':'abc@gmail.com'}";
    }

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(mContext, s, Toast.LENGTH_LONG).show();
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        System.out.println(result.toString());
        return result.toString();
    }
}
