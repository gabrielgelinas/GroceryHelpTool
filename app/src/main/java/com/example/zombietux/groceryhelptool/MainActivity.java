package com.example.zombietux.groceryhelptool;

import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "GroceryHelpTool";
    private List<Product> productList;
    private List<JsonObject> jsonProductList;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter adapter;
    private ProgressBar progressBar;
    private Pattern pattern;
    private URLBuilder urlBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //TODO Setup ProgressBar
//        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        pattern = Pattern.compile("<div class=\"js-product js-equalized js-addtolist-container js-ga\" data-product=\"(.+?)" + "\"");
        urlBuilder = new URLBuilder("https://www.iga.net/en/search?page=", 1, "&pageSize=60"); //TODO Loop through pages
        new DownloadTask().execute();

    }

    private class DownloadTask extends AsyncTask<Void, Void, Integer> {
        @Override
        protected void onPreExecute() {
            productList = new ArrayList<>();
            jsonProductList = new ArrayList<>();
            //TODO Setup ProgressBar
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //TODO Setup ProgressBar
//            super.onProgressUpdate(values);
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            Integer result = 0;
            //TODO Loop through pages

//            HttpURLConnection urlConnection;
            try {
//                URL url = new URL(params[0]);
//                urlConnection = (HttpURLConnection) url.openConnection();
//                int statusCode = urlConnection.getResponseCode();
                URL url = new URL(urlBuilder.getURL());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //TODO Check if necessary :
//                urlConnection.setRequestMethod("GET");
                //

                int statusCode = urlConnection.getResponseCode(); // 200 = HTTP OK
                if (statusCode == 200) {
//                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//                    StringBuilder response = new StringBuilder();
//                    String line;
//                    while ((line = r.readLine()) != null) {
//                        response.append(line);
//                    }

                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(urlConnection.getInputStream())
                    );

                    String inputLine;
                    StringBuilder stringBuilder = new StringBuilder();

                    while ((inputLine = bufferedReader.readLine()) != null) {
                        stringBuilder.append(inputLine); // .append("\n") ???
                    }
                    bufferedReader.close();

                    parseResult(stringBuilder);
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
//            progressBar.setVisibility(View.GONE);

            if (result == 1) {
                adapter = new MyRecyclerViewAdapter(MainActivity.this, productList);
                mRecyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(Product product) {
                        Toast.makeText(MainActivity.this, product.getName(), Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                Toast.makeText(MainActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseResult(StringBuilder result) {
        Matcher matcher = pattern.matcher(result.toString());
        int cnt = 0;
        while (matcher.find()) {
            Gson gson = new Gson();
            JsonObject jsonObject = (new JsonParser().parse(matcher.group(1)).getAsJsonObject());
            Product product = gson.fromJson(jsonObject, Product.class);
            productList.add(product);
            jsonProductList.add(jsonObject);
            System.out.println(matcher.group(1));
            cnt++;
        }

        WriteJSON();
        System.out.println("Page " + "" + " done with " + cnt + " products...........................................");
    }

    public void ConnectUser(View view) {
        EditText txtUser;
        EditText txtPwd;

        txtUser = (EditText) findViewById(R.id.input_username);
        txtPwd = (EditText) findViewById(R.id.input_pwd);

        String user = txtUser.getText().toString();
        String pwd = txtPwd.getText().toString();

        DatabaseWorker databaseWorker = new DatabaseWorker(this);

        databaseWorker.execute(user, pwd);


        ReadJsonFile();
    }

    public void WriteJSON() {
//        setName();
//        createJSONFolder();
//        CompositionJso obj = new CompositionJso();
//        File ppath = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS + "iga_data.json");
//        System.out.println("ppath ( getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS + \"iga_data.json\") ) = " + ppath);

//        String path = getExternalFilesDir(null) + "/AppName/App_cache" + File.separator;
//        System.out.println(isExternalStorageReadable());
//        System.out.println(isExternalStorageWritable());
//        System.out.println("Environment.getExternalStorageDirectory() = " + Environment.getExternalStorageDirectory());
//        System.out.println("String path = Environment.getExternalStorageDirectory() + \"/AppName/App_cache\" + File.separator; = " + path);
//
//        File dir = new File(path);
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//        path += ;
//        System.out.println("getFilesDir() = " + getFilesDir() + "   getExternalFilesDir() = " + getExternalFilesDir(null));
//        File data = new File(path,"data.json");


//        File ffile = new File(ppath, "iga_data.json");
        File file = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS + "/iga_data.json");

        try {
            if (!file.createNewFile()) {
                file.delete();
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("file.toString() = " + file.toString());
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            for (JsonObject j :
                    jsonProductList) {
                outputStreamWriter.write(j.toString());
                outputStreamWriter.write("\n");
            }
            outputStreamWriter.close();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

//        try {
//            BufferedWriter output = new BufferedWriter(new FileWriter(ppath + "iga_data.json"));
//            output.append(jsonObject.toString()).append("\n");
//            output.close();
//        } catch (Exception e) {
//            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//        }
    }

    private void ReadJsonFile() {
        File ppath = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        String inputLine;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(ppath + "/iga_data.json"));
            while ((inputLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputLine).append("\n"); // .append("\n") ???
                System.out.println(inputLine);
            }
//            System.out.println(stringBuilder);
            bufferedReader.close();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e(TAG, "Directory not created");
        }
        return file;
    }
}
