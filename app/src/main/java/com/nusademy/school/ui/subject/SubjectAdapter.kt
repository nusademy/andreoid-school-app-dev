package com.nusademy.school.ui.subject


import android.view.LayoutInflater

import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.init
import com.developer.kalert.KAlertDialog
import com.nusademy.school.dataapi.ListDataSubject.ListDataSubjectItem
import com.nusademy.school.databinding.ItemSubjectBinding


class SubjectAdapter(val itemClickListener: ItemClickListener) : RecyclerView.Adapter<SubjectAdapter.ListDataViewHolder>() {

    val dataAdapter = ArrayList<ListDataSubjectItem>()
    private lateinit var pDialog: KAlertDialog
    fun setDataItem(setItemData: ArrayList<ListDataSubjectItem>) {
        dataAdapter.clear()
        dataAdapter.addAll(setItemData)
        notifyDataSetChanged()
    }

    interface ItemClickListener{
        fun onEditClick(id: String,name:String,decs:String,obj:String)
        fun onDelClick(id: String)
    }



    override fun onBindViewHolder(holder: ListDataViewHolder, position: Int) {
        holder.bind(dataAdapter[position])
    }

    override fun getItemCount(): Int = dataAdapter.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDataViewHolder {
        return ListDataViewHolder(ItemSubjectBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    inner class ListDataViewHolder(val binding: ItemSubjectBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListDataSubjectItem) {
//            Glide.with(itemView.context)
//                .load(dataUser.avatar)
//                .apply(RequestOptions().override(60, 60))
//                .into(binding.ivAvatarUrl)

            binding.tvSubjectName.text = data.name.toString()
            binding.tvSubjectDescription.text = data.description.toString()


            binding.btDel.setOnClickListener {
               itemClickListener.onDelClick(data.id.toString())}

            binding.btEdit.setOnClickListener {
                itemClickListener.onEditClick(data.id.toString(),data.name.toString(),data.description,data.learningObjectives)}


            }

        }
    }
