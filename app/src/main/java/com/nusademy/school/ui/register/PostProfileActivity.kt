package com.nusademy.school.ui.register

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.developer.kalert.KAlertDialog
import com.nusademy.nusademy.dataapi.RetrofitClient
import com.nusademy.nusademy.storage.SharedPrefManager
import com.nusademy.school.dataapi.DataProfileSchool
import com.nusademy.school.databinding.ActivityProfileBinding
import com.nusademy.school.databinding.ActivityProfileEditBinding
import com.nusademy.school.ui.home.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileEditBinding
    private lateinit var pDialog: KAlertDialog

    //Ambil id dan token dari SharedPreference
    private val token= SharedPrefManager.getInstance(this).Getuser.token
    private val iduser= SharedPrefManager.getInstance(this).Getuser.id
    private val rolename= SharedPrefManager.getInstance(this).Getuser.role
    private val fullname= SharedPrefManager.getInstance(this).Getuser.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pDialog = KAlertDialog(this, KAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading"
        pDialog.setCancelable(false)

        binding.btSave.setOnClickListener {
            PostSchool(
                binding.editNamasekolah.text.toString(),
                binding.editKepalasekolah.text.toString(),
                binding.editAlamat.text.toString(),
                binding.editWebsite.text.toString(),
                binding.editPhonenumber.text.toString()
            )
        }



    }

    //Inisiasi Fuction UpdateSchool
    fun PostSchool(name: String, headmaster: String, address: String, website: String, phonenumber: String) {
        pDialog.changeAlertType(KAlertDialog.PROGRESS_TYPE)
        pDialog.show()
        RetrofitClient.instanceUserApi.addProfileSchool(
            "Bearer " + token,
            name,
            headmaster,
            address,
            phonenumber,
            website,
            iduser
        )
            .enqueue(object : Callback<DataProfileSchool> {
                override fun onResponse(
                    call: Call<DataProfileSchool>,
                    response: Response<DataProfileSchool>
                ) {
                    Log.d("Post",response.toString())
                    if (response.isSuccessful) {
                        SharedPrefManager.getInstance(applicationContext).setUser(
                            iduser,
                            response.body()?.id.toString(),
                            token,
                            fullname,
                            rolename
                        )
                        pDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE)
                        pDialog.setTitleText("Berhasil")
                        pDialog.setContentText("Data Profil Sekolah Berhasil Dibuat")
                        pDialog.setConfirmText("OK")
                        pDialog.setConfirmClickListener {
                                sDialog -> sDialog.dismissWithAnimation()
                            val intent = Intent(this@PostProfileActivity, HomeActivity::class.java)
                            startActivity(intent)
                        }

                    } else {
                        Toast.makeText(applicationContext, "Gagal Cek kembali Isian", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(calls: Call<DataProfileSchool>, ts: Throwable) {
                    Log.d("Error", ts.message.toString())
                }
            })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}