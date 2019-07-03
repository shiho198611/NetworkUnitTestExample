import network.FakeApiService
import network.devicedetail.FakeDeviceDetailPresenter
import network.devicedetail.FakeDeviceDetailUpdateListener
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class DeviceDetailInfoVerifyTest {

    @get:Rule
    var mockServer: MockWebServer = MockWebServer()

    @get:Rule
    var rxJavaTestSchedulerRule = RxJavaTestSchedulerRule()

    lateinit var detailUpdateListener: FakeDeviceDetailUpdateListener
    lateinit var fakePresenter: FakeDeviceDetailPresenter

    @Before
    fun intiTest() {
        FakeApiService.serviceUrl = "http://${mockServer.hostName}:${mockServer.port}/"

        detailUpdateListener = Mockito.mock(FakeDeviceDetailUpdateListener::class.java)
        fakePresenter = FakeDeviceDetailPresenter(detailUpdateListener)
    }

    @Test
    fun successDeviceDetail() {
        mockServer.enqueue(DeviceDetailFakeResponseGenerator.genSuccessCase())

        fakePresenter.getDeviceDetail("fake_device_id")

        Mockito.verify(detailUpdateListener)
                .updateDeviceDetail(DeviceDetailFakeResponseGenerator.getSuccessResponseModel())

        mockServer.shutdown()
    }

}