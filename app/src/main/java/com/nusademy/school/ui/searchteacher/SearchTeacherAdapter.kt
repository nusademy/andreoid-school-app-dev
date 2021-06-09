package com.nusademy.school.ui.searchteacher

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nusademy.school.R
import com.nusademy.school.dataapi.ListDataTeacher.ListDataTeacherItem
import com.nusademy.school.databinding.ItemListTeacherBinding
import com.nusademy.school.ui.guestteacherrequest.GuestTeacherRequestActivity
import com.nusademy.school.ui.requestteacher.RequestTeacherActivity
import com.nusademy.school.ui.subject.SubjectActivity

class SearchTeacherAdapter(val itemClickListener: SearchTeacherActivity) : RecyclerView.Adapter<SearchTeacherAdapter.ListUserViewHolder>() {

    val userAdapter = ArrayList<ListDataTeacherItem>()

    fun setDataUser(setUserData: ArrayList<ListDataTeacherItem>) {
        userAdapter.clear()
        userAdapter.addAll(setUserData)
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun onInviteClick(idteacher: String)
    }



    override fun onBindViewHolder(holder: ListUserViewHolder, position: Int) {
        holder.bind(userAdapter[position])
    }

    override fun getItemCount(): Int = userAdapter.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserViewHolder {
        return ListUserViewHolder(ItemListTeacherBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    inner class ListUserViewHolder(val binding: ItemListTeacherBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListDataTeacherItem) {
            Glide.with(itemView.context)
                .load(R.drawable.profile_null)
                .into(binding.ivAvatarTeacher)

            binding.txtNameTeacher.text = data.user.fullName.toString()
            binding.txtStatusTeacher.text = data.spesialitation?.name.toString()

            binding.root.setOnClickListener {
                itemClickListener.onInviteClick(data.id.toString())}
            }
        }
    }