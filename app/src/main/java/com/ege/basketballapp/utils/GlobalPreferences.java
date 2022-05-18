package com.ege.basketballapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ege.basketballapp.model.Player;
import com.ege.basketballapp.model.User;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class GlobalPreferences {

    private static final String APP_LANGUAGE = "language";
    private static final String PREFS_NAME = "TPOSPref";
    Context context;
    private SharedPreferences prefs;
    private final SharedPreferences.Editor prefsEditor;

    final static String USER_Img = "user_img";
    final static String USERNAME = "username";
    final static String USER_DATA = "user_data";
    final static String TEAMNAME = "TEAM_NAME";


    public GlobalPreferences(Context context) {
        this.context = context;
        prefs = null;
        prefs = context.getSharedPreferences(PREFS_NAME, 0);
        prefsEditor = prefs.edit();
    }


    public void storeUserInfo(User user) {
        Gson gson2 = new Gson();
        String userData = gson2.toJson(user);
        prefsEditor.putString(USER_DATA, userData);
        prefsEditor.putString(TEAMNAME, user.getTeamName());
        prefsEditor.putString(USERNAME, user.getName());
        Gson gson = new Gson();
        prefsEditor.commit();
    }


    public User getUser() {
        Gson gson = new Gson();
        String json = prefs.getString(USER_DATA, "");
        TypeAdapter<User> adapter = gson.getAdapter(User.class);
        User user = null;
        try {
            user = adapter.fromJson(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }




    public void storeLocale(String locale) {
        prefsEditor.putString(APP_LANGUAGE, locale);
        prefsEditor.commit();
    }



    public String getLocale() {
        return prefs.getString(APP_LANGUAGE, "en");
    }

    public String getImage() {
        return prefs.getString(USER_Img, "");
    }

    public void storePlayers(ArrayList<Player> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        prefsEditor.putString("task list", json);
        prefsEditor.apply();
    }

    public ArrayList<Player> getPlayers() {
        Gson gson = new Gson();
        String json = prefs.getString("task list", null);
        Type type = new TypeToken<ArrayList<Player>>() {
        }.getType();
        ArrayList<Player> mExampleList = gson.fromJson(json, type);

        if (mExampleList == null) {
            mExampleList = new ArrayList<>();
        }
        return mExampleList;
    }

}
