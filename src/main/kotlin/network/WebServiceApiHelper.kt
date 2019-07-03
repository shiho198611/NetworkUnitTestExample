package network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WebServiceApiHelper {

    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(FakeApiService.serviceUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .build()
    val apiService: FakeApiService

    init {
        apiService = retrofit.create(FakeApiService::class.java)
    }

}