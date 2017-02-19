package com.example.ashishsharma.temporayappname;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 *This class provides a common interface between the frontend and the backend.
 * It contains several methods that use HttpURLConnection to connect to the server.
 *
 */


public class CommomUtilites {
    static String url;

    public static String post(String url, JSONObject jsonObject) {
        InputStream inputStream = null;
        String result = "9424501010";
        try {
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            StringEntity se = new StringEntity(jsonObject.toString());

            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(se);
            HttpResponse response = httpclient.execute(post);
            inputStream = response.getEntity().getContent();
            // convert inputstream to string
            if (inputStream != null) {
                result = readResponse(inputStream);
            } else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    //Method that returns array of titles of the news
    public static String[] getTitles(int cls){
        String result=GET("http://192.168.146.155:8080/Megabizz/webapi/news/"+cls+"/titles");
        String[] titles=null;
        Log.i("msg1","title method called");
        try {
            result ="{titles:"+result+"}";
            JSONObject jsonObject=new JSONObject(result);
            JSONObject temp=null;
            JSONArray jsonArray=jsonObject.getJSONArray("titles");
            int length=jsonArray.length();
            titles=new String[length];
            for (int i=0;i<length;i++){
                temp= (JSONObject) jsonArray.get(i);
                titles[i]=temp.getString("title");
                Log.i("title 1",titles[i]);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return titles;
    }
    //Method that returns description of the specific title
    public  static String getDescription(String title){
        String description=null;
        String result=GET("http://192.168.146.155:8080/Megabizz/webapi/news/6/"+title);
        try {
            result ="{descriptions:"+result+"}";
            JSONObject jsonObject=new JSONObject(result);
            JSONArray jsonArray=jsonObject.getJSONArray("descriptions");
            JSONObject temObject= (JSONObject) jsonArray.get(0);
            description=temObject.getString("content");
            Log.i("description ",description);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("THERES EXCEPTION IN " , "GetDiscription");
        }
        return description;
    }


    // Method to return all the album name
    public static String[]getCategories(){
        String result=GET("http://192.168.146.155:8080/Megabizz/webapi/gallery/categories");
        String[] categories=null;
        Log.i("msg1","Hey, I will you give you categories");
        try {
            result ="{categories:"+result+"}";
            JSONObject jsonObject=new JSONObject(result);
            JSONObject temp=null;
            JSONArray jsonArray=jsonObject.getJSONArray("categories");
            int length=jsonArray.length();
            categories=new String[length];
            for (int i=0;i<length;i++){
                temp= (JSONObject) jsonArray.get(i);
                categories[i]=temp.getString("category");
                Log.i("category " + i, categories[i]);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return categories;
    }

    //Method to return the array of id
    public static int[] getIds(String category){
        String result=GET("http://192.168.146.155:8080/Megabizz/webapi/gallery/categories/"+category);
        int[] ids=null;
        Log.i("msg1","Hey, I will you give you categories");
        try {
            result ="{ids:"+result+"}";
            JSONObject jsonObject=new JSONObject(result);
            JSONObject temp=null;
            JSONArray jsonArray=jsonObject.getJSONArray("ids");
            int length=jsonArray.length();
            ids= new int[length];
            for (int i=0;i<length;i++){
                temp= (JSONObject) jsonArray.get(i);
                ids[i]=temp.getInt("id");
                Log.i("category " + i, ids[i]+"");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ids;
    }
    //This method will return the hashMap containing two arrays of the string id(at key value 1) and title(at key value 2) for the given url.
    /**
     *URL for getting the subject id's and subject names is "192.168.146.155:8080/Megabizz/webapi/notes/{cls}/subjects"
     *URL for getting the id's and titles of the notes is "192.168.146.155:8080/Megabizz/webapi/notes/{cls}/{sid}/titles"
     */

    public static HashMap<Integer,String[]> getNotes(String url){
        HashMap<Integer,String[]>hashMap=null;

        String result=GET(url);


        String[] title=null;
        String[]   id=null;
        Log.i("msg1","title method called");
        try {
            result ="{titles:"+result+"}";
            JSONObject jsonObject=new JSONObject(result);
            JSONObject temp=null;
            JSONArray jsonArray=jsonObject.getJSONArray("titles");
            int length=jsonArray.length();
            title=new String[length];
            id=new String[length];
            for (int i=0;i<length;i++){
                temp= (JSONObject) jsonArray.get(i);
                title[i]=temp.getString("title");
                id[i]= (String) temp.get("id");
                Log.i(id[i],title[i]);
            }
            hashMap=new HashMap<Integer,String[]>();
            hashMap.put(1,id);
            hashMap.put(2,title);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return hashMap;
    }


    //Method to return the array of title of the events
    public static String[] getEventTitles(int cls){
        String result=GET("http://192.168.146.155:8080/Megabizz/webapi/events/"+cls+"/titles");
        String[] titles=null;
        Log.i("msg1","getEventTItles method called");
        try {
            result ="{titles:"+result+"}";
            JSONObject jsonObject=new JSONObject(result);
            JSONObject temp=null;
            JSONArray jsonArray=jsonObject.getJSONArray("titles");

            int length=jsonArray.length();
            titles=new String[length];
            for (int i=0;i<length;i++){
                temp= (JSONObject) jsonArray.get(i);
                titles[i]=temp.getString("title");
                Log.i("title " + i ,titles[i]);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return titles;

    }

    /*Method to return the array of string with description and id of
        the event corresponding to the given class and title*/
    public static String[] getEventDescription(String cls,String title){
        String description[]=new String[2];
        String result=GET("http://192.168.146.155:8080/Megabizz/webapi/events/description/"+cls+"/"+title);
        JSONObject jsonObject= null;
        try {
            jsonObject = new JSONObject(result);
            description[0]=jsonObject.getString("id");
            description[1]=jsonObject.getString("content");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  description;

    }

    public static String GET(String url){
        InputStream inputStream = null;
        String result = "<contacts>";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            Log.i("InputStream", "1");




            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
            Log.i("InputStream", "2");

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
            Log.i("InputStream", "3");

            // convert inputstream to string
            if(inputStream != null) {
                result = readResponse(inputStream);
                Log.i("InputStream", "4");
            }
            else {
                result = "Did not work!";
                Log.i("InputStream", "1");
            }

        } catch (Exception e) {
            Log.d("InputStream", e.toString());
        }

        return result;
    }

    public static JSONObject postFile(String url,String filePath,int id){
        String result="";
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        File file = new File(filePath);
        MultipartEntity mpEntity = new MultipartEntity();
        ContentBody cbFile = new FileBody(file, "image/jpeg");
        StringBody stringBody= null;
        JSONObject responseObject=null;
        try {
            stringBody = new StringBody(id+"");
            mpEntity.addPart("file", cbFile);
            mpEntity.addPart("id",stringBody);
            httpPost.setEntity(mpEntity);
            System.out.println("executing request " + httpPost.getRequestLine());
            URL url1=new URL(url);
            HttpURLConnection httpURLConnection=(HttpURLConnection)url1.openConnection();
            httpURLConnection.setConnectTimeout(50000);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();
            result=readResponse(resEntity.getContent());
            responseObject=new JSONObject(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return responseObject;
    }

    private static String readResponse(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}
