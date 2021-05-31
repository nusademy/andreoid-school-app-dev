package com.nusademy.school.dataapi
import com.google.gson.annotations.SerializedName
import com.nusademy.school.dataapi.ListDataClasses.ListDataClassesItem

class ListDataClasses : ArrayList<ListDataClassesItem>(){
    data class ListDataClassesItem(
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("name")
        val name: String = "",
        @SerializedName("school")
        val school: School = School(),
        @SerializedName("created_at")
        val createdAt: String = "",
        @SerializedName("updated_at")
        val updatedAt: String = "",
        @SerializedName("subjects")
        val subjects: List<Subject> = listOf(),
        @SerializedName("temporary_teacher_requests")
        val temporaryTeacherRequests: List<Any> = listOf(),
        @SerializedName("guest_teacher_requests")
        val guestTeacherRequests: List<Any> = listOf(),
        @SerializedName("temporary_teacher_jobs")
        val temporaryTeacherJobs: List<Any> = listOf(),
        @SerializedName("guest_teacher_jobs")
        val guestTeacherJobs: List<Any> = listOf()
    ) {
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

        data class Subject(
            @SerializedName("id")
            val id: Int = 0,
            @SerializedName("Name")
            val name: String = "",
            @SerializedName("Description")
            val description: String = "",
            @SerializedName("Learning_objectives")
            val learningObjectives: String = "",
            @SerializedName("class")
            val classX: Int = 0,
            @SerializedName("created_at")
            val createdAt: String = "",
            @SerializedName("updated_at")
            val updatedAt: String = ""
        )
    }
}