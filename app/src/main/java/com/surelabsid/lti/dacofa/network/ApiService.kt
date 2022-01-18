package com.surelabsid.lti.dacofa.network

import com.surelabsid.lti.dacofa.model.SyncModel
import com.surelabsid.lti.dacofa.model.User
import com.surelabsid.lti.dacofa.response.*
import retrofit2.http.*

interface ApiService {

    @GET("Api_provinsi/getAllProvinsi")
    fun getListProvinsi(@Query("country_code") countryCode: String?): retrofit2.Call<ResponseProvinsi>

    @GET("Api_kabupaten/getKabupaten")
    fun getListKabupaten(@Query("id_prov") idProvinsi: String): retrofit2.Call<ResponseKabupaten>

    @GET("Api_negara/getListNegara")
    fun getListNegara(): retrofit2.Call<ResponseNegara>

    @POST("Api_user/login")
    suspend fun getCredential(@Body user: User): ResponseUser


    @POST("Api_fishery/retrieveData")
    fun syncData(@Body syncModel: SyncModel): retrofit2.Call<GeneralResponse>

    @GET("Api_fishery/getData")
    fun getDataFishery(@Query("userid") userid: String): retrofit2.Call<ResponseFishery>


    @GET("Api_provinsi/getProvinsi")
    suspend fun getAllProvinsi(): ResponseProvinsi

    @GET("Api_kabupaten/getAllKabupaten")
    suspend fun getAllKabupaten(): ResponseKabupaten

    @GET("Api_ikan/getListIkan")
    suspend fun getListIkan(): ResponseIkan

    @GET("Api_fishinggear/getFishingGear")
    suspend fun getFishingGear(): ResponseFishingGear

    @GET("Api_negara/getListNegara")
    suspend fun getAllCountries(): ResponseNegara


    @GET("Api_fishery/getHasilTangkapan")
    suspend fun getHasilTangkapan(@Query("header") header: String?): ResponseDetailTangakapan

    @FormUrlEncoded
    @POST("Api_user/reset_password")
    suspend fun resetPassword(@Field("email") email: String?) : GeneralResponse

}
