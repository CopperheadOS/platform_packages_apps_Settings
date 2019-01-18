/*
 * Copyright (C) 2017 The Android Open Source Project
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

public class SELinuxStatusDialogController {

    static final int SELINUX_STATUS_LABEL_ID = R.id.selinux_status_label;
    static final int SELINUX_STATUS_VALUE_ID = R.id.selinux_status_value;
    static final String SELINUX_STATUS_PROPERTY = "ro.build.selinux";

    private final SecurityFlagsDialogFragment mDialog;

    public SELinuxStatusDialogController(SecurityFlagsDialogFragment dialog) {
        mDialog = dialog;
    }

    /**
     * Updates the SELinux status field of the dialog.
     */
    public void initialize() {
        final Context context = mDialog.getContext();
        String status;

        if (!SELinux.isSELinuxEnabled()) {
            status = context.getString(R.string.selinux_status_disabled);
        } else if (!SELinux.isSELinuxEnforced()) {
            status = context.getString(R.string.selinux_status_permissive);
        } else {
            status = context.getString(R.string.selinux_status_enforcing);
        }
        mDialog.setText(SELINUX_STATUS_VALUE_ID, status);
    }
}

