package com.nusademy.school.ui.searchteacher

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.developer.kalert.KAlertDialog
import com.nusademy.nusademy.dataapi.RetrofitClient
import com.nusademy.nusademy.storage.SharedPrefManager
import com.nusademy.school.R
import com.nusademy.school.dataapi.DataTeacher
import com.nusademy.school.databinding.ActivityDetailTeacherBinding
import com.nusademy.school.ui.requestteacher.RequestTeacherActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailTeacherActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailTeacherBinding
    val token= SharedPrefManager.getInstance(this).Getuser.token
    var iduser=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailTeacherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
        actionBar?.setTitle("Informasi Guru")
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        val idteacher = intent.getStringExtra("idteacher").toString()
        getprofile(idteacher)
        Glide.with(this)
            .load(R.drawable.profile_null)
            .into(binding.ivAvatarTeacher)

        binding.btCancel.setOnClickListener {
            finish()
        }
        binding.btInvite.setOnClickListener {
            val intent = Intent(applicationContext, RequestTeacherActivity::class.java)
            intent.putExtra("idteacher", iduser)
            startActivity(intent)
        }
    }
    
    
    fun getprofile(idteacher:String){
        val pDialog = KAlertDialog(this, KAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading"
        pDialog.setCancelable(false)
        pDialog.show()
        RetrofitClient.instanceUserApi.getProfileTeacher(idteacher, "Bearer " + token)
            .enqueue(object : Callback<DataTeacher> {
                override fun onResponse(
                    call: Call<DataTeacher>,
                    response: Response<DataTeacher>
                ) {
                    pDialog.dismissWithAnimation()
                    Log.d("Teacher", response.body().toString())
                    // Cek Koneksi API Behasil
                    if (response.code().toString() == "200") {
                        //Cek Output Json
                        //Set data JSON ke tampilan
                        val data = response.body()
                        binding.tvNama.text = data?.user?.fullName
                        binding.tvPendidikan.text = data?.lastEducation
                        binding.tvKampus.text = data?.campus
                        binding.tvJurusan.text = data?.major
                        binding.tvIpk.text = data?.ipk.toString()
                        binding.tvBio.text = data?.shortBrief
                        binding.tvLinkedin.text = data?.linkedin
                        binding.tvVideo.text = data?.videoBranding.toString()
                        binding.tvDomisili.text = data?.domicilie?.name.toString()
                        binding.tvSpesialisasi.text = data?.spesialitation?.name.toString()
                        iduser=data?.user?.id.toString()
                        // Cek Koneksi API Gagal
                    } else {
                        Toast.makeText(applicationContext, "Failed To Get Data", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                override fun onFailure(call: Call<DataTeacher>, ts: Throwable) {
                    Log.d("Error", ts.message.toString())
                }
            })
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}