package com.nusademy.school.ui.searchteacher

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nusademy.school.dataapi.DataTeacher
import com.nusademy.school.dataapi.ListDataTeacher.ListDataTeacherItem
import com.nusademy.school.databinding.ItemListTeacherBinding
import com.nusademy.school.ui.requestteacher.RequestTeacherActivity
import com.nusademy.school.ui.subject.SubjectActivity

class SearchTeacherAdapter : RecyclerView.Adapter<SearchTeacherAdapter.ListUserViewHolder>() {

    val userAdapter = ArrayList<ListDataTeacherItem>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setDataUser(setUserData: ArrayList<ListDataTeacherItem>) {
        userAdapter.clear()
        userAdapter.addAll(setUserData)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ListDataTeacherItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
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
//            Glide.with(itemView.context)
//                .load(dataUser.avatar)
//                .apply(RequestOptions().override(60, 60))
//                .into(binding.ivAvatarUrl)

            binding.txtNameTeacher.text = data.user.fullName.toString()
            binding.txtStatusTeacher.text = data.spesialitation?.name.toString()

            binding.root.setOnClickListener {
                val intent = Intent(itemView.context, RequestTeacherActivity::class.java)
                intent.putExtra("idteacher", data.id.toString())
//                intent.putExtras(bundle)
                itemView.context.startActivity(intent)
            }
        }
    }
}