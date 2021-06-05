package com.example.ronelo.retrofit
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface Api {
    @Multipart
    @POST("predict")

    fun getDataPost(
        @Part file: MultipartBody.Part,
        @Part("composition") composition: String,
        @Part("contraindication") contraindication: String,
        @Part("description") description: String,
        @Part("dosage") dosage: String,
        @Part("how_to_use") how_to_use: String,
        @Part("name") name: String,
        @Part("side_effects") side_effects: String,
        @Part("warning") warning: String

    ): Call<CreatePostResponse>

}