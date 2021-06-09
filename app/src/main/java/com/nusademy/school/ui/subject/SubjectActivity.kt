package com.nusademy.school.ui.subject

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
import com.nusademy.school.dataapi.ListDataSubject
import com.nusademy.school.dataapi.ListDataSubject.ListDataSubjectItem
import com.nusademy.school.databinding.ActivitySubjectBinding
import com.nusademy.school.ui.subject.SubjectAdapter.ItemClickListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubjectActivity : AppCompatActivity(), ItemClickListener {

    private val list = MutableLiveData<ArrayList<ListDataSubjectItem>>()
    private lateinit var dataBinding: ActivitySubjectBinding
    private lateinit var dataAdapter: SubjectAdapter
    val token= SharedPrefManager.getInstance(this).Getuser.token
    val id= SharedPrefManager.getInstance(this).Getuser.idschool

//    val bundle = intent.extras
//    val classname = bundle!!.getString("nameclass","0")
//    val classid = bundle!!.getString("idclass","0")

    var classname = ""
    var classid = ""




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivitySubjectBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
        actionBar?.setTitle("Mata Pelajaran")
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        classname = intent.getStringExtra("nameclass").toString()
        classid = intent.getStringExtra("idclass").toString()
        dataBinding.tvClass.text=classname




        GetListSubject()

        dataAdapter = SubjectAdapter(this)
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

        dataBinding.btAddSubject.setOnClickListener {
            initCustomDialog("Tambah Mata Pelajaran", false, "", "","","")
        }


    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }



    override fun onEditClick(id: String, name: String,decs:String,obj:String){
        initCustomDialog("Edit Subject", true, id, name,decs,obj)
    }

    override fun onDelClick(id: String){
    DELSubject(id)
    }

    // Function Add Dialog -------------------------------------------------------------------------------------------
    private fun initCustomDialog(dialogtitle: String, edit: Boolean, idsubject: String, namesubject: String, decssubject:String, objsubject:String) {
        val adddialog = Dialog(this)
        adddialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        adddialog.setCancelable(true)
        adddialog.setContentView(R.layout.dialog_subject)
        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()

        adddialog.getWindow()?.setLayout(width, (width * 1.3).toInt())

        val name = adddialog.findViewById(R.id.edit_subjectname) as EditText
        val desc = adddialog.findViewById(R.id.edit_subjectdesc) as EditText
        val obj = adddialog.findViewById(R.id.edit_subjectobj) as EditText
        val title = adddialog.findViewById(R.id.tv_title) as TextView
        val btSave = adddialog.findViewById(R.id.bt_save) as Button
        val btCancel = adddialog.findViewById(R.id.bt_cancel) as Button

        if(edit){
            name.setText(namesubject)
            desc.setText(decssubject)
            obj.setText(objsubject)
        }

        title.setText(dialogtitle)
        btCancel.setOnClickListener {
            adddialog.dismiss()
        }
        btSave.setOnClickListener {
            if(edit){
                EDITSubject(
                    name.text.toString(),
                    idsubject,
                    desc.text.toString(),
                    obj.text.toString()
                )
            }else{
                ADDSubject(
                    name.text.toString(),
                    desc.text.toString(),
                    obj.text.toString(),
                    classid
                )
            }

            adddialog.dismiss()
        }
        adddialog.show()
    }



    // Get List Data From API ----------------------------------------------------------------------------------------
    fun GetListSubject(){
        setItems(classid.toString(), token)
        getItems().observe(this, {
            if (it != null) {
                dataAdapter.setDataItem(it)
            }
        })
    }
    fun setItems(idclass: String, token: String) {
        val pDialog = KAlertDialog(this, KAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading"
        pDialog.setCancelable(false)
        pDialog.show()
        RetrofitClient.instanceUserApi.getSubject(idclass, "id", "Bearer " + token)
            .enqueue(object : Callback<ListDataSubject> {
                override fun onResponse(call: Call<ListDataSubject>, response: Response<ListDataSubject>) {
                    Log.d("JSON", response.toString())
                    if (response.isSuccessful) {
                        list.postValue(response.body())
                        pDialog.dismissWithAnimation()
                    }
                }

                override fun onFailure(call: Call<ListDataSubject>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                    pDialog.dismissWithAnimation()
                }
            })
    }
    fun getItems(): LiveData<ArrayList<ListDataSubjectItem>> {
        return list
    }

    // Function ADDSubject API -----------------------------------------------------------------------------------------
    fun ADDSubject(name: String, description:String,objective:String,idclass:String) {
        val pDialog = KAlertDialog(this, KAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading"
        pDialog.setCancelable(false)
        pDialog.changeAlertType(KAlertDialog.PROGRESS_TYPE)
        pDialog.show()
        RetrofitClient.instanceUserApi.addSubject(
            "Bearer " + token,
            name,
            description,
            objective,
            idclass
        )
            .enqueue(object : Callback<ListDataSubjectItem> {
                override fun onResponse(
                    call: Call<ListDataSubjectItem>,
                    response: Response<ListDataSubjectItem>
                ) {
                    Log.d("subject",response.toString())
                    if (response.isSuccessful) {
                        // Saat response sukses finnish activity (menutup/mengakhiri activity editprofile)

                        pDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE)
                        pDialog.setTitleText("Berhasil")
                        pDialog.setContentText("Kelas $name berhasil ditambahkan")
                        pDialog.setConfirmText("OK")
                        pDialog.setConfirmClickListener { sDialog ->
                            sDialog.dismissWithAnimation()
                            GetListSubject()
                        }
                    } else {
                        Toast.makeText(applicationContext, "Gagal Cek kembali Isian", Toast.LENGTH_SHORT).show()
                        pDialog.dismissWithAnimation()
                    }
                }

                override fun onFailure(calls: Call<ListDataSubjectItem>, ts: Throwable) {
                    Log.d("Error", ts.message.toString())
                    pDialog.dismissWithAnimation()
                }
            })
    }

    // Function ADDSubject API -----------------------------------------------------------------------------------------
    fun EDITSubject(name: String, idsubject: String,description:String,objective:String) {
        val pDialog = KAlertDialog(this, KAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading"
        pDialog.setCancelable(false)
        pDialog.changeAlertType(KAlertDialog.PROGRESS_TYPE)
        pDialog.show()
        RetrofitClient.instanceUserApi.editSubject(
            "Bearer " + token,
            name,
            description,
            objective,
            idsubject
        )
            .enqueue(object : Callback<ListDataSubjectItem> {
                override fun onResponse(
                    call: Call<ListDataSubjectItem>,
                    response: Response<ListDataSubjectItem>
                ) {
                    Log.d("subject",response.toString())

                    if (response.isSuccessful) {
                        // Saat response sukses finnish activity (menutup/mengakhiri activity editprofile)

                        pDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE)
                        pDialog.setTitleText("Berhasil")
                        pDialog.setContentText("Kelas $name berhasil diperbarui")
                        pDialog.setConfirmText("OK")
                        pDialog.setConfirmClickListener { sDialog ->
                            sDialog.dismissWithAnimation()
                            GetListSubject()
                        }
                    } else {
                        pDialog.dismissWithAnimation()
                        Toast.makeText(applicationContext, "Gagal Cek kembali Isian", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(calls: Call<ListDataSubjectItem>, ts: Throwable) {
                    Log.d("Error", ts.message.toString())
                    pDialog.dismissWithAnimation()
                }
            })
    }

    // Function DELSubject API -----------------------------------------------------------------------------------------
    fun DELSubject(idclass: String) {
        val pDialog = KAlertDialog(this, KAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading"
        pDialog.setCancelable(false)
        pDialog.show()
        RetrofitClient.instanceUserApi.delSubject("Bearer " + token, idclass)
            .enqueue(object : Callback<ListDataSubjectItem> {
                override fun onResponse(
                    call: Call<ListDataSubjectItem>,
                    response: Response<ListDataSubjectItem>
                ) {
                    if (response.isSuccessful) {
                        // Saat response sukses finnish activity (menutup/mengakhiri activity editprofile)
                        pDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE)
                        pDialog.setTitleText("Berhasil")
                        pDialog.setContentText("Kelas ${response.body()?.name} berhasil dihapus")
                        pDialog.setConfirmText("OK")
                        pDialog.setConfirmClickListener { sDialog ->
                            sDialog.dismissWithAnimation()
                            GetListSubject()
                        }
                    } else {
                        Toast.makeText(applicationContext, "Gagal Cek kembali Isian", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(calls: Call<ListDataSubjectItem>, ts: Throwable) {
                    Log.d("Error", ts.message.toString())
                }
            })
    }
}