package com.example.android.sunshine;

// TODO (4) Create SettingsFragment and extend PreferenceFragmentCompat

// Do steps 5 - 11 within SettingsFragment
// TODO (10) Implement OnSharedPreferenceChangeListener from SettingsFragment

// TODO (8) Create a method called setPreferenceSummary that accepts a Preference and an Object and sets the summary of the preference

// TODO (5) Override onCreatePreferences and add the preference xml file using addPreferencesFromResource



import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

public class SettingsFragment extends PreferenceFragmentCompat implements
        SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        // Do step 9 within onCreatePreference
        addPreferencesFromResource(R.xml.pref_general);
        // TODO (9) Set the preference summary on each preference that isn't a CheckBoxPreference
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        SharedPreferences sharedPreferences = preferenceScreen.getSharedPreferences();

        for (int i=0; i < preferenceScreen.getPreferenceCount(); i++) {
            Preference p = preferenceScreen.getPreference(i);

            if (!(p instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(p.getKey(), "");
                setPreferenceSummary(p, value);
            }
        }

        // TODO (13) Unregister SettingsFragment (this) as a SharedPreferenceChangedListener in onStop

        // TODO (12) Register SettingsFragment (this) as a SharedPreferenceChangedListener in onStart


    }

    private void setPreferenceSummary(Preference preference, String value) {
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = ((ListPreference) preference).findIndexOfValue(value);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            preference.setSummary(value.toString());
        }
    }


        // TODO (11) Override onSharedPreferenceChanged to update non CheckBoxPreferences when they are changed
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Preference preference = findPreference(s);
        if (preference != null) {
            if (!(preference instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(s, "");
                setPreferenceSummary(preference, value);
            }
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
