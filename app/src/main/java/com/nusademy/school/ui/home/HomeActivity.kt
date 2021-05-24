package com.nusademy.school.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.nusademy.school.R
import com.nusademy.school.R.drawable
import com.nusademy.school.databinding.ActivityHomeBinding

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



        val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList, ScaleTypes.FIT)
    }
}