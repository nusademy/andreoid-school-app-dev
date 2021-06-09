package com.nusademy.school.ui.profile

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.developer.kalert.KAlertDialog
import com.nusademy.nusademy.dataapi.RetrofitClient
import com.nusademy.nusademy.storage.SharedPrefManager
import com.nusademy.school.R
import com.nusademy.school.dataapi.DataBasicUser
import com.nusademy.school.databinding.ActivityProfileUserEditBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileUserEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileUserEditBinding


    //Ambil id dan token dari SharedPreference
    private val token= SharedPrefManager.getInstance(this).Getuser.token
    private val iduser= SharedPrefManager.getInstance(this).Getuser.id
    private val idschool= SharedPrefManager.getInstance(this).Getuser.idschool
    private val fullname= SharedPrefManager.getInstance(this).Getuser.name
    private val rolename= SharedPrefManager.getInstance(this).Getuser.role

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileUserEditBinding.inflate(this.layoutInflater)
        setContentView(binding.root)

        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar?.hide()
        GetUserProfile(iduser,token)

        // Action Saat Button Save Di Klik
        binding.btSave.setOnClickListener(View.OnClickListener {

            // Memanggil Function UpdateSchool
            UpdateUsers(
                binding.tvName.text.toString(),
                binding.tvEmail.text.toString())
        })
    }

    //Inisiasi Fuction UpdateSchool
    fun UpdateUsers(
        fullName:String,
        email:String) {
        val pDialog = KAlertDialog(this, KAlertDialog.SUCCESS_TYPE)
        pDialog.contentText = "Updated data has been saved"
        pDialog.show()
        RetrofitClient.instanceUserApi.editProfileUsers(iduser,"Bearer "+token,fullName,email)
            .enqueue(object : Callback<DataBasicUser> {
                override fun onResponse(
                    call: Call<DataBasicUser>,
                    response: Response<DataBasicUser>
                ) {
                    pDialog.dismissWithAnimation()
                    if (response.isSuccessful) {
                        // Saat response sukses finnish activity (menutup/mengakhiri activity editprofile)
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "Gagal Cek kembali Isian", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(calls: Call<DataBasicUser>, ts: Throwable) {
                    Log.d("Error", ts.message.toString())

                }
            })
    }

    fun GetUserProfile(id: String,token:String) {
        val pDialog1 = KAlertDialog(this, KAlertDialog.PROGRESS_TYPE)
        pDialog1.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog1.titleText = "Loading"
        pDialog1.setCancelable(false)
        pDialog1.show()

        RetrofitClient.instanceUserApi.getProfileBasicUser(id,"Bearer "+token)
            .enqueue(object : Callback<DataBasicUser> {
                override fun onResponse(
                    call: Call<DataBasicUser>,
                    response: Response<DataBasicUser>
                ) {
                    pDialog1.dismissWithAnimation()
                    // Cek Koneksi API Behasil
                    Log.d("UserBasic", response.toString())
                    Log.d("UserBasic", response.errorBody().toString())
                    if (response.code().toString()=="200"){

                        //Cek Output Json
                        Log.d("UserBasic", response.body().toString())

                        //Set data JSON ke tampilan
                        val data = response.body()
                        binding.tvName.setText( data?.fullName.toString())
                        binding.tvEmail.setText(data?.email.toString())

                        // Cek Koneksi API Gagal
                    } else {
                        Toast.makeText(applicationContext, "Failed To Get Data", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(calls: Call<DataBasicUser>, ts: Throwable) {
                    Log.d("Error", ts.message.toString())

                }
            })
    }
}