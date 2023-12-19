package org.wit.cryptoTracker.views.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import org.wit.cryptoTracker.R
import org.wit.cryptoTracker.databinding.FragmentCryptoBinding
import org.wit.cryptoTracker.main.MainApp
import org.wit.cryptoTracker.models.CryptoModel



class CryptoFragment : Fragment() {

    lateinit var app: MainApp
    private var _fragBinding: FragmentCryptoBinding? = null
    //private lateinit var cryptoModel: CryptoModel
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private val fragBinding get() = _fragBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = FragmentCryptoBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_donate)

        setButtonListener(fragBinding)

        return root;
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            CryptoFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
    override fun onResume() {
        super.onResume()
        fragBinding.cryptoTitle.text = null
        fragBinding.amount.text = null
        fragBinding.value.text = null
        //totalDonated = app.donationsStore.findAll().sumOf { it.amount }
       // fragBinding.progressBar.progress = totalDonated
        //fragBinding.totalSoFar.text = getString(R.string.totalSoFar,totalDonated)
    }

    fun setButtonListener(layout: FragmentCryptoBinding) {
        layout.btnAdd.setOnClickListener {
            val amount = if (layout.amount.text.isNotEmpty())
                layout.amount.text.toString().toDouble()
            else
                1

            val title = if (layout.cryptoTitle.text.isNotEmpty())
                layout.cryptoTitle.text.toString()
            else
                "??????"

            val value = if (layout.value.text.isNotEmpty())
                layout.value.text.toString().toDouble()
            else
                1.0
            val total = amount.toDouble() * value
            app.cryptos.create(
                CryptoModel(
                    title = title,
                    amount = amount.toDouble(),
                    value = value,
                    total = total
                )
            )
            // presenter.doAddOrSave(
            // title = title,
            // amount = amount.toDouble(),
            //amount1 = amount.toDouble(),
            // value = value)

            //val intent = Intent(context, ReportViewModel::class.java)
            //startActivity(intent)
        }

        layout.chooseImage.setOnClickListener {


        }
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_crypto, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }
}