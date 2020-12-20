package com.jstark.gitu.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jstark.gitu.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        initiateUI()
    }

    private fun initiateUI() {
        btn_language.setOnClickListener(this)
        btn_back_settings.setOnClickListener(this)
        btn_reminder_settings.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_language -> startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            R.id.btn_reminder_settings -> startActivity(
                Intent(
                    this@SettingsActivity,
                    ReminderActivity::class.java
                )
            )
            R.id.btn_back_settings -> onBackPressed()
        }
    }
}