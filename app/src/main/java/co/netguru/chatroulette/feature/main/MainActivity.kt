package co.netguru.chatroulette.feature.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import co.netguru.chatroulette.R
import co.netguru.chatroulette.app.App
import co.netguru.chatroulette.feature.base.BaseMvpActivity
import co.netguru.chatroulette.feature.main.video.VideoFragment
import kotlinx.android.synthetic.main.activity_main.*


fun Context.startMainActivity() {
    val intent = Intent(this, MainActivity::class.java)
    this.startActivity(intent)
}

class MainActivity : BaseMvpActivity<MainView, MainPresenter>(), MainView {

    val videoFragment = VideoFragment.newInstance()

    override fun retrievePresenter() = App.getApplicationComponent(this).getMainComponent().mainPresenter()

    override fun getLayoutId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        thisDeviceUuidText.text = App.DEVICE_UUID
        connectButton.setOnClickListener { connectToDevice() }
        disconnectButton.setOnClickListener { disconnectDevice() }
        getReplaceFragmentTransaction(R.id.fragmentContainer, videoFragment, VideoFragment.TAG)
                .commit()
    }

    private fun connectToDevice() {
        getPresenter().startSearching()
    }

    private fun disconnectDevice() {
        getPresenter().stop()
    }

    override fun passOfferDevice(deviceUuid: String) {
        videoFragment.offerDevice(deviceUuid)
    }
}