package org.wit.cryptoTracker.views.cryptoList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.cryptoTracker.databinding.CardCryptoBinding
import org.wit.cryptoTracker.models.CryptoModel
import java.text.DecimalFormat

interface CryptoListener {
    fun onCryptoClick(crypto: CryptoModel, adapterPosition: Int)
}

class CryptoAdapter constructor(private var cryptos: List<CryptoModel>,
                                   private val listener: CryptoListener
) :
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
           // binding.value.text = "Value: "+ crypto.value
            val df = DecimalFormat("#,###.00")
            val amount = df.format(crypto.amount)
            val total = df.format(crypto.total)
            val fAmount = "Amount: $amount"
            val fTotal = "Total: €$total"
            binding.amount.text = fAmount
            binding.total.text = fTotal

            Picasso.get().load(crypto.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onCryptoClick(crypto,adapterPosition) }
        }
    }
}
