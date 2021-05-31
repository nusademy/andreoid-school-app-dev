package com.nusademy.school.ui.login

import DataLogin
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.nusademy.nusademy.dataapi.RetrofitClient
import com.nusademy.nusademy.storage.SharedPrefManager

import com.nusademy.school.databinding.ActivityLoginBinding
import com.nusademy.school.R
import com.nusademy.school.ui.home.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(this.layoutInflater)
        setContentView(binding.root)
        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar?.hide()

        binding.btLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
        loginAuth(email,password)
        }
    }

    fun loginAuth(email: String,password:String) {

        RetrofitClient.instanceUserApi.login(email,password)
            .enqueue(object : Callback<DataLogin> {
                override fun onResponse(
                    call: Call<DataLogin>,
                    response: Response<DataLogin>
                ) {

                    if (response.code().toString()=="200"){
                        Log.d("Login", response.body().toString())
                        if (response.body()?.user?.role?.name.toString()=="School Manager"){
                            SharedPrefManager.getInstance(applicationContext).setLogin(true)
                            SharedPrefManager.getInstance(applicationContext).setUser(
                                response.body()?.user?.id.toString(),
                                response.body()?.user?.school?.id.toString(),
                                response.body()?.jwt.toString(),
                                response.body()?.user?.fullName.toString(),
                                response.body()?.user?.role?.name.toString()
                            )
                            val intent = Intent(this@LoginActivity, HomeActivity ::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(applicationContext, "Akun yang digunakan tidak sesuai", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        Toast.makeText(applicationContext, "Username/Email/Password Salah", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(calls: Call<DataLogin>, ts: Throwable) {
                    Log.d("Error", ts.message.toString())
                }
            })
    }

    override fun onStart() {
        super.onStart()
        if(SharedPrefManager.getInstance(this).IsLogin){
                val intent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(intent)
        }
    }

}