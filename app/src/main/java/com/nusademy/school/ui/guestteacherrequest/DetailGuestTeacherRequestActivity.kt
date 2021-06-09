package com.nusademy.school.ui.guestteacherrequest

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.developer.kalert.KAlertDialog
import com.nusademy.nusademy.dataapi.RetrofitClient
import com.nusademy.nusademy.storage.SharedPrefManager
import com.nusademy.school.R
import com.nusademy.school.dataapi.DataTeacher
import com.nusademy.school.dataapi.ListDataGuestRequest.ListDataGuestRequestItem
import com.nusademy.school.databinding.ActivityDetailGuestTeacherRequestBinding
import com.nusademy.school.databinding.ActivityRequestTeacherBinding
import com.nusademy.school.ui.requestteacher.RequestTeacherActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailGuestTeacherRequestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailGuestTeacherRequestBinding
    val token= SharedPrefManager.getInstance(this).Getuser.token
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailGuestTeacherRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
        actionBar?.setTitle("Informasi Request")
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        val idguest = intent.getStringExtra("idguest").toString()

        getrequest(idguest)

        binding.btApprove.setOnClickListener {
            EditRequest(idguest,"Approved")
        }

        binding.btReject.setOnClickListener {
            EditRequest(idguest,"Rejected")
        }


    }

    fun getrequest(idguest:String){
        val pDialog = KAlertDialog(this, KAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading"
        pDialog.setCancelable(false)
        pDialog.show()
        RetrofitClient.instanceUserApi.getDetailGuestRequest("Bearer " + token,idguest)
            .enqueue(object : Callback<ListDataGuestRequestItem> {
                override fun onResponse(
                    call: Call<ListDataGuestRequestItem>,
                    response: Response<ListDataGuestRequestItem>
                ) {
                    pDialog.dismissWithAnimation()
                    Log.d("Detail", response.body().toString())
                    // Cek Koneksi API Behasil
                    if (response.code().toString() == "200") {
                        //Cek Output Json
                        //Set data JSON ke tampilan
                        val data = response.body()
                        binding.editGsTeacher.setText(data?.topTalent?.fullName.toString())
                        binding.editGsName.setText(data?.name.toString())
                        binding.editGsDesc.setText(data?.description.toString())
                        binding.editGsDate.setText(data?.dateOfTeaching.toString())
                        binding.editGsTimeStart.setText(data?.timeStart.toString())
                        binding.editGsTimeEnd.setText(data?.timeFinished.toString())
                        binding.editGsClass.setText(data?.classX?.name.toString())
                        // Cek Koneksi API Gagal
                    } else {
                        Toast.makeText(applicationContext, "Failed To Get Data", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                override fun onFailure(call: Call<ListDataGuestRequestItem>, ts: Throwable) {
                    Log.d("Error", ts.message.toString())
                }
            })
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun EditRequest(idrequest: String, status: String) {
        val pDialog = KAlertDialog(this, KAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading"
        pDialog.setCancelable(false)
        pDialog.changeAlertType(KAlertDialog.PROGRESS_TYPE)
        pDialog.show()
        RetrofitClient.instanceUserApi.editGuestRequest(
            "Bearer " + token,
            status,
            idrequest,
        )
            .enqueue(object : Callback<ListDataGuestRequestItem> {
                override fun onResponse(
                    call: Call<ListDataGuestRequestItem>,
                    response: Response<ListDataGuestRequestItem>
                ) {
                    if (response.isSuccessful) {
                        // Saat response sukses finnish activity (menutup/mengakhiri activity editprofile)
                        var statusrespon: String
                        if (status == "Approved") {
                            statusrespon = "Diterima"
                        } else {
                            statusrespon = "Ditolak"
                        }
                        pDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE)
                        pDialog.setTitleText("Berhasil")
                        pDialog.setContentText("Permohonan berhasil $statusrespon")
                        pDialog.setConfirmText("OK")
                        pDialog.setConfirmClickListener { sDialog ->
                            sDialog.dismissWithAnimation()
                            finish()
                        }
                    } else {
                        Toast.makeText(applicationContext, "Gagal cek Intenet anda", Toast.LENGTH_SHORT).show()
                        pDialog.dismissWithAnimation()
                    }
                }

                override fun onFailure(calls: Call<ListDataGuestRequestItem>, ts: Throwable) {
                    Log.d("Error", ts.message.toString())
                    pDialog.dismissWithAnimation()
                }
            })
    }
}