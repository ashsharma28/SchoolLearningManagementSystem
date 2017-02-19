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
import android.text.Html;
import android.text.method.LinkMovementMethod;
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
import android.net.Uri;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class NotesViewer extends ActionBarActivity
{
    String[] theText;
    InputStream newsBoy;
    ListView lst1;
    NotesViewer thisObject = this;
    byte[] byteArray = new byte[0];

    String[] imageURLArray;

    /*{
        imageURLArray = new String[]{
                "http://ssl.gstatic.com/onebox/weather/64/sunny.png",
                "http://blackboard.svkm.ac.in/images/ci/logos/Powered_Bb_050.png",
                //"http://www.nmims.edu/images/img3.jpg",
                "http://www.nmims.edu/images/nmims-logo.jpg",
                "http://www.nmims.edu/images/2a4ca0eeb31e71b312f1df1b25865a8a.png"};
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try);
        lst1 = (ListView) findViewById(R.id.listView123);

        theText = imageURLArray;

        Intent intent = getIntent();

        String subjectChosen = intent.getStringExtra( SomeAdapter8.EXTRA_MESSAGE1 );
        Toast.makeText(this , subjectChosen , Toast.LENGTH_SHORT ).show() ;
        //IN REALTIME EXTRACT ALL THE URLS FROM THIS AND FEED IT HERE IN imageURLArray



        ConnectivityManager cnmgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cnmgr.getActiveNetworkInfo();

        if (info == null) {
            Toast toast = Toast.makeText(this, "No Network Connection!!!", Toast.LENGTH_LONG);
            toast.show();
        }
        else if (info.isConnected() == false) {
            Toast toast = Toast.makeText(this, "No Network Connection!!!", Toast.LENGTH_LONG);
            toast.show();

        }
        else
        {

            new doDownload().execute(subjectChosen);


        }

    }

    private class doDownload extends AsyncTask<String, Void, String[] > {
        String titlesOfNotes[];
        String imageURLArray[] ;

        @Override
        protected String[] doInBackground(String... urls) {

            //theText = CommomUtilites.getIds(urls[0]); //--> This is to download the list of the uploaded files

            //NOW SEND THIS urls[0] TO THE PARSER AND RECIEVE BACK THE FOLLOWING TWO FROM THE HASHMAP


            HashMap hashMap = CommomUtilites.getNotes("http://192.168.146.155:8080/Megabizz/webapi/notes/" + urls[0] +  "/subjects"  );

            /*new String[]{"a.txtx", "b.jpg", "c.docx", "d.pdf"};*/
            titlesOfNotes = (String[]) hashMap.get(2);


            imageURLArray = (String[]) hashMap.get(1);
            /*new String[]{"1", "2", "3", "4"};*/


           /* Log.i("theText Length is: ", theText.length + "");


            imageURLArray= new String[theText.length] ;

            for(int i = 0 ; i < theText.length ; i++)
            {

                imageURLArray[i] = "http://192.168.146.155:8080/Megabizz/webapi/gallery/" +theText[i] ;
                Log.i("URL" +i+ "Is" , imageURLArray[i] );
            }



            Log.i("ImageURLs Length is: ", imageURLArray.length + "");*/

            return theText;

        }

        @Override
        protected void onPostExecute(String[] result) {

            SomeAdapter9 adapter = new SomeAdapter9(thisObject, titlesOfNotes /*This is theText only actually, ( the formality for Adapter)*/, imageURLArray);
            lst1.setAdapter(adapter);
        }

    }

}
















class SomeAdapter9 extends ArrayAdapter<String>
{
    Context context1;
    String[] fileURL ;
    String[] titles;
    InputStream newsBoyforImage;
    View ourRow = null;
    public final static String EXTRA_MESSAGE1 = "these things dont matter largely..at this stage thus are ignorable";

    private LruCache<String, Bitmap> mMemoryCache ;
    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    final int cacheSize = maxMemory / 8;



