package network

import io.reactivex.Observable
import network.response.DeviceDetailInfo
import network.request.DeviceDetailInfoReq
import network.response.UserInfo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FakeApiService {

    companion object {
        var serviceUrl: String = ""
    }

    @GET("fake/service/userinfo/{userid}")
    fun getUserInfo(@Path("userid") userId: String): Observable<UserInfo>

    @POST("fake/service/detail/device")
    fun detailDeviceInfo(@Body req: DeviceDetailInfoReq): Observable<DeviceDetailInfo>

}