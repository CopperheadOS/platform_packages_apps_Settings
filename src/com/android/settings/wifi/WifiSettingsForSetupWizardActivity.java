package com.android.settings.wifi;

import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settings.SettingsActivity;

import com.google.android.setupdesign.view.NavigationBar;
import com.google.android.setupdesign.view.NavigationBar.NavigationBarListener;

public class WifiSettingsForSetupWizardActivity extends SettingsActivity implements NavigationBarListener,
        ViewTreeObserver.OnPreDrawListener {

    private NavigationBar mNavigationBar;

    private static final int IMMERSIVE_FLAGS =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    private int mSystemUiFlags = IMMERSIVE_FLAGS | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(getColor(R.color.background_for_setup_wizard));

        ViewStub navStub = (ViewStub) findViewById(R.id.setupwizard_navbar_stub);
        navStub.setLayoutResource(R.layout.setupwizard_navbar_layout);
        navStub.inflate();

        ViewStub headerStub = (ViewStub) findViewById(R.id.header_stub);
        headerStub.setLayoutResource(R.layout.header_condensed);
        headerStub.inflate();

        TextView title = (TextView) findViewById(android.R.id.title);
        title.setText(getTitle());

        ImageView icon = (ImageView) findViewById(R.id.header_icon);
        icon.setImageResource(R.drawable.ic_wifi_signal_4);

        mNavigationBar = getNavigationBar();
        if (mNavigationBar != null) {
            mNavigationBar.setNavigationBarListener(this);
            mNavigationBar.addOnLayoutChangeListener((View view, int left , int top, int right,
                    int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) -> {
                view.requestApplyInsets();
            });
            mNavigationBar.setSystemUiVisibility(mSystemUiFlags);
            ViewTreeObserver viewTreeObserver = mNavigationBar.getViewTreeObserver();
            viewTreeObserver.addOnPreDrawListener(this);
        }
    }

    @Override
    public boolean onPreDraw() {
        mNavigationBar.setSystemUiVisibility(mSystemUiFlags);
        return true;
    }

    public NavigationBar getNavigationBar() {
        final View view = findViewById(R.id.navigation_bar);
        return view instanceof NavigationBar ? (NavigationBar) view : null;
    }

    @Override
    public void onNavigateNext() {
        setResult(RESULT_OK, null);
        finish();
    }

    @Override
    public void onNavigateBack() {
        setResult(RESULT_CANCELED, null);
        finish();
    }
}