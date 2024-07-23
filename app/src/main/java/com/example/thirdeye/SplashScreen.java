//package com.example.thirdeye;
//
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import com.example.thirdeye.MainActivity;
//
//import android.os.Handler;
//import android.os.Looper;
//import android.provider.Settings;
//
//import android.Manifest;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.speech.tts.TextToSpeech;
//import android.speech.tts.UtteranceProgressListener;
//import android.util.Log;
//import android.widget.Toast;
//
//import java.util.HashMap;
//import java.util.Locale;
//
//public class SplashScreen extends AppCompatActivity {
//
//    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;
//
//    private static final int MICROPHONE_PERMISSION_REQUEST_CODE = 2;
//    public static TextToSpeech textToSpeech;
//    public static HashMap<String, String> translationsMap = new HashMap<>();
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splashscreen);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                initializeTTS();
//                instructionmap.makeinstruction();
//                initializelanguageMap();
//                translationmap.initializetransMap();
//                start();
//            }
//        }, 1500);
//    }
//
//    private void start(){
//
//        // Check camera permission
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED) {
//            // Camera permission not granted, request it
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.CAMERA},
//                    CAMERA_PERMISSION_REQUEST_CODE);
//        } else {
//            // Camera permission granted, check microphone permission
//            checkMicrophonePermission();
//        }
//    }
//    private void checkMicrophonePermission() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.RECORD_AUDIO},
//                    MICROPHONE_PERMISSION_REQUEST_CODE);
//        } else {
//            proceedToNextActivity();
//        }
//    }
//    public void initializeTTS() {
//        textToSpeech = new TextToSpeech(this, status -> {
//            if (status == TextToSpeech.SUCCESS) {
//                int result = textToSpeech.setLanguage(new Locale("en"));
//
//                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                    // Handle language initialization errors here
//                }
//            } else {
//                // Handle Text-to-Speech initialization error
//            }
//        });
//        textToSpeech.setSpeechRate(0.8f);
//    }
//    private void proceedToNextActivity() {
//        Intent intent = new Intent(SplashScreen.this, Onboarding.class);
//        startActivity(intent);
//        finish();
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Camera permission granted, check microphone permission
//
//                checkMicrophonePermission();
//            } else {
//                // Camera permission denied, display Toast and close the app
//                showOpenSettingsAlertDialog();
//            }
//        } else if (requestCode == MICROPHONE_PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Microphone permission granted, proceed to next activity
//                proceedToNextActivity();
//            } else {
//                // Microphone permission denied, display Toast and close the app
//                showOpenSettingsAlertDialog();
////                finish();
//            }
//        }
//    }
//    private void showOpenSettingsAlertDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Permissions Required");
//        builder.setMessage("This app requires camera and microphone permissions to function properly. Please grant the permissions in settings.");
//        builder.setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                openAppSettings();
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // Close the app if the user cancels
//                Toast.makeText(SplashScreen.this, "Permissions are required", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        });
//        builder.setCancelable(false); // Prevent dismissing the dialog by tapping outside of it
//        builder.show();
//    }
//    private void openAppSettings() {
//        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//        intent.setData(Uri.parse("package:" + getPackageName()));
//        startActivity(intent);
//    }
//    @Override
//    public void onRestart(){
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED) {
//            // Camera permission not granted, request it
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.CAMERA},
//                    CAMERA_PERMISSION_REQUEST_CODE);
//        } else {
//            // Camera permission granted, check microphone permission
//            checkMicrophonePermission();
//        }
//        super.onRestart();
//    }
//    private void initializelanguageMap() {
//        // Add languages and their Locale codes to the HashMap
////        MainActivity.languageMap.put("Afrikaans", "af");
//        //MainActivity.languageMap.put("Albanian", "sq");
////        MainActivity.languageMap.put("Arabic", "ar");
////        MainActivity.languageMap.put("Bengali", "bn");
////        MainActivity.languageMap.put("Bulgarian", "bg");
//        //MainActivity.languageMap.put("Catalan", "ca");
////        MainActivity.languageMap.put("Chinese", "zh");
//        //MainActivity.languageMap.put("Croatian", "hr");
////        MainActivity.languageMap.put("Czech", "cs");
////        MainActivity.languageMap.put("Danish", "da");
////        MainActivity.languageMap.put("Dutch", "nl");
////        MainActivity.languageMap.put("English", "en");
////        MainActivity.languageMap.put("Finnish", "fi");
////        MainActivity.languageMap.put("French", "fr");
////        MainActivity.languageMap.put("Galician", "gl");
//        //MainActivity.languageMap.put("Georgian", "ka");
////        MainActivity.languageMap.put("German", "de");
////        MainActivity.languageMap.put("Greek", "el");
////        MainActivity.languageMap.put("Gujarati", "gu");
//        //MainActivity.languageMap.put("Haitian", "ht");
//        //MainActivity.languageMap.put("Hebrew", "he");
////        MainActivity.languageMap.put("Hindi", "hi");
////        MainActivity.languageMap.put("Hungarian", "hu");
////        MainActivity.languageMap.put("Icelandic", "is");
////        MainActivity.languageMap.put("Indonesian", "id");
////        MainActivity.languageMap.put("Italian", "it");
////        MainActivity.languageMap.put("Japanese", "ja");
////        MainActivity.languageMap.put("Kannada", "kn");
////        MainActivity.languageMap.put("Korean", "ko");
////        MainActivity.languageMap.put("Latvian", "lv");
////        MainActivity.languageMap.put("Lithuanian", "lt");
//        //MainActivity.languageMap.put("Macedonian", "mk");
////        MainActivity.languageMap.put("Malay", "ms");
////        MainActivity.languageMap.put("Malayalam", "ml");
//        //MainActivity.languageMap.put("Maltese", "mt");
////        MainActivity.languageMap.put("Marathi", "mr");
////        MainActivity.languageMap.put("Norwegian", "no");
////        MainActivity.languageMap.put("Polish", "pl");
////        MainActivity.languageMap.put("Portuguese", "pt");
////        MainActivity.languageMap.put("Romanian", "ro");
////        MainActivity.languageMap.put("Russian", "ru");
////        MainActivity.languageMap.put("Slovak", "sk");
//        //MainActivity.languageMap.put("Slovenian", "sl");
////        MainActivity.languageMap.put("Spanish", "es");
//        //MainActivity.languageMap.put("Swahili", "sw");
////        MainActivity.languageMap.put("Swedish", "sv");
//        //MainActivity.languageMap.put("Tagalog", "tl");
////        MainActivity.languageMap.put("Tamil", "ta");
////        MainActivity.languageMap.put("Telugu", "te");
////        MainActivity.languageMap.put("Thai", "th");
////        MainActivity.languageMap.put("Turkish", "tr");
////        MainActivity.languageMap.put("Ukrainian", "uk");
////        MainActivity.languageMap.put("Urdu", "ur");
////        MainActivity.languageMap.put("Vietnamese", "vi");
//        MainActivity.languageMap.put("Tamil", "ta");
//        MainActivity.languageMap.put("Telugu", "te");
//        MainActivity.languageMap.put("Marathi", "mr");
////        MainActivity.languageMap.put("Malayalam", "ml");
//        MainActivity.languageMap.put("Hindi", "hi");
//        MainActivity.languageMap.put("Gujarati", "gu");
//        MainActivity.languageMap.put("English", "en");
//        MainActivity.languageMap.put("Bengali", "bn");
//        MainActivity.languageMap.put("Kannada", "kn");
//
//
//    }
//}
package com.example.thirdeye;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.thirdeye.MainActivity;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.HintRequest;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class SplashScreen extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;
    private static final int MICROPHONE_PERMISSION_REQUEST_CODE = 2;
    private static final int OTHER_PERMISSIONS_REQUEST_CODE = 100;
    private final int CREDENTIAL_PICKER_REQUEST = 120;

    private static final String[] OTHER_REQUIRED_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.READ_PHONE_STATE,

            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_SMS,
            Manifest.permission.VIBRATE,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.RECEIVE_BOOT_COMPLETED
    };

    public static TextToSpeech textToSpeech;
    public static HashMap<String, String> translationsMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initializeTTS();
                instructionmap.makeinstruction();
//                initializelanguageMap();
                translationmap.initializetransMap();
                start();
        }
        }, 1500);
        UserPreferences userPreferences = new UserPreferences(this);
        Location location = AnalyticsManager.getLocation(this);
        String Country = "";
        if(location!=null){
            Country = AnalyticsManager.getCountry(this,location.getLatitude(),location.getLongitude());
            Log.d("Country check",Country);
        }

        userPreferences.saveCountry(Country);
    }

    private void start() {
        // Check camera permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Camera permission not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            // Camera permission granted, check microphone permission
            checkMicrophonePermission();
        }
    }

    private void checkMicrophonePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    MICROPHONE_PERMISSION_REQUEST_CODE);
        } else {
            // Microphone permission granted, check other permissions
            checkOtherPermissions();
        }
    }

    private void checkOtherPermissions() {
        if (!areOtherPermissionsGranted()) {
            ActivityCompat.requestPermissions(this, OTHER_REQUIRED_PERMISSIONS, OTHER_PERMISSIONS_REQUEST_CODE);
        } else {
            proceedToNextActivity();
        }
    }

    private boolean areOtherPermissionsGranted() {
        for (String permission : OTHER_REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public void initializeTTS() {
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = textToSpeech.setLanguage(new Locale("en"));
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    // Handle language initialization errors here
                }
            } else {
                // Handle Text-to-Speech initialization error
            }
        });
        textToSpeech.setSpeechRate(0.8f);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREDENTIAL_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                if (credential != null) {
                    String phoneNumber = credential.getId(); // Process the phone number string
                    // Use the phone number as needed
                    Log.d("Getting phone number",phoneNumber);
                }
                // credential.getId();  <-- will need to process phone number string
            }
        }
    }
    private void proceedToNextActivity() {
        UserPreferences userPreferences = new UserPreferences(this);
        Location location = AnalyticsManager.getLocation(this);
        String Country = "";
        if(location!=null){
            Country = AnalyticsManager.getCountry(this,location.getLatitude(),location.getLongitude());
            Log.d("Country check",Country);
        }

        userPreferences.saveCountry(Country);
        initializelanguageMap();
        Intent intent = new Intent(SplashScreen.this, Onboarding.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission granted, check microphone permission
                checkMicrophonePermission();
            } else {
                // Camera permission denied, display Toast and close the app
                showOpenSettingsAlertDialog();
            }
        } else if (requestCode == MICROPHONE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Microphone permission granted, check other permissions
                checkOtherPermissions();
            } else {
                // Microphone permission denied, display Toast and close the app
                showOpenSettingsAlertDialog();
            }
        } else if (requestCode == OTHER_PERMISSIONS_REQUEST_CODE) {
            if (areOtherPermissionsGranted()) {
                proceedToNextActivity();
            } else {
                showOpenSettingsAlertDialog();
            }
        }
    }

    private void showOpenSettingsAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissions Required");
        builder.setMessage("This app requires all permissions to function properly. Please grant the permissions in settings.");
        builder.setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openAppSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(SplashScreen.this, "Permissions are required", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void openAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        start();
    }

    private void initializelanguageMap() {
        UserPreferences userPreferences = new UserPreferences(this);
        String country = userPreferences.getCountry();
        if(!Objects.equals(country, "India")){
            MainActivity.languageMap.put("Afrikaans", "af");
            MainActivity.languageMap.put("Albanian", "sq");
        MainActivity.languageMap.put("Arabic", "ar");
        MainActivity.languageMap.put("Bengali", "bn");
        MainActivity.languageMap.put("Bulgarian", "bg");
            MainActivity.languageMap.put("Catalan", "ca");
        MainActivity.languageMap.put("Chinese", "zh");
            MainActivity.languageMap.put("Croatian", "hr");
        MainActivity.languageMap.put("Czech", "cs");
        MainActivity.languageMap.put("Danish", "da");
        MainActivity.languageMap.put("Dutch", "nl");
        MainActivity.languageMap.put("English", "en");
        MainActivity.languageMap.put("Finnish", "fi");
        MainActivity.languageMap.put("French", "fr");
        MainActivity.languageMap.put("Galician", "gl");
            MainActivity.languageMap.put("Georgian", "ka");
        MainActivity.languageMap.put("German", "de");
        MainActivity.languageMap.put("Greek", "el");
        MainActivity.languageMap.put("Gujarati", "gu");
            MainActivity.languageMap.put("Haitian", "ht");
            MainActivity.languageMap.put("Hebrew", "he");
        MainActivity.languageMap.put("Hindi", "hi");
        MainActivity.languageMap.put("Hungarian", "hu");
        MainActivity.languageMap.put("Icelandic", "is");
        MainActivity.languageMap.put("Indonesian", "id");
        MainActivity.languageMap.put("Italian", "it");
        MainActivity.languageMap.put("Japanese", "ja");
        MainActivity.languageMap.put("Kannada", "kn");
        MainActivity.languageMap.put("Korean", "ko");
        MainActivity.languageMap.put("Latvian", "lv");
        MainActivity.languageMap.put("Lithuanian", "lt");
            MainActivity.languageMap.put("Macedonian", "mk");
        MainActivity.languageMap.put("Malay", "ms");
        MainActivity.languageMap.put("Malayalam", "ml");
            MainActivity.languageMap.put("Maltese", "mt");
        MainActivity.languageMap.put("Marathi", "mr");
        MainActivity.languageMap.put("Norwegian", "no");
        MainActivity.languageMap.put("Polish", "pl");
        MainActivity.languageMap.put("Portuguese", "pt");
        MainActivity.languageMap.put("Romanian", "ro");
        MainActivity.languageMap.put("Russian", "ru");
        MainActivity.languageMap.put("Slovak", "sk");
            MainActivity.languageMap.put("Slovenian", "sl");
        MainActivity.languageMap.put("Spanish", "es");
            MainActivity.languageMap.put("Swahili", "sw");
        MainActivity.languageMap.put("Swedish", "sv");
            MainActivity.languageMap.put("Tagalog", "tl");
        MainActivity.languageMap.put("Tamil", "ta");
        MainActivity.languageMap.put("Telugu", "te");
        MainActivity.languageMap.put("Thai", "th");
        MainActivity.languageMap.put("Turkish", "tr");
        MainActivity.languageMap.put("Ukrainian", "uk");
        MainActivity.languageMap.put("Urdu", "ur");
        MainActivity.languageMap.put("Vietnamese", "vi");
        }
        else{
            MainActivity.languageMap.put("Tamil", "ta");
            MainActivity.languageMap.put("Telugu", "te");
            MainActivity.languageMap.put("Marathi", "mr");
//        MainActivity.languageMap.put("Malayalam", "ml");
            MainActivity.languageMap.put("Hindi", "hi");
            MainActivity.languageMap.put("Gujarati", "gu");
            MainActivity.languageMap.put("English", "en");
            MainActivity.languageMap.put("Bengali", "bn");
            MainActivity.languageMap.put("Kannada", "kn");
        }
//


    }
}
