package org.wit.CryptoTracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.CryptoTracker.databinding.CardCryptoBinding
import org.wit.CryptoTracker.models.CryptoModel

interface CryptoListener {
    fun onCryptoClick(crypto: CryptoModel)
}

class CryptoAdapter constructor(private var cryptos: List<CryptoModel>,
                                   private val listener: CryptoListener) :
        RecyclerView.Adapter<CryptoAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardCryptoBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val crypto = cryptos[holder.adapterPosition]
        holder.bind(crypto, listener)
    }

    override fun getItemCount(): Int = cryptos.size

    class MainHolder(private val binding : CardCryptoBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(crypto: CryptoModel, listener: CryptoListener) {
            binding.cryptoTitle.text = crypto.title
            binding.description.text = crypto.description
            binding.root.setOnClickListener { listener.onCryptoClick(crypto) }
        }
    }
}
