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

public class DatabaseWorker extends AsyncTask<Product,Integer,String> {
    private Context mContext;
    private String mtarget;
    private AlertDialog alertDialog;

    public DatabaseWorker(Context mContext, String mTarget) {
        this.mContext = mContext;
        this.mtarget = mTarget;
    }

    @Override
    protected void onPreExecute() {
//        this.alertDialog = new AlertDialog.Builder(this.mContext).create();
//        this.alertDialog.setTitle("Login status");
    }

    @Override
    protected String doInBackground(Product... products) {

            try {
                URL url = new URL(mtarget);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
//                connection.setDoOutput(true);
                connection.setRequestMethod("POST");

                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));

                String msg =
                        URLEncoder.encode("ProductId", "utf-8") + "=" + URLEncoder.encode((String) products[0].getProductId(), "utf-8") + "&" +
                        URLEncoder.encode("FullDisplayName", "utf-8") + "=" + URLEncoder.encode((String) products[0].getName(), "utf-8") + "&" +
                        URLEncoder.encode("BrandName", "utf-8") + "=" + URLEncoder.encode((String) products[0].getBrandName(), "utf-8") + "&" +
                        URLEncoder.encode("IsAgeRequired", "utf-8") + "=" + URLEncoder.encode((String) products[0].getAgeRequired().toString(), "utf-8") + "&" +
                        URLEncoder.encode("SizeLabel", "utf-8") + "=" + URLEncoder.encode((String) products[0].getSizeLabel(), "utf-8") + "&" +
                        URLEncoder.encode("Size", "utf-8") + "=" + URLEncoder.encode((String) products[0].getSize(), "utf-8") + "&" +
                        URLEncoder.encode("ProductUrl", "utf-8") + "=" + URLEncoder.encode((String) products[0].getProductUrl(), "utf-8") + "&" +
                        URLEncoder.encode("ProductImageUrl", "utf-8") + "=" + URLEncoder.encode((String) products[0].getProductImageUrl(), "utf-8") + "&" +
                        URLEncoder.encode("HasNewPrice", "utf-8") + "=" + URLEncoder.encode((String) products[0].getHasNewPrice().toString(), "utf-8") + "&" +
                        URLEncoder.encode("RegularPrice", "utf-8") + "=" + URLEncoder.encode((String) products[0].getRegularPrice().toString(), "utf-8") + "&" +
                        URLEncoder.encode("PromotionName", "utf-8") + "=" + URLEncoder.encode((String) products[0].getPromotionName(), "utf-8") + "&" +
                        URLEncoder.encode("SalesPrice", "utf-8") + "=" + URLEncoder.encode((String) products[0].getSalesPrice().toString(), "utf-8");

                bufferedWriter.write(msg);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

//                System.out.println("Sent to PHP file: " + msg);

                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
//                    System.out.println("PHP file returned: " + line);
                }
            bufferedReader.close();

                return stringBuilder.toString();
            } catch (Exception e) {
                return e.getMessage();
            }

//        for (Product product :
//                products) {
//            try {
//                URL url = new URL(mtarget);
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setDoInput(true);
//                connection.setDoOutput(true);
//                connection.setRequestMethod("POST");
//
//                OutputStream outputStream = connection.getOutputStream();
//                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
//
//                String msg =
//                        URLEncoder.encode("ProductId", "utf-8") + "=" + URLEncoder.encode((String) product.getProductId(), "utf-8") + "&" +
//                        URLEncoder.encode("FullDisplayName", "utf-8") + "=" + URLEncoder.encode((String) product.getName(), "utf-8") + "&" +
//                        URLEncoder.encode("BrandName", "utf-8") + "=" + URLEncoder.encode((String) product.getBrandName(), "utf-8") + "&" +
//                        URLEncoder.encode("IsAgeRequired", "utf-8") + "=" + URLEncoder.encode((String) product.getAgeRequired().toString(), "utf-8") + "&" +
//                        URLEncoder.encode("SizeLabel", "utf-8") + "=" + URLEncoder.encode((String) product.getSizeLabel(), "utf-8") + "&" +
//                        URLEncoder.encode("Size", "utf-8") + "=" + URLEncoder.encode((String) product.getSize(), "utf-8") + "&" +
//                        URLEncoder.encode("ProductUrl", "utf-8") + "=" + URLEncoder.encode((String) product.getProductUrl(), "utf-8") + "&" +
//                        URLEncoder.encode("ProductImageUrl", "utf-8") + "=" + URLEncoder.encode((String) product.getProductImageUrl(), "utf-8") + "&" +
//                        URLEncoder.encode("HasNewPrice", "utf-8") + "=" + URLEncoder.encode((String) product.getHasNewPrice().toString(), "utf-8") + "&" +
//                        URLEncoder.encode("RegularPrice", "utf-8") + "=" + URLEncoder.encode((String) product.getRegularPrice().toString(), "utf-8") + "&" +
//                        URLEncoder.encode("PromotionName", "utf-8") + "=" + URLEncoder.encode((String) product.getPromotionName(), "utf-8") + "&" +
//                        URLEncoder.encode("SalesPrice", "utf-8") + "=" + URLEncoder.encode((String) product.getSalesPrice().toString(), "utf-8");
//
//                bufferedWriter.write(msg);
//                bufferedWriter.flush();
//                bufferedWriter.close();
//                outputStream.close();
//
//                System.out.println("Sent to PHP file: " + msg);
//
//                InputStream inputStream = connection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
//                String line;
//                StringBuilder stringBuilder = new StringBuilder();
//                while ((line = bufferedReader.readLine()) != null) {
//                    stringBuilder.append(line + "\n");
//                    System.out.println("PHP file returned: " + line);
//                }
////            bufferedReader.close();
//
//                return stringBuilder.toString();
//            } catch (Exception e) {
//                return e.getMessage();
//            }
//        }
//        return "Didn't loop through any product...";
    }

    @Override
    protected void onPostExecute(String result) {
//        this.alertDialog.setMessage(result);
//        this.alertDialog.show();
    }
}
