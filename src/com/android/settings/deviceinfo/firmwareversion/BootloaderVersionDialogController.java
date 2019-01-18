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

package com.android.settings.deviceinfo.firmwareversion;

import android.content.Context;
import android.os.SystemProperties;
import android.support.annotation.VisibleForTesting;

import com.android.settings.R;

public class BootloaderVersionDialogController {

    @VisibleForTesting
    static final int BOOTLOADER_VERSION_LABEL_ID = R.id.bootloader_version_label;
    @VisibleForTesting
    static final int BOOTLOADER_VERSION_VALUE_ID = R.id.bootloader_version_value;
    @VisibleForTesting
    static final String BOOTLOADER_PROPERTY = "ro.bootloader";

    private final FirmwareVersionDialogFragment mDialog;

    public BootloaderVersionDialogController(FirmwareVersionDialogFragment dialog) {
        mDialog = dialog;
    }

    /**
     * Updates the bootloader version field of the dialog.
     */
    public void initialize() {
        final Context context = mDialog.getContext();

        mDialog.setText(BOOTLOADER_VERSION_VALUE_ID, SystemProperties.get(BOOTLOADER_PROPERTY,
                context.getString(R.string.device_info_default)));
    }
}
