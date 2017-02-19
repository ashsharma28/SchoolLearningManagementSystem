package com.example.ashishsharma.temporayappname;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
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


public class GalleryFragment extends Fragment
{
    String[] theText;
    String[] imageURLArray;
    InputStream newsBoy;
    ListView lst1;
    GalleryFragment thisObject = this;
    byte[] byteArray = new byte[0];
    View rootView;
    public static GalleryFragment newInstance() {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public GalleryFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_try, container, false);
        lst1 = (ListView) rootView.findViewById(R.id.listView123);
        theText = new String[]{"Class 12th Science Stream", "Farewell 2014", "Just Dance Mumbai", "Trustee's Visit"};

        ConnectivityManager cnmgr = (ConnectivityManager) rootView.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cnmgr.getActiveNetworkInfo();

        if (info == null) {
            Toast toast = Toast.makeText(rootView.getContext(), "No Network Connection!!!", Toast.LENGTH_LONG);
            toast.show();
        }
        else if (info.isConnected() == false) {
            Toast toast = Toast.makeText(rootView.getContext(), "No Network Connection!!!", Toast.LENGTH_LONG);
            toast.show();
        }
        else
        {
            int Class = 6;
            new doDownload().execute(Class);

        }

        return rootView;

    }

    private class doDownload extends AsyncTask<Integer, Void, String[] > {
        String theText[];

        @Override
        protected String[] doInBackground(Integer... urls) {

            theText = CommomUtilites.getCategories();

            Log.i("I reached at: ", theText[0] + theText[1] +
                    theText[3]);

            if(theText[0] == null)
                Log.i("Asdswddsdsdd" , "EMPTYTTTTT") ;


            return theText;

        }

        @Override
        protected void onPostExecute(String[] result) {
            SomeAdapter adapter = new SomeAdapter(thisObject.getActivity(), theText/*, imageURLArray*/);
            lst1.setAdapter(adapter);
        }

    }

}



class SomeAdapter extends ArrayAdapter<String> {
    Context context1;
    String[] imageURL;

    String[] titles;
    InputStream newsBoyforImage;
    View ourRow = null;

    public final static String EXTRA_MESSAGE1 = "these things don't matter largely..at this stage thus are ignorable";
    Intent intent;

    public Intent getIntent() {
        return intent;
    }

    SomeAdapter(Context context, String[] description) {
        super(context, R.layout.try_textview, R.id.textView123, description);
        Log.i("I reached at: ", "pre6 ");

        this.context1 = context;

        this.titles = description;

        intent = new Intent(context1, PhotoViewer.class);

    }





    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater view1 = (LayoutInflater) context1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = view1.inflate(R.layout.try_textview, parent, false);
        TextView txt1 = (TextView) row.findViewById(R.id.textView123);


        row.setOnClickListener(new View.OnClickListener( ) {

            @Override
            public void onClick(View v) {
                intent.putExtra(EXTRA_MESSAGE1 , "" + titles[position]);//IN REALTIME SEND THE URLS TO THE PhotoViewer ACTIVITY SO THAT IT CAN DIRECT DOWNLOAD AND VIEW PHOTOS
                context1.startActivity(intent);


            }
        });


        txt1.setText(titles[position]);

        return row;

    }



}
