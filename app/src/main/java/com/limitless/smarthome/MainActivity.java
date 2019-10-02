package com.limitless.smarthome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;


import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button speechButton;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);


        editText = findViewById(R.id.editText);
        speechButton = findViewById(R.id.speechButton);


        final SpeechRecognizer limitlessSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        final Intent limitlessSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        limitlessSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        limitlessSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());

limitlessSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int error) {

    }

    @Override
    public void onResults(Bundle results) {
            //getting array of words.
        try{
        ArrayList<String> wordsList =results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        //displaying the first 2 words of the array.Those ones are the necessary ones.
        if(wordsList != null){
            editText.setText(wordsList.get(0));
            //TODO Use this first two words with the api.
            String postReceiverUrl = "";
            if(wordsList.get(0).contains("on") && !wordsList.get(0).contains("off")){
                if(wordsList.get(0).contains("fan")){
                     postReceiverUrl = "https://appliance-modifier.herokuapp.com/on&fan";
                    Toast.makeText(getApplicationContext(),"Command to put fan on sent",Toast.LENGTH_LONG).show();

                }else if(wordsList.get(0).contains("pump")){
                     postReceiverUrl = "https://appliance-modifier.herokuapp.com/on&pump";
                    Toast.makeText(getApplicationContext(),"Command to put pump on sent",Toast.LENGTH_LONG).show();
                }else if(wordsList.get(0).contains("television") || wordsList.get(0).contains("TV") ){
                     postReceiverUrl = "https://appliance-modifier.herokuapp.com/on&tv";
                     wordsList.clear();
                    Toast.makeText(getApplicationContext(),"Command to put Tv on sent",Toast.LENGTH_LONG).show();
                }else if(wordsList.get(0).contains("bulb") || wordsList.get(0).contains("light") ){
                     postReceiverUrl = "https://appliance-modifier.herokuapp.com/on&bulb";
                }else if(wordsList.get(0).contains("fridge") || wordsList.get(0).contains("freezer") ){
                     postReceiverUrl = "https://appliance-modifier.herokuapp.com/on&fridge";
                    Toast.makeText(getApplicationContext(),"Command to put fridge on sent",Toast.LENGTH_LONG).show();
                }

            }else if(wordsList.get(0).contains("off") && !wordsList.get(0).contains("on")){
                if(wordsList.get(0).contains("fan")){
                     postReceiverUrl = "https://appliance-modifier.herokuapp.com/off&fan";
                    Toast.makeText(getApplicationContext(),"Command to put fan off sent",Toast.LENGTH_LONG).show();

                }else if(wordsList.get(0).contains("pump")){
                     postReceiverUrl = "https://appliance-modifier.herokuapp.com/off&pump";
                    Toast.makeText(getApplicationContext(),"Command to put pump off sent",Toast.LENGTH_LONG).show();

                }else if(wordsList.get(0).contains("television") || wordsList.get(0).contains("TV") ){
                     postReceiverUrl = "https://appliance-modifier.herokuapp.com/off&tv";
                    Toast.makeText(getApplicationContext(),"Command to put television off sent",Toast.LENGTH_LONG).show();
                }else if(wordsList.get(0).contains("bulb") || wordsList.get(0).contains("light") ){
                     postReceiverUrl = "https://appliance-modifier.herokuapp.com/off&bulb";
                    Toast.makeText(getApplicationContext(),"Command to put bulb off sent",Toast.LENGTH_LONG).show();
                }else if(wordsList.get(0).contains("fridge") || wordsList.get(0).contains("freezer") ){
                     postReceiverUrl = "https://appliance-modifier.herokuapp.com/off&fridge";
                    Toast.makeText(getApplicationContext(),"Command to put freezer off sent",Toast.LENGTH_LONG).show();
                }
            }else if(wordsList.get(0).contains("off") && wordsList.get(0).contains("on")){
                Toast.makeText(getApplicationContext(),"Cannot put device on and off at the same time,retry",Toast.LENGTH_SHORT).show();
               //TODO FIX THIS
                wordsList.get(0).replace("off","h");
            }
            else{
                Toast.makeText(getApplicationContext(),"No on or off command",Toast.LENGTH_SHORT).show();
            }
            useUrl(postReceiverUrl);
        }}catch(Exception e)

    {
    }
    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }
});

speechButton.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                limitlessSpeechRecognizer.stopListening();
                editText.setHint("You will see input here");
                break;

            case MotionEvent.ACTION_DOWN:
                limitlessSpeechRecognizer.startListening(limitlessSpeechRecognizerIntent);
                editText.setText("");
                editText.setHint("Listening...");
                break;
        }
        return false;
    }
});
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!((ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED)){
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                finish();
            }

        }
    }

    private void useUrl(String postReceiverUrl){

        try{
            Log.v(TAG, "postURL: " + postReceiverUrl);
            // HttpClient
            HttpClient httpClient = new DefaultHttpClient();

            // post header
            HttpPost httpPost = new HttpPost(postReceiverUrl);

            // execute HTTP post request
            HttpResponse response = httpClient.execute(httpPost);

            HttpEntity resEntity = response.getEntity();


            if (resEntity != null) {

                String responseStr = EntityUtils.toString(resEntity).trim();
                Log.v(TAG, "Response: " +  responseStr);
                //  textView.setText(responseStr);

                // you can add an if statement here and do other actions based on the response
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
