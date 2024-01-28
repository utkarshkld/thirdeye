package com.example.thirdeye;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.example.thirdeye.databinding.CheckMainBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Main entry point into our app. This app follows the single-activity pattern, and all
 * functionality is implemented in the form of fragments.
 */
public class cMainActivity extends AppCompatActivity {

    private CheckMainBinding activityMainBinding;
    public static HashMap<String, String> languageMap = new HashMap<>();
    public static Spinner spinnerLanguages;
    private ArrayAdapter<String> languageAdapter;
    private List<String> selectedLanguages;
    private MainViewModel viewModel;
    public static boolean isPlay = true;
    ImageView backbtn;
    Button exitbtn;
    private String outputlang = MainActivity.output_lang;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = CheckMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        isPlay = true;
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        assert navHostFragment != null;
//        NavController navController = navHostFragment.getNavController();


        // Initialize ViewModel using ViewModelProvider
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        backbtn = findViewById(R.id.backbtn_);
        exitbtn = findViewById(R.id.exitbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPlay = false;
                ObjectDetectorHelper.textToSpeech.stop();
                onBackPressed();
            }
        });
        exitbtn.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           isPlay = false;
                                           ObjectDetectorHelper.textToSpeech.stop();
                                           onBackPressed();
                                       }
                                   });
            initializeLanguageMap();
        spinnerLanguages = findViewById(R.id.objdetspinnerlang);
        selectedLanguages = new ArrayList<>(languageMap.values());
        languageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languageMap.keySet().toArray(new String[0]));
        languageAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerLanguages.setAdapter(languageAdapter);
        spinnerLanguages.setSelection(selectedLanguages.indexOf(outputlang));
    }

    private void initializeLanguageMap() {
        // Add languages and their Locale codes to the HashMap
        languageMap.put("Bengali", "bn");
        languageMap.put("Hindi", "hi");
        languageMap.put("Gujarati", "gu");
        languageMap.put("English", "en");
        languageMap.put("Kannada", "kn");
        languageMap.put("Marathi", "mr");
        languageMap.put("Tamil", "ta");
        languageMap.put("Telugu", "te");
        languageMap.put("Urdu", "ur");
        languageMap.put("Malayalam", "ml");

        languageMap.put("Afrikaans", "af");
        languageMap.put("Arabic", "ar");

        languageMap.put("Bulgarian", "bg");
//        languageMap.put("Catalan", "ca");
        languageMap.put("Czech", "cs");

        languageMap.put("Danish", "da");
        languageMap.put("German", "de");
        languageMap.put("Greek", "el");

        languageMap.put("Spanish", "es");


        languageMap.put("Finnish", "fi");
        languageMap.put("French", "fr");

        languageMap.put("Galician", "gl");//note
//        languageMap.put("Hebrew", "he");
//        languageMap.put("Croatian", "hr");
//        languageMap.put("Haitian", "ht");
        languageMap.put("Hungarian", "hu");
        languageMap.put("Indonesian", "id");//in
        languageMap.put("Icelandic", "is");
        languageMap.put("Italian", "it");
        languageMap.put("Japanese", "ja");
//        languageMap.put("Georgian", "ka");
        languageMap.put("Korean", "ko");
        languageMap.put("Lithuanian", "lt");
        languageMap.put("Latvian", "lv");
//        languageMap.put("Macedonian", "mk");
        languageMap.put("Malay", "ms");
//        languageMap.put("Maltese", "mt");
        languageMap.put("Dutch", "nl");
        languageMap.put("Norwegian", "no");//nb
        languageMap.put("Polish", "pl");
        languageMap.put("Portuguese", "pt");
        languageMap.put("Romanian", "ro");
        languageMap.put("Russian", "ru");
        languageMap.put("Slovak", "sk");
//        languageMap.put("Slovenian", "sl");
//        languageMap.put("Albanian", "sq");
        languageMap.put("Swedish", "sv");
//        languageMap.put("Swahili", "sw");
        languageMap.put("Thai", "th");
//        languageMap.put("Tagalog", "tl");
        languageMap.put("Turkish", "tr");
        languageMap.put("Ukrainian", "uk");
        languageMap.put("Vietnamese", "vi");
        languageMap.put("Chinese", "zh");
        // Add more languages as needed
    }
    @Override
    protected void onPause() {
        backbtn.performClick();
        super.onPause();
    }
    @Override
    public void onBackPressed() {
        isPlay = false;

        super.onBackPressed();

    }
}
