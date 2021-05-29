package com.nusademy.school.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.nusademy.nusademy.dataapi.RetrofitClient
import com.nusademy.nusademy.storage.SharedPrefManager
import com.nusademy.school.R
import com.nusademy.school.R.drawable
import com.nusademy.school.dataapi.DataProfileSchool
import com.nusademy.school.dataapi.ListDataTeacher
import com.nusademy.school.databinding.ActivityHomeBinding
import com.nusademy.school.ui.profile.ProfileActivity
import com.nusademy.school.ui.searchteacher.SearchTeacherActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        binding = ActivityHomeBinding.inflate(this.layoutInflater)
        setContentView(binding.root)
        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar?.hide()

//        binding.btAbout.setOnClickListener(View.OnClickListener {
//            val intent = Intent(this, AboutActivity ::class.java)
//            startActivity(intent)
//        })

        Glide.with(this)
            .load(R.drawable.profile_null)
            .into(findViewById(R.id.img_me))

        var imageList = ArrayList<SlideModel>()

// imageList.add(SlideModel("String Url" or R.drawable)
// imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title

        imageList.add(SlideModel(drawable.carousel1))
        imageList.add(SlideModel(drawable.carousel2))
        imageList.add(SlideModel(drawable.carousel3))


        binding.linearProfile.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        })


        binding.btSearchteacher.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, SearchTeacherActivity::class.java)
            startActivity(intent)
        })

        val id= SharedPrefManager.getInstance(this).Getuser.id
        val token = SharedPrefManager.getInstance(this).Getuser.token
        binding.btRecrute.setOnClickListener(View.OnClickListener {
            setUsers("t",token)
        })




        val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList, ScaleTypes.FIT)
    }



    fun setUsers(query: String,token:String) {
        RetrofitClient.instanceUserApi.getSearchTeacher(query,"Bearer "+token)
            .enqueue(object : Callback<ListDataTeacher> {
                override fun onResponse(call: Call<ListDataTeacher>, response: Response<ListDataTeacher>) {
                    Log.d("JSON",response.toString())
                    if (response.isSuccessful) {
//                        list.postValue(response.body()?.items)
                        Log.d("JSON",response.body().toString())
                    }
                }

                override fun onFailure(call: Call<ListDataTeacher>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                }

            })
    }
}