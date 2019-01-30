package com.junhyeoklee.todolist.ui;

import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.os.Bundle;

import com.example.android.todolist.R;

public class SettingHelp extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    Preference text_Help;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_help);
        text_Help = (Preference)findPreference("help_text");

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        return false;
    }
}
