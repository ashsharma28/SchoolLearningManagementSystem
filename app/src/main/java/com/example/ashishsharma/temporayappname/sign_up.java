package com.example.ashishsharma.temporayappname;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class sign_up extends ActionBarActivity {
    EditText name,email,password,confirmPassword,contact,cls,address,rNo;
    Button button2;
    TextView wait;
    ProgressBar pBar;
    String sentOTP;

    Intent intent;
    public final static String EXTRA_MESSAGE1 = "qwertyuiop";
    public final static String EXTRA_MESSAGE2 = "qwaxertyuiop";


    String emailAddress;
    EditText editText;
    ConnectivityManager cnmgr;
    static sign_up THIS ;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String contactPattern="^[6789]\\d{9}$";
    String addressPattern="[a-zA-Z0-9\\d ,.]*$";

    CheckBox terms;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_sign_up);

        intent = new Intent( this, otp_.class );

        name=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        confirmPassword=(EditText)findViewById(R.id.confirmPassword);
        contact=(EditText)findViewById(R.id.contact);
        cls=(EditText)findViewById(R.id.cls);
        address=(EditText)findViewById(R.id.address);
        rNo= (EditText) findViewById(R.id.rno);
        button2 = (Button) findViewById(R.id.button2);
        wait= (TextView)findViewById(R.id.textView);
        pBar = (ProgressBar)findViewById(R.id.pBarOfWait);

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String s=email.getText().toString();
                if(!s.matches(emailPattern)) {
                    email.setError("Invalid email");
                }
                else email.setError(null);
            }
        });
        address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String s=address.getText().toString();
                if(!s.matches(addressPattern)) {
                    address.setError("Enter a valid address");
                }
                else address.setError(null);
            }
        });
        contact.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String s=contact.getText().toString();
                if(!s.matches(contactPattern)) {
                    contact.setError("Enter a valid 10 digit contact");
                }
                else contact.setError(null);
            }
        });

        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String s=name.getText().toString();
                if(s.equals("")) {
                    name.setError("Field can't be blank");
                }
                else name.setError(null);
            }
        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String s=email.getText().toString();
                if(s.equals("")) {
                    password.setError("Passwords cannot be blank");
                }
                else password.setError(null);
            }
        });
        confirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String s=password.getText().toString();
                if(!confirmPassword.getText().toString().equals(s)) {
                    confirmPassword.setError("Passwords do not match");
                }
                else confirmPassword.setError(null);
            }
        });
        cls.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String s=cls.getText().toString();
                if(!s.equals("")) {
                    int n = Integer.parseInt(s);
                    if (n<0 && n>12) {
                        cls.setError("Enter a valid class");
                    }
                    else cls.setError(null);
                }
                else cls.setError("Enter a valid class");
            }
        });
    }


    public void register(View view){

        if(email.getError()==null && address.getError()==null&&cls.getError()==null&&
                password.getError()==null&&confirmPassword.getError()==null&&
                name.getError()==null&&contact.getError()==null&&terms.isChecked()==true) {
            String s1 = name.getText().toString();
            String s2 = email.getText().toString();
            String s3 = password.getText().toString();
            String s4 = contact.getText().toString();
            String s5 = address.getText().toString();
            String s6 = cls.getText().toString();
            String s7 = confirmPassword.getText().toString();
            String s8= rNo.getText().toString();
            Log.i("","register method called without errors");
            new ExecuteTask().execute(s1, s2, s3, s4, s5, s6,s8);
        }

        else Log.i("","register method called with errors");

    }
    class ExecuteTask extends AsyncTask<String,Integer,String>{
        @Override
        protected String doInBackground(String... params) {
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("name",params[0]);
                jsonObject.put("email",params[1]);
                jsonObject.put("password",params[2]);
                jsonObject.put("id",params[3]);
                jsonObject.put("address",params[4]);
                jsonObject.put("cls",params[5]);
                jsonObject.put("rno",params[6]);
                Log.i("request","json prepared");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String result=CommomUtilites.post("http://192.168.146.155:8080/Megabizz/webapi/register/student",jsonObject);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            JSONObject jsonObject= null;
            int status=0;
            try {
                jsonObject = new JSONObject(result);
                status=(Integer)jsonObject.get("status");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(status==1) {
                Toast.makeText(getApplicationContext(),"Successfully registered",Toast.LENGTH_SHORT).show();
                name.setVisibility(View.INVISIBLE);
                email.setVisibility(View.INVISIBLE);
                password.setVisibility(View.INVISIBLE);
                confirmPassword.setVisibility(View.INVISIBLE);
                contact.setVisibility(View.INVISIBLE);
                cls.setVisibility(View.INVISIBLE);
                address.setVisibility(View.INVISIBLE);
                rNo.setVisibility(View.INVISIBLE);
                terms.setVisibility(View.INVISIBLE);
                button2.setVisibility(View.INVISIBLE);
                wait.setVisibility(View.VISIBLE);
                pBar.setVisibility(View.VISIBLE);
                sentOTP = doMail();
            }
            else
            {
                Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();
            }

        }
    }
    public  String doMail(){
        emailAddress = email.getText().toString();

        ConnectivityManager cnmgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        THIS = this;

        String OTP = new otp_().sendMail(emailAddress , cnmgr );

        Log.i("OTP", "FINAL OTP IS : " + OTP);

        return OTP;


    }

    public  void makeToast(String msj)
    {

        if(msj.equals("EMAIL SENT!"))
        {
            intent.putExtra(EXTRA_MESSAGE1 , sentOTP);
            intent.putExtra(EXTRA_MESSAGE2 , email.getText().toString() );

            Toast toast11 = Toast.makeText(THIS, msj, Toast.LENGTH_LONG);
            toast11.show();

            startActivity(intent);
        }
        else {
            Toast toast = Toast.makeText(THIS, msj, Toast.LENGTH_LONG);
            toast.show();
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
        return true;
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
