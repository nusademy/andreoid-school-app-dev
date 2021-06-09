package com.nusademy.school.ui.home

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.developer.kalert.KAlertDialog
import com.nusademy.nusademy.storage.SharedPrefManager
import com.nusademy.school.R
import com.nusademy.school.R.drawable
import com.nusademy.school.databinding.ActivityHomeBinding
import com.nusademy.school.ui.about.AboutActivity
import com.nusademy.school.ui.classes.ClassesActivity
import com.nusademy.school.ui.dialog.DialogInviteteacher
import com.nusademy.school.ui.guestteacherrequest.GuestTeacherRequestActivity
import com.nusademy.school.ui.tempteacherrequest.TempTeacherRequestActivity
import com.nusademy.school.ui.profile.ProfileActivity
import com.nusademy.school.ui.register.PostProfileActivity
import com.nusademy.school.ui.searchteacher.SearchTeacherActivity

class HomeActivity : AppCompatActivity() {

    private var customDialog: Dialog? = null
    private lateinit var binding:ActivityHomeBinding
    private val token= SharedPrefManager.getInstance(this).Getuser.token
    private val iduser= SharedPrefManager.getInstance(this).Getuser.id
    private val idschool= SharedPrefManager.getInstance(this).Getuser.idschool


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

        binding.tvSchoolName.text=SharedPrefManager.getInstance(this).Getuser.name
        binding.tvRole.text=SharedPrefManager.getInstance(this).Getuser.role


        Glide.with(this)
            .load(R.drawable.profile_null)
            .into(findViewById(R.id.img_me))

        var imageList = ArrayList<SlideModel>()

// imageList.add(SlideModel("String Url" or R.drawable)
// imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title

        imageList.add(SlideModel(drawable.carousel1))
        imageList.add(SlideModel(drawable.carousel2))
        imageList.add(SlideModel(drawable.carousel3))


        binding.btAbout.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        binding.linearProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }


        binding.btSearchteacher.setOnClickListener {
            val intent = Intent(this, SearchTeacherActivity::class.java)
            startActivity(intent)
        }

        binding.btClasses.setOnClickListener {
            val intent = Intent(this, ClassesActivity::class.java)
            startActivity(intent)
        }

        binding.btGuestrequest.setOnClickListener{
            val intent = Intent(this, GuestTeacherRequestActivity::class.java)
            startActivity(intent)
        }

        binding.btTemprequest.setOnClickListener {
            val intent = Intent(this, TempTeacherRequestActivity::class.java)
            startActivity(intent)
        }

        val id= SharedPrefManager.getInstance(this).Getuser.id
        val token = SharedPrefManager.getInstance(this).Getuser.token


        val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList, ScaleTypes.FIT)
    }

    override fun onBackPressed() {
        initCloseDialog()
    }

    // Function Add Dialog -------------------------------------------------------------------------------------------
    private fun initCloseDialog() {
        val adddialog = Dialog(this)
        adddialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        adddialog.setCancelable(true)
        adddialog.setContentView(R.layout.dialog_close)
        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        adddialog.getWindow()?.setLayout(width, (width/1.5).toInt())

        val btSave = adddialog.findViewById(R.id.bt_save) as Button
        val btCancel = adddialog.findViewById(R.id.bt_cancel) as Button

        btCancel.setOnClickListener {
            adddialog.dismiss()
        }
        btSave.setOnClickListener {
            adddialog.dismiss()
            finishAffinity()
        }
        adddialog.show()
    }

    override fun onStart() {
        super.onStart()

            if(SharedPrefManager.getInstance(this).Getuser.idschool=="null"){
                val intent = Intent(applicationContext, PostProfileActivity::class.java)
                startActivity(intent)
            }
        binding.tvSchoolName.text=SharedPrefManager.getInstance(this).Getuser.name
        binding.tvRole.text=SharedPrefManager.getInstance(this).Getuser.role
    }


}