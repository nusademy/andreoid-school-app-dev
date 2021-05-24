package com.nusademy.nusademy.ui.inviteteacherSchool

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class InviteTeacherFragmentAdapter(activity: AppCompatActivity, bundle: Bundle) : FragmentStateAdapter(activity) {

    private var userFragmentBundle: Bundle = bundle

    override fun createFragment(position: Int): Fragment {
        var fragmented: Fragment? = null
        when (position) {
            0 -> fragmented = WaitingFragment()
            1 -> fragmented = AcceptedFragment()
            2 -> fragmented = RejectedFragment()
        }; fragmented?.arguments = this.userFragmentBundle
        return fragmented as Fragment
    }
    override fun getItemCount(): Int {
        return 3
    }
}