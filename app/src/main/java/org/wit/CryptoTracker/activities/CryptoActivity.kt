package org.wit.CryptoTracker.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.CryptoTracker.R
import org.wit.CryptoTracker.databinding.ActivityCryptoBinding
import org.wit.CryptoTracker.helpers.showImagePicker
import org.wit.CryptoTracker.main.MainApp
import org.wit.CryptoTracker.models.CryptoModel
import timber.log.Timber.Forest.i
import java.nio.file.Files.delete

class CryptoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCryptoBinding
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>

    var crypto = CryptoModel()
    //val cryptos = ArrayList<CryptoModel>()
    lateinit var app: MainApp
    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //var edit = false

        binding = ActivityCryptoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        i("Crypto Activity started...")

        if (intent.hasExtra("crypto_edit")) {
            edit = true
            crypto = intent.extras?.getParcelable("crypto_edit")!!
            binding.cryptoTitle.setText(crypto.title)
            binding.amount.setText(crypto.amount.toString())
            //binding.amount.setText(crypto.amount)
            binding.value.setText(crypto.value.toString())
            //binding.value.setText(crypto.value)
            binding.btnAdd.setText(R.string.save_crypto)
            binding.chooseImage.setText(R.string.change_image)
        }

        binding.btnAdd.setOnClickListener() {
            crypto.title = binding.cryptoTitle.text.toString()

           // crypto.amount = binding.amount.text.toString()
           // crypto.value= binding.value.text.toString()
           // var value = binding.value.text.toString().toLong()
           // var amount = binding.amount.text.toString().toLong()
           // crypto.total = (value * amount).toString()

            crypto.amount = binding.amount.text.toString().toLong()
            crypto.value= binding.value.text.toString().toLong()
            var amountC = binding.amount.text.toString().toLong()
            var valueC = binding.value.text.toString().toLong()
            var totalC = amountC * valueC
            crypto.total = totalC

            if (crypto.title.isEmpty()) {
                Snackbar.make(it,R.string.enter_crypto_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.cryptos.update(crypto.copy())
                } else {
                    app.cryptos.create(crypto.copy())
                }
            }
            i("add Button Pressed: $crypto")
            setResult(RESULT_OK)
            finish()
        }

        binding.chooseImage.setOnClickListener {
            i("Select image")
            showImagePicker(imageIntentLauncher,this)
        }

        registerImagePickerCallback()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_crypto, menu)
        if (edit) menu.getItem(1).isVisible = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }

        when (item.itemId) {
            R.id.item_delete -> {
                app.cryptos.delete(crypto)
                finish()
           }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")

                            val image = result.data!!.data!!
                            contentResolver.takePersistableUriPermission(image,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            crypto.image = image

                            Picasso.get()
                                .load(crypto.image)
                                .into(binding.cryptoImage)
                            binding.chooseImage.setText(R.string.change_crypto_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}


