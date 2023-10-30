package org.wit.CryptoTracker.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import org.wit.CryptoTracker.R
import org.wit.CryptoTracker.databinding.ActivityCryptoBinding
import org.wit.CryptoTracker.main.MainApp
import org.wit.CryptoTracker.models.CryptoModel
import timber.log.Timber.Forest.i

class CryptoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCryptoBinding
    var crypto = CryptoModel()
    //val cryptos = ArrayList<CryptoModel>()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var edit = false

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
            binding.description.setText(crypto.description)
            binding.btnAdd.setText(R.string.save_crypto)
        }

        binding.btnAdd.setOnClickListener() {
            crypto.title = binding.cryptoTitle.text.toString()
            crypto.description = binding.description.text.toString()
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
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_crypto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}