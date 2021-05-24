package com.nusademy.nusademy.ui.managerecruitmentSchool

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nusademy.nusademy.databinding.ItemsSchoolRecommendationBinding
import com.nusademy.ui.schoolrecommendationTeacher.ItemSchoolRecommendation

class ManageRecruitmentAdapter : RecyclerView.Adapter<ManageRecruitmentAdapter.ListUserViewHolder>() {

    val schoolAdapter = ArrayList<ItemSchoolRecommendation>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setDataTeacher(setTeacherData: ArrayList<ItemSchoolRecommendation>) {
        schoolAdapter.clear()
        schoolAdapter.addAll(setTeacherData)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemSchoolRecommendation)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: ListUserViewHolder, position: Int) {
        holder.bind(schoolAdapter[position])
    }

    override fun getItemCount(): Int = schoolAdapter.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserViewHolder {
        return ListUserViewHolder(ItemsSchoolRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    inner class ListUserViewHolder(val binding: ItemsSchoolRecommendationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dataSchool: ItemSchoolRecommendation) {

            binding.txtNameSchool.text = dataSchool.nameSchool
            binding.txtStatusSchool.text = dataSchool.statusSchool
            binding.txtDateSchool.text = dataSchool.dateSchool

            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(dataSchool)
            }
        }
    }
}