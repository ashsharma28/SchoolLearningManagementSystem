package com.example.ashishsharma.temporayappname;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class notification extends Fragment {
public View rootView;
    public Button notes;
    public Button results;
    public Button attendance;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }
    public static notification newInstance() {
        notification fragment = new notification();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        attendance=(Button)rootView.findViewById(R.id.attendance);
        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toStud_attendance(view);
            }
        });

        results=(Button)rootView.findViewById(R.id.results);
        results.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               toStud_Result(view);
           }
       });

        notes=(Button)rootView.findViewById(R.id.notes);
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toStud_notes(view);
            }
        });
        return rootView;

    }

    public void toStud_Result(View view)
    {
        Intent intent = new Intent(rootView.getContext(), Results.class );
        startActivity(intent);
    }


    public void toStud_attendance(View view)
    {
        Intent intent = new Intent(rootView.getContext(),  stud_attendance.class );
        startActivity(intent);

    }


    public void toStud_notes(View view)
    {
        Intent intent = new Intent(rootView.getContext(), Notes.class );
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
