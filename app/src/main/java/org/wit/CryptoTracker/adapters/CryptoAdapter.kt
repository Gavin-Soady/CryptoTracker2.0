package org.wit.CryptoTracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
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
            binding.value.text = "Value: "+ crypto.value
            binding.amount.text = "Amount: "+ crypto.amount
            binding.total.text = "Total: €" + crypto.total

            Picasso.get().load(crypto.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onCryptoClick(crypto) }
        }
    }
}
