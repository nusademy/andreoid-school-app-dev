package com.nusademy.school.ui.home

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.nusademy.nusademy.storage.SharedPrefManager
import com.nusademy.school.R
import com.nusademy.school.R.drawable
import com.nusademy.school.databinding.ActivityHomeBinding
import com.nusademy.school.ui.classes.ClassesActivity
import com.nusademy.school.ui.dialog.DialogInviteteacher
import com.nusademy.school.ui.profile.ProfileActivity
import com.nusademy.school.ui.searchteacher.SearchTeacherActivity

class HomeActivity : AppCompatActivity() {

    private var customDialog: Dialog? = null
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

        binding.btClasses.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ClassesActivity::class.java)
            startActivity(intent)
        })

        val id= SharedPrefManager.getInstance(this).Getuser.id
        val token = SharedPrefManager.getInstance(this).Getuser.token
        binding.btRecrute.setOnClickListener(View.OnClickListener {
        DialogInviteteacher(this).show()
        })


        val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList, ScaleTypes.FIT)
    }


}