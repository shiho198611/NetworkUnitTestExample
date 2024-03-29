import com.google.gson.Gson
import network.response.DeviceDetailInfo
import okhttp3.MediaType
import okhttp3.ResponseBody
import okhttp3.mockwebserver.MockResponse
import org.mockito.Mockito
import retrofit2.HttpException
import retrofit2.Response

object DeviceDetailFakeResponseGenerator {

    fun genSuccessCase(): MockResponse {
        val successRes = MockResponse()
        successRes.setResponseCode(200)

        val fakeDetail = getSuccessResponseModel()
        successRes.setBody(Gson().toJson(fakeDetail))

        return successRes
    }

    fun genServerErrorCase(): MockResponse {
        val failRes = MockResponse()
        failRes.setResponseCode(500)

        return failRes
    }

    fun getSuccessResponseModel(): DeviceDetailInfo {
        return DeviceDetailInfo("fake desc",
                "fake device name",
                "fake serial")
    }

}