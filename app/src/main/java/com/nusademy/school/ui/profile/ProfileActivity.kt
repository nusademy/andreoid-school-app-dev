package com.nusademy.school.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.nusademy.nusademy.dataapi.RetrofitClient
import com.nusademy.nusademy.storage.SharedPrefManager
import com.nusademy.school.R
import com.nusademy.school.dataapi.DataProfileSchool
import com.nusademy.school.databinding.ActivityProfileBinding
import com.nusademy.school.databinding.ActivitySearchTeacherBinding
import com.nusademy.school.ui.searchteacher.SearchTeacherAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val token= SharedPrefManager.getInstance(this).Getuser.token
    private val id= SharedPrefManager.getInstance(this).Getuser.id

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar?.hide()
        GetSchoolProfile(id,token)

        binding.btnChangeprofil.setOnClickListener(
            View.OnClickListener {
                GetSchoolProfile(id,token)
                val bundle = Bundle()
                bundle.putString("name", binding.tvNameSchoolProfil.text.toString())
                bundle.putString("headmaster", binding.tvEmailSchoolProfil.text.toString())
                bundle.putString("website", binding.tvWebsiteSchoolProfil.text.toString())
                bundle.putString("address", binding.tvLocationSchoolProfil.text.toString())
                bundle.putString("phonenumber", binding.tvPhoneSchoolProfil.text.toString())

                val intent = Intent(this, ProfileEditActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        )
    }

    override fun onResume() {
        super.onResume()
        GetSchoolProfile(id,token)
    }

    fun GetSchoolProfile(id: String,token:String) {
        RetrofitClient.instanceUserApi.getProfileSchool(id,"Bearer "+token)
            .enqueue(object : Callback<DataProfileSchool> {
                override fun onResponse(
                    call: Call<DataProfileSchool>,
                    response: Response<DataProfileSchool>
                ) {
                    // Cek Koneksi API Behasil
                    if (response.code().toString()=="200"){
                        //Cek Output Json
                        Log.d("School", response.body().toString())

                        //Set data JSON ke tampilan
                        val data = response.body()
                        binding.tvNameSchoolProfil.text = data?.name.toString()
                        binding.tvEmailSchoolProfil.text = data?.headmaster.toString()
                        binding.tvWebsiteSchoolProfil.text = data?.website.toString()
                        binding.tvLocationSchoolProfil.text = data?.address.toString()
                        binding.tvPhoneSchoolProfil.text = data?.phoneNumber.toString()
                        binding.etvMailnonresSchoolProfil.text = data?.headmaster.toString()

                        // Cek Koneksi API Gagal
                    } else {
                        Toast.makeText(applicationContext, "Gagal Mendapatkan Data", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(calls: Call<DataProfileSchool>, ts: Throwable) {
                    Log.d("Error", ts.message.toString())

                }
            })
    }
}

