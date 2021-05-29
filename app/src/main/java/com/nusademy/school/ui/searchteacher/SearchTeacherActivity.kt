package com.nusademy.school.ui.searchteacher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchTeacherActivity : AppCompatActivity() {

    private val list = MutableLiveData<ArrayList<ListDataTeacherItem>>()
    private lateinit var dataBinding: ActivitySearchTeacherBinding
    private lateinit var dataAdapter: SearchTeacherAdapter
    val token= SharedPrefManager.getInstance(this).Getuser.token

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivitySearchTeacherBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)
        searchTeacherItem()
        getUsers().observe(this, {
            if (it != null) {
                dataAdapter.setDataUser(it)
            }
        })


        dataAdapter = SearchTeacherAdapter()
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

    private fun searchTeacherItem() {
//        val query = dataBinding.txtSearchuser.text.toString()
        val query="t"
        if (query.isEmpty()) return
//        showLoading(true)
        setUsers(query)
    }

    fun setUsers(query: String) {
        RetrofitClient.instanceUserApi.getSearchTeacher(query,"Bearer "+token)
            .enqueue(object : Callback<ListDataTeacher> {
                override fun onResponse(call: Call<ListDataTeacher>, response: Response<ListDataTeacher>) {
                    Log.d("JSON",response.toString())
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
}