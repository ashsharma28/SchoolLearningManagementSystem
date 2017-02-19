package com.example.ashishsharma.temporayappname;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class Notes extends ActionBarActivity
{

    String[] imageURLArray;
    InputStream newsBoy;
    ListView lst1;


    Notes thisObject = this;
    byte[] byteArray = new byte[0];


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try);
        lst1 = (ListView) findViewById(R.id.listView123);


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
            int Class = 8;
            new doDownload().execute(Class);

        }

    }




    private class doDownload extends AsyncTask<Integer, Void, String[] >
    {

        String[] ids;


        @Override
        protected String[] doInBackground(Integer... urls)
        {
            Log.i("I reached at: " , "1");


            HashMap hashMap = CommomUtilites.getNotes("http://192.168.146.155:8080/Megabizz/webapi/notes/" + urls[0] +  "/subjects"  );

            String[] theText =(String[]) hashMap.get(2);


            /*
               {
                    "Physics",
                    "Chemistry",
                    "Maths",
                    "English",
                    "Social Science"
                 };
            */



            ids = (String[]) hashMap.get(1);

            //theText = CommomUtilites.getEventTitles(urls[0]);

//            Log.i("sff" , theText[0]);

            /*result =  downloadUrl(urls[0]);*/
            //newsBoy = result ;

            return theText;

        }

        @Override
        protected void onPostExecute(String[]result){



            SomeAdapter8 adapter = new SomeAdapter8(thisObject, result , ids );
            lst1.setAdapter(adapter);
        }




    }


}








class SomeAdapter8 extends ArrayAdapter<String> {
    Context context1;
    String[] imageURL;
    String[] titles;
    String[] ids;
    InputStream newsBoyforImage;
    View ourRow = null;
    public final static String EXTRA_MESSAGE1 = "these things dont matter largely..at this stage thus are ignorable";
    Intent intent;




    SomeAdapter8(Context context, String[] description , String[] ids) {
        super(context, R.layout.try_textview, R.id.textView123, description);
        Log.i("I reached at: ", "pre6 ");

        this.context1 = context;
        //this.imageURL = imageURL;
        this.titles = description;

        this.ids = ids ;

        intent = new Intent(context1, NotesViewer.class);

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater view1 = (LayoutInflater) context1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = view1.inflate(R.layout.try_textview, parent, false);
        TextView txt1 = (TextView) row.findViewById(R.id.textView123);

        row.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /*Toast.makeText( context1 , "Please wait while syncing lecture counts", Toast.LENGTH_LONG).show() ;*/


                intent.putExtra(EXTRA_MESSAGE1 , ids[position]);
                context1.startActivity(intent);


            }
        });


        txt1.setText(titles[position]);

        return row;

    }



}
