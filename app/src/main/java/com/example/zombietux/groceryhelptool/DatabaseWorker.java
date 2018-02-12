package com.example.zombietux.groceryhelptool;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by gggab(Zombietux) on 2018-02-12.
 */

public class DatabaseWorker extends AsyncTask {
    private Context mContext;
    private AlertDialog alertDialog;

    public DatabaseWorker(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected void onPreExecute() {
        this.alertDialog = new AlertDialog.Builder(this.mContext).create();
        this.alertDialog.setTitle("Login status");
    }

    @Override
    protected Object doInBackground(Object[] param) {
        String target = "http://10.1.12.161/AndroidToMySQL/login.php";

        try {
            URL url = new URL(target);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"utf-8"));

            String msg = URLEncoder.encode("user","utf-8") + "=" +
                    URLEncoder.encode((String)param[0],"utf-8") + "&" +
                    URLEncoder.encode("pw","utf-8") + "=" +
                    URLEncoder.encode((String)param[1],"utf-8");

            bufferedWriter.write(msg);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            System.out.println("Sent to PHP file: "+msg);

            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine())!=null){
                stringBuilder.append(line+"\n");
                System.out.println("PHP file returned: "+line);
            }
//            bufferedReader.close();

            return stringBuilder.toString();
        } catch (Exception e){
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(Object o) {
        this.alertDialog.setMessage((String)o);
        this.alertDialog.show();
    }
}
