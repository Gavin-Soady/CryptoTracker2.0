package org.wit.cryptoTracker.ui.crypto

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import org.wit.cryptoTracker.R
import org.wit.cryptoTracker.databinding.FragmentCryptoBinding
import org.wit.cryptoTracker.main.MainApp
import org.wit.cryptoTracker.models.CryptoModel



class CryptoFragment : Fragment() {

    lateinit var app: MainApp
    private var _fragBinding: FragmentCryptoBinding? = null
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private val fragBinding get() = _fragBinding!!
    private lateinit var cryptoViewModel: CryptoViewModel


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
        activity?.title = getString(R.string.add_crypto)
        setupMenu()

        cryptoViewModel = ViewModelProvider(this).get(CryptoViewModel::class.java)
        cryptoViewModel.observableStatus.observe(viewLifecycleOwner, Observer {
                status -> status?.let { render(status) }
        })
        setButtonListener(fragBinding)
        //setImageButtonListener(fragBinding)

        return root;
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_crypto, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Validate and handle the selected menu item
                return NavigationUI.onNavDestinationSelected(menuItem,
                    requireView().findNavController())
            }       }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                    //Uncomment this if you want to immediately return to Report
                    //findNavController().popBackStack()
                }
            }
            false -> Toast.makeText(context,getString(R.string.cryptoError),Toast.LENGTH_LONG).show()
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            CryptoFragment().apply {
                arguments = Bundle().apply {}
            }
    }


    override fun onResume() {
        super.onDestroyView()
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

    }

   /* fun setImageButtonListener(layout: FragmentCryptoBinding) {
        layout.chooseImage.setOnClickListener {

            cryptoViewModel.cacheCrypto(
                    fragBinding.cryptoTitle.text.toString(),
                    fragBinding.amount.text.toString().toDouble(),
                    fragBinding.value.text.toString().toDouble(),
                )
            cryptoViewModel.doSelectImage()
            }


    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

   /* override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_crypto, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }*/
}