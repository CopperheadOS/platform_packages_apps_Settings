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

import android.content.Context;
import android.os.SELinux;
import android.os.SystemProperties;
import android.service.persistentdata.PersistentDataBlockManager;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.text.TextUtils;

import com.android.settings.R;

public class TheftProtectionStatusDialogController {

    static final int THEFT_PROTECTION_STATUS_LABEL_ID = R.id.theft_protection_status_label;
    static final int THEFT_PROTECTION_STATUS_VALUE_ID = R.id.theft_protection_status_value;
    static final String PROPERTY_CRYPTO_TYPE = "ro.crypto.type";
    private final SecurityFlagsDialogFragment mDialog;

    public TheftProtectionStatusDialogController(SecurityFlagsDialogFragment dialog) {
        mDialog = dialog;
    }

    /**
     * Updates the theft protection state field of the dialog.
     */
    public void initialize() {
        final Context context = mDialog.getContext();

        final PersistentDataBlockManager manager = (PersistentDataBlockManager)
                context.getSystemService(Context.PERSISTENT_DATA_BLOCK_SERVICE);
        if (manager == null || !"file".equals(SystemProperties.get(PROPERTY_CRYPTO_TYPE))) {
            mDialog.setText(THEFT_PROTECTION_STATUS_VALUE_ID, context.getString(R.string.anti_theft_protection_status_not_available));
        } else if (manager.getOemUnlockEnabled() || manager.getFlashLockState() != manager.FLASH_LOCK_LOCKED) {
            mDialog.setText(THEFT_PROTECTION_STATUS_VALUE_ID, context.getString(R.string.anti_theft_protection_status_disabled));
        } else {
            mDialog.setText(THEFT_PROTECTION_STATUS_VALUE_ID, context.getString(R.string.anti_theft_protection_status_enforcing));
        }
    }
}

