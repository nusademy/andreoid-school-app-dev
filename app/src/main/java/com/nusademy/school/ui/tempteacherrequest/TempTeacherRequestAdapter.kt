package com.nusademy.school.ui.tempteacherrequest

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.developer.kalert.KAlertDialog
import com.nusademy.school.dataapi.ListdataTemporaryRequest.ListdataTemporaryRequestItem
import com.nusademy.school.databinding.ItemApplyrequestBinding
import com.nusademy.school.ui.guestteacherrequest.DetailGuestTeacherRequestActivity
import com.nusademy.school.ui.tempteacherrequest.TempTeacherRequestActivity

class TempTeacherRequestAdapter(val itemClickListener: TempTeacherRequestActivity,val enable:Boolean) : RecyclerView.Adapter<TempTeacherRequestAdapter.ListDataViewHolder>() {

    val dataAdapter = ArrayList<ListdataTemporaryRequestItem>()

    //    private lateinit var itemClickListener: ItemClickListener
    private lateinit var pDialog: KAlertDialog
    fun setDataItem(setItemData: ArrayList<ListdataTemporaryRequestItem>) {
        dataAdapter.clear()
        dataAdapter.addAll(setItemData)
        notifyDataSetChanged()
    }

    interface ItemClickListener {

        fun onApproveClick(idrequest: String)
        fun onRejectClick(idrequest: String)
    }

    override fun onBindViewHolder(holder: ListDataViewHolder, position: Int) {
        holder.bind(dataAdapter[position])
    }

    override fun getItemCount(): Int = dataAdapter.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDataViewHolder {
        return ListDataViewHolder(ItemApplyrequestBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    inner class ListDataViewHolder(val binding: ItemApplyrequestBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ListdataTemporaryRequestItem) {
//            Glide.with(itemView.context)
//                .load(dataUser.avatar)
//                .apply(RequestOptions().override(60, 60))
//                .into(binding.ivAvatarUrl)

            binding.tvRequestName.text = data.name.toString()
            binding.tvTeacherName.text = data.teacher.fullName.toString()


            if (enable) {
                binding.root.setOnClickListener {
                    val intent = Intent(itemView.context, DetailTempteacherActivity::class.java)
                    intent.putExtra("idtemp", data?.id.toString())
                    itemView.context.startActivity(intent)
                }
            }

                binding.btApprove.isVisible = false
                binding.btReject.isVisible = false


                binding.btApprove.setOnClickListener {
                    itemClickListener.onApproveClick(data.id.toString())
                }

                binding.btReject.setOnClickListener {
                    itemClickListener.onRejectClick(data.id.toString())
                }
            }
           }
    }

