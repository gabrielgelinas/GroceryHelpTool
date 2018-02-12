package com.example.zombietux.groceryhelptool;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "GroceryHelpTool";
    private List<Product> productList;
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
            Product product = gson.fromJson(new JsonParser().parse(matcher.group(1)).getAsJsonObject(), Product.class);
            productList.add(product);
//            System.out.println(matcher.group(1));
            cnt++;
        }
        System.out.println("Page " + "" + " done with " + cnt + " products...........................................");
    }
}
