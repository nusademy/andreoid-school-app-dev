package com.nusademy.nusademy.storage
import android.content.Context


class SharedPrefManager private constructor(private val mCtx: Context) {


    val IsLogin :Boolean
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return  sharedPreferences.getBoolean("isLogin",false)
        }


    fun setLogin(islogin:Boolean) {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLogin", islogin)
        editor.apply()
    }

    fun setUser(id:String,idschool:String,token:String,name:String,role:String) {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("id", id)
        editor.putString("idschool", idschool)
        editor.putString("token", token)
        editor.putString("name", name)
        editor.putString("role", role)
        editor.apply()
    }

    val Getuser: UserModel
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return UserModel(
                sharedPreferences.getString("id", null).toString(),
                sharedPreferences.getString("idschool", null).toString(),
                sharedPreferences.getString("token", null).toString(),
                sharedPreferences.getString("name", null).toString(),
                sharedPreferences.getString("role", null).toString()
            )
        }


    fun clear() {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        private val SHARED_PREF_NAME = "my_shared_preff"
        private var mInstance: SharedPrefManager? = null
        @Synchronized
        fun getInstance(mCtx: Context): SharedPrefManager {
            if (mInstance == null) {
                mInstance = SharedPrefManager(mCtx)
            }
            return mInstance as SharedPrefManager
        }
    }

}