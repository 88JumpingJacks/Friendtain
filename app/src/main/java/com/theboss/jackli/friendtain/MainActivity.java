package com.theboss.jackli.friendtain;

import android.os.Bundle;
import android.os.Looper;
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

import com.nuance.nina.mmf.NinaConfiguration;
import com.theboss.jackli.friendtain.configurations.NinaSetup;

import com.nuance.nina.mmf.MMFController;
import com.nuance.nina.mmf.PromptType;
import com.nuance.nina.mmf.listeners.Interpretation;
import com.nuance.nina.mmf.listeners.InterpretationError;
import com.nuance.nina.mmf.listeners.InterpretationListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity implements InterpretationListener
{
    ImageView sendTextImg;
    String txtphoneNo;
    String txtMessage;
    List<ContactPerson> contactsList = new ArrayList<>();
    List<String> messagesList = new ArrayList<>();
    List<Integer> smsImageList = new ArrayList<>();

    private static boolean recording = false;
    private static boolean doneRecording = true;

    private static boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendTextImg = (ImageView)findViewById(R.id.imageView_go);

        contactsList.add(new ContactPerson("Jack", "6138882316"));
        contactsList.add(new ContactPerson("Luis", "6133070645"));
//        contactsList.add(new ContactPerson("Ly", "9057589989"));
        contactsList.add(new ContactPerson("Radu", "4389928065"));

        messagesList.add("Hey haven't spoken to you in a while, how's it going?");
        messagesList.add("Hi, how are you?");

        sendTextImg.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                sendSMSMessage();
            }
        });

//        connect();

        //listen button
        final ImageView dictationButton = (ImageView) findViewById(R.id.dictationButton);
        dictationButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                Log.d("***onclick", "YO");
