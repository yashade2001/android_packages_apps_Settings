package com.android.settings.disco;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.provider.Settings;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class BatteryStatusIconSettings extends SettingsPreferenceFragment implements
OnPreferenceChangeListener {

    private static final String KEY_BATTERY_STATUS_ICON = "battery_status_icon";

    private ListPreference mBatteryStatusIcon;


            boolean batteryStyle =
                    Settings.System.getIntForUser(mContext.getContentResolver(),
                    Settings.System.STATUS_BAR_BATTERY_STYLE, 0, ActivityManager.getCurrentUser());

            boolean batteryPercentage =
                    Settings.System.getIntForUser(mContext.getContentResolver(),
                    Settings.System.STATUS_BAR_SHOW_BATTERY_PERCENT, 0, ActivityManager.getCurrentUser());


    String batteryStyleStatus = null;


	if ((batteryStyle == 0) && (batteryPercentage == 0)) {
			batteryStyleStatus = "bar";

	} else if ((batteryStyle == 2) && (batteryPercentage == 0)) {
			batteryStyleStatus = "circle";

	} else if ((batteryStyle == 5) && (batteryPercentage == 2)) {
			batteryStyleStatus = "percent";

	} else ((batteryStyle == 4) && (batteryPercentage == 0)) {
			batteryStyleStatus = "hidden";
	}
		
	
private void setBatteryStyle (String theStyle) {

      if (theStyle == "battery_bar") {

		    Settings.System.putInt(getActivity().getApplicationContext().getContentResolver(),
                    Settings.System.STATUS_BAR_BATTERY_STYLE, 0);

		    Settings.System.putInt(getActivity().getApplicationContext().getContentResolver(),
                    Settings.System.STATUS_BAR_SHOW_BATTERY_PERCENT, 0);

    } else if (theStyle == "battery_circle") {
		
		    Settings.System.putInt(getActivity().getApplicationContext().getContentResolver(),
                    Settings.System.STATUS_BAR_BATTERY_STYLE, 2);

		    Settings.System.putInt(getActivity().getApplicationContext().getContentResolver(),
                    Settings.System.STATUS_BAR_SHOW_BATTERY_PERCENT, 0);

    } else if (theStyle == "battery_percent") {
		
		    Settings.System.putInt(getActivity().getApplicationContext().getContentResolver(),
                    Settings.System.STATUS_BAR_BATTERY_STYLE, 5);

		    Settings.System.putInt(getActivity().getApplicationContext().getContentResolver(),
                    Settings.System.STATUS_BAR_SHOW_BATTERY_PERCENT, 2);

    } else (theStyle == "battery_hidden") {
		
		    Settings.System.putInt(getActivity().getApplicationContext().getContentResolver(),
                    Settings.System.STATUS_BAR_BATTERY_STYLE, 4);

		    Settings.System.putInt(getActivity().getApplicationContext().getContentResolver(),
                    Settings.System.STATUS_BAR_SHOW_BATTERY_PERCENT, 0);

    }

}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.battery_status_icon_settings_dialog);

        mBatteryStatusIcon = (ListPreference) findPreference(KEY_BATTERY_STATUS_ICON);
        mBatteryStatusIcon.setOnPreferenceChangeListener(this);

        int statusBatteryStatusIcon =
			batteryStyleStatus == "bar";

        mBatteryStatusIcon.setValue(String.valueOf(statusBatteryStatusIcon));
        mBatteryStatusIcon.setSummary(mBatteryStatusIcon.getEntry());
    }

    public boolean onPreferenceChange(Preference preference, Object objValue) {
        if (preference == mBatteryStatusIcon) {

            int statusBatteryStatusIcon = Integer.valueOf((String) objValue);
            int index = mBatteryStatusIcon.findIndexOfValue((String) objValue);

	    setBatteryStyle(statusBatteryStatusIcon);

            mBatteryStatusIcon.setSummary(mBatteryStatusIcon.getEntries()[index]);
        }

        return true;
    }
}
