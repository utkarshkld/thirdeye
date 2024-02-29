package com.example.thirdeye;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Onboarding extends AppCompatActivity {

    ViewPager viewPager;
    Button button;
    private OnboardingAdapter pagerAdapter;
    private int pageCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        viewPager = findViewById(R.id.viewPager);
        button = findViewById(R.id.buttonNextonboard);

        pagerAdapter = new OnboardingAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        pageCount = pagerAdapter.getCount();

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String firstTime = preferences.getString("isFirstRun", "");

        if(firstTime.equals("")){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("isFirstRun", "Yes");
            editor.apply();


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == pagerAdapter.getCount()){
                        startMainActivity();
                }



            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        }else{
            startMainActivity();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager.getCurrentItem();

                if (currentItem < pageCount - 1) {
                    viewPager.setCurrentItem(currentItem + 1);
                } else {
                    startMainActivity();
                }
            }
        });
    }


    private void startMainActivity() {
        Intent intent = new Intent(Onboarding.this, MainActivity.class);
        startActivity(intent);
        finish();
    }



}