package com.enbd.learning.api

import com.example.ccctest.api.respone.gallery.GetImagesResponse
import io.reactivex.Observable
import retrofit2.http.*

interface ENAPIServices {


    @GET(ENAPIConstants.GET_IMAGES)
    @Headers("Content-Type: application/json;charset=UTF-8")
    fun getImages(): Observable<GetImagesResponse>
}