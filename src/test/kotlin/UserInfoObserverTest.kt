import network.FakeApiService
import network.WebServiceApiHelper
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import java.net.SocketTimeoutException

class UserInfoObserverTest {

    @get:Rule
    var mockServer: MockWebServer = MockWebServer()

    @get:Rule
    var rxJavaTestSchedulerRule = RxJavaTestSchedulerRule()

    lateinit var apiHelper: WebServiceApiHelper

    @Before
    fun initTest() {
        FakeApiService.serviceUrl = "http://${mockServer.hostName}:${mockServer.port}/"
        apiHelper = WebServiceApiHelper()
    }

    @Test
    fun successUserInfoTest() {
        mockServer.enqueue(UserInfoFakeResponseGenerator.genSuccessCase())

        val result = apiHelper.apiService
                .getUserInfo("fake_user_id")
                .test()
        result.awaitTerminalEvent()

        result.assertValue {
            it.age == UserInfoFakeResponseGenerator.SUCCESS_AGE &&
                    it.name == UserInfoFakeResponseGenerator.SUCCESS_NAME &&
                    it.desc == UserInfoFakeResponseGenerator.SUCCESS_DESC
        }

        mockServer.shutdown()
    }

    @Test
    fun authFailTest() {
        mockServer.enqueue(UserInfoFakeResponseGenerator.genAuthFailCase())

        val result = apiHelper.apiService
                .getUserInfo("fake_user_id")
                .test()

        result.awaitTerminalEvent()
        result.assertError {
            it is HttpException && it.code() == 401
        }

        mockServer.shutdown()
    }

    @Test
    fun unknownServerErrorTest() {

        mockServer.enqueue(UserInfoFakeResponseGenerator.serverErrorCase())

        val result = apiHelper.apiService
                .getUserInfo("fake_user_id")
                .test()

        result.awaitTerminalEvent()
        result.assertError {
            it is HttpException && it.code() == 500
        }

        mockServer.shutdown()
    }

    @Test
    fun requestTimeoutTest() {
        mockServer.enqueue(UserInfoFakeResponseGenerator.timeoutCase())

        val result = apiHelper.apiService
                .getUserInfo("fake_user_id")
                .test()

        result.awaitTerminalEvent()
        result.assertError {
            it is SocketTimeoutException
        }

        mockServer.shutdown()
    }

}