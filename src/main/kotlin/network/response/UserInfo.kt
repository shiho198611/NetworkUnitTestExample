package network.response


import com.google.gson.annotations.SerializedName

data class UserInfo(
        @SerializedName("age")
        val age: Int,
        @SerializedName("desc")
        val desc: String,
        @SerializedName("name")
        val name: String
)