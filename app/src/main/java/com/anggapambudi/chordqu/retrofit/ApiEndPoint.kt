package com.anggapambudi.chordqu.retrofit

import com.anggapambudi.chordqu.model.ChordResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEndPoint {

    @GET("chord")
    fun getChord(
        @Query("q") stringJudulLagu: String,
    ): Call<ChordResponse>

}