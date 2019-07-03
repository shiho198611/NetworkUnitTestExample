package network.devicedetail

import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import network.WebServiceApiHelper
import network.request.DeviceDetailInfoReq
import network.response.DeviceDetailInfo

/**
 * For testing Mockito verify.
 * */
class FakeDeviceDetailPresenter {

    private val updateListener: FakeDeviceDetailUpdateListener
    private val serviceApiHelper: WebServiceApiHelper = WebServiceApiHelper()

    constructor(updateListener: FakeDeviceDetailUpdateListener) {
        this.updateListener = updateListener
    }

    fun getDeviceDetail(deviceId: String) {
        val detailReq = DeviceDetailInfoReq(deviceId)
        serviceApiHelper.apiService
                .detailDeviceInfo(detailReq)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(object : Observer<DeviceDetailInfo> {
                    override fun onComplete() {}

                    override fun onSubscribe(d: Disposable) {}

                    override fun onNext(detailInfo: DeviceDetailInfo) {
                        updateListener.updateDeviceDetail(detailInfo)
                    }

                    override fun onError(e: Throwable) {
                        updateListener.failDeviceDetailRequest(e)
                    }
                })
    }

}