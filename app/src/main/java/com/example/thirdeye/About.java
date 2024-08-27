package com.example.thirdeye;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class About extends AppCompatActivity {

    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        // Initialize TextToSpeech
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        // Handle the error if the language is not supported
                    } else {
                        // Get the text from the TextView and speak it
                        TextView aboutMessage = findViewById(R.id.about_message);
                        String textToSpeak = aboutMessage.getText().toString();
                        textToSpeech.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, null);
                    }
                } else {
                    // Initialization failed
                }
            }
        });

        // Back button functionality
        ImageButton backButton = findViewById(R.id.backbtnfeedback);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the current activity and go back
            }
        });

        // Redirect button functionality
        Button redirectButton = findViewById(R.id.RedirectButton);
        redirectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the website in a browser
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://zoblik.com/"));
                startActivity(browserIntent);
            }
        });
    }
    @Override
    protected void onResume(){
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        // Handle the error if the language is not supported
                    } else {
                        // Get the text from the TextView and speak it
                        TextView aboutMessage = findViewById(R.id.about_message);
                        String textToSpeak = aboutMessage.getText().toString();
                        textToSpeech.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, null);
                    }
                } else {
                    // Initialization failed
                }
            }
        });
        super.onResume();
    }
    @Override
    protected void onPause(){
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        // Shutdown TextToSpeech to release resources
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
