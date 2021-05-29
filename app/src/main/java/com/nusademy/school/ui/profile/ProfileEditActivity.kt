package com.nusademy.school.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.nusademy.nusademy.dataapi.RetrofitClient
import com.nusademy.nusademy.storage.SharedPrefManager
import com.nusademy.school.dataapi.DataProfileSchool
import com.nusademy.school.databinding.ActivityProfileBinding
import com.nusademy.school.databinding.ActivityProfileEditBinding
import com.nusademy.school.ui.home.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileEditActivity : AppCompatActivity() {


    private lateinit var binding: ActivityProfileEditBinding

    //Ambil id dan token dari SharedPreference
    private val token= SharedPrefManager.getInstance(this).Getuser.token
    private val id= SharedPrefManager.getInstance(this).Getuser.id

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val bundle = intent.extras
        if (bundle != null) {
            binding.editNamasekolah.setText(bundle.getString("name",""))
            binding.editKepalasekolah.setText( bundle.getString("headmaster",""))
            binding.editAlamat.setText( bundle.getString("address",""))
            binding.editWebsite.setText( bundle.getString("website",""))
            binding.editPhonenumber.setText( bundle.getString("phonenumber",""))
        }

        // Action Saat Button Save Di Klik
        binding.btSave.setOnClickListener(View.OnClickListener {

            // Memanggil Function UpdateSchool
            UpdateSchool(
                binding.editNamasekolah.text.toString(),
                binding.editKepalasekolah.text.toString(),
                binding.editAlamat.text.toString(),
                binding.editWebsite.text.toString(),
                binding.editPhonenumber.text.toString())
        })

    }

    //Inisiasi Fuction UpdateSchool
    fun UpdateSchool(name:String,headmaster:String,address:String,website:String,phonenumber:String) {
        RetrofitClient.instanceUserApi.editProfileSchool(id,"Bearer "+token,name,headmaster,address,phonenumber,website)
            .enqueue(object : Callback<DataProfileSchool> {
                override fun onResponse(
                    call: Call<DataProfileSchool>,
                    response: Response<DataProfileSchool>
                ) {
                    if (response.isSuccessful) {
                        // Saat response sukses finnish activity (menutup/mengakhiri activity editprofile)
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "Gagal Cek kembali Isian", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(calls: Call<DataProfileSchool>, ts: Throwable) {
                    Log.d("Error", ts.message.toString())

                }
            })
    }
}