package com.nusademy.school.dataapi
import com.google.gson.annotations.SerializedName


data class DataBasicUser(
    @SerializedName("assignToRole")
    val assignToRole: Any?,
    @SerializedName("blocked")
    val blocked: Boolean,
    @SerializedName("confirmed")
    val confirmed: Boolean,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("guest_teacher_jobs")
    val guestTeacherJobs: List<Any>,
    @SerializedName("guest_teacher_requests")
    val guestTeacherRequests: List<Any>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("mbti_result")
    val mbtiResult: Any?,
    @SerializedName("narration_videos")
    val narrationVideos: List<Any>,
    @SerializedName("provider")
    val provider: String,
    @SerializedName("role")
    val role: Role,
    @SerializedName("school")
    val school: School,
    @SerializedName("teacher")
    val teacher: Any?,
    @SerializedName("temporary_teacher_jobs")
    val temporaryTeacherJobs: List<Any>,
    @SerializedName("temporary_teacher_requests")
    val temporaryTeacherRequests: List<Any>,
    @SerializedName("top_talent")
    val topTalent: Any?,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("username")
    val username: String
)

data class Role(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String
)

data class School(
    @SerializedName("address")
    val address: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("creator")
    val creator: Int,
    @SerializedName("headmaster")
    val headmaster: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("website")
    val website: String
)