package com.nusademy.school.ui.register

import DataLogin
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.developer.kalert.KAlertDialog
import com.nusademy.nusademy.dataapi.RetrofitClient
import com.nusademy.nusademy.storage.SharedPrefManager
import com.nusademy.school.dataapi.DataProfileSchool
import com.nusademy.school.dataapi.DataUser
import com.nusademy.school.databinding.ActivityLoginBinding
import com.nusademy.school.databinding.ActivityRegisterBinding
import com.nusademy.school.ui.home.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(this.layoutInflater)
        setContentView(binding.root)
        binding.btRegister.setOnClickListener {
            register(
                binding.editUsername.text.toString(),
                binding.editEmail.text.toString(),
                binding.editFullname.text.toString(),
                binding.editPassword.text.toString(),
                "true",
                "4"
            )
        }
    }

    fun register(
        username: String,
        email: String,
        fullname: String,
        password: String,
        confirm: String,
        assignrole: String
    ) {

        RetrofitClient.instanceUserApi.register(
        username,
        email,
        fullname,
        password,
        confirm,
        assignrole,
        )
            .enqueue(object : Callback<DataLogin> {
                override fun onResponse(
                    call: Call<DataLogin>,
                    response: Response<DataLogin>
                ) {
                    Log.d("Login", response.toString())
                    if (response.code().toString()=="200"){
                        Log.d("Login", response.body().toString())
                            SharedPrefManager.getInstance(applicationContext).setLogin(true)
                            SharedPrefManager.getInstance(applicationContext).setUser(
                                response.body()?.user?.id.toString(),
                                response.body()?.user?.school?.id.toString(),
                                response.body()?.jwt.toString(),
                                response.body()?.user?.fullName.toString(),
                                response.body()?.user?.role?.name.toString()
                            )
                        AssignRole(
                            response.body()?.jwt.toString(),
                            assignrole,
                            response.body()?.user?.id.toString()
                        )


                    } else {
                        Toast.makeText(applicationContext, "Username/Email sudah digunakan", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(calls: Call<DataLogin>, ts: Throwable) {
                    Log.d("Error", ts.message.toString())
                }
            })
    }

    fun AssignRole(token:String,role: String,iduser:String) {
        RetrofitClient.instanceUserApi.editregister(
            "Bearer " + token,role,iduser
        )
            .enqueue(object : Callback<DataUser> {
                override fun onResponse(
                    call: Call<DataUser>,
                    response: Response<DataUser>
                ) {
                    if (response.isSuccessful) {
                        // Saat response sukses finnish activity (menutup/mengakhiri activity editprofile)
                        val intent = Intent(this@RegisterActivity, HomeActivity ::class.java)
                        startActivity(intent)

                    } else {
                    }
                }

                override fun onFailure(calls: Call<DataUser>, ts: Throwable) {
                    Log.d("Error", ts.message.toString())
                }
            })
    }

}