package com.nusademy.school.dataapi


import com.google.gson.annotations.SerializedName
import com.nusademy.school.dataapi.ListDataGuestRequest.ListDataGuestRequestItem

class ListDataGuestRequest : ArrayList<ListDataGuestRequestItem>(){
    data class ListDataGuestRequestItem(
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("Name")
        val name: String = "",
        @SerializedName("target_audience")
        val targetAudience: String = "",
        @SerializedName("Description")
        val description: String = "",
        @SerializedName("date_of_teaching")
        val dateOfTeaching: String = "",
        @SerializedName("Notes")
        val notes: String? = null,
        @SerializedName("Status")
        val status: String = "",
        @SerializedName("time_start")
        val timeStart: String = "",
        @SerializedName("time_finished")
        val timeFinished: String = "",
        @SerializedName("top_talent")
        val topTalent: TopTalent = TopTalent(),
        @SerializedName("school")
        val school: School = School(),
        @SerializedName("class")
        val classX: Class? = null,
        @SerializedName("created_at")
        val createdAt: String = "",
        @SerializedName("updated_at")
        val updatedAt: String = "",
        @SerializedName("CreatedBy")
        val createdBy: String = ""
    ) {
        data class TopTalent(
            @SerializedName("id")
            val id: Int = 0,
            @SerializedName("username")
            val username: String = "",
            @SerializedName("email")
            val email: String = "",
            @SerializedName("provider")
            val provider: String = "",
            @SerializedName("confirmed")
            val confirmed: Boolean = false,
            @SerializedName("blocked")
            val blocked: Boolean = false,
            @SerializedName("role")
            val role: Int = 0,
            @SerializedName("created_at")
            val createdAt: String = "",
            @SerializedName("updated_at")
            val updatedAt: String = "",
            @SerializedName("top_talent")
            val topTalent: Any? = null,
            @SerializedName("full_name")
            val fullName: String = "",
            @SerializedName("school")
            val school: Int = 0,
            @SerializedName("teacher")
            val teacher: Any? = null,
            @SerializedName("mbti_result")
            val mbtiResult: Any? = null,
            @SerializedName("assignToRole")
            val assignToRole: Any? = null
        )
        data class School(
            @SerializedName("id")
            val id: Int = 0,
            @SerializedName("name")
            val name: String = "",
            @SerializedName("headmaster")
            val headmaster: String = "",
            @SerializedName("address")
            val address: String = "",
            @SerializedName("phone_number")
            val phoneNumber: String = "",
            @SerializedName("website")
            val website: String = "",
            @SerializedName("creator")
            val creator: Int = 0,
            @SerializedName("created_at")
            val createdAt: String = "",
            @SerializedName("updated_at")
            val updatedAt: String = ""
        )
        data class Class(
            @SerializedName("id")
            val id: Int = 0,
            @SerializedName("name")
            val name: String = "",
            @SerializedName("school")
            val school: Int = 0,
            @SerializedName("created_at")
            val createdAt: String = "",
            @SerializedName("updated_at")
            val updatedAt: String = ""
        )
    }
}