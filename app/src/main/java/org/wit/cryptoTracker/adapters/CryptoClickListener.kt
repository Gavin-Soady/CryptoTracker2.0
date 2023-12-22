package org.wit.cryptoTracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.cryptoTracker.R
import org.wit.cryptoTracker.databinding.CardCryptoBinding
import org.wit.cryptoTracker.models.CryptoModel


interface CryptoClickListener {
    fun onCryptoClick(crypto: CryptoModel)
}

class CryptoSAdapter constructor(private var cryptos: List<CryptoModel>,
                                private val listener: CryptoClickListener)
    : RecyclerView.Adapter<CryptoSAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardCryptoBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val crypto = cryptos[holder.adapterPosition]
        holder.bind(crypto,listener)
    }

    override fun getItemCount(): Int = cryptos.size

    inner class MainHolder(val binding : CardCryptoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(crypto: CryptoModel, listener: CryptoClickListener) {
            binding.crypto = crypto
            binding.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
            binding.root.setOnClickListener { listener.onCryptoClick(crypto) }
            binding.executePendingBindings()
        }
    }
}