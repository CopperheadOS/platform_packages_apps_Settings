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

import android.content.Context;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.overlay.FeatureFactory;
import com.android.settings.R;

public class KeyguardCameraPreferenceController extends TogglePreferenceController {

    private static final String KEY_KEYGUARD_CAMERA = "keyguard_camera";
    private static final String KEYGUARD_CAMERA_PERSIST_PROP = "persist.keyguard.camera";
    private static final int MY_USER_ID = UserHandle.myUserId();

    public KeyguardCameraPreferenceController(Context context) {
        super(context, KEY_KEYGUARD_CAMERA);
    }

    @Override
    public boolean isChecked() {
        return SystemProperties.getBoolean(KEYGUARD_CAMERA_PERSIST_PROP, true);
    }

    @Override
    public boolean setChecked(boolean isChecked) {
        SystemProperties.set(KEYGUARD_CAMERA_PERSIST_PROP, isChecked ? "1" : "0");
        return true;
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }

}

