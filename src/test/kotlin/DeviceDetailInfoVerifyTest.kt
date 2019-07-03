import io.reactivex.Observer
import network.FakeApiService
import network.WebServiceApiHelper
import network.request.DeviceDetailInfoReq
import network.response.DeviceDetailInfo
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import retrofit2.HttpException

class DeviceDetailInfoVerifyTest {

    @get:Rule
    var mockServer: MockWebServer = MockWebServer()

    @get:Rule
    var rxJavaTestSchedulerRule = RxJavaTestSchedulerRule()

    lateinit var resultObserver: Observer<DeviceDetailInfo>
    lateinit var serviceApiHelper: WebServiceApiHelper

    @Before
    fun intiTest() {
        FakeApiService.serviceUrl = "http://${mockServer.hostName}:${mockServer.port}/"

        serviceApiHelper = WebServiceApiHelper()
        resultObserver = Mockito.mock(Observer::class.java) as Observer<DeviceDetailInfo>
    }

    @Test
    fun successDeviceDetailTest() {
        mockServer.enqueue(DeviceDetailFakeResponseGenerator.genSuccessCase())

        serviceApiHelper.apiService
                .detailDeviceInfo(DeviceDetailInfoReq(("fake_device_id")))
                .subscribe(resultObserver)

        Thread.sleep(100)

        Mockito.verify(resultObserver).onNext(DeviceDetailFakeResponseGenerator.getSuccessResponseModel())

        mockServer.shutdown()
    }

    @Test
    fun serverErrTest() {
        mockServer.enqueue(DeviceDetailFakeResponseGenerator.genServerErrorCase())

        serviceApiHelper.apiService
                .detailDeviceInfo(DeviceDetailInfoReq(("fake_device_id")))
                .subscribe(resultObserver)

        Thread.sleep(100)

        Mockito.verify(resultObserver).onError(Mockito.argThat {
            it is HttpException && it.code() == 500
        })

        mockServer.shutdown()
    }

}