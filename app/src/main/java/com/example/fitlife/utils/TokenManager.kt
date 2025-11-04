package com.example.fitlife.utils

import android.content.Context

object TokenManager {
    private const val PREFS_NAME = "auth"
    private const val KEY_TOKEN = "token"

    fun saveToken(context: Context, token: String) {
        val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPref.edit().putString(KEY_TOKEN, token).apply()
    }

    fun getToken(context: Context): String? {
        val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(KEY_TOKEN, null)
    }

    fun clearToken(context: Context) {
        val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPref.edit().remove(KEY_TOKEN).apply()
    }

    fun isLoggedIn(context: Context): Boolean {
        return getToken(context) != null
    }
}