//                if (!doneRecording){
//                    MMFController.getInstance().stopRecordingAudio();
//                    dictationButton.setText("Dictate");
//                    dictationButton.setEnabled(false);
//                    Log.d("***onclick", "in if");
////                    String lTextTranscription = MMFController.getInstance().playPrompt(, PromptType.TEXT);
//                    doneRecording = true;
//                    try
//                    {
//                        Thread.sleep(1500);
//                    }
//                    catch (InterruptedException e)
//                    {
//                        // Do nothing
//                    }
//                    dictationButton.setEnabled(true);
//                }
//                else{
//                    Log.d("***onclick", "in else");
//                    MMFController.getInstance().startListeningForRecognition();
//                    dictationButton.setText("Done");
//                    doneRecording = false;
//                }
            }
        });
    }

    protected void sendSMSMessage()
    {
        Log.i("Send SMS", "");

        Random generator = new Random();

        ContactPerson lPerson = contactsList.get(generator.nextInt(contactsList.size()));
        txtphoneNo = lPerson.getPhoneNumber();

        txtMessage = messagesList.get(generator.nextInt(messagesList.size()));

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

    @Override
    public void onInterpretation(Interpretation interpretation)
    {
        Log.d("****TRANSCRIPTION", interpretation.mmfRecognitionResult.getBestTranscription());
    }

    @Override
    public void onInterpretationError(InterpretationError interpretationError)
    {
        // empty
    }

    /**
     * Checks if the application is connected to Nina
     * @return
     */
    public boolean isConnected(){
        return connected;
    }

//    /**
//     * Method to connect to Nina
//     */
//    private void connect(){
//        NinaConfiguration config = MMFController.getInstance().getNinaConfiguration();
//        config.setGatewayApplicationId(NinaSetup.getNmaidId());
//        config.setGatewayApplicationKey(NinaSetup.getNmaidKey());
//
//        config.setGatewayAddress(NinaSetup.getGatewayHost(), 443);
//        config.setTtsDefaultVoice("Samantha");
//
//        if (isConnected())
//        {
//            MMFController.getInstance().disconnect();
//            Log.d("***connect()", "Diconnecting...");
//        }
//        else
//        {
//            MMFController.getInstance().connect(getApplicationContext(), NinaSetup.getCloudApplicationInfo());
//            Log.d("***connect()", "Connecting...");
//        }
//    }

//    @Override
//    public void onInterpretation(final Interpretation data) {
//                String prompt = "";
//                switch(data.resultStatus){
//                    case NO_INPUT_TIMEOUT:
//                        prompt =  "I didn't hear anything. Can you please repeat your question?";
//                        MMFController.getInstance().playPrompt(prompt, PromptType.TEXT);
//                        break;
//                    case NO_MATCH:
//                        prompt =  "Sorry, I wasn't able to recognize what you said";
//                        MMFController.getInstance().playPrompt(prompt, PromptType.TEXT);
//                        break;
//                    case SUCCESS:
//                        //Interpretation result of input text or recognized speech using NLU grammar
//                        //Contains:
//                        // - Filtered input text: filteredInputText
//                        // - Raw input text entered by user or transcription of speech recognition result: inputText
//                        // - Meaning returned with the highest confidence score or null if no meanings extracted: getBestMeaning()
//                        if (data.mmfFindMeaningResult != null){
//                        }
//                        //Container object for the recognition portion of the Nina Cloud result
//                        //Contains:
//                        // - The text of the transcription: literal
//                        // - The confidence level, the higher the number the higher the perceived accuracy: confidence
//                        else if(data.mmfRecognitionResult != null) {
//                            ((MainActivity)myActivity).log(DateFormat.getDateTimeInstance().format(new Date()) + ": Dictation recognized");
//                            playResult(data);
//                        }
//                        else{
//                            ((MainActivity)myActivity).log(DateFormat.getDateTimeInstance().format(new Date()) + ": Interpretation unknown");
//                            playResult(data);
//                        }
//                        break
//                    case SPEECH_NOT_DETECTED:
//                        prompt =  "Sorry, speech not detected";
//                        MMFController.getInstance().playPrompt(prompt, PromptType.TEXT);
//                        displayMessage(((MainActivity)myActivity).getCurrentFragment(), prompt );
//                        break;
//                    case RETRY:
//                        prompt =  "Sorry, please try again later";
//                        MMFController.getInstance().playPrompt(prompt, PromptType.TEXT);
//                        displayMessage(((MainActivity)myActivity).getCurrentFragment(), prompt );
//                        break;
//                    case NO_MEANING:
//                        prompt =  "Sorry, no meaning";
//                        MFController.getInstance().playPrompt(prompt, PromptType.TEXT);
//                        displayMessage(((MainActivity)myActivity).getCurrentFragment(), prompt );
//                        break;
//                    case SPEECH_TOO_EARLY:
//                        prompt =  "Sorry, speech too early";
//                        MMFController.getInstance().playPrompt(prompt, PromptType.TEXT);
//                        displayMessage(((MainActivity)myActivity).getCurrentFragment(), prompt );
//                        break;
//                    case TOO_MUCH_SPEECH_TIMEOUT:
//                        prompt =  "Sorry, too much speech";
//                        MMFController.getInstance().playPrompt(prompt, PromptType.TEXT);
//                        displayMessage(((MainActivity)myActivity).getCurrentFragment(), prompt );
//                        break;
//                    default:
//                        prompt =  "Sorry, no match";
//                        MMFController.getInstance().playPrompt(prompt, PromptType.TEXT);
//                        displayMessage(((MainActivity)myActivity).getCurrentFragment(), prompt );
//                        break;
//            }
//    }
//    /**
//     * Triggered when there is an interpretation error
//     */
//    @Override
//    public void onInterpretationError(final InterpretationError data) {
//        Handler handler = new Handler(Looper.getMainLooper());
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                if (data.reason != Reason.CANCELLED){
//                    ((MainActivity)myActivity).log(DateFormat.getDateTimeInstance().format(new Date()) + ": Interpretation Error");
//                    displayAndPlayErrorMessage(((MainActivity)myActivity).getCurrentFragment(), data);
//                }
//                else{
//                    ((MainActivity)myActivity).log(DateFormat.getDateTimeInstance().format(new Date()) + ": Interpretation Cancelled");
//                    MyEnergyLevelListener.updateLevelSoundBar(0);
//                }
//            }
//        } );
//    }
}
