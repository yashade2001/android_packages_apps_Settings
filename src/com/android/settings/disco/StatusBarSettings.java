/*
 * Copyright (C) 2015 The ProjectDisco Open Source Project
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

package com.android.settings.disco;

import android.os.Bundle;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.R;
import android.preference.Preference;
import android.preference.SwitchPreference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.provider.Settings;

public class StatusBarSettings extends SettingsPreferenceFragment implements
	OnPreferenceChangeListener {

    public static final String KEY_STATUSBAR_IMMERSIVE = "statusbar_immersive_switchpreference";

    public SwitchPreference mImmersiveStatusbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.projectdisco_tweaks_statusbar);

	mImmersiveStatusbar = (SwitchPreference) findPreference(KEY_STATUSBAR_IMMERSIVE);
	mImmersiveStatusbar.setOnPreferenceChangeListener(this);
	int statusSystemDesignFlags =
		Settings.System.getInt(getActivity().getApplicationContext().getContentResolver(),
		Settings.System.SYSTEM_DESIGN_FLAGS, 0);
	mImmersiveStatusbar.setChecked((statusSystemDesignFlags == 2) || (statusSystemDesignFlags == 3));

    }

    public boolean onPreferenceChange(Preference preference, Object objValue) {

        if (preference == mImmersiveStatusbar) {
		if (mImmersiveStatusbar != null) {
	SwitchPreference mImmersiveNavbarMirror =
		(SwitchPreference) findPreference(NavigationBarSettings.KEY_NAVBAR_IMMERSIVE);

			if (mImmersiveNavbarMirror.isChecked()) {

        Settings.System.putInt(getActivity().getApplicationContext().getContentResolver(),
                Settings.System.SYSTEM_DESIGN_FLAGS,
                mImmersiveStatusbar.isChecked() ? 3 : 1);

		} else {

        Settings.Secure.putInt(getActivity().getApplicationContext().getContentResolver(),
                Settings.System.SYSTEM_DESIGN_FLAGS,
                mImmersiveStatusbar.isChecked() ? 2 : 0);

		}

        }
      }
        return true;

    }

}
