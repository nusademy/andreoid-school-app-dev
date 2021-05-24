package com.nusademy.school.ui.partnerteacherSchool

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nusademy.school.R

class PartnerTeacherSchoolActivity : AppCompatActivity() {
    companion object {
        const val extra_name = "extra_subject"
        const val extra_status = "extra_status"
        const val extra_date_teaching = "extra_date_teaching"
        const val extra_avatar = "extra_avatar"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_recruitment)
        supportActionBar?.title = "Manage Recruitment"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}