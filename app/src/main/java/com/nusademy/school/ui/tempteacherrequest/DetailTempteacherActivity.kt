package com.nusademy.school.ui.tempteacherrequest

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.developer.kalert.KAlertDialog
import com.nusademy.nusademy.dataapi.RetrofitClient
import com.nusademy.nusademy.storage.SharedPrefManager
import com.nusademy.school.R
import com.nusademy.school.dataapi.ListdataTemporaryRequest.ListdataTemporaryRequestItem
import com.nusademy.school.databinding.ActivityDetailGuestTeacherRequestBinding
import com.nusademy.school.databinding.ActivityDetailTempteacherBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailTempteacherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailTempteacherBinding
    val token= SharedPrefManager.getInstance(this).Getuser.token
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTempteacherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
        actionBar?.setTitle("Informasi Request")
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        val idtemp = intent.getStringExtra("idtemp").toString()

        getrequest(idtemp)

        binding.btApprove.setOnClickListener {
            EditRequest(idtemp,"Approved")
        }

        binding.btReject.setOnClickListener {
            EditRequest(idtemp,"Rejected")
        }


    }

    fun getrequest(idtemp:String){
        val pDialog = KAlertDialog(this, KAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading"
        pDialog.setCancelable(false)
        pDialog.show()
        RetrofitClient.instanceUserApi.getDetailTempRequest("Bearer " + token,idtemp)
            .enqueue(object : Callback<ListdataTemporaryRequestItem> {
                override fun onResponse(
                    call: Call<ListdataTemporaryRequestItem>,
                    response: Response<ListdataTemporaryRequestItem>
                ) {
                    pDialog.dismissWithAnimation()
                    Log.d("Detail", response.body().toString())
                    // Cek Koneksi API Behasil
                    if (response.code().toString() == "200") {
                        //Cek Output Json
                        //Set data JSON ke tampilan
                        val data = response.body()
                        binding.editTmTeacher.setText(data?.teacher?.fullName.toString())
                        binding.editTmName.setText(data?.name.toString())
                        binding.editTmDuration.setText(data?.durations.toString()+" Bulan")
                        binding.editTmClass.setText(data?.classX?.name.toString())
                        binding.editTmDate.setText(data?.expectationsStartTeaching.toString())

                        // Cek Koneksi API Gagal
                    } else {
                        Toast.makeText(applicationContext, "Failed To Get Data", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                override fun onFailure(call: Call<ListdataTemporaryRequestItem>, ts: Throwable) {
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
        RetrofitClient.instanceUserApi.editTempRequest(
            "Bearer " + token,
            status,
            idrequest,
        )
            .enqueue(object : Callback<ListdataTemporaryRequestItem> {
                override fun onResponse(
                    call: Call<ListdataTemporaryRequestItem>,
                    response: Response<ListdataTemporaryRequestItem>
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

                override fun onFailure(calls: Call<ListdataTemporaryRequestItem>, ts: Throwable) {
                    Log.d("Error", ts.message.toString())
                    pDialog.dismissWithAnimation()
                }
            })
    }
}