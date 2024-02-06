package com.example.thirdeye;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
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
import java.util.Map;


public class cMainActivity extends AppCompatActivity {

    private CheckMainBinding activityMainBinding;
    public static Map<String, Long> objectResolver = new HashMap<>();
    public static HashMap<String, String> languageMap = new HashMap<>();
    public static Spinner spinnerLanguages;
    private ArrayAdapter<String> languageAdapter;
    private List<String> selectedLanguages;
    private MainViewModel viewModel;
    public static boolean isPlay = true;
    private ImageView backbtn;
    private Button exitbtn;
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
                if(ObjectDetectorHelper.textToSpeech!=null) {
                    ObjectDetectorHelper.textToSpeech.stop();
                    ObjectDetectorHelper.textToSpeech.shutdown();
                }
                onBackPressed();
            }
        });
        exitbtn.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           isPlay = false;
                                           if(ObjectDetectorHelper.textToSpeech!=null) {
                                               ObjectDetectorHelper.textToSpeech.stop();
                                               ObjectDetectorHelper.textToSpeech.shutdown();
                                           }
                                           onBackPressed();
                                       }
                                   });

        initializeLanguageMap();
//        spinnerLanguages = findViewById(R.id.objdetspinnerlang);
//        selectedLanguages = new ArrayList<>(languageMap.values());
//        languageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languageMap.keySet().toArray(new String[0]));
//        languageAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
//        spinnerLanguages.setDropDownVerticalOffset(dpToPx(26));
//        spinnerLanguages.setDropDownHorizontalOffset(dpToPx(-3));
//        spinnerLanguages.setAdapter(languageAdapter);
//        spinnerLanguages.setSelection(selectedLanguages.indexOf(outputlang));
    }
    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    private void initializeLanguageMap() {
        // Add languages and their Locale codes to the HashMap
        languageMap.put("bn", "Bengali");
        languageMap.put("hi", "Hindi");
        languageMap.put("gu", "Gujarati");
        languageMap.put("en", "English");
        languageMap.put("kn", "Kannada");
        languageMap.put("mr", "Marathi");
        languageMap.put("ta", "Tamil");
        languageMap.put("te", "Telugu");
        languageMap.put("ur", "Urdu");
        languageMap.put("ml", "Malayalam");

        languageMap.put("af", "Afrikaans");
        languageMap.put("ar", "Arabic");

        languageMap.put("bg", "Bulgarian");
//        languageMap.put("ca", "Catalan");
        languageMap.put("cs", "Czech");

        languageMap.put("da", "Danish");
        languageMap.put("de", "German");
        languageMap.put("el", "Greek");

        languageMap.put("es", "Spanish");

        languageMap.put("fi", "Finnish");
        languageMap.put("fr", "French");

        languageMap.put("gl", "Galician");//note
//        languageMap.put("he", "Hebrew");
//        languageMap.put("hr", "Croatian");
//        languageMap.put("ht", "Haitian");
        languageMap.put("hu", "Hungarian");
        languageMap.put("id", "Indonesian");//in
        languageMap.put("is", "Icelandic");
        languageMap.put("it", "Italian");
        languageMap.put("ja", "Japanese");
//        languageMap.put("ka", "Georgian");
        languageMap.put("ko", "Korean");
        languageMap.put("lt", "Lithuanian");
        languageMap.put("lv", "Latvian");
//        languageMap.put("mk", "Macedonian");
        languageMap.put("ms", "Malay");
//        languageMap.put("mt", "Maltese");
        languageMap.put("nl", "Dutch");
        languageMap.put("no", "Norwegian");//nb
        languageMap.put("pl", "Polish");
        languageMap.put("pt", "Portuguese");
        languageMap.put("ro", "Romanian");
        languageMap.put("ru", "Russian");
        languageMap.put("sk", "Slovak");
//        languageMap.put("sl", "Slovenian");
//        languageMap.put("sq", "Albanian");
        languageMap.put("sv", "Swedish");
//        languageMap.put("sw", "Swahili");
        languageMap.put("th", "Thai");
//        languageMap.put("tl", "Tagalog");
        languageMap.put("tr", "Turkish");
        languageMap.put("uk", "Ukrainian");
        languageMap.put("vi", "Vietnamese");
        languageMap.put("zh", "Chinese");
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
        if(ObjectDetectorHelper.textToSpeech!=null) {
            ObjectDetectorHelper.textToSpeech.stop();
            ObjectDetectorHelper.textToSpeech.shutdown();
        }
        super.onBackPressed();

    }
}
