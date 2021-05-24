package com.nusademy.nusademy.ui.inviteteacherSchool

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.nusademy.nusademy.R
import com.nusademy.nusademy.databinding.FragmentAcceptedOrRejectedBinding

class AcceptedFragment : Fragment(R.layout.fragment_acceptedorrejected_school) {

    private lateinit var dataAdapter: InviteTeacherAdapter
    private lateinit var accepted : String
    private var dataBindingS: FragmentAcceptedOrRejectedBinding? = null
    private val dataBinding get() = dataBindingS!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        accepted = args?.getString(InviteTeacherActivity.extra_name).toString()
        dataBindingS = FragmentAcceptedOrRejectedBinding.bind(view)

        dataAdapter = InviteTeacherAdapter()
        dataAdapter.notifyDataSetChanged()

        dataBinding.rvAcceptedOrRejected.apply {
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