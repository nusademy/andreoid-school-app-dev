package com.nusademy.school.dataapi
import com.google.gson.annotations.SerializedName
import com.nusademy.school.dataapi.ListDataSubject.ListDataSubjectItem

class ListDataSubject : ArrayList<ListDataSubjectItem>(){
    data class ListDataSubjectItem(
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("Name")
        val name: String = "",
        @SerializedName("Description")
        val description: String = "",
        @SerializedName("Learning_objectives")
        val learningObjectives: String = "",
        @SerializedName("class")
        val classX: Class? = null,
        @SerializedName("created_at")
        val createdAt: String = "",
        @SerializedName("updated_at")
        val updatedAt: String = ""
    ) {
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