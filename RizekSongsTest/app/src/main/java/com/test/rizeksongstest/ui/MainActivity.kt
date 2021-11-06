package com.test.rizeksongstest.ui

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.test.rizeksongstest.R
import com.test.rizeksongstest.helpers.DataRequestResource
import com.test.rizeksongstest.ui.fragments.SongsFragment
import com.test.rizeksongstest.viewmodels.MainViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import kotlin.system.exitProcess

class MainActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var mainViewModel: MainViewModel
    private val mTimeInterval = 2000
    private var mExitPressed: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this, viewModelFactory)
            .get(MainViewModel::class.java)
        setContentView(R.layout.activity_main)

        mainViewModel.getTokenObserver().observe(this, {
            when(it) {
                is DataRequestResource.Loading -> {

                }
                is DataRequestResource.Error -> {
                    val alertBuilder = android.app.AlertDialog.Builder(this)
                    alertBuilder
                        .setMessage(getString(R.string.mainActivity_error_message))
                        .setPositiveButton(getString(R.string.mainActivity_ok)) { _, _ ->
                            finishAffinity()
                            exitProcess(0)
                        }
                        .show()
                }
                is DataRequestResource.Success -> {
                    if (savedInstanceState == null) {
                        val transaction = supportFragmentManager.beginTransaction()
                        transaction.add(fcv_main_mainActivity.id, SongsFragment())
                        transaction.commit()
                    }
                }
            }
        })
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            if (mExitPressed + mTimeInterval > System.currentTimeMillis()) {
                finishAffinity()
                exitProcess(0)
            } else {
                Toast.makeText(
                    baseContext,
                    getString(R.string.mainActivity_press_again_to_exit),
                    Toast.LENGTH_SHORT
                ).show()
                mExitPressed = System.currentTimeMillis()
            }
        } else {
            super.onBackPressed()
        }
    }

    fun openView(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(fcv_main_mainActivity.id, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun closeKeyboard() {
        this.currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}