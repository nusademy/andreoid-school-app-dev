package com.nusademy.school.ui.profile

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.developer.kalert.KAlertDialog
import com.nusademy.nusademy.dataapi.RetrofitClient
import com.nusademy.nusademy.storage.SharedPrefManager
import com.nusademy.school.dataapi.DataProfileSchool
import com.nusademy.school.databinding.ActivityProfileEditBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileEditActivity : AppCompatActivity() {


    private lateinit var binding: ActivityProfileEditBinding
    private lateinit var pDialog: KAlertDialog


    //Ambil id dan token dari SharedPreference
    private val token= SharedPrefManager.getInstance(this).Getuser.token
    private val iduser= SharedPrefManager.getInstance(this).Getuser.id
    private val idschool= SharedPrefManager.getInstance(this).Getuser.idschool
    private val nameschool= SharedPrefManager.getInstance(this).Getuser.name
    private val role= SharedPrefManager.getInstance(this).Getuser.role

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pDialog = KAlertDialog(this, KAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading"
        pDialog.setCancelable(false)

        GetSchoolProfile(idschool, token)

        // Action Saat Button Save Di Klik
        binding.btSave.setOnClickListener(View.OnClickListener {

            // Memanggil Function UpdateSchool
            UpdateSchool(
                binding.editNamasekolah.text.toString(),
                binding.editKepalasekolah.text.toString(),
                binding.editAlamat.text.toString(),
                binding.editWebsite.text.toString(),
                binding.editPhonenumber.text.toString()
            )
        })

    }

    //Inisiasi Fuction UpdateSchool
    fun UpdateSchool(name: String, headmaster: String, address: String, website: String, phonenumber: String) {
        pDialog.changeAlertType(KAlertDialog.PROGRESS_TYPE)
        pDialog.show()
        RetrofitClient.instanceUserApi.editProfileSchool(
            idschool,
            "Bearer " + token,
            name,
            headmaster,
            address,
            phonenumber,
            website
        )
            .enqueue(object : Callback<DataProfileSchool> {
                override fun onResponse(
                    call: Call<DataProfileSchool>,
                    response: Response<DataProfileSchool>
                ) {
                    if (response.isSuccessful) {
                        // Saat response sukses finnish activity (menutup/mengakhiri activity editprofile)
                        SharedPrefManager.getInstance(applicationContext).setUser(
                            iduser,
                            idschool,token,
                            response.body()?.name.toString(),
                            role
                        )

                        pDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE)
                        pDialog.setTitleText("Berhasil")
                        pDialog.setContentText("Data Profil Sekolah Berhasil Diperbarui")
                        pDialog.setConfirmText("OK")
                        pDialog.setConfirmClickListener {
                                sDialog -> sDialog.dismissWithAnimation()
                                finish()
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
    //Inisiasi Fuction GetSchoolProfile
    fun GetSchoolProfile(id: String, token: String) {
        pDialog.changeAlertType(KAlertDialog.PROGRESS_TYPE)
        pDialog.show()
        RetrofitClient.instanceUserApi.getProfileSchool(id, "Bearer " + token)
            .enqueue(object : Callback<DataProfileSchool> {
                override fun onResponse(
                    call: Call<DataProfileSchool>,
                    response: Response<DataProfileSchool>
                ) {
                    pDialog.hide()

                    // Cek Koneksi API Behasil
                    if (response.code().toString() == "200") {
                        //Cek Output Json
                        Log.d("School", response.body().toString())

                        //Set data JSON ke tampilan
                        val data = response.body()
                        binding.editNamasekolah.setText(data?.name.toString())
                        binding.editKepalasekolah.setText(data?.headmaster.toString())
                        binding.editAlamat.setText(data?.website.toString())
                        binding.editWebsite.setText(data?.address.toString())
                        binding.editPhonenumber.setText(data?.phoneNumber.toString())

                        // Cek Koneksi API Gagal
                    } else {
                        Toast.makeText(applicationContext, "Gagal Mendapatkan Data", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(calls: Call<DataProfileSchool>, ts: Throwable) {
                    pDialog.hide()
                    Log.d("Error", ts.message.toString())
                }
            })
    }
}