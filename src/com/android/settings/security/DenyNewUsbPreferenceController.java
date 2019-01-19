/*
 * Copyright (C) 2019 Copperhead
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.android.settings.security;

import android.content.Context;
import android.provider.Settings;
import android.os.SystemProperties;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;

/**
 * Setting where user can pick if new USB devices should be enumerated, enumerated
 * only if unlocked or never enumerated at all.
 */
public class DenyNewUsbPreferenceController extends BasePreferenceController
        implements Preference.OnPreferenceChangeListener {

    private static final String KEY_DENY_NEW_USB = "deny_new_usb";
    private static final String DENY_NEW_USB_PROP = "security.deny_new_usb";
    private static final String DENY_NEW_USB_PERSIST_PROP = "persist.security.deny_new_usb";
    private ListPreference mDenyNewUsbPref;

    public DenyNewUsbPreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        mDenyNewUsbPref = (ListPreference) screen.findPreference(getPreferenceKey());
        String value = SystemProperties.get(DENY_NEW_USB_PERSIST_PROP);
        mDenyNewUsbPref.setValue(value);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String mode = (String) newValue;
        SystemProperties.set(DENY_NEW_USB_PERSIST_PROP, mode);
        // The dynamic mode defaults to the disabled state
        if (mode.equals("dynamic")) {
            SystemProperties.set(DENY_NEW_USB_PROP, "0");
        }
        return true;
    }
}
