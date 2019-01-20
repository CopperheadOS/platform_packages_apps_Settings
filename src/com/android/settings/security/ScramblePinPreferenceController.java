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
 * limitations under the License.
 */

package com.android.settings.security;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v7.preference.Preference;
import android.support.v7.preference.TwoStatePreference;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.TogglePreferenceController;

public class ScramblePinPreferenceController extends TogglePreferenceController {

    private static final String KEY_SCRAMBLE_PIN_LAYOUT = "scramble_pin_layout";

    private final LockPatternUtils mLockPatternUtils;

    public ScramblePinPreferenceController(Context context) {
        super(context, KEY_SCRAMBLE_PIN_LAYOUT);
        mLockPatternUtils = new LockPatternUtils(context);
    }

    @Override
    public int getAvailabilityStatus() {
        // Only show the preference if PIN is selected as the keyguard
        int passQuality = mLockPatternUtils.getKeyguardStoredPasswordQuality(UserHandle.myUserId());
        if ((passQuality == DevicePolicyManager.PASSWORD_QUALITY_NUMERIC) ||
             (passQuality == DevicePolicyManager.PASSWORD_QUALITY_NUMERIC_COMPLEX)) {
            return BasePreferenceController.AVAILABLE;
        } else {
            return BasePreferenceController.DISABLED_FOR_USER;
        }
    }

    @Override
    public boolean isChecked() {
        return Settings.Secure.getInt(mContext.getContentResolver(),
                Settings.Secure.SCRAMBLE_PIN_LAYOUT, 0) != 0;
    }

    @Override
    public boolean setChecked(boolean isChecked) {
        Settings.Secure.putInt(mContext.getContentResolver(),
                Settings.Secure.SCRAMBLE_PIN_LAYOUT, isChecked ? 1 : 0);
        return true;
    }
}
