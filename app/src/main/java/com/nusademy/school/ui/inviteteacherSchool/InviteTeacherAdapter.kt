package com.nusademy.school.ui.inviteteacherSchool

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nusademy.school.databinding.ItemInviteTeacherBinding

class InviteTeacherAdapter : RecyclerView.Adapter<InviteTeacherAdapter.ListUserViewHolder>() {

    val schoolAdapter = ArrayList<ItemInviteTeacher>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setDataSchool(setSchoolData: ArrayList<ItemInviteTeacher>) {
        schoolAdapter.clear()
        schoolAdapter.addAll(setSchoolData)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemInviteTeacher)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: ListUserViewHolder, position: Int) {
        holder.bind(schoolAdapter[position])
    }

    override fun getItemCount(): Int = schoolAdapter.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserViewHolder {
        return ListUserViewHolder(ItemInviteTeacherBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    inner class ListUserViewHolder(val binding: ItemInviteTeacherBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dataSchool: ItemInviteTeacher) {
            Glide.with(itemView.context)
                    .load(dataSchool.avatarTeacher)
                    .apply(RequestOptions().override(60, 60))
                    .into(binding.ivAvatarTeacher)

            binding.txtNameTeacher.text = dataSchool.nameTeacher
            binding.txtStatusTeacher.text = dataSchool.statusTeacher
            binding.txtDateTeacher.text = dataSchool.dateTeacher

            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(dataSchool)
            }
        }
    }
}