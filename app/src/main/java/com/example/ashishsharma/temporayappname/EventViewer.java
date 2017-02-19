package com.example.ashishsharma.temporayappname;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;


public class EventViewer extends ActionBarActivity
{
    String[] theText = {
            " Try to use AndroidHttpClient if you can. HttpClient stubs are indeed contained inside the android jar, so there should be no need to refer to it explicitly. Note that the android version of httpclient is probably 4.1.1. Trying to use a newer version on top of that is usually asking for trouble (read: doesn't work, because the firmware classloader always wins). –  dhke Aug 22 at 7:06 ",
            "HttpClient was deprecated in API Level 22 and removed in API Level 23. You can still use it in API Level 23 and onwards if you must, however it is best to move to supported methods to handle HTTP. So, if you're compiling with 23, add this in your build.gradle:",
            //"http://www.nmims.edu/images/img3.jpg",
            "Package Org.apache.httpcomponents.httpclient.debian Does Not Ex - achab ZT ... Org.apache.axis.enum error; Exception at org.apache.commons.ssl.ASN1Util.main; Example 11-6 JSTL XML Web App / ModelFacade / commons.beanutils. ... to run it shows package org.apache.commons.httpclient.methods does not exist.",
            " It doesn't work that way, you can't copy jar files into a classpath (which is actually not a good idea regardless). A classpath specified both directories and jar files -- the directories (like the jar files) are searched only for class files; putting a jar file into these directories doesn't add them to the search. You need to add the jar file to the classoath (meaning the classpath variable needs to mention the jar file). "};

    InputStream newsBoy;
    TextView txt1;
    ImageView img;

    ListView lst1;
    EventViewer thisObject = this;
    byte[] byteArray = new byte[0];

    String[] imageURLArray = {
            "http://ssl.gstatic.com/onebox/weather/64/sunny.png",
            "http://blackboard.svkm.ac.in/images/ci/logos/Powered_Bb_050.png",
            //"http://www.nmims.edu/images/img3.jpg",
            "http://www.nmims.edu/images/nmims-logo.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/d/d9/Narendra_Damodardas_Modi.jpg"};


    private LruCache<String, Bitmap> mMemoryCache ;
    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    final int cacheSize = maxMemory / 8;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_viewer);
        //lst1 = (ListView) findViewById(R.id.listView123);

        Intent intent = getIntent();
        String titleClicked = intent.getStringExtra(SomeAdapter2.EXTRA_MESSAGE1);

        //HERE WE ARE (FOR SAMPLE USE) BRINGING THE POSITION OF THE BUTTON ON
        // EVENT ACTIVITY(THAT IS  CLICKED) AND THEN ACCORDINGLY THE DATA OF
        // IMAGE AND DESCRIPTION IS DOWNLOADED AND POPULATED TO THE FRONT END.

        txt1 = (TextView) findViewById(R.id.textView123);
        img = (ImageView) findViewById(R.id.imageView);


        ConnectivityManager cnmgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cnmgr.getActiveNetworkInfo();

        if (info == null) {
            Toast toast = Toast.makeText(this, "No Network Connection!!!", Toast.LENGTH_LONG);
            toast.show();
        } else if (info.isConnected() == false) {
            Toast toast = Toast.makeText(this, "No Network Connection!!!", Toast.LENGTH_LONG);
            toast.show();

        } else {
           /*SomeAdapter6 adapter = new SomeAdapter6(thisObject, theText, imageURLArray);
           lst1.setAdapter(adapter);*/

            mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    // The cache size will be measured in kilobytes rather than
                    // number of items.
                    return bitmap.getByteCount() / 1024;
                }
            };

            int Class = 6;
            new doDownload().execute( Class + "" ,titleClicked );




        }


    }





    private class doDownload extends AsyncTask<String, Void, String[] >
    {
        String[] desc;
        String imageURL ;


        @Override
        protected String[] doInBackground(String... urls)
        {
            Log.i("I reached at: " , "1");

            desc = CommomUtilites.getEventDescription( urls[0] , urls[1]);
            Log.i("sff" , desc[0] + "  ++  " + desc[1]);




            return desc;

        }

        @Override
        protected void onPostExecute(String[]result){

            txt1.setText(result[1]);
            new getImage(img).execute(result[0]);


        }



    }





    private class getImage extends AsyncTask<String, Void, Void>
        {   ImageView img1;
            TextView txt1;
            ProgressBar pgbar;

            String[] titles;
            String keyForCache;
            int position;

            View row;
            Bitmap bmp;
            InputStream newsBoyforImage;


            public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
                if (getBitmapFromMemCache(key) == null) {
                    mMemoryCache.put(key, bitmap);
                }
            }

            public boolean isKeyExists(String key) {
                if (getBitmapFromMemCache(key) == null) {

                    Log.i(" IMAGE FOUND IN CACHE " , "YYYYYY");
                    return false;// Returns False if there is no such key

                }
                else
                {
                    Log.i(" IMAGE FOUND IN CACHE " , "XXXXX");
                    return true;

                }
            }

            public Bitmap getBitmapFromMemCache(String key)
            {
                return mMemoryCache.get(key);
            }


            public getImage(ImageView img1 )
            {
                this.img1 = img1;
                //this.position = position;
                Log.i("I reached at:" , " GETIMAGE CONSTRUCTOR ");

            }



            @Override
            protected Void doInBackground(String... urls)
            {
                Log.i("I reached at:" , " DO IN BACKGROUND ");

                newsBoyforImage =  downloadUrl(urls[0]);
                bmp = ThumbnailUtils.extractThumbnail( BitmapFactory.decodeStream(newsBoyforImage), 480, 360);


                return null;


            }


            @Override
            protected void onPostExecute(Void result)
            {

                img1.setImageBitmap(bmp);


                Log.i("IMAGE", "has been assigned");
            }

            InputStream downloadUrl(String url)
            {
                try
                {

                    long httpCacheSize = 5 * 1024 * 1024; // 5 MiB
                    File httpCacheDir = new File( getApplicationContext().getCacheDir(), "http");
                    Class.forName("android.net.http.HttpResponseCache")
                            .getMethod("install", File.class, long.class)
                            .invoke(null, httpCacheDir, httpCacheSize);

                    URL urlMain = new URL(url);

                    HttpURLConnection urlConnection = (HttpURLConnection) urlMain.openConnection();
                    return urlConnection.getInputStream();
                }
                catch (Exception e)
                {
                    String a =  new String("Unable to retrieve web page. ERROR URL may be invalid.");
                    byte[] byteArray = a.getBytes() ;
                    ByteArrayInputStream sendBackThis = new  ByteArrayInputStream(byteArray);
                    return (sendBackThis);
                }

            }


        }






    }




