package com.jstark.gitu.sharedpreferences

import android.content.Context

internal class ReminderPreference(context: Context) {
    companion object {
        private const val PREFS_NAME = "reminder_pref"
        private const val REMINDER_ON = "isreminderon"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setReminder(value: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(REMINDER_ON, value)
        editor.apply()
    }

    fun getReminder(): Boolean {
        return preferences.getBoolean(REMINDER_ON, false)
    }
}