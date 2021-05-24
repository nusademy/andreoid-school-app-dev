package com.nusademy.school.ui.managerecruitmentSchool

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nusademy.school.R

class ManageRecruitmentActivity : AppCompatActivity() {
    companion object {
        const val extra_subject = "extra_subject"
        const val extra_domicile = "extra_domicile"
        const val extra_status = "extra_status"
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