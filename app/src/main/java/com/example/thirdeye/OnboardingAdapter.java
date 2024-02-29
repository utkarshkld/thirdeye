package com.example.thirdeye;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class OnboardingAdapter extends PagerAdapter {
    private Context context;
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
        //Spinner spinner = view.findViewById(R.id.spinerOnboard);
        container.addView(view);
        return view;
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
            case 2:
                return R.layout.onboard3;
            default:
                return -1;
        }

    }

}
