package org.wit.cryptoTracker.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.wit.cryptoTracker.R

class CryptoDetailFragment : Fragment() {

    companion object {
        fun newInstance() = CryptoDetailFragment()
    }

    private lateinit var viewModel: CryptoDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_crypto_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CryptoDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}