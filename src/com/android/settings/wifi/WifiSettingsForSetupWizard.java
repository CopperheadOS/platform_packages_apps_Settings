package com.android.settings.wifi;

import android.os.Bundle;
import android.view.View;

import com.android.settings.R;

public class WifiSettingsForSetupWizard extends WifiSettings {

    private static final int IMMERSIVE_FLAGS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    private int mSystemUiFlags = IMMERSIVE_FLAGS | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

    public WifiSettingsForSetupWizard() {
        super();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        android.util.Log.d("TEST", "onCreate wifi for setup");
        getActivity().getActionBar().hide();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getWindow().getDecorView().setSystemUiVisibility(mSystemUiFlags);
    }

    protected WifiEnabler createWifiEnabler() {
        return null;
    }
}