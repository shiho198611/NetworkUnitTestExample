package network.request


import com.google.gson.annotations.SerializedName

data class DeviceDetailInfoReq(
        @SerializedName("deviceId")
        val deviceId: String
)