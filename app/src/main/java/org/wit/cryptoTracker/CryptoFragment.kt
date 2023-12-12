package org.wit.cryptoTracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.wit.cryptoTracker.databinding.FragmentCryptoBinding
import org.wit.cryptoTracker.main.MainApp

class CryptoFragment : Fragment() {

    lateinit var app: MainApp
    private var _fragBinding: FragmentCryptoBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_crypto, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CryptoFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}