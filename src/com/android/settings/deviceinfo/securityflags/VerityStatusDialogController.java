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
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.text.TextUtils;

import com.android.settings.R;

public class VerityStatusDialogController {

    static final int VERITY_STATUS_LABEL_ID = R.id.verity_status_label;
    static final int VERITY_STATUS_VALUE_ID = R.id.verity_status_value;
    static final String PROPERTY_VERIFIED_BOOT_STATE = "ro.boot.verifiedbootstate";
    static final String PROPERTY_VERITY_MODE = "ro.boot.veritymode";
    static final String PROPERTY_PARTITION_SYSTEM_VERIFIED = "partition.system.verified";
    static final String PROPERTY_PARTITION_VENDOR_VERIFIED = "partition.vendor.verified";
    private final SecurityFlagsDialogFragment mDialog;

    public VerityStatusDialogController(SecurityFlagsDialogFragment dialog) {
        mDialog = dialog;
    }

    /**
     * Updates the verified boot state field of the dialog.
     */
    public void initialize() {
        final Context context = mDialog.getContext();

        final String verifiedBootState = SystemProperties.get(PROPERTY_VERIFIED_BOOT_STATE);
        final String verityMode = SystemProperties.get(PROPERTY_VERITY_MODE);
        final int partitionSystemVerified = SystemProperties.getInt(PROPERTY_PARTITION_SYSTEM_VERIFIED, 0);
        final int partitionVendorVerified = SystemProperties.getInt(PROPERTY_PARTITION_VENDOR_VERIFIED, 0);
        if (("green".equals(verifiedBootState) || "yellow".equals(verifiedBootState)) &&
                "enforcing".equals(verityMode) && partitionSystemVerified == 2 && partitionVendorVerified == 2) {
            mDialog.setText(VERITY_STATUS_VALUE_ID, context.getString(R.string.verified_boot_status_enforcing));
        } else {
            mDialog.setText(VERITY_STATUS_VALUE_ID, context.getString(R.string.verified_boot_status_disabled));
        }
    }
}

