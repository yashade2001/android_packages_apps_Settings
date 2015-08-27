
package com.android.settings.disco;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.SwitchPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class NavigationBarSettings extends SettingsPreferenceFragment implements
OnPreferenceChangeListener {

    private static final String KEY_NAVIGATION_BAR_HEIGHT = "navigation_bar_height";
    public static final String KEY_NAVBAR_IMMERSIVE = "navbar_immersive_switchpreference";

    private ListPreference mNavigationBarHeight;
    public SwitchPreference mImmersiveNavbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.navigation_bar_settings);

        mNavigationBarHeight = (ListPreference) findPreference(KEY_NAVIGATION_BAR_HEIGHT);
        mNavigationBarHeight.setOnPreferenceChangeListener(this);
        int statusNavigationBarHeight = Settings.System.getInt(getActivity().getApplicationContext()
                .getContentResolver(),
                Settings.System.NAVIGATION_BAR_HEIGHT, 48);
        mNavigationBarHeight.setValue(String.valueOf(statusNavigationBarHeight));
        mNavigationBarHeight.setSummary(mNavigationBarHeight.getEntry());

	mImmersiveNavbar = (SwitchPreference) findPreference(KEY_NAVBAR_IMMERSIVE);
	mImmersiveNavbar.setOnPreferenceChangeListener(this);
	int statusSystemDesignFlags =
		Settings.System.getInt(getActivity().getApplicationContext().getContentResolver(),
		Settings.System.SYSTEM_DESIGN_FLAGS, 0);
	mImmersiveNavbar.setChecked((statusSystemDesignFlags == 1) || (statusSystemDesignFlags == 3));

    }

    public boolean onPreferenceChange(Preference preference, Object objValue) {
        if (preference == mNavigationBarHeight) {
            int statusNavigationBarHeight = Integer.valueOf((String) objValue);
            int index = mNavigationBarHeight.findIndexOfValue((String) objValue);
            Settings.System.putInt(getActivity().getApplicationContext().getContentResolver(),
                    Settings.System.NAVIGATION_BAR_HEIGHT, statusNavigationBarHeight);
            mNavigationBarHeight.setSummary(mNavigationBarHeight.getEntries()[index]);
        }

        if (preference == mImmersiveNavbar) {
		if (mImmersiveNavbar != null) {
	SwitchPreference mImmersiveStatusbarMirror =
		(SwitchPreference) findPreference(StatusBarSettings.KEY_STATUSBAR_IMMERSIVE);

			if (mImmersiveStatusbarMirror.isChecked()) {

        Settings.System.putInt(getActivity().getApplicationContext().getContentResolver(),
                Settings.System.SYSTEM_DESIGN_FLAGS,
                mImmersiveNavbar.isChecked() ? 3 : 2);

		} else {

        Settings.System.putInt(getActivity().getApplicationContext().getContentResolver(),
                Settings.System.SYSTEM_DESIGN_FLAGS,
                mImmersiveNavbar.isChecked() ? 1 : 0);

		}
        }
      }
        return true;
    }
}
