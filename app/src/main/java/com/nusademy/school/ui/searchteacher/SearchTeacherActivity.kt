package com.nusademy.school.ui.searchteacher

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.nusademy.nusademy.dataapi.RetrofitClient
import com.nusademy.nusademy.storage.SharedPrefManager
import com.nusademy.school.R
import com.nusademy.school.dataapi.DataTeacher
import com.nusademy.school.dataapi.ListDataTeacher
import com.nusademy.school.dataapi.ListDataTeacher.ListDataTeacherItem
import com.nusademy.school.databinding.ActivitySearchTeacherBinding
import com.nusademy.school.databinding.DialogInviteTeacherBinding
import com.nusademy.school.ui.requestteacher.RequestTeacherActivity
import com.nusademy.school.ui.searchteacher.SearchTeacherAdapter.ItemClickListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchTeacherActivity : AppCompatActivity(), ItemClickListener {

    private val list = MutableLiveData<ArrayList<ListDataTeacherItem>>()
    private lateinit var dataBinding: ActivitySearchTeacherBinding
    private lateinit var dataAdapter: SearchTeacherAdapter
    val token= SharedPrefManager.getInstance(this).Getuser.token

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivitySearchTeacherBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)
        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
        actionBar?.setTitle("Cari Guru")
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)


        searchTeacherItem()
        getUsers().observe(this, {
            if (it != null) {
                dataAdapter.setDataUser(it)
            }
        })


        dataAdapter = SearchTeacherAdapter(this)
        dataAdapter.notifyDataSetChanged()

        dataBinding.rvUsers.apply {
            layoutManager =
                LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL, false
                )
            setHasFixedSize(true)
            adapter = dataAdapter
        }

    }

    override fun onInviteClick(id: String) {
        val intent = Intent(applicationContext, DetailTeacherActivity::class.java)
        intent.putExtra("idteacher", id)
        startActivity(intent)
    }


    private fun searchTeacherItem() {
//        val query = dataBinding.txtSearchuser.text.toString()
        val query=""
//        if (query.isEmpty()) return
//        showLoading(true)
        setUsers(query)
    }

    fun setUsers(query: String) {
        RetrofitClient.instanceUserApi.getSearchTeacher(query, "Bearer " + token)
            .enqueue(object : Callback<ListDataTeacher> {
                override fun onResponse(call: Call<ListDataTeacher>, response: Response<ListDataTeacher>) {
                    Log.d("JSON", response.toString())
                    if (response.isSuccessful) {
                        list.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ListDataTeacher>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                }
            })
    }

    fun getUsers(): LiveData<ArrayList<ListDataTeacherItem>> {
        return list
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}