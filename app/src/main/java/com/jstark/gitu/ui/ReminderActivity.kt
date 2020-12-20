package com.jstark.gitu.ui

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jstark.gitu.R
import com.jstark.gitu.broadcast.AlarmReceiver
import com.jstark.gitu.sharedpreferences.ReminderPreference
import kotlinx.android.synthetic.main.activity_reminder.*

class ReminderActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener,
    View.OnClickListener {

    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        alarmReceiver = AlarmReceiver()
        initiateUI()
    }

    private fun initiateUI() {
        switch_reminder.setOnCheckedChangeListener(this)
        btn_back_reminder.setOnClickListener(this)
        val reminderPreference = ReminderPreference(this)
        switch_reminder.isChecked = reminderPreference.getReminder()
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        val reminderPreference = ReminderPreference(this)

        if (isChecked) {
            reminderPreference.setReminder(isChecked)
            setAlarmOn()
        } else {
            reminderPreference.setReminder(isChecked)
            cancelAlarm()
        }
    }

    private fun setAlarmOn() {
        val title = resources.getString(R.string.title_notification)
        val text = resources.getString(R.string.description_notification)
        val subText = resources.getString(R.string.sub_title_notification)
        val timeRepeat = "09:00"
        alarmReceiver.setRepeatingAlarm(this, timeRepeat, title, text, subText)
        Toast.makeText(this, getString(R.string.reminder_on), Toast.LENGTH_SHORT)
            .show()
    }

    private fun cancelAlarm() {
        alarmReceiver.cancelAlarmUser(this)
        Toast.makeText(this, getString(R.string.reminder_off), Toast.LENGTH_SHORT)
            .show()
    }

    override fun onClick(v: View?) {
        onBackPressed()
    }
}