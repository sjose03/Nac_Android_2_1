package com.example.nac_2_1.models


import com.google.gson.annotations.SerializedName

data class Techs (
    val tech: String,
    val descricao: String,
    @SerializedName("ulr_imag") val ulr_imag: String
        )