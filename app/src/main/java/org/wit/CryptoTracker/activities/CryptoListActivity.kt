package org.wit.CryptoTracker.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.CryptoTracker.R
import org.wit.CryptoTracker.adapters.CryptoAdapter
import org.wit.CryptoTracker.adapters.CryptoListener
import org.wit.CryptoTracker.databinding.ActivityCryptoListBinding
import org.wit.CryptoTracker.main.MainApp
import org.wit.CryptoTracker.models.CryptoModel
import java.text.NumberFormat

class CryptoListActivity : AppCompatActivity(), CryptoListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityCryptoListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCryptoListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        app = application as MainApp
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        //binding.recyclerView.adapter = CryptoAdapter(app.cryptos.findAll(),this)
        loadCryptos()
        registerRefreshCallback()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, CryptoActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.cryptos.findAll().size)
            }
        }

    override fun onCryptoClick(crypto: CryptoModel) {
        val launcherIntent = Intent(this, CryptoActivity::class.java)
        launcherIntent.putExtra("crypto_edit", crypto)
        //getClickResult.launch(launcherIntent)
        refreshIntentLauncher.launch(launcherIntent)
    }

    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.cryptos.findAll().size)
            }
        }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadCryptos()

            }
    }

    private fun loadCryptos() {
        showCryptos(app.cryptos.findAll())
    }

    fun showCryptos (cryptos: List<CryptoModel>) {
        binding.recyclerView.adapter = CryptoAdapter(cryptos, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()

    }

}