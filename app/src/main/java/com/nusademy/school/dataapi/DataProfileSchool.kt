package com.nusademy.school.dataapi
import com.google.gson.annotations.SerializedName


data class DataProfileSchool(
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
    val creator: Creator = Creator(),
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("updated_at")
    val updatedAt: String = "",
    @SerializedName("classes")
    val classes: List<Classe> = listOf(),
    @SerializedName("temporary_teacher_requests")
    val temporaryTeacherRequests: List<TemporaryTeacherRequest> = listOf(),
    @SerializedName("guest_teacher_requests")
    val guestTeacherRequests: List<GuestTeacherRequest> = listOf(),
    @SerializedName("temporary_teacher_jobs")
    val temporaryTeacherJobs: List<Any> = listOf(),
    @SerializedName("guest_teacher_jobs")
    val guestTeacherJobs: List<GuestTeacherJob> = listOf()
) {
    data class Creator(
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
        val teacher: Any? = null
    )

    data class Classe(
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

    data class TemporaryTeacherRequest(
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("Name")
        val name: String = "",
        @SerializedName("class")
        val classX: Int = 0,
        @SerializedName("durations")
        val durations: Int = 0,
        @SerializedName("expectations_start_teaching")
        val expectationsStartTeaching: String = "",
        @SerializedName("Notes")
        val notes: String = "",
        @SerializedName("Status")
        val status: String = "",
        @SerializedName("top_talent")
        val topTalent: Any? = null,
        @SerializedName("school")
        val school: Int = 0,
        @SerializedName("created_at")
        val createdAt: String = "",
        @SerializedName("updated_at")
        val updatedAt: String = "",
        @SerializedName("teacher")
        val teacher: Int = 0
    )

    data class GuestTeacherRequest(
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
        val notes: Any? = null,
        @SerializedName("Status")
        val status: String = "",
        @SerializedName("time_start")
        val timeStart: String = "",
        @SerializedName("time_finished")
        val timeFinished: String = "",
        @SerializedName("top_talent")
        val topTalent: Int = 0,
        @SerializedName("school")
        val school: Int = 0,
        @SerializedName("class")
        val classX: Int = 0,
        @SerializedName("created_at")
        val createdAt: String = "",
        @SerializedName("updated_at")
        val updatedAt: String = ""
    )

    data class GuestTeacherJob(
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("name")
        val name: String = "",
        @SerializedName("target_audience")
        val targetAudience: String = "",
        @SerializedName("Description")
        val description: String = "",
        @SerializedName("date")
        val date: String = "",
        @SerializedName("time_start")
        val timeStart: String = "",
        @SerializedName("time_finished")
        val timeFinished: String = "",
        @SerializedName("Status")
        val status: String = "",
        @SerializedName("school")
        val school: Int = 0,
        @SerializedName("class")
        val classX: Any? = null,
        @SerializedName("created_at")
        val createdAt: String = "",
        @SerializedName("updated_at")
        val updatedAt: String = ""
    )
}