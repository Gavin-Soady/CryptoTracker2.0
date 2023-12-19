package org.wit.cryptoTracker.views.crypto

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.location.Location
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.github.ajalt.timberkt.Timber
import org.wit.cryptoTracker.databinding.ActivityCryptoBinding
import org.wit.cryptoTracker.views.editLocation.EditLocationView
import org.wit.cryptoTracker.helpers.showImagePicker
import org.wit.cryptoTracker.main.MainApp
import org.wit.cryptoTracker.models.CryptoModel
import org.wit.cryptoTracker.models.LocationModel


class CryptoPresenter(private val view: CryptoView) {

    var crypto = CryptoModel()
    var app: MainApp = view.application as MainApp
    var binding: ActivityCryptoBinding = ActivityCryptoBinding.inflate(view.layoutInflater)
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>

    var edit = false;

    init {
        if (view.intent.hasExtra("crypto_edit")) {
            edit = true
            crypto = view.intent.extras?.getParcelable("crypto_edit")!!
            view.showCrypto(crypto)
        }
        registerImagePickerCallback()
        registerMapCallback()
    }
    fun doAddOrSave(amount1: Any, title: String, value: Double, amount: Double) {
        crypto.title = title
        crypto.value = value
        crypto.amount = amount
        crypto.total = value * amount

        if (edit) {
            app.cryptos.update(crypto)
        } else {
            app.cryptos.create(crypto)
        }
        view.setResult(RESULT_OK)
        view.finish()
    }
    fun doCancel() {
        view.finish()
    }
    fun doDelete() {
        view.setResult(99)
        app.cryptos.delete(crypto)
        view.finish()
    }
    fun doSelectImage() {
        showImagePicker(imageIntentLauncher,view)
    }

    fun doSetLocation() {
        val location = LocationModel(88.3,-12.5, 14f)
        if (crypto.zoom != 0f) {
            location.lat =  crypto.lat
            location.lng = crypto.lng
            location.zoom = crypto.zoom
        }
        val launcherIntent = Intent(view, EditLocationView::class.java)
            .putExtra("location", location)
        mapIntentLauncher.launch(launcherIntent)
    }

    fun cacheCrypto (title: String, value: Double, amount: Double) {
        crypto.title = title
        crypto.value = value
        crypto.amount = amount
    }
    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            crypto.image = result.data!!.data!!
                            view.contentResolver.takePersistableUriPermission(crypto.image,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            view.updateImage(crypto.image)
                        } // end of if  
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }            }    }



private fun registerMapCallback() {
    mapIntentLauncher =
        view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->
            when (result.resultCode) {
                AppCompatActivity.RESULT_OK -> {
                    if (result.data != null) {
                        Timber.i("Got Location ${result.data.toString()}")
                        val location = result.data!!.extras?.getParcelable<Location>("location")!!
                        Timber.i("Location == $location")
                        crypto.lat = 45.234
                        crypto.lng = -6.56
                        crypto.zoom = 14f
                    } // end of if  
                }
                AppCompatActivity.RESULT_CANCELED -> { } else -> { }
            }            }    }

}

fun Timber.i(t: String) {

}
