package com.surelabsid.lti.dacofa.network

import com.surelabsid.lti.dacofa.response.ResponseIkan
import com.surelabsid.lti.dacofa.response.ResponseKabupaten
import com.surelabsid.lti.dacofa.response.ResponseNegara
import com.surelabsid.lti.dacofa.response.ResponseProvinsi
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("Api_provinsi/getAllProvinsi")
    fun getListProvinsi(@Query("country_code") countryCode: String?): retrofit2.Call<ResponseProvinsi>

    @GET("Api_kabupaten/getKabupaten")
    fun getListKabupaten(@Query("id_prov") idProvinsi: String): retrofit2.Call<ResponseKabupaten>

    @GET("Api_negara/getListNegara")
    fun getListNegara(): retrofit2.Call<ResponseNegara>

    @GET("Api_ikan/getListIkan")
    fun getListIkan(): retrofit2.Call<ResponseIkan>

}
