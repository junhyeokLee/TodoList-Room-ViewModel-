package com.junhyeoklee.todolist.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.example.android.todolist.R;
import com.junhyeoklee.todolist.AppExecutors;
import com.junhyeoklee.todolist.data.database.AppDatabase;
import com.junhyeoklee.todolist.data.model.TaskEntry;
import com.junhyeoklee.todolist.ui.adapter.TaskAdapter;

import java.util.List;

public class SettingPreferenceActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener,Preference.OnPreferenceClickListener {

    private static final String TAG = "PreSettingActivity";

    public static final String USE_BACKGROUND_COLOR = "background_color";


    PreferenceScreen screen;
    Preference text_Help;
    private Preference text_Background;
    Preference text_Questions;
    private int mColor;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_preference);

        // SharePreference Adapter에서 넘겨받은 값으로 배경컬러 저장하기
        Intent lIntent = getIntent();
        if(lIntent != null) {
            SharedPreferences pref = getSharedPreferences("colorData", MODE_PRIVATE);

            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("colorData",lIntent.getIntExtra("colorData",mColor));

            editor.commit();
        }
        screen = getPreferenceScreen();

        text_Help = (Preference)findPreference("text_help");
        text_Questions = (Preference)findPreference("text_questions");
        text_Background = (Preference)findPreference(USE_BACKGROUND_COLOR);

        text_Help.setOnPreferenceClickListener(this);
//        text_Background.setOnPreferenceChangeListener(this);
        text_Background.setOnPreferenceClickListener(this);

    }// onCreate

    SharedPreferences.OnSharedPreferenceChangeListener prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        }
    };

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

        String value = (String) newValue;
        if(preference == text_Background){
            ListPreference listPreference = (ListPreference) preference;
            int index = listPreference.findIndexOfValue(value);
            text_Background.setSummary(index>=0?listPreference.getEntries()[index]:null); // entries 값 대신 이에 해당하는 entryValues값 set
        }
        return true;
    }

    private void updateSummary(){
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {

        if(preference.getKey() == text_Background.getKey()){
            Intent sIntent = new Intent(this,SettingColorActivity.class);
            startActivity(sIntent);

            return true;
        }


        Intent lIntent = new Intent(this,SettingHelp.class);
//        lIntent.putExtra(USE_BACKGROUND_COLOR,text_Background.getEntry());
        startActivity(lIntent);

        return false;
    }


    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if(preference.getKey().equals("key_all_memo_clear")) {
            Log.i(TAG, "key_all_memo_clear");
            //모든 메모 삭제 기능 넣어야함
            // showRemoveMemoDialog();
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateSummary();
    }

}