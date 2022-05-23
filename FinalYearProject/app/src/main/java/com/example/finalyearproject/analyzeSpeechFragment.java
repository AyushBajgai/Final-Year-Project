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
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.Toast;

import com.example.finalyearproject.databinding.FragmentAnalyzeSpeechBinding;

import java.util.ArrayList;
import java.util.Locale;


public class analyzeSpeechFragment extends Fragment {

    private View rootView;
    private static final String TAG = "VoiceRecognition";



    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

   // private static final int REQ_CODE_SPEECH_INPUT = 100;

    SpeechRecognizer speechRecognizer;

    EditText recordedText;
    ImageButton mic;

    public static final Integer RecordAudioRequestCode = 1;

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
private FragmentAnalyzeSpeechBinding dashboardBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_analyze_speech, container, false);

         ((dashboard)getActivity()).changeTitle("Analyze Speech");

        //Getting an ID
        recordedText = (EditText) rootView.findViewById(R.id.record_text);
        mic = (ImageButton) rootView.findViewById(R.id.recorder);
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