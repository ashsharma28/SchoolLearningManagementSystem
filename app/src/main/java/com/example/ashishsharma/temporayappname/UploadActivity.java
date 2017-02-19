package com.example.ashishsharma.temporayappname;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class UploadActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    EditText title;
    EditText file;
    SharedPreferences sp;
    String url1,url2,tid;
    String[] cls;
    String[] subjects;
    ProgressBar progressBar;
    Spinner clsSpinner,subSinner;
    Integer sid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        sp=getSharedPreferences("details",0);
        tid=sp.getInt("id",0)+"";
    }

    public void uploadFile(View view){
        progressBar.setVisibility(View.VISIBLE);
        final EditText cls= (EditText) findViewById(R.id.cls);
        title= (EditText) findViewById(R.id.title);
        file= (EditText) findViewById(R.id.file);
        int c=Integer.parseInt(cls.getText().toString());
        String titleString=title.getText().toString();
        String fileString=file.getText().toString();


        if (cls.getError()==null&&title.getError()==null) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("title", titleString);
                jsonObject.put("cls", c);
                jsonObject.put("tid", tid);
                Log.i("tid", "The tid was saved to preferences");
                url1 = "http://192.168.146.155:8080/Megabizz/webapi/attendance";
                url2 = "http://192.168.146.155:8080/Megabizz/webapi/attendance/upload";
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new UploadFile().execute(jsonObject);
        }
    }

    public void uploadNotes(View view){
        Spinner subject= (Spinner) findViewById(R.id.subject);
        if(title.getError()==null) {
            Spinner cls = (Spinner) findViewById(R.id.cls);
            String s1 = cls.getSelectedItem().toString();
            Log.i("cls", cls + ",Hey I was selected ");
            String s2 = subject.getSelectedItem().toString();
            String titleString = title.getText().toString();
            String fileString = file.getText().toString();
            JSONObject jsonObject = new JSONObject();
            url1 = "http://192.168.146.155:8080/Megabizz/webapi/notes";
            url2 = "http://192.168.146.155:8080/Megabizz/webapi/notes/upload";
            try {
                jsonObject.put("tid", tid);
                jsonObject.put("title", titleString);
                jsonObject.put("cls", s1);
                jsonObject.put("sid", sid);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressBar.setVisibility(View.VISIBLE);
            new UploadFile().execute(jsonObject);
        }
    }

    public void uploadForm(View view){
        setContentView(R.layout.result_attendance);
        file= (EditText) findViewById(R.id.file);
        title= (EditText) findViewById(R.id.title);
        EditText cls= (EditText) findViewById(R.id.cls);
        progressBar= (ProgressBar) findViewById(R.id.wait);
        title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String s=title.getText().toString();

                if(s.equals(""))title.setError("Title cannot be blank");
                else title.setError(null);
            }
        });

        cls.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText editText= (EditText) v;
                String s=editText.getText().toString();
                if(!s.equals("")) {
                    int n = Integer.parseInt(s);
                    if (n<0 && n>12) {
                        editText.setError("Enter a valid class");
                    }
                    else editText.setError(null);
                }
                else editText.setError("Enter a valid class");
            }
        });

    }

    public void NoteUploadForm(View view){
        setContentView(R.layout.notes);

        title= (EditText) findViewById(R.id.title);
        file= (EditText) findViewById(R.id.file);

        clsSpinner= (Spinner) findViewById(R.id.cls);
        subSinner= (Spinner) findViewById(R.id.subject);
        title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String s=title.getText().toString();
                if (s.equals(""))title.setError("Title cannot be blank");
                else title.setError(null);
            }
        });

        url1="http://192.168.146.155:8080/Megabizz/webapi/class/"+tid;
        progressBar= (ProgressBar) findViewById(R.id.wait);
        progressBar.setVisibility(View.VISIBLE);
        new DownloadClass().execute(url1);
    }

    public void onButtonChooseFile(View v) {
        Log.i("click","1");
        file.setText("");
        //Create FileOpenDialog and register a callback
        SimpleFileDialog fileOpenDialog =  new SimpleFileDialog(UploadActivity.this,"FileOpen..",
                new SimpleFileDialog.SimpleFileDialogListener()
                {
                    @Override
                    public void onChosenDir(String chosenDir)
                    {
                        // The code in this function will be executed when the dialog OK button is pushed
                        file.setText(chosenDir);
                    }
                }
        );
        Log.i("click","2");
        Log.i("click",file.getText().toString()+"appended");
        //You can change the default filename using the public variable "Default_File_Name"
        fileOpenDialog.default_file_name = file.getText().toString();
        Log.i("click","3");
        fileOpenDialog.chooseFile_or_Dir(fileOpenDialog.default_file_name);
        Log.i("click", "4");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView textView= (TextView) view;
        String s = textView.getText().toString();
        String url="http://192.168.146.155:8080/Megabizz/webapi/class/"+tid+"/"+s;
        new DownloadSubjects().execute(url);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    class UploadFile extends AsyncTask<JSONObject,Integer,JSONObject> {
        String result;
        JSONObject jsonObject;

        @Override
        protected JSONObject doInBackground(JSONObject... params) {
            String s = file.getText().toString();
            jsonObject = new JSONObject();
            JSONObject responseObject=null;
            try {
                result = CommomUtilites.post(url1, params[0]);
                Log.i("response", result + "blank inputstream");
                jsonObject = new JSONObject(result);
                if ((jsonObject.get("status")).equals(1)) {
                    responseObject=CommomUtilites.postFile(url2,s,jsonObject.getInt("id"));
                } else {
                    Toast.makeText(getApplicationContext(), "File not uploaded", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return responseObject;
        }

        @Override
        protected void onPostExecute(JSONObject responseObject) {
            try {
                if(responseObject.getInt("status")==1)Toast.makeText(getApplicationContext(), "File Successfully uploaded", Toast.LENGTH_LONG).show();
                else Toast.makeText(getApplicationContext(), "File not uploaded", Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressBar.setVisibility(View.GONE);
        }
    }

    class DownloadClass extends AsyncTask<String,String,String>{



        @Override
        protected String doInBackground(String... params) {
            JSONObject jsonObject=null;
            String result=CommomUtilites.GET(params[0]);

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            JSONArray jsonArray=new JSONArray();
            result ="{cls:"+result+"}";
            JSONObject jsonObject=null;
            try {
                jsonObject=new JSONObject(result);
                Log.i("response",jsonObject.toString());
                jsonArray=jsonObject.getJSONArray("cls");
                int length=jsonArray.length();
                cls=new String[length];
                JSONObject temp;
                for (int i=0;i<length;i++){
                    temp= (JSONObject) jsonArray.get(i);
                    cls[i]=temp.getString("cls");
                    Log.i("cls",cls[i]+", was received");
                }
                ArrayAdapter<String> arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.cls_subjects, cls);
                //arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                clsSpinner.setAdapter(arrayAdapter);
                clsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        TextView textView= (TextView) view;
                        String s=textView.getText().toString();
                        String url="http://192.168.146.155:8080/Megabizz/webapi/class/"+tid+"/"+s+"/subjects";
                        new DownloadSubjects().execute(url);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressBar.setVisibility(View.GONE);
        }
    }

    class DownloadSubjects extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute(){
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String result=CommomUtilites.GET(params[0]);

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            JSONArray jsonArray=new JSONArray();
            result ="{subjects:"+result+"}";
            JSONObject jsonObject=null;
            final HashMap<String,Integer>hashMap=new HashMap<>();
            try {
                jsonObject=new JSONObject(result);
                Log.i("response",jsonObject.toString());
                jsonArray=jsonObject.getJSONArray("subjects");
                int length=jsonArray.length();
                subjects=new String[length];
                JSONObject temp;
                for (int i=0;i<length;i++){
                    temp= (JSONObject) jsonArray.get(i);
                    hashMap.put(temp.getString("sname"),temp.getInt("id"));
                    subjects[i]=temp.getString("sname");
                    Log.i("subject",subjects[i]+", was received");
                }
                ArrayAdapter<String> arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.cls_subjects, subjects);
                //arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subSinner.setAdapter(arrayAdapter);
                subSinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        TextView textView= (TextView) view;
                        sid=hashMap.get(textView.getText().toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressBar.setVisibility(View.GONE);
        }
    }

}
