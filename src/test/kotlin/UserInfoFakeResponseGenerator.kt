import com.google.gson.Gson
import network.response.UserInfo
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.SocketPolicy

object UserInfoFakeResponseGenerator {

    const val SUCCESS_AGE = 18
    const val SUCCESS_NAME = "FakeName"
    const val SUCCESS_DESC = "Fake desc"

    fun genSuccessCase(): MockResponse {
        val response = MockResponse()
        response.setResponseCode(200)

        val fakeUser = UserInfo(SUCCESS_AGE, SUCCESS_DESC, SUCCESS_NAME)

        response.setBody(Gson().toJson(fakeUser))
        return response
    }

    fun genAuthFailCase(): MockResponse {
        val response = MockResponse()
        response.setResponseCode(401)
        return response
    }

    fun serverErrorCase(): MockResponse {
        val response = MockResponse()
        response.setResponseCode(500)
        return response
    }

    fun timeoutCase(): MockResponse {
        val response = MockResponse()
        response.socketPolicy = SocketPolicy.NO_RESPONSE
        return response
    }

}