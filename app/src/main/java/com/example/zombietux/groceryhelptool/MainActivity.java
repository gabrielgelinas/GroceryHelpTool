package com.example.zombietux.groceryhelptool;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "GroceryHelpTool";
    private ArrayList<Product> productList;
    private List<JsonObject> jsonProductList;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter adapter;
    private ProgressDialog progress;
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
        progress = new ProgressDialog(this);

        pattern = Pattern.compile("<div class=\"js-product js-equalized js-addtolist-container js-ga\" data-product=\"(.+?)" + "\"");
        urlBuilder = new URLBuilder("https://www.iga.net/en/search?page=", "&pageSize=60"); //TODO Loop through pages


        productList = new ArrayList<>();
        jsonProductList = new ArrayList<>();

//        ReadJsonFile();

        new DownloadTask().execute();

    }

    private class DownloadTask extends AsyncTask<Void, Integer, Integer> {
        int maxpages = 1;
        private int cnt = 0;

        @Override
        protected void onPreExecute() {
            //TODO Setup ProgressBar
            progress.setTitle("Fetching Data...");
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.setCancelable(false);
            progress.show();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //TODO Setup ProgressBar
//            super.onProgressUpdate(values);
            progress.setMessage("Fetching data from page " + values[0] + " out of " + maxpages + ".");
            progress.setProgress(values[0]);
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            Integer result = 0;
            StringBuilder stringBuilder = null;

            //TODO Loop through pages

            PageHTML pageHTML = new PageHTML(urlBuilder.getURL(1));
            try {
                ArrayList<Object> results = pageHTML.getHTML();
                if (((boolean) results.get(0))) {
                    maxpages = GetMaxPages((StringBuilder) results.get(1));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

//            maxpages = 1;
            for (int i = 369; i <= maxpages; i++) {
//                try {
//                    URL url = new URL(urlBuilder.getURL());
//                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//
//                    //TODO Check if necessary
////                      urlConnection.setRequestMethod("GET");
//                    //
//
//                    int statusCode = urlConnection.getResponseCode(); // 200 = HTTP OK
//                    if (statusCode == 200) {
//
//                        BufferedReader bufferedReader = new BufferedReader(
//                                new InputStreamReader(urlConnection.getInputStream())
//                        );
//
//                        String inputLine;
//                        stringBuilder = new StringBuilder();
//
//                        while ((inputLine = bufferedReader.readLine()) != null) {
//                            stringBuilder.append(inputLine); // .append("\n") ???
//                        }
//                        bufferedReader.close();
//
//                        result = 1; //"Successful";
//                    } else {
//                        result = 0; //"Failed to fetch data!";
//                    }
//                } catch (Exception e) {
//                    Log.d(TAG, e.getLocalizedMessage());
//                }
                pageHTML = new PageHTML(urlBuilder.getURL(i));
                try {
                    ArrayList<Object> results = pageHTML.getHTML();
                    if (((boolean) results.get(0))) {
                        parseResult((StringBuilder) results.get(1));
                        result = 1;
                    } else {
                        result = 0;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    result = 0;
                }


                Integer int_i = i;
                Integer int_maxpages = maxpages;

                Double dbl_i = int_i.doubleValue();
                Double dbl_maxpages = int_maxpages.doubleValue();

                Double dbl_progress = dbl_i / dbl_maxpages * 100;

                publishProgress(dbl_progress.intValue());
                System.out.println("Page " + i + " done with " + cnt + " products...........................................");
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            //TODO Setup ProgressBar
            progress.dismiss();
//            progressBar.setVisibility(View.GONE);

            if (result == 1) {

                setAdater();

            } else {
                Toast.makeText(MainActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }

        private void parseResult(StringBuilder result) {
            Matcher matcher = pattern.matcher(result.toString());
            while (matcher.find()) {
                Gson gson = new Gson();
                JsonObject jsonObject = (new JsonParser().parse(matcher.group(1)).getAsJsonObject());
                Product product = gson.fromJson(jsonObject, Product.class);
                productList.add(product);
                jsonProductList.add(jsonObject);
//                System.out.println(matcher.group(1));
                cnt++;
            }

        }

        private int GetMaxPages(StringBuilder pageHTML) {
            Integer maxpage = 1;

            Pattern mPattern = Pattern.compile("<p>\\n\\s*(.+?)\\s+?match");
            Matcher mMatcher = mPattern.matcher(pageHTML);

            while (mMatcher.find()) {
                maxpage = (Integer.parseInt(mMatcher.group(1)) / 60)+1;
                System.out.println(maxpage);
            }

            return maxpage;
        }

        private class PageHTML {
            String urlstring;

            PageHTML(String urlstring) {
                this.urlstring = urlstring;
            }

            ArrayList<Object> getHTML() throws IOException {
                StringBuilder stringBuilder = null;
                boolean successful;

                URL url = new URL(urlstring);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //TODO Check if necessary
                urlConnection.setRequestMethod("GET");
                //

                int statusCode = urlConnection.getResponseCode(); // 200 = HTTP OK
                if (statusCode == 200) {

                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(urlConnection.getInputStream())
                    );

                    String inputLine;
                    stringBuilder = new StringBuilder();

                    while ((inputLine = bufferedReader.readLine()) != null) {
                        stringBuilder.append(inputLine).append("\n"); // .append("\n") ???
                    }
                    bufferedReader.close();

                    successful = true;
                } else {
                    successful = false;
                }

                ArrayList<Object> results = new ArrayList<>();
                results.add(successful);
                results.add(stringBuilder);
                return results;
            }
        }
    }

    public void setAdater(){
        adapter = new MyRecyclerViewAdapter(MainActivity.this, productList);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                Toast.makeText(MainActivity.this, product.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void ShowCartClick(View view) {
        ReadJsonFile();
    }

    public void ConnectUser(View view) {
        SendToDatabase();
        WriteJSON();
    }

    public void SendToDatabase() {

        for (Product product :
                productList) {
            DatabaseWorker databaseWorker = new DatabaseWorker(this, "http://192.168.0.134/AndroidToMySQL/dbwrite.php");
            databaseWorker.execute(product);
        }
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
//        System.out.println("file.toString() = " + file.toString());
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            for (JsonObject j :
                    jsonProductList) {
                outputStreamWriter.write(j.toString() + "\n");
//                outputStreamWriter.append("\n");
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
        productList = new ArrayList<>();
        jsonProductList = new ArrayList<>();

        String inputLine;

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS + "/iga_data.json")));
            while ((inputLine = bufferedReader.readLine()) != null) {
                Gson gson = new Gson();
                JsonObject jsonObject = (new JsonParser().parse(inputLine).getAsJsonObject());
                Product product = gson.fromJson(jsonObject, Product.class);
                productList.add(product);
                jsonProductList.add(jsonObject);
            }
            bufferedReader.close();

            setAdater();
        } catch (Exception e) {
//            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        System.out.println("productList size = " + productList.size());
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
