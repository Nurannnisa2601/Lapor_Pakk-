package com.example.laporpakk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private  OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndicators;
    private MaterialButton buttonOnboardingAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutOnboardingIndicators = findViewById(R.id.layoutOnboardingIndicators);
        buttonOnboardingAction = findViewById(R.id.buttonOnboardingAction);

        setupOnboardingAdapter();

        ViewPager2 onboardingViewPager = findViewById(R.id.onboardingViewPager);
        onboardingViewPager.setAdapter(onboardingAdapter);

        setupOnboardingIndicators();
        setCurrentOnboardingIndicator(0);

        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
            }
        });

        buttonOnboardingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onboardingViewPager.getCurrentItem() + 1  < onboardingAdapter.getItemCount()) {
                    onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() + 1);
                } else {
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();
                }
            }
        });
    }
    private void  setupOnboardingAdapter() {

        List<Onboardingitem> onboardingitems = new ArrayList<>();

        Onboardingitem itemPayOnline = new Onboardingitem();
        itemPayOnline.setTitle("Lapor Masalah Anda !!");
        itemPayOnline.setDescriptions("Laporkan Permasalahan anda secara jelas dan mudah");
        itemPayOnline.setImage(R.drawable.megaphonesp);

        Onboardingitem itemOnTheWay = new Onboardingitem();
        itemOnTheWay.setTitle("Fitur Berbagi Lokasi !!");
        itemOnTheWay.setDescriptions("Memudahkan Pelapor untuk Melampirkan bukti pendukung");
        itemOnTheWay.setImage(R.drawable.map2);

        Onboardingitem itemEatTogether = new Onboardingitem();
        itemEatTogether.setTitle("Fitur Ambil Gambar !!");
        itemEatTogether.setDescriptions("Pelapor dapat berbagi gambar lokasi kejadian");
        itemEatTogether.setImage(R.drawable.kamera);

        onboardingitems.add(itemPayOnline);
        onboardingitems.add(itemOnTheWay);
        onboardingitems.add(itemEatTogether);

        onboardingAdapter = new OnboardingAdapter(onboardingitems);


    }
    private void setupOnboardingIndicators() {
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicators.addView(indicators[i]);
        }
    }

    private void  setCurrentOnboardingIndicator(int index) {
        int childCount = layoutOnboardingIndicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView)layoutOnboardingIndicators.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicators_active)
                );
            } else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive)

                );
            }
        }
        if(index == onboardingAdapter.getItemCount() - 1) {
            buttonOnboardingAction.setText("Start");
        } else {
            buttonOnboardingAction.setText("Next");
        }
    }
}