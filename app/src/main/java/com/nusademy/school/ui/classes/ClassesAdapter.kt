package com.nusademy.school.ui.classes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.init
import com.developer.kalert.KAlertDialog
import com.nusademy.nusademy.dataapi.RetrofitClient
import com.nusademy.school.dataapi.ListDataClasses.ListDataClassesItem
import com.nusademy.school.databinding.ItemClassBinding

import com.nusademy.school.databinding.ItemListTeacherBinding
import com.nusademy.school.ui.subject.SubjectActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClassesAdapter(val itemClickListener: ItemClickListener) : RecyclerView.Adapter<ClassesAdapter.ListDataViewHolder>() {

    val dataAdapter = ArrayList<ListDataClassesItem>()
//    private lateinit var itemClickListener: ItemClickListener
    private lateinit var pDialog: KAlertDialog
    fun setDataItem(setItemData: ArrayList<ListDataClassesItem>) {
        dataAdapter.clear()
        dataAdapter.addAll(setItemData)
        notifyDataSetChanged()
    }

    interface ItemClickListener{
        fun onEditClick(id: String,name:String)
        fun onDelClick(id: String)
    }



    override fun onBindViewHolder(holder: ListDataViewHolder, position: Int) {
        holder.bind(dataAdapter[position])
    }

    override fun getItemCount(): Int = dataAdapter.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDataViewHolder {
        return ListDataViewHolder(ItemClassBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    inner class ListDataViewHolder(val binding: ItemClassBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListDataClassesItem) {
//            Glide.with(itemView.context)
//                .load(dataUser.avatar)
//                .apply(RequestOptions().override(60, 60))
//                .into(binding.ivAvatarUrl)

            binding.tvClassName.text = data.name.toString()
            binding.root.setOnClickListener {

//                val bundle = Bundle()
//                bundle.putString("nameclass",data.name.toString())
//                bundle.putString("idclass",  data.id.toString())


                val intent = Intent(itemView.context, SubjectActivity::class.java)
                intent.putExtra("nameclass", data.name.toString())
                intent.putExtra("idclass", data.id.toString())
//                intent.putExtras(bundle)
                itemView.context.startActivity(intent)

            }

            binding.btDel.setOnClickListener {
               itemClickListener.onDelClick(data.id.toString())}

            binding.btEdit.setOnClickListener {
                itemClickListener.onEditClick(data.id.toString(),data.name.toString())}


            }

        }
    }
