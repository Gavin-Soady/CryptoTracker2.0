package org.wit.cryptoTracker.views.crypto

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.cryptoTracker.R
import org.wit.cryptoTracker.databinding.ActivityCryptoBinding
import org.wit.cryptoTracker.models.CryptoModel
import timber.log.Timber.Forest.i


class CryptoView : AppCompatActivity() {

    private lateinit var binding: ActivityCryptoBinding
    private lateinit var presenter: CryptoPresenter
    var crypto = CryptoModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCryptoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        presenter = CryptoPresenter(this)

        i("Crypto Activity started...")

        binding.chooseImage.setOnClickListener {
            presenter.cacheCrypto(
                binding.cryptoTitle.text.toString(),
                binding.amount.text.toString().toDouble(),
               binding.value.text.toString().toDouble(),
            )
            presenter.doSelectImage()
        }


        binding.cryptoLocation.setOnClickListener {
            presenter.cacheCrypto(
                binding.cryptoTitle.text.toString(),
                binding.amount.text.toString().toDouble(),
               binding.value.text.toString().toDouble()
            )
            presenter.doSetLocation()
        }

        binding.btnAdd.setOnClickListener {
            if (binding.cryptoTitle.text.toString().isEmpty()) {
                Snackbar.make(binding.root, R.string.enter_crypto_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                var title = binding.cryptoTitle.text.toString()
                var value = binding.value.text.toString().toDouble()
                var amount = binding.amount.text.toString().toDouble()
                presenter.doAddOrSave(title,value, amount)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_crypto, menu)
        val deleteMenu: MenuItem = menu.findItem(R.id.item_delete)
        deleteMenu.isVisible = presenter.edit
        return super.onCreateOptionsMenu(menu)
    }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.item_delete -> {
                    presenter.doDelete()
                }

                R.id.item_cancel -> {
                    presenter.doCancel()
                }
            }
            return super.onOptionsItemSelected(item)
        }
        fun updateImage(image: Uri) {
            i("Image updated")
            Picasso.get()
                .load(image)
                .into(binding.cryptoImage)
            binding.chooseImage.setText(R.string.change_crypto_image)
        }

        fun showCrypto(crypto: CryptoModel) {
            binding.cryptoTitle.setText(crypto.title)
            binding.amount.setText(crypto.amount.toString())
            binding.value.setText(crypto.value.toString())
            binding.btnAdd.setText(R.string.save_crypto)
            Picasso.get()
                .load(crypto.image)
                .into(binding.cryptoImage)
            if (crypto.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_crypto_image)
            }

        }
    }




