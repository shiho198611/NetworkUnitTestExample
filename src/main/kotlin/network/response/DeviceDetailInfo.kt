package network.response


import com.google.gson.annotations.SerializedName

data class DeviceDetailInfo(
        @SerializedName("deviceDesc")
        val deviceDesc: String,
        @SerializedName("deviceName")
        val deviceName: String,
        @SerializedName("serial")
        val serial: String
)