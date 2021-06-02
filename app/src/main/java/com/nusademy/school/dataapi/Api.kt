package com.nusademy.nusademy.dataapi

import DataLogin
import DataLogin.User
import com.nusademy.school.dataapi.DataProfileSchool
import com.nusademy.school.dataapi.DataTeacher
import com.nusademy.school.dataapi.ListDataClasses
import com.nusademy.school.dataapi.ListDataClasses.ListDataClassesItem
import com.nusademy.school.dataapi.ListDataGuestRequest.ListDataGuestRequestItem
import com.nusademy.school.dataapi.ListDataSubject
import com.nusademy.school.dataapi.ListDataSubject.ListDataSubjectItem
import com.nusademy.school.dataapi.ListDataTeacher
import com.nusademy.school.dataapi.ListdataTemporaryRequest.ListdataTemporaryRequestItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    // GET ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GET("teachers/{id}")
    fun getProfileTeacher(
        @Path("id") id: String,@Header("Authorization") token: String
    ): Call<DataTeacher>

    @GET("schools/{id}")
    fun getProfileSchool(
        @Path("id") id: String,@Header("Authorization") token: String
    ): Call<DataProfileSchool>

    @GET("teachers")
    fun getSearchTeacher(
        @Query("user.username_contains") name: String,@Header("Authorization") token: String
    ): Call<ListDataTeacher>

    @GET("classes")
    fun getClasses(
        @Query("school.id_contains") id: String,
        @Query("_sort") sort: String,
        @Header("Authorization") token: String
    ): Call<ListDataClasses>

    @GET("subjects")
    fun getSubject(
        @Query("class.id_contains") idclass: String,
        @Query("_sort") sort: String,
        @Header("Authorization") token: String
    ): Call<ListDataSubject>








    // PUT ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FormUrlEncoded
    @PUT("schools/{id}")
    fun editProfileSchool(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Field("name") name: String?,
        @Field("headmaster") headmaster: String?,
        @Field("address") address: String?,
        @Field("phone_number") phone_number: String?,
        @Field("website") website: String?,
        ):Call<DataProfileSchool>

    @FormUrlEncoded
    @PUT("classes/{id}")
    fun editClasses(
        @Header("Authorization") token: String,
        @Field("name") name: String?,
        @Path("id") idclass: String?,
    ):Call<ListDataClassesItem>

    @FormUrlEncoded
    @PUT("subjects/{id}")
    fun editSubject(
        @Header("Authorization") token: String,
        @Field("Name") name: String?,
        @Field("Description") description: String?,
        @Field("Learning_objectives") objective: String?,
        @Path("id") idsubject: String?,
    ):Call<ListDataSubjectItem>








    // POST //////////////////////////////////////////////////////////////////////////////////////////////////////////

    @FormUrlEncoded
    @POST("auth/local")
    fun login(
        @Field("identifier") email: String?,
        @Field("password") password: String?,
    ):Call<DataLogin>


    @FormUrlEncoded
    @POST("classes")
    fun addClasses(
        @Header("Authorization") token: String,
        @Field("name") name: String?,
        @Field("school") idcschool: String?,
    ):Call<ListDataClassesItem>


    @FormUrlEncoded
    @POST("subjects")
    fun addSubject(
        @Header("Authorization") token: String,
        @Field("Name") name: String?,
        @Field("Description") description: String?,
        @Field("Learning_objectives") objective: String?,
        @Field("class") idclass: String?,
    ):Call<ListDataSubjectItem>

    @FormUrlEncoded
    @POST("guest-teacher-requests")
    fun addGuestRequest(
        @Header("Authorization") token: String,
        @Field("Name") name: String?,
        @Field("Description") description: String?,
        @Field("date_of_teaching") dateteach: String?,
        @Field("time_start") timestart: String?,
        @Field("time_finished") timeend: String?,
        @Field("Notes") notes: String?,
        @Field("top_talent") idteacher: String?,
        @Field("school") idschool: String?,
        @Field("class") idclass: String?,
        @Field("target_audience") target: String?,
        @Field("Status") status: String?,
        @Field("CreatedBy") createdby: String?,
    ):Call<ListDataGuestRequestItem>

    @FormUrlEncoded
    @POST("temporary-teacher-requests")
    fun addTempRequest(
        @Header("Authorization") token: String,
        @Field("Name") name: String?,
        @Field("durations") durations: String?,
        @Field("expectations_start_teaching") dateteach: String?,
        @Field("class") idclass: String?,
        @Field("teacher") idteacher: String?,
        @Field("school") idschool: String?,
        @Field("Status") status: String?,
        @Field("CreatedBy") createdby: String?,
    ):Call<ListdataTemporaryRequestItem>







    // DELETE //////////////////////////////////////////////////////////////////////////////////////////////////////////
    @DELETE("classes/{id}")
    fun delClasses(
        @Header("Authorization") token: String,
        @Path("id") idclass: String?,
    ):Call<ListDataClassesItem>

    @DELETE("subjects/{id}")
    fun delSubject(
        @Header("Authorization") token: String,
        @Path("id") idclass: String?,
    ):Call<ListDataSubjectItem>








}