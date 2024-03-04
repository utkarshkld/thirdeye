package com.example.thirdeye;

//import static androidx.appcompat.graphics.drawable.DrawableContainerCompat.Api21Impl.getResources;
//import static com.example.thirdeye.Onboarding.showPopupWindow;

import static com.example.thirdeye.appsettings.dpToPx;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OnboardingAdapter extends PagerAdapter {
    private Context context;
    public static Spinner languageSpinner;
    public static Map<String, String> languageMap;


    private String UserDeafultLanguage = Locale.getDefault().getLanguage();

    private static int[] onboardingLayouts = {
            R.layout.onboard1,
            R.layout.onboard2
    };

    public OnboardingAdapter(Context context) {
        this.context = context;
    }


    public int getCount() {
        return onboardingLayouts.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(onboardingLayouts[position], container, false);
        if(position == 0){
            languageSpinner = view.findViewById(R.id.spinnerOnboard);
            languageMap = new HashMap<>();
            languageMap.put("Afrikaans", "af");
            //languageMap.put("Albanian", "sq");
            languageMap.put("Arabic", "ar");
            languageMap.put("Bengali", "bn");
            languageMap.put("Bulgarian", "bg");
            //languageMap.put("Catalan", "ca");
            languageMap.put("Chinese", "zh");
            //languageMap.put("Croatian", "hr");
            languageMap.put("Czech", "cs");
            languageMap.put("Danish", "da");
            languageMap.put("Dutch", "nl");
            languageMap.put("English", "en");
            languageMap.put("Finnish", "fi");
            languageMap.put("French", "fr");
            languageMap.put("Galician", "gl");
            //languageMap.put("Georgian", "ka");
            languageMap.put("German", "de");
            languageMap.put("Greek", "el");
            languageMap.put("Gujarati", "gu");
            //languageMap.put("Haitian", "ht");
            //languageMap.put("Hebrew", "he");
            languageMap.put("Hindi", "hi");
            languageMap.put("Hungarian", "hu");
            languageMap.put("Icelandic", "is");
            languageMap.put("Indonesian", "id");
            languageMap.put("Italian", "it");
            languageMap.put("Japanese", "ja");
            languageMap.put("Kannada", "kn");
            languageMap.put("Korean", "ko");
            languageMap.put("Latvian", "lv");
            languageMap.put("Lithuanian", "lt");
            //languageMap.put("Macedonian", "mk");
            languageMap.put("Malay", "ms");
            languageMap.put("Malayalam", "ml");
            //languageMap.put("Maltese", "mt");
            languageMap.put("Marathi", "mr");
            languageMap.put("Norwegian", "no");
            languageMap.put("Polish", "pl");
            languageMap.put("Portuguese", "pt");
            languageMap.put("Romanian", "ro");
            languageMap.put("Russian", "ru");
            languageMap.put("Slovak", "sk");
            //languageMap.put("Slovenian", "sl");
            languageMap.put("Spanish", "es");
            //languageMap.put("Swahili", "sw");
            languageMap.put("Swedish", "sv");
            //languageMap.put("Tagalog", "tl");
            languageMap.put("Tamil", "ta");
            languageMap.put("Telugu", "te");
            languageMap.put("Thai", "th");
            languageMap.put("Turkish", "tr");
            languageMap.put("Ukrainian", "uk");
            languageMap.put("Urdu", "ur");
            languageMap.put("Vietnamese", "vi");
            List<String> languages = new ArrayList<>(languageMap.keySet());
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, languages);
            languageSpinner.setAdapter(adapter);
//        Log.d("Spinner",languageSpinner.toString());
            languageSpinner.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        showPopupWindow(languageSpinner, adapter);
//                        }
                        return true;
                    }
                    return true;
                }
            });

            String temp="";
            for( Map.Entry<String, String> entry : languageMap.entrySet()){
                if(entry.getValue().equals(UserDeafultLanguage)){
                    temp = entry.getKey();
                    break;
                }
            }
            languageSpinner.setSelection(languages.indexOf(temp));
        }else if(position == 1){
            TextView tx = view.findViewById(R.id.textView5);

            tx.setText(SplashScreen.translationsMap.get(Onboarding.output_lang));
            Onboarding.textToSpeech.setLanguage(new Locale(Onboarding.output_lang));
            Log.d("Checking Instruction", ""+Onboarding.output_lang+" "+tx.getText().toString());
            Onboarding.textToSpeech.speak(tx.getText().toString(),0, null, null);
        }

        container.addView(view);
        return view;
    }
    public  void showPopupWindow(Spinner spinnerDefaultLanguage, ArrayAdapter<String> adapter) {
        // Create a ListView for the PopupWindow
        ListView listView = new ListView(adapter.getContext());
        listView.setAdapter(adapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            listView.setVerticalScrollbarThumbDrawable(ContextCompat.getDrawable(context, R.drawable.scrollbar));
        }
        // Create the PopupWindow
        PopupWindow popupWindow = new PopupWindow(listView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        ; // Set background color

        popupWindow.setOutsideTouchable(true); // Allow the popup window to be dismissed when touched outside
        popupWindow.setHeight(dpToPx(200));
        popupWindow.setWidth(Onboarding.width-dpToPx(40));
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_FROM_FOCUSABLE);
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.popupbackground);

// Set the drawable as the background for the PopupWindow
        popupWindow.setBackgroundDrawable(drawable);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Update the selected item of the Spinner
                spinnerDefaultLanguage.setSelection(position);
                // Dismiss the PopupWindow
                popupWindow.dismiss();
            }
        });
        // Show the PopupWindow below the Spinner
        popupWindow.showAsDropDown(spinnerDefaultLanguage,dpToPx(-10),0);
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    int getLayoutResourceId(int position) {
        switch (position) {
            case 0:
                return R.layout.onboard1;
            case 1:
                return R.layout.onboard2;
            default:
                return -1;
        }

    }

}