    SomeAdapter9(Context context, String[] description, String[] imageURL)
    {
        super(context, R.layout.try_textview, R.id.textView123, description);

        this.context1 = context ;
        this.titles = description;
        this.fileURL = imageURL;

        Log.i("CONSTRUCTOR CALLED , cache size : " , "" + cacheSize );

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };



    }


    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if ((key != null || bitmap != null) && getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public boolean isKeyExists(String key) {
        if (getBitmapFromMemCache(key) == null) {

            Log.i(" IMAGE FOUND IN CACHE ----" , "NO");
            return false;// Returns False if there is no such key

        }
        else
        {
            Log.i(" IMAGE FOUND IN CACHE ---- " , "YES");
            return true;

        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        Log.i("@ GETVIEW for position :  " , " OOOOOOOOOOOOOOOOOOO -- "  + position);
        LayoutInflater view1 = (LayoutInflater) context1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = view1.inflate(R.layout.photoviewer, parent,false );
        TextView txt1 = (TextView) row.findViewById(R.id.textView123);
        ImageView img1 = (ImageView) row.findViewById(R.id.imageView);

        row.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent();

                    fileURL[position] =  "http://192.168.146.155:8080/Megabizz/webapi/notes/" +fileURL[position]  ;

                   Uri url =  Uri.parse( fileURL[position] );


                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(url);
                    context1.startActivity(intent);
/*
                intent.putExtra(EXTRA_MESSAGE1, fileURL[position]);
*/

            }
        });

        ProgressBar pgbar = (ProgressBar) row.findViewById(R.id.pbar123);
        pgbar.setVisibility(View.INVISIBLE);

        txt1.setClickable(true);
        txt1.setMovementMethod(LinkMovementMethod.getInstance());


        String text = "<a href='" + fileURL[position] + "'>"+  titles[position] + "</a>" ;
        txt1.setText(Html.fromHtml(text));

/*
        try
        {

            String key = new URL(imageURL[position]).getFile();

            Log.i("KEY IS" , key);

            if(isKeyExists(key))
            {
                img1.setImageBitmap(getBitmapFromMemCache(key));
                Log.i(" Setting image from cache  " , "position : "  + position);

            }

            else if( key != null )
            {
                new getImage(img1, txt1, titles, position, row, pgbar, key).execute(imageURL);
            }

        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }*/

        return  row;
    }





    private class getImage extends AsyncTask<String, Void, Void>
    {   ImageView img1; TextView txt1; String[] titles; int position; View row;
        Bitmap bmp;ProgressBar pgbar;
        Bitmap resized; String keyForCache;


        public getImage(ImageView img1, TextView txt1, String[] titles , int position, View row, ProgressBar pgbar, String keyForCache)
        {
            this.img1 = img1;
            this.txt1 = txt1;
            this.titles = titles;
            this.position = position;
            this.row = row;
            this.keyForCache = keyForCache ;
            this.pgbar = pgbar;
            Log.i("I reached at:" , " GETIMAGE CONSTRUCTOR ");


        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pgbar.setVisibility(View.VISIBLE);
        }


        @Override
        protected Void doInBackground(String... urls)
        {
            Log.i("I reached at:" , " DO IN BACKGROUND12345 ");

            newsBoyforImage =  downloadUrl(urls[position]);
            bmp = BitmapFactory.decodeStream(newsBoyforImage);


            // bmp = ThumbnailUtils.extractThumbnail( bmp , bmp.getWidth() / , 360);


            return null;


        }


        @Override
        protected void onPostExecute(Void result)
        {

            pgbar.setVisibility(View.INVISIBLE);
            addBitmapToMemoryCache(keyForCache , bmp);

            img1.setImageBitmap(bmp);

            Log.i("IMAGE", "has been assigned");
        }

        InputStream downloadUrl(String url)
        {
            try
            {

                long httpCacheSize = 5 * 1024 * 1024; // 5 MiB
                File httpCacheDir = new File( context1.getCacheDir(), "http");
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












