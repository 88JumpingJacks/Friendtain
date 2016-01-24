package com.theboss.jackli.friendtain;

import android.os.Bundle;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.nuance.nina.mmf.MMFController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    ImageView sendTextImg;
    String txtphoneNo;
    String txtMessage;
    List<ContactPerson> contactsList = new ArrayList<>();
    List<Integer> smsImageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendTextImg = (ImageView)findViewById(R.id.imageView_go);

        contactsList.add(new ContactPerson("Jack", "6138882316"));
        contactsList.add(new ContactPerson("Luis", "6133070645"));
        contactsList.add(new ContactPerson("Ly", "9057589989"));
        contactsList.add(new ContactPerson("Radu", "4389928065"));

        sendTextImg.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                sendSMSMessage();
            }
        });

//        //listen button
//        final Button dictationButton = (Button) rootView.findViewById(R.id.dictationButton);
//        dictationButton.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                if (!doneRecording){
//                    MMFController.getInstance().stopRecordingAudio();
//                    dictationButton.setText("Dictate");
//                    dictationButton.setEnabled(false);
//                    doneRecording = true;
//                }
//                else{
//                    MMFController.getInstance().startListeningForRecognition();
//                    dictationButton.setText("Done");
//                    doneRecording = false;
//                }
//            }
//        });
    }

    protected void sendSMSMessage()
    {
        Log.i("Send SMS", "");

        Random generator = new Random();

        ContactPerson lPerson = contactsList.get(generator.nextInt(contactsList.size()));
        txtphoneNo = lPerson.getPhoneNumber();
        txtMessage = "Hey haven't spoken to you in a while, how's it going?";

        try
        {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(txtphoneNo, null, txtMessage, null,
                    null);
            Toast.makeText(getApplicationContext(), "SMS sent", Toast
                    .LENGTH_LONG).show();
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "SMS failed, please try " +
                    "again", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
