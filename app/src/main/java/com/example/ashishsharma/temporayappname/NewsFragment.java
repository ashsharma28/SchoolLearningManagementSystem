package com.example.ashishsharma.temporayappname;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;


public class NewsFragment extends Fragment
{
    ListView lst1;
    NewsFragment ThisObject = this;
    View rootView;
    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_try, container, false);
        lst1 = (ListView) rootView.findViewById(R.id.listView123);
        /*theText = new String[]{"Class 12th student Mr. XYZ selected for nationals Table Tennis",
                "Our school to host CBSE Clusters 2015-16",
                "Congratulations Dr. XYZ Sir for completion of Phd.",
                "Tiny tots from Class 1st represent school at inter school craft competition"};
*/


        ConnectivityManager cnmgr = (ConnectivityManager) rootView.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cnmgr.getActiveNetworkInfo();

        if (info == null) {
            Toast toast = Toast.makeText(getActivity(), "No Network Connection!!!", Toast.LENGTH_LONG);
            toast.show();
        }
        else if (info.isConnected() == false) {
            Toast toast = Toast.makeText(getActivity(), "No Network Connection!!!", Toast.LENGTH_LONG);
            toast.show();
        }
        else
        {
            int Class = 6;
            /*new getXML().execute(" ");*/
            new doDownload().execute(Class);

        }

        return rootView;

    }

    private class doDownload extends AsyncTask<Integer, Void, String[] > {
        String theText[];

        @Override
        protected String[] doInBackground(Integer... urls) {

            theText = CommomUtilites.getTitles(urls[0]);

            Log.i("I reached at: ", theText[0] + theText[1] +
                    theText[3]);

            if(theText[0] == null)
                Log.i("Asdswddsdsdd" , "EMPTYTTTTT") ;
            /*result =  downloadUrl(urls[0]);*/
            //newsBoy = result ;
            return theText;

        }

        @Override
        protected void onPostExecute(String[] result) {
            SomeAdapter3 adapter = new SomeAdapter3( ThisObject.getActivity() , result);
            lst1.setAdapter(adapter);
        }

    }
       /* @Override
        protected void onPostExecute(InputStream result)
        {

            Log.i("I reached at: " , "2 --- XML ASSIGNED");
            Log.i("I reached at: " , "5");
            SomeAdapter5 adapter = new SomeAdapter5(thisObject, theText, imageURLArray);
            lst1.setAdapter(adapter);

        }

       InputStream downloadUrl(String url)
        {
            try
            {
                URL urlMain = new URL(url);
                HttpURLConnection urlConnection = (HttpURLConnection) urlMain.openConnection();
                Log.i( "I reached at: " , "XML has been Downloaded");
                return urlConnection.getInputStream();
            }
            catch (Exception e)
            {

                String a =  new String("Unable to retrieve web page. ERROR URL may be invalid.");
                byte[] byteArray = a.getBytes() ;
                Log.e("I reached at: " , "DownloadURL error", e);

                return null;
            }

        }*/




}







class SomeAdapter3 extends ArrayAdapter<String> {
    Context context1;
    String[] imageURL;
    String[] titles;
    InputStream newsBoyforImage;
    View ourRow = null;
    public final static String EXTRA_MESSAGE1 = "these things dont matter largely..at this stage thus are ignorable";



    SomeAdapter3(Context context, String[] description) {
        super(context, R.layout.try_textview, R.id.textView123, description);
        Log.i("I reached at: ", "pre6 ");

        this.context1 = context;

        this.titles = description;

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

                Intent intent = new Intent( context1, NewsViewer.class ) ;
                intent.putExtra(EXTRA_MESSAGE1 , titles[position]);
                context1.startActivity(intent);


            }
        });


        txt1.setText(titles[position]);

        return row;

    }



}
