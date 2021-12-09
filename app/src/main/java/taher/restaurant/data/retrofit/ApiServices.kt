package com.devartlab.data.retrofit

import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {


    @GET("category/get")
    fun getCategory(): Call<Data>

    @POST("category/insert")
    @FormUrlEncoded
    fun saveCategory(@FieldMap pStringMap: MutableMap<String, String>): Call<ResponseBody>


    @POST("order/insert")
    fun saveOrder(@Body objects: JsonObject): Call<ResponseBody>


    @GET("items/get")
    fun getItem(): Call<Data>

    @GET("items/syncItems")
    fun syncItems(): Call<Data>

    @POST("items/insert")
    @FormUrlEncoded
    fun saveItem(@FieldMap pStringMap: MutableMap<String, String>): Call<ResponseBody>

    @POST("extra/insert")
    @FormUrlEncoded
    fun saveExtra(@FieldMap pStringMap: MutableMap<String, String>): Call<ResponseBody>


    companion object {

        const val BaseURL = "https://mysterious-retreat-05032.herokuapp.com/api/"
    }
}