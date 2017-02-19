package com.example.ashishsharma.temporayappname;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by ashsharma28 on 06-07-2015.
 */
public class otp_ extends Activity {

    private String response = "";
    private String OTP  = "";
    String emailIdFromIntent;
    String sentOTPFromIntent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_);

        Intent intent = getIntent();
        sentOTPFromIntent= intent.getStringExtra(sign_up.EXTRA_MESSAGE1);
        emailIdFromIntent  = intent.getStringExtra(sign_up.EXTRA_MESSAGE2);

        TextView txt =(TextView) findViewById(R.id.emailIDtxt);
        txt.setText(emailIdFromIntent);


    }


    public void verifyOTP(View view)
    {

        EditText otp = (EditText) findViewById(R.id.otp123);
        String enteredOTP =  otp.getText().toString();



        if(sentOTPFromIntent.equalsIgnoreCase(enteredOTP))
        {

            Toast toast = Toast.makeText(this, "SignUp Successful", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            Intent intent123 = new Intent(this , student_dashboard.class);
            startActivity(intent123);

        }

        else{

            Toast toast = Toast.makeText(this, " Wrong OTP entered, Please try again", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

    }



    public  String sendMail(String to ,ConnectivityManager cnmgr   ) {


           /*
           ----------------------------------------------------------------------------------------------------
           ----------------------------------------------------------------------------------------------------
           ----------------------------------------------------------------------------------------------------
           ----------------------------------------------------------------------------------------------------
           */

                                         /*VALIDATING THE EMAIL ADDRESS*/
       try {
           InternetAddress internetAddress = new InternetAddress(to);
           internetAddress.validate();
       } catch (AddressException e) {
           new sign_up().makeToast("Please enter a valid e-mail address!!!");

           e.printStackTrace();
       }
           /*
           ----------------------------------------------------------------------------------------------------
           ----------------------------------------------------------------------------------------------------
           ----------------------------------------------------------------------------------------------------
           ----------------------------------------------------------------------------------------------------
           */

                                                 /*GENERATING THE OTP*/
               OTP = getOTP();

         /*
           ----------------------------------------------------------------------------------------------------
           ----------------------------------------------------------------------------------------------------
           ----------------------------------------------------------------------------------------------------
           ----------------------------------------------------------------------------------------------------
           */

                                        /* CHECKING THE NETWORK CONNECTION*/


                   NetworkInfo info = cnmgr.getActiveNetworkInfo();

                   if (info == null) {
                       new sign_up().makeToast( "No Network Connection!!!");

                   }
                   else if (info.isConnected() == false) {
                       new sign_up().makeToast("No Network Connection!!!");

                   }
           else
           {

           /*
           ----------------------------------------------------------------------------------------------------
           ----------------------------------------------------------------------------------------------------
           ----------------------------------------------------------------------------------------------------
           ----------------------------------------------------------------------------------------------------
           */
                                 /*CALLING ASYNCTASK TO MAIL TO THE EMAIL ID: to */

                                         new Mailer(to).execute();

            /*
           ----------------------------------------------------------------------------------------------------
           ----------------------------------------------------------------------------------------------------
           ----------------------------------------------------------------------------------------------------
           ----------------------------------------------------------------------------------------------------
           */

           }

       return OTP;
   }

    private String getOTP() {

        String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(5, 15) ;
        Log.i("OTP" , "OTP for this user is : " + uuid);

        return uuid;
    }

    private class Mailer extends AsyncTask<Void , Void, Void> {


        public Mailer(String to)
        {
            this.to = to;
        }

        final String username = "megabizzschoolapp@gmail.com";
        final String password = "adminsknowthepassword";
        final String to;
        String result123 ="";
        Message message;


        @Override
            protected void onPreExecute() {
            super.onPreExecute();

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Log.i("SOMETHING ", "I AM AT POSITION 1");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            Log.i("SOMETHING ", "I AM AT POSITION 2");

            try {

                message = new MimeMessage(session);
                Log.i("SOMETHING ", "I AM AT POSITION 3");
                message.setFrom(new InternetAddress("megabizzschoolapp@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject("TemporaryAppName: Please Confirm your Account");
                message.setContent( " <html xmlns= \"http://www.w3.org/1999/xhtml \" > " +
                        "<head> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"" +
                        " /> <title>Untitled Document</title> <style type=\"text/css\"> .something { f" +
                        "ont-family: \"Lucida Sans Unicode\", \"Lucida Grande\", sans-serif; font-size" +
                        ": small; font-style: normal; color: #333; font-weight: normal; font-variant" +
                        ": normal; white-space: nowrap; } .header { background-color: #F1EBD8; backgro" +
                        "und-image: url(http://ash646.site40.net/MegaBizz/logo.png); background-repeat: no-repeat; background-position: " +
                        "left -230px; } .Text_Arial { font-family: Arial, Helvetica, sans-serif; } #te" +
                        "xt123 { font-family: Trebuchet MS, Arial, Helvetica, sans-serif; } </style> <" +
                        "/head> <body> <pre>Just one more step to get started</pre> <table width=\"100%\" >" +
                        " <tr> <td width=\"505\" height=\"65\" valign=\"middle\" bgcolor=\"#003366\" c" +
                        "lass=\"header\">&nbsp;</td> </tr> </table> <h3>Hi there,</h3> <p><span class=" +
                        "\"Text_Arial\">  To complete your sign up, please verify your email id.<br />" +
                        " Enter the following code when asked:<br /> <br /> </span> <table width=\"200" +
                        "\" height=\"20\" border=\"0\"> <tr> <td bgcolor=\"#F5F5F5\" class=\"Text_Arial\"> "
                        + OTP +
                        " </td> </tr> </table> <br /> <br /> <p class=\"Text_Arial\"> <span class=\"" +
                        "Text_Arial\"><strong>Cheers</strong>,<br /> The TemporaryAppName Team</span" +
                        "></p> <p><img src=\"http://ash646.site40.net/MegaBizz/logo.jpg\" alt=\"MegaBizz\" width=\"86\" height=\"98\" " +
                        "hspace=\"30\" /> </p> <div > </div> <table width=\"100%\" border=\"0\" cell" +
                        "padding=\"20\"> <tr> <td width=\"106\" height=\"100\" align=\"left\" valign" +
                        "=\"top\" bgcolor=\"#F5F5F5\"><p class=\"something\">Explore our <a href=\"h" +
                        "ttp://megabizz.co.in/index.html#two\">Services</a></p>      <p class=\"some" +
                        "thing\">Visit our <a href=\"http://megabizz.co.in/about.html\">Blog</a></p>" +
                        "</td> <td width=\"139\" align=\"center\" valign=\"top\" bgcolor=\"#F5F5F5\"" +
                        "><table width=\"170\" cellpadding=\"0\" cellspacing=\"0\"> <tbody> <tr> <td" +
                        " align=\"center\" valign=\"top\" class=\"something\" id=\"text123\">Join ou" +
                        "r community</td> </tr> <tr> <td></td> </tr> </tbody> </table> <br /> <img s" +
                        "rc=\"http://ash646.site40.net/MegaBizz/unnamed.gif\" alt=\"Facebook\" width=\"31\" height=\"31\" align=\"base" +
                        "line\" /><img src=\"http://ash646.site40.net/MegaBizz/fb.gif\" width=\"31\" height=\"31\" /><img src=\"http://ash646.site40.net/MegaBizz/twitte" +
                        "r.gif\" width=\"31\" height=\"31\" /><img src=\"http://ash646.site40.net/MegaBizz/LinkedIn.gif\" width=\"31\"" +
                        " height=\"31\" /></td> <td width=\"146\" align=\"center\" valign=\"top\" bg" +
                        "color=\"#F5F5F5\"><p class=\"something\">Download Our App</p>    <p><img sr" +
                        "c=\"http://ash646.site40.net/MegaBizz/unnamed.png\" width=\"167\" height=\"47\" /></p></td> </tr> </table> <p" +
                        ">&nbsp;</p> </body> </html>", "text/html" );


                Log.i("SOMETHING ", "I AM AT POSITION 4");


            } catch (Exception e) {
                Log.e("HERE", " ERROR", e);
            }
        }

        @Override
            protected Void doInBackground (Void... voids)
            {

                try {

                    Log.i("SOMETHING ", "I AM AT POSITION 5");

                    Transport.send(message);
                    Log.i("SOMETHING ", "I AM AT POSITION 6");
                    result123 = "EMAIL SENT!";


                } catch (MessagingException e) {
                    e.printStackTrace();
                    result123 = "Connection Error, Please try again!";
                    Log.e("HERE", " ERROR in SMTP CONNECTION", e);
                }

                return null;
            }

        @Override
            protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            sign_up.THIS.makeToast(result123);


        }


    }


}


/*
         0) Show progress bar
        #1) network connection on or off
        #2) validate email id
        #3) Generate random password
        #4) return random password
        #5) display email reached notification
*/
