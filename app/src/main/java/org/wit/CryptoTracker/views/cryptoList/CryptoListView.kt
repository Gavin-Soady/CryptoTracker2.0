package org.wit.CryptoTracker.views.cryptoList

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.CryptoTracker.R
import org.wit.CryptoTracker.databinding.ActivityCryptoListBinding
import org.wit.CryptoTracker.main.MainApp
import org.wit.CryptoTracker.models.CryptoModel
import org.wit.CryptoTracker.views.cryptoList.CryptoAdapter


class CryptoListView : AppCompatActivity(), CryptoListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityCryptoListBinding
    lateinit var presenter: CryptoListPresenter
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCryptoListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        presenter = CryptoListPresenter(this)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadCryptos()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> { presenter.doAddCrypto() }
            R.id.item_map -> { presenter.doShowCryptosMap() }
        }
        return super.onOptionsItemSelected(item)
    }

   override fun onCryptoClick(crypto: CryptoModel, position: Int) {
        this.position = position
        presenter.doEditCrypto(crypto, this.position)
    }

    private fun loadCryptos() {
        binding.recyclerView.adapter = CryptoAdapter(presenter.getCryptos(), this)
        onRefresh()
    }

    fun onRefresh() {
        binding.recyclerView.adapter?.
        notifyItemRangeChanged(0,presenter.getCryptos().size)
    }

    fun onDelete(position : Int) {
        binding.recyclerView.adapter?.notifyItemRemoved(position)
    }

}