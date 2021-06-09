package com.nusademy.school.ui.classes

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.developer.kalert.KAlertDialog
import com.nusademy.nusademy.dataapi.RetrofitClient
import com.nusademy.nusademy.storage.SharedPrefManager
import com.nusademy.school.R
import com.nusademy.school.dataapi.ListDataClasses
import com.nusademy.school.dataapi.ListDataClasses.ListDataClassesItem
import com.nusademy.school.databinding.ActivityClassesBinding
import com.nusademy.school.ui.classes.ClassesAdapter.ItemClickListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClassesActivity : AppCompatActivity(), ItemClickListener{

    private val list = MutableLiveData<ArrayList<ListDataClassesItem>>()
    private lateinit var dataBinding: ActivityClassesBinding
    private lateinit var dataAdapter: ClassesAdapter
    val token= SharedPrefManager.getInstance(this).Getuser.token
    val id= SharedPrefManager.getInstance(this).Getuser.idschool

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityClassesBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
        actionBar?.setTitle("Kelas")
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)



        GetListClass()

        dataAdapter = ClassesAdapter(this)
        dataAdapter.notifyDataSetChanged()


        dataBinding.rvItems.apply {
            layoutManager =
                LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL, false
                )
            setHasFixedSize(true)
            adapter = dataAdapter
        }

        dataBinding.btAdd.setOnClickListener {
            initCustomDialog("Tambah Kelas", false, "", "")
        }


    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    override fun onEditClick(id: String, name: String){
        initCustomDialog("Tambah Kelas", true, id, name)
    }

    override fun onDelClick(id: String){
    DELClass(id)
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
        val btSave = adddialog.findViewById(R.id.bt_save) as Button
        val btCancel = adddialog.findViewById(R.id.bt_cancel) as Button

        if(edit){
            name.setText(nameclass)
        }

        title.setText(dialogtitle)
        btCancel.setOnClickListener {
            adddialog.dismiss()
        }
        btSave.setOnClickListener {
            if(edit){
                EDITClass(name.text.toString(), idclass)
            }else{
                ADDClass(name.text.toString())
            }

            adddialog.dismiss()
        }
        adddialog.show()
    }



    // Get List Data From API ----------------------------------------------------------------------------------------
    fun GetListClass(){
        setItems(id, token)
        getItems().observe(this, {
            if (it != null) {
                dataAdapter.setDataItem(it)
            }
        })
    }
    fun setItems(id: String, token: String) {
        val pDialog = KAlertDialog(this, KAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading"
        pDialog.setCancelable(false)
        pDialog.show()
        RetrofitClient.instanceUserApi.getClasses(id, "school.id", "Bearer " + token)
            .enqueue(object : Callback<ListDataClasses> {
                override fun onResponse(call: Call<ListDataClasses>, response: Response<ListDataClasses>) {
                    Log.d("JSON", response.toString())
                    if (response.isSuccessful) {
                        list.postValue(response.body())
                        pDialog.dismissWithAnimation()
                    }
                }

                override fun onFailure(call: Call<ListDataClasses>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                    pDialog.dismissWithAnimation()
                }
            })
    }
    fun getItems(): LiveData<ArrayList<ListDataClassesItem>> {
        return list
    }

    // Function ADDClass API -----------------------------------------------------------------------------------------
    fun ADDClass(name: String) {
        val pDialog = KAlertDialog(this, KAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading"
        pDialog.setCancelable(false)
        pDialog.changeAlertType(KAlertDialog.PROGRESS_TYPE)
        pDialog.show()
        RetrofitClient.instanceUserApi.addClasses(
            "Bearer " + token,
            name,
            id
        )
            .enqueue(object : Callback<ListDataClassesItem> {
                override fun onResponse(
                    call: Call<ListDataClassesItem>,
                    response: Response<ListDataClassesItem>
                ) {
                    if (response.isSuccessful) {
                        // Saat response sukses finnish activity (menutup/mengakhiri activity editprofile)

                        pDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE)
                        pDialog.setTitleText("Berhasil")
                        pDialog.setContentText("Kelas $name berhasil ditambahkan")
                        pDialog.setConfirmText("OK")
                        pDialog.setConfirmClickListener { sDialog ->
                            sDialog.dismissWithAnimation()
                            GetListClass()
                        }
                    } else {
                        Toast.makeText(applicationContext, "Gagal Cek kembali Isian", Toast.LENGTH_SHORT).show()
                        pDialog.dismissWithAnimation()
                    }
                }

                override fun onFailure(calls: Call<ListDataClassesItem>, ts: Throwable) {
                    Log.d("Error", ts.message.toString())
                    pDialog.dismissWithAnimation()
                }
            })
    }

    // Function ADDClass API -----------------------------------------------------------------------------------------
    fun EDITClass(name: String, idclass: String) {
        val pDialog = KAlertDialog(this, KAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading"
        pDialog.setCancelable(false)
        pDialog.changeAlertType(KAlertDialog.PROGRESS_TYPE)
        pDialog.show()
        RetrofitClient.instanceUserApi.editClasses(
            "Bearer " + token,
            name,
            idclass
        )
            .enqueue(object : Callback<ListDataClassesItem> {
                override fun onResponse(
                    call: Call<ListDataClassesItem>,
                    response: Response<ListDataClassesItem>
                ) {
                    if (response.isSuccessful) {
                        // Saat response sukses finnish activity (menutup/mengakhiri activity editprofile)

                        pDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE)
                        pDialog.setTitleText("Berhasil")
                        pDialog.setContentText("Kelas $name berhasil diperbarui")
                        pDialog.setConfirmText("OK")
                        pDialog.setConfirmClickListener { sDialog ->
                            sDialog.dismissWithAnimation()
                            GetListClass()
                        }
                    } else {
                        pDialog.dismissWithAnimation()
                        Toast.makeText(applicationContext, "Gagal Cek kembali Isian", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(calls: Call<ListDataClassesItem>, ts: Throwable) {
                    Log.d("Error", ts.message.toString())
                    pDialog.dismissWithAnimation()
                }
            })
    }

    // Function DELClass API -----------------------------------------------------------------------------------------
    fun DELClass(idclass: String) {
        val pDialog = KAlertDialog(this, KAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading"
        pDialog.setCancelable(false)
        pDialog.show()
        RetrofitClient.instanceUserApi.delClasses("Bearer " + token, idclass)
            .enqueue(object : Callback<ListDataClassesItem> {
                override fun onResponse(
                    call: Call<ListDataClassesItem>,
                    response: Response<ListDataClassesItem>
                ) {
                    if (response.isSuccessful) {
                        // Saat response sukses finnish activity (menutup/mengakhiri activity editprofile)
                        pDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE)
                        pDialog.setTitleText("Berhasil")
                        pDialog.setContentText("Kelas ${response.body()?.name} berhasil dihapus")
                        pDialog.setConfirmText("OK")
                        pDialog.setConfirmClickListener { sDialog ->
                            sDialog.dismissWithAnimation()
                            GetListClass()
                        }
                    } else {
                        Toast.makeText(applicationContext, "Gagal Cek kembali Isian", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(calls: Call<ListDataClassesItem>, ts: Throwable) {
                    Log.d("Error", ts.message.toString())
                }
            })
    }
}