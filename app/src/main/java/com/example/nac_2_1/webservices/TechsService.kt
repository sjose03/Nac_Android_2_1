package com.example.nac_2_1.webservices

import com.example.nac_2_1.models.Techs
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface TechsService {
    @GET("techs/{id}")
    fun getTech(@Path("id") id: Int) : Call<Techs>
}
class TechsConnection {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://ec2-18-222-183-71.us-east-2.compute.amazonaws.com:8081/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val techsService = retrofit.create(TechsService::class.java)
}
