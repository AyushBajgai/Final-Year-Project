package com.example.finalyearproject;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.Toast;

import com.example.finalyearproject.databinding.FragmentAnalyzeSpeechBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class analyzeSpeechFragment extends Fragment {

    private OkHttpClient client;
    private String BASE_URL = "";

    private View rootView;
    private static final String TAG = "VoiceRecognition";

    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

   // private static final int REQ_CODE_SPEECH_INPUT = 100;

    SpeechRecognizer speechRecognizer;

    EditText recordedText;
    ImageButton mic;
    Button check_grammar;

    public static final Integer RecordAudioRequestCode = 1;

private FragmentAnalyzeSpeechBinding dashboardBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_analyze_speech, container, false);

         ((dashboard)getActivity()).changeTitle("Analyze Speech");

        //Getting an ID
        recordedText = (EditText) rootView.findViewById(R.id.record_text);
        mic = (ImageButton) rootView.findViewById(R.id.recorder);
        check_grammar = (Button) rootView.findViewById(R.id.grammar_check);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getActivity());

        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
                    checkPermission();
//                    checkSpeech();
                }
                checkSpeech();
            }
        });

        check_grammar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recordText = recordedText.getText().toString();
                //Getting request
                Log.d("text", "onClick: "+recordText);
                client = new OkHttpClient();
                //   RequestBody body2 = new FormBody.Builder().add("check","recordedText").add("language","en-US").build();
                RequestBody body = new FormBody.Builder().add("check",recordText).add("language","en-US").build();

                Request request = new Request.Builder()
                        .url("https://grammarbot.p.rapidapi.com/check")
                        .post(body)
                        .addHeader("content-type", "application/x-www-form-urlencoded")
                        .addHeader("X-RapidAPI-Host", "grammarbot.p.rapidapi.com")
                        .addHeader("X-RapidAPI-Key", "08f0c5d657mshcd07a10b9a6e829p16bdbajsnc8abf85306ce")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });




        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        speechRecognizer.destroy();
    }

    private void checkSpeech(){
        RecognitionListener recognitionListener = new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {
                Log.d("testspeech", "onResults:ready ");

            }

            @Override
            public void onBeginningOfSpeech() {
                Log.d("SpeechListening","onBeginningOfSpeech");
            }

            @Override
            public void onRmsChanged(float v) {
                Log.d("testspeech", "onResults:change ");

            }

            @Override
            public void onBufferReceived(byte[] bytes) {
                Log.d("testspeech", "onResults:buffer ");

            }

            @Override
            public void onEndOfSpeech() {
                Log.d("testspeech", "onResults: end");
            }

            @Override
            public void onError(int i) {
                Log.d("testspeech", "onResults:ereror ");
            }

            @Override
            public void onResults(Bundle bundle) {
                Log.d("testspeech", "onResults: "+bundle);
                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                Log.d("testspeech", "onResults: "+data.get(0));
                recordedText.setText(data.get(0));
            }

            @Override
            public void onPartialResults(Bundle bundle) {
                Log.d("testspeech", "onResults:partial ");

            }

            @Override
            public void onEvent(int i, Bundle bundle) {
                Log.d("testspeech", "onResults:event ");

            }
        };

        speechRecognizer.setRecognitionListener(recognitionListener);
        if (speechRecognizer.isRecognitionAvailable(getActivity()))
        {
            Log.d("SpeechListening","started listening hopefully");
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something!");
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

            //less than 5 or 12 can be set for accuracy
            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
            startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case VOICE_RECOGNITION_REQUEST_CODE:{
                if(resultCode == Activity.RESULT_OK && null !=data){
                    ArrayList<String> result =data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    recordedText.setText(result.get(0));
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == RecordAudioRequestCode && grantResults.length>0){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();
                checkSpeech();
            }
        }
    }

    private void checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.RECORD_AUDIO},RecordAudioRequestCode);
        }
    }
}