package com.nusademy.school.dataapi
import com.google.gson.annotations.SerializedName

data class DataTeacher(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("last_education")
    val lastEducation: String = "",
    @SerializedName("campus")
    val campus: String = "",
    @SerializedName("major")
    val major: String = "",
    @SerializedName("ipk")
    val ipk: Int = 0,
    @SerializedName("short_brief")
    val shortBrief: String = "",
    @SerializedName("video_branding")
    val videoBranding: Any? = null,
    @SerializedName("linkedin")
    val linkedin: String = "",
    @SerializedName("domicilie")
    val domicilie: Domicilie = Domicilie(),
    @SerializedName("spesialitation")
    val spesialitation: Spesialitation = Spesialitation(),
    @SerializedName("user")
    val user: User = User(),
    @SerializedName("published_at")
    val publishedAt: String = "",
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("updated_at")
    val updatedAt: String = ""
) {
    data class Domicilie(
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("Name")
        val name: String = "",
        @SerializedName("created_at")
        val createdAt: String = "",
        @SerializedName("updated_at")
        val updatedAt: String = ""
    )

    data class Spesialitation(
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("Name")
        val name: String = "",
        @SerializedName("Category")
        val category: String = "",
        @SerializedName("created_at")
        val createdAt: String = "",
        @SerializedName("updated_at")
        val updatedAt: String = ""
    )

    data class User(
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
        val school: Any? = null,
        @SerializedName("teacher")
        val teacher: Int = 0
    )
}
