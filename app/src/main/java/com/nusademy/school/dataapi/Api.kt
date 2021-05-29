package com.nusademy.nusademy.dataapi

import DataLogin
import DataLogin.User
import com.nusademy.school.dataapi.DataProfileSchool
import com.nusademy.school.dataapi.DataTeacher
import com.nusademy.school.dataapi.ListDataTeacher
import retrofit2.Call
import retrofit2.http.Body
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

    // POST //////////////////////////////////////////////////////////////////////////////////////////////////////////

    @FormUrlEncoded
    @POST("auth/local")
    fun login(
        @Field("identifier") email: String?,
        @Field("password") password: String?,
    ):Call<DataLogin>
}