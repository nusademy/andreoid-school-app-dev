package com.nusademy.nusademy.ui.inviteteacherSchool

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.nusademy.nusademy.R
import com.nusademy.nusademy.databinding.FragmentWaitingBinding

class WaitingFragment : Fragment(R.layout.fragment_waiting_school) {

    private lateinit var dataAdapter: InviteTeacherAdapter
    private lateinit var waiting: String
    private var dataBindingS: FragmentWaitingBinding? = null
    private val dataBinding get() = dataBindingS!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        waiting = args?.getString(InviteTeacherActivity.extra_name).toString()
        dataBindingS = FragmentWaitingBinding.bind(view)

        dataAdapter = InviteTeacherAdapter()
        dataAdapter.notifyDataSetChanged()

        dataBinding.rvWaiting.apply {
            layoutManager =
                    LinearLayoutManager(
                            context,
                            LinearLayoutManager.VERTICAL, false
                    )
            setHasFixedSize(true)
            adapter = dataAdapter
        }

    }
}