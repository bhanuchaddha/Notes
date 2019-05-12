package com.ben.notes;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class SharedPreferenceUtil {
    private Gson gson = new Gson();


    public <T> void save(Context context, String key, T value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.ben.notes", Context.MODE_PRIVATE);
        String stringValue = gson.toJson(value);
        sharedPreferences.edit().putString(key, stringValue).apply();
    }

    public <T> T get(Context context, String key, Class<T> clazz) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.ben.notes", Context.MODE_PRIVATE);
        String stringValue = sharedPreferences.getString(key, "");
        if(stringValue.equals("")) {
            try {
                return clazz.newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        return gson.fromJson(stringValue, clazz);

    }
}
