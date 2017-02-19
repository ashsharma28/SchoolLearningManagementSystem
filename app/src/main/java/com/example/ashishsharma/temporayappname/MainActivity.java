package com.example.ashishsharma.temporayappname;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar2;
    EditText userName,password;
    CheckBox checkBox;
    static String url;
    SharedPreferences sp1;
    ConnectivityManager cnmgr;
    NetworkInfo info;

    SharedPreferences sp2;
    JSONObject reader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);


        MainActivity.this.setContentView(R.layout.activity_main);
        progressBar2=(ProgressBar)findViewById(R.id.progressBar2);
        progressBar2.setVisibility(View.INVISIBLE);


        //progressBar2.setVisibility(View.GONE);
        checkBox = (CheckBox) findViewById(R.id.checkBox1);
        userName=(EditText)findViewById(R.id.userName);
        password=(EditText)findViewById(R.id.password);

        cnmgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        info = cnmgr.getActiveNetworkInfo();

        if (info == null) {
            Toast toast = Toast.makeText(getApplicationContext(), "No Network Connection!!!", Toast.LENGTH_LONG);
            toast.show();
        }
        else if (info.isConnected() == false) {
            Toast toast = Toast.makeText(getApplicationContext(), "No Network Connection!!!", Toast.LENGTH_LONG);
            toast.show();
        }

        sp1 = getSharedPreferences("RememberMe", 0);
        String password1 = sp1.getString("password" , null );
        String name1 = sp1.getString("name" , null );
        userName.setText(name1);
        password.setText(password1);

    }


    public void signUp(View view){
        Intent intent=new Intent(this,sign_up.class);
        startActivity(intent);
    }


    public void login(View view){
        String userName = this.userName.getText().toString();

        /**
         * @password is not encrypted as of now !
         */
        String password = this.password.getText().toString();
        progressBar2.setVisibility(View.VISIBLE);
       /* if (info == null) {
            Toast toast = Toast.makeText(getApplicationContext(), "No Network Connection!!!", Toast.LENGTH_LONG);
            toast.show();
        }
        else if (info.isConnected() == false) {
            Toast toast = Toast.makeText(getApplicationContext(), "No Network Connection!!!", Toast.LENGTH_LONG);
            toast.show();
        }
        else*/ {

            if (checkBox.isChecked()) {
                SharedPreferences.Editor editor = sp1.edit().putString("name", userName).putString("password", password);
                editor.commit();
            }
            new ExecuteTask().execute(userName, password);
        }
    }


    class ExecuteTask extends AsyncTask<String, Integer, String>{
        String uname; String pwd;
        @Override
        protected String doInBackground(String... params) {
            uname = params[0];
            pwd = params[1];
            if ( uname.equals("teacher") && pwd.equals("qwerty") )
            {
                Intent intent=new Intent(getApplicationContext(),teacher_dashboard.class);
                startActivity(intent);
            }
            else if( ( uname.equals("student") && pwd.equals("qwerty") ) )
            {
                Intent intent=new Intent(getApplicationContext(),student_dashboard.class);
                startActivity(intent);
            }
            JSONObject jsonObject=new JSONObject();
            url = "http://192.168.146.155:8080/Megabizz/webapi/login";
            try {
                jsonObject.put("id",params[0]);
                jsonObject.put("password",params[1]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String res = CommomUtilites.post(url,jsonObject);
            return res;
        }
        @Override
        protected void onPostExecute(String result) {
            progressBar2.setVisibility(View.GONE);
            try {
                reader=new JSONObject(result);
                int status=(Integer)reader.get("status");
                sp2=getSharedPreferences("details",0);
                SharedPreferences.Editor editor=sp2.edit();
                editor.putInt("id",reader.getInt("id"));
                editor.commit();
                Log.i("id",reader.getInt("id")+", ID is received");
                    Log.i(" ENTERED THE ELSE ", "!@#$%^&*()");
                    if(status==0)
                        Toast.makeText(getApplicationContext(),"Invalid!Credentials", Toast.LENGTH_LONG).show();

                    else if(status==1)
                        Toast.makeText(getApplicationContext(),"You are not approved user", Toast.LENGTH_LONG).show();
                    else
                    {
                        String userType=(String)reader.get("userType");
                        if(userType.equals("teacher")  )
                        {
                            Intent intent=new Intent(getApplicationContext(),teacher_dashboard.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Intent intent=new Intent(getApplicationContext(),student_dashboard.class);
                            startActivity(intent);
                        }
                    }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
    }
}
