package com.example.checkinternetconnectivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.meetfriend.app.connectivity.base.ConnectivityProvider

class MainActivity : AppCompatActivity(), ConnectivityProvider.ConnectivityStateListener {
    val provider: ConnectivityProvider by lazy { ConnectivityProvider.createProvider(this@MainActivity) }
    private var snackbar: Snackbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val view = findViewById<View>(android.R.id.content)
        if (view != null) {
            snackbar = Snackbar
                    .make(view, "No internet connection", Snackbar.LENGTH_INDEFINITE)
        }
    }
    override fun onStart() {
        super.onStart()
        provider.addListener(this)
    }

    override fun onStop() {
        super.onStop()
        provider.removeListener(this)
    }

    override fun onStateChange(state: ConnectivityProvider.NetworkState) {
        val hasInternet = state.hasInternet()
        if (snackbar != null) {
            if (hasInternet) {
                snackbar!!.dismiss()
            } else {
                snackbar!!.show()
            }
        }
    }

    companion object {
        fun ConnectivityProvider.NetworkState.hasInternet(): Boolean {
            return (this as? ConnectivityProvider.NetworkState.ConnectedState)?.hasInternet == true
        }
    }
}