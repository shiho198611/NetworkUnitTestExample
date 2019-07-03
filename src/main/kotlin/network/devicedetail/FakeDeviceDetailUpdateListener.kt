package network.devicedetail

import network.response.DeviceDetailInfo

interface FakeDeviceDetailUpdateListener {
    fun updateDeviceDetail(detailInfo: DeviceDetailInfo)
    fun failDeviceDetailRequest(throwable: Throwable)
}