package com.nusademy.school.ui.applyschoolTeacher

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nusademy.school.databinding.ItemApplySchoolBinding

class ApplySchoolAdapter : RecyclerView.Adapter<ApplySchoolAdapter.ListUserViewHolder>() {

    val schoolAdapter = ArrayList<ItemApplySchool>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setDataSchool(setSchoolData: ArrayList<ItemApplySchool>) {
        schoolAdapter.clear()
        schoolAdapter.addAll(setSchoolData)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemApplySchool)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: ListUserViewHolder, position: Int) {
        holder.bind(schoolAdapter[position])
    }

    override fun getItemCount(): Int = schoolAdapter.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserViewHolder {
        return ListUserViewHolder(ItemApplySchoolBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    inner class ListUserViewHolder(val binding: ItemApplySchoolBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dataSchool: ItemApplySchool) {
            Glide.with(itemView.context)
                .load(dataSchool.logoSchool)
                .apply(RequestOptions().override(60, 60))
                .into(binding.ivLogoSchool)

            binding.txtNameSchool.text = dataSchool.nameSchool
            binding.txtStatusSchool.text = dataSchool.statusSchool
            binding.txtDateSchool.text = dataSchool.dateSchool

            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(dataSchool)
            }
        }
    }
}