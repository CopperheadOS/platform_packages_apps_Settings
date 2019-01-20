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

package com.android.settings.deviceinfo.securityflags;

import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.SELinux;
import android.os.SystemProperties;
import android.service.persistentdata.PersistentDataBlockManager;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.text.TextUtils;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;

public class SecurityFlagsPreferenceController extends AbstractPreferenceController implements
        PreferenceControllerMixin {

    private final static String SECURITY_FLAGS_KEY = "security_flags";

    private Context mContext;
    private final Fragment mFragment;

    public SecurityFlagsPreferenceController(Context context, Fragment fragment) {
        super(context);

        mContext = context;
        mFragment = fragment;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        final Preference pref = screen.findPreference(getPreferenceKey());
        if (isSecure()) {
            pref.setSummary(R.string.security_all_active);
        } else {
            pref.setSummary(R.string.security_needs_attention);
        }
    }

    private Boolean isSecure() {
        // Check SELinux
        if (!SELinux.isSELinuxEnabled() || !SELinux.isSELinuxEnforced()) {
            return false;
        }
        // Check Theft Protection
        final PersistentDataBlockManager manager = (PersistentDataBlockManager)
                mContext.getSystemService(Context.PERSISTENT_DATA_BLOCK_SERVICE);
        if (manager.getOemUnlockEnabled() || manager.getFlashLockState() != manager.FLASH_LOCK_LOCKED) {
            return false;
        }
        // Check Verity
        final String verifiedBootState = SystemProperties.get(VerityStatusDialogController.PROPERTY_VERIFIED_BOOT_STATE);
        final String verityMode = SystemProperties.get(VerityStatusDialogController.PROPERTY_VERITY_MODE);
        final int partitionSystemVerified = SystemProperties.getInt(VerityStatusDialogController.PROPERTY_PARTITION_SYSTEM_VERIFIED, 0);
        final int partitionVendorVerified = SystemProperties.getInt(VerityStatusDialogController.PROPERTY_PARTITION_VENDOR_VERIFIED, 0);
        if (!(("green".equals(verifiedBootState) || "yellow".equals(verifiedBootState)) &&
                "enforcing".equals(verityMode) && partitionSystemVerified == 2 && partitionVendorVerified == 2)) {
            return false;
        }
        return true;
    }

    @Override
    public String getPreferenceKey() {
        return SECURITY_FLAGS_KEY;
    }

    @Override
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }

        SecurityFlagsDialogFragment.show(mFragment);
        return true;
    }
}
