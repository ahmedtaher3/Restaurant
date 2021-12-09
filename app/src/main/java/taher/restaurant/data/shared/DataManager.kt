package com.devartlab.data.shared

import com.google.firebase.crashlytics.internal.model.CrashlyticsReport
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import taher.restaurant.data.shared.SharedPrefsHelper

class DataManager(var mSharedPrefsHelper: SharedPrefsHelper) {
    fun clear() {
        mSharedPrefsHelper.clear()
    }

    fun isSavedData(b: Boolean) {
        mSharedPrefsHelper.putSavedData(b)
    }

    val savedData: Boolean
        get() = mSharedPrefsHelper.savedData




    fun saveToken(b: String) {
        mSharedPrefsHelper.putToken(b)
    }

    val token: String
        get() = mSharedPrefsHelper.token



    fun isMobileData(b: Boolean) {
        mSharedPrefsHelper.putMobileData(b)
    }

    val mobileData: Boolean
        get() = mSharedPrefsHelper.mobileData


    fun isUpdatePlan(b: Boolean) {
        mSharedPrefsHelper.putUpdatePlan(b)
    }

    val updatePlan: Boolean
        get() = mSharedPrefsHelper.updatePlan


}