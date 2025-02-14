package name.mikanoshi.customiuizer.subs;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;

import name.mikanoshi.customiuizer.R;
import name.mikanoshi.customiuizer.SubFragment;
import name.mikanoshi.customiuizer.prefs.PreferenceEx;
import name.mikanoshi.customiuizer.utils.Helpers;

public class Various_HiddenFeatures extends SubFragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		final AppCompatActivity act = (AppCompatActivity) getActivity();

		PreferenceEx aosp = findPreference("pref_key_various_memorystats");
		aosp.setCustomSummary("AOSP");
		aosp.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				Helpers.launchActivity(act, "com.android.settings", "com.android.settings.Settings$MemorySettingsActivity");
				return true;
			}
		});

		aosp = findPreference("pref_key_various_runningservices");
		aosp.setCustomSummary("AOSP");
		aosp.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_DEFAULT);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
				intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.SubSettings"));
				intent.putExtra(":settings:show_fragment", "com.android.settings.applications.RunningServices");
				act.startActivity(intent);
				act.overridePendingTransition(R.anim.activity_open_enter, R.anim.activity_open_exit);
				return true;
			}
		});

		aosp = (PreferenceEx)findPreference("pref_key_various_appusagestats");
		aosp.setCustomSummary("AOSP");
		aosp.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				Helpers.launchActivity(act, "com.android.settings", "com.android.settings.UsageStatsActivity");
				return true;
			}
		});

		aosp = (PreferenceEx)findPreference("pref_key_various_aospnotif");
		aosp.setCustomSummary("AOSP");
		aosp.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				if (!Helpers.launchActivity(act, "com.android.settings", "com.android.settings.Settings$AppAndNotificationDashboardActivity", true))
				Helpers.launchActivity(act, "com.android.settings", "com.android.settings.Settings$ConfigureNotificationSettingsActivity");
				return true;
			}
		});

		aosp = (PreferenceEx)findPreference("pref_key_various_aospnotiflog");
		aosp.setCustomSummary("AOSP");
		aosp.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				Helpers.launchActivity(act, "com.android.settings", "com.android.settings.Settings$NotificationStationActivity");
				return true;
			}
		});

		findPreference("pref_key_various_clearspeaker").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				Helpers.launchActivity(act, "com.android.settings", "com.android.settings.Settings$SpeakerSettingsActivity");
				return true;
			}
		});

	}

}
