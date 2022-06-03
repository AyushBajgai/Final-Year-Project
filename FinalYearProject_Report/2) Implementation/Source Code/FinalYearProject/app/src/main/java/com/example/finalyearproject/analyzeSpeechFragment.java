package com.example.finalyearproject;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import android.os.Environment;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalyearproject.Models.Description;
import com.example.finalyearproject.Models.Error;
import com.example.finalyearproject.Models.NewsApiResponse;
import com.example.finalyearproject.Models.Report;
import com.example.finalyearproject.Models.RequestManager;
import com.example.finalyearproject.Models.ResponseGrammar;
import com.example.finalyearproject.databinding.FragmentAnalyzeSpeechBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


public class analyzeSpeechFragment extends Fragment {

    private OkHttpClient client;
    private String BASE_URL = "";

    //Listing errors
    ArrayList errors = new ArrayList<>();

    public ArrayList<Error> errorArrayList = new ArrayList<>();

    int startIndex, endIndex;

    private View rootView;
    //Dialog dialog = new Dialog(getActivity());
    File dirpath;
    private static final String TAG = "VoiceRecognition";

    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

   // private static final int REQ_CODE_SPEECH_INPUT = 100;

    SpeechRecognizer speechRecognizer;
    private DatabaseReference mDatabase;

    EditText recordedText;
    ImageButton mic;
    TextView description, suggestion;
    Button check_grammar;

    public static final Integer RecordAudioRequestCode = 1;
    private FirebaseUser user;

private FragmentAnalyzeSpeechBinding dashboardBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_analyze_speech, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();

         ((dashboard)getActivity()).changeTitle("Analyze Speech");

         String Test = "Hello this is an example";
                String subString = Test.substring(6,10);
              //  String subString2 = Test.substring(11,15);

      //  Log.d("text", "onClick: "+subString+subString2);

        //Getting an ID
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        recordedText = (EditText) rootView.findViewById(R.id.record_text);
        mic = (ImageButton) rootView.findViewById(R.id.recorder);
        check_grammar = (Button) rootView.findViewById(R.id.grammar_check);
        description = (TextView) rootView.findViewById(R.id.grammar_description);
        suggestion = (TextView) rootView.findViewById(R.id.grammar_suggestion);
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
                client = new OkHttpClient();

                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.textgears.com/grammar/")
                        .addConverterFactory(GsonConverterFactory.create()).build();
               GrammarApi api= retrofit.create(GrammarApi.class);
                Call<ResponseGrammar> call = api.call(recordText,"en-US","zJbdUnwOejNPn9Sb");
                call.enqueue(new Callback<ResponseGrammar>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseGrammar> call, @NonNull Response<ResponseGrammar> response) {
                        Log.d(TAG, "onResponse: test");
                        ResponseGrammar responseGrammar = response.body();

                        if (responseGrammar != null) {
                           errorArrayList.add((Error) responseGrammar.getResponse().getErrors().get(1));
                            if (errorArrayList != null) {
                                for (int i=0; i<errorArrayList.size(); i++){
                                    description.setText(errorArrayList.get(i).getDescription().getDescription());
                                    String better = TextUtils.join("\n", errorArrayList.get(i).getBetter());
                                    suggestion.setText("Possible Suggestions:\n" + better);
                                    Log.d("response2", "onResponse: "+errorArrayList.get(i).getDescription());
                                }
                            }
                        }

                        Report report = new Report(recordText, suggestion.getText().toString(), description.getText().toString());
                        mDatabase.child(user.getUid()).child("Reports").push().setValue(report);
                    }
                    @Override
                    public void onFailure(Call<ResponseGrammar> call, Throwable t) {
                        Toast.makeText(getActivity(), "Grammar error", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });




        return rootView;
    }
    //Calling the API via queries
    public interface GrammarApi{
/*        @Headers({
                "content-type: application/json;charset=UTF-8",
              //  "content-type: application/x-www-form-urlencoded",
                "X-RapidAPI-Host: grammarbot.p.rapidapi.com",
                "X-RapidAPI-Key: 8d911776acmshef38150aa1b1abdp121c36jsn24400eaad090"
        })*/
        @GET("/grammar")
        Call<ResponseGrammar> call(
                //Passing the queries
                @Query("text") String text,
                @Query("language") String language,
                @Query("key") String key
        );
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

    public void layoutToImage(View view) {
        // get view group using reference
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
        // convert view group to bitmap
        relativeLayout.setDrawingCacheEnabled(true);
        relativeLayout.buildDrawingCache();
        Bitmap bm = relativeLayout.getDrawingCache();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        dirpath = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM);
        File f = new File(dirpath, File.separator + "image.jpg");
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void imageToPDF() throws FileNotFoundException {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(dirpath + "/parse.pdf")); //  Change pdf's name.
            document.open();
            Image img = Image.getInstance(dirpath + File.separator + "image.jpg");
            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - 0) / img.getWidth()) * 100;
            img.scalePercent(scaler);
            img.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
            document.add(img);
            document.close();
            Toast.makeText(getActivity(), "PDF Generated successfully!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d("Error: ", "imageToPDF: " + e.toString());
        }
    }
}
