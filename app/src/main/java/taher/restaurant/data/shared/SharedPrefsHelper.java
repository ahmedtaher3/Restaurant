package taher.restaurant.data.shared;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsHelper {

    public static final String MY_PREFS = "MY_PREFS";
    public static final String SavedData = "SavedData";
    public static final String MobileData = "MobileData";
    public static final String UpdatePlan = "UpdatePlan";

    private static final String Token = "Token";

    SharedPreferences mSharedPreferences;

    public SharedPrefsHelper(Context context) {
        this.mSharedPreferences = context.getSharedPreferences(MY_PREFS, 0);
    }

    public void clear() {
        this.mSharedPreferences.edit().clear().apply();
    }


    public void putSavedData(boolean b) {
        this.mSharedPreferences.edit().putBoolean(SavedData, b).apply();
    }

    public Boolean getSavedData() {
        return this.mSharedPreferences.getBoolean(SavedData, false);
    }


    public void putToken(String b) {
        this.mSharedPreferences.edit().putString(Token, b).apply();
    }

    public String getToken() {
        return this.mSharedPreferences.getString(Token, "");
    }


    public void putMobileData(boolean b) {
        this.mSharedPreferences.edit().putBoolean(MobileData, b).apply();
    }

    public Boolean getMobileData() {
        return this.mSharedPreferences.getBoolean(MobileData, false);
    }


    public void putUpdatePlan(boolean b) {
        this.mSharedPreferences.edit().putBoolean(UpdatePlan, b).apply();
    }

    public Boolean getUpdatePlan() {
        return this.mSharedPreferences.getBoolean(UpdatePlan, false);
    }





    }

