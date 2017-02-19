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


public class EventsFragment extends Fragment
{
    String[] theText;
    InputStream newsBoy;
    ListView lst1;
    EventsFragment thisObject = this;
    byte[] byteArray = new byte[0];
    public View rootView;


    public static EventsFragment newInstance()
    {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public EventsFragment() {
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


        /*TheText = new String[]{"Inter House Debate Competition ",
                "Annual Sports Day",
                "Science Exhibition 2015 ",
                "Fancy Dress Competition"};*/



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
            new doDownload().execute(Class);

        }


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private class doDownload extends AsyncTask<Integer, Void, String[] >
    {


        @Override
        protected String[] doInBackground(Integer... urls)
        {

            Log.i("I reached at: " , "1");
            theText = CommomUtilites.getEventTitles(urls[0]);
            return theText;

        }

        @Override
        protected void onPostExecute(String[]result){

            SomeAdapter2 adapter = new SomeAdapter2(thisObject.getActivity(), theText/*, imageURLArray*/);
            lst1.setAdapter(adapter);

        }




    }


}


class SomeAdapter2 extends ArrayAdapter<String> {
    Context context1;
    String[] titles;
    View ourRow = null;
    public final static String EXTRA_MESSAGE1 = "these things dont matter largely..at this stage thus are ignorable";
    Intent intent;

    SomeAdapter2(Context context, String[] description) {
        super(context, R.layout.try_textview, R.id.textView123, description);
        Log.i("I reached at: ", "pre6 ");

        this.context1 = context;
        //this.imageURL = imageURL;
        this.titles = description;

            intent = new Intent(context1, EventViewer.class);

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


                intent.putExtra(EXTRA_MESSAGE1 , titles[position]);
                context1.startActivity(intent);


            }
        });


        txt1.setText(titles[position]);

        return row;

    }



}
