package com.nusademy.school.ui.guestteacherrequest

import android.app.Dialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.developer.kalert.KAlertDialog
import com.nusademy.nusademy.dataapi.RetrofitClient
import com.nusademy.nusademy.storage.SharedPrefManager
import com.nusademy.school.R
import com.nusademy.school.dataapi.ListDataGuestRequest
import com.nusademy.school.dataapi.ListDataGuestRequest.ListDataGuestRequestItem
import com.nusademy.school.databinding.ActivityRequestListBinding
import com.nusademy.school.ui.guestteacherrequest.GuestTeacherRequestAdapter.ItemClickListener

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GuestTeacherRequestActivity : AppCompatActivity(), ItemClickListener {

    private val list = MutableLiveData<ArrayList<ListDataGuestRequestItem>>()
    private lateinit var binding: ActivityRequestListBinding
    private lateinit var dataAdapter: GuestTeacherRequestAdapter
    val token = SharedPrefManager.getInstance(this).Getuser.token
    val idschool = SharedPrefManager.getInstance(this).Getuser.idschool

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
        actionBar?.setTitle("Permohonan Guru Tamu")
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        GetListApply("Requested")

        dataAdapter = GuestTeacherRequestAdapter(this,true)
        dataAdapter.notifyDataSetChanged()
        binding.rvItems.apply {
            layoutManager =
                LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL, false
                )
            setHasFixedSize(true)
            adapter = dataAdapter
        }

        binding.toggleButtonGroup.check(R.id.bt_list_wait)
        binding.toggleButtonGroup.addOnButtonCheckedListener { toggleButtonGroup, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.bt_list_wait -> {
                        dataAdapter = GuestTeacherRequestAdapter(this,true)
                        dataAdapter.notifyDataSetChanged()
                        binding.rvItems.apply {
                            layoutManager =
                                LinearLayoutManager(
                                    context,
                                    LinearLayoutManager.VERTICAL, false
                                )
                            setHasFixedSize(true)
                            adapter = dataAdapter
                        }
                        GetListApply("Requested")

                    }
                    R.id.bt_list_accept -> {
                        dataAdapter = GuestTeacherRequestAdapter(this,false)
                        dataAdapter.notifyDataSetChanged()
                        binding.rvItems.apply {
                            layoutManager =
                                LinearLayoutManager(
                                    context,
                                    LinearLayoutManager.VERTICAL, false
                                )
                            setHasFixedSize(true)
                            adapter = dataAdapter
                        }
                        GetListApply("Approved")

                    }
                    R.id.bt_list_reject -> {
                        dataAdapter = GuestTeacherRequestAdapter(this,false)
                        dataAdapter.notifyDataSetChanged()
                        binding.rvItems.apply {
                            layoutManager =
                                LinearLayoutManager(
                                    context,
                                    LinearLayoutManager.VERTICAL, false
                                )
                            setHasFixedSize(true)
                            adapter = dataAdapter
                        }
                        GetListApply("Rejected")

                    }
                }
            } else {
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onApproveClick(id: String) {
        EditRequest(id, "Approved")
    }

    override fun onRejectClick(id: String) {
        EditRequest(id, "Rejected")
    }

    // Function Add Dialog -------------------------------------------------------------------------------------------
    private fun initCustomDialog(dialogtitle: String, edit: Boolean, idclass: String, nameclass: String) {
        val adddialog = Dialog(this)
        adddialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        adddialog.setCancelable(true)
        adddialog.setContentView(R.layout.dialog_class)
        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()

        adddialog.getWindow()?.setLayout(width, width)

        val name = adddialog.findViewById(R.id.edit_classname) as EditText
        val title = adddialog.findViewById(R.id.tv_title) as TextView
        val btCancel = adddialog.findViewById(R.id.bt_cancel) as Button

        if (edit) {
            name.setText(nameclass)
        }

        title.setText(dialogtitle)
        btCancel.setOnClickListener {
            adddialog.dismiss()
        }
        adddialog.show()
    }

    // Get List Data From API ----------------------------------------------------------------------------------------
    fun GetListApply(status:String) {
        setItems(idschool, token,status)
        getItems().observe(this, {
            if (it != null) {
                dataAdapter.setDataItem(it)
            }
        })
    }

    fun setItems(id: String, token: String,status:String) {
        val pDialog = KAlertDialog(this, KAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading"
        pDialog.setCancelable(false)
        pDialog.show()
        RetrofitClient.instanceUserApi.getGuestRequest("Bearer " + token, "Teacher", idschool, status)
            .enqueue(object : Callback<ListDataGuestRequest> {
                override fun onResponse(call: Call<ListDataGuestRequest>, response: Response<ListDataGuestRequest>) {
                    Log.d("JSON", response.toString())
                    if (response.isSuccessful) {
                        list.postValue(response.body())
                        pDialog.dismissWithAnimation()
                    }
                }

                override fun onFailure(call: Call<ListDataGuestRequest>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                    pDialog.dismissWithAnimation()
                }
            })
    }

    fun getItems(): LiveData<ArrayList<ListDataGuestRequestItem>> {
        return list
    }

    // Function ADDClass API -----------------------------------------------------------------------------------------
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
                            GetListApply("Requested")
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


    override fun onResume() {
        super.onResume()
        GetListApply("Requested")
    }
}


    
    
    
    
    
    
    
    
