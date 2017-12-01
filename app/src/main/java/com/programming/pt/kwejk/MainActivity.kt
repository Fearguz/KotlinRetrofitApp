package com.programming.pt.kwejk

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.ContextThemeWrapper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{
    private var reinitAttemptCount = 0
    private val reinitAttemptsLimit = 3
    private val reinitDelayMillis = 10000L // 10 seconds
    private val reinitHandler by lazy { Handler() }

    private val kwejk by lazy { Kwejk() }
    private val loader by lazy { ImageLoader(this@MainActivity, imageView, supportActionBar) }
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_left -> {
                if (kwejk.images.previous()) {
                    loader.load(kwejk.images.getImage())
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_right -> {
                if (kwejk.images.next()) {
                    loader.load(kwejk.images.getImage())
                }
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setNavigationMenuEnabled(false)
        navigation.selectedItemId = R.id.navigation_right
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        imageView.setImageResource(R.drawable.image_placeholder)
    }

    override fun onStart() {
        super.onStart()
        if (!kwejk.isInitialized) {
            initKwejk()
        }
    }

    private fun initKwejk() {
        reinitKwejk(0L)
    }

    private fun reinitKwejk(delay: Long) {
        reinitHandler.postDelayed({
            kwejk.start(onSuccessCallback = {
                setNavigationMenuEnabled(true)
                loader.load(kwejk.images.getImage())
            }, onFailureCallback = {
                if (reinitAttemptCount < reinitAttemptsLimit) {
                    ++reinitAttemptCount
                    reinitKwejk(reinitDelayMillis)
                } else {
                    val dialog = ReinitDialog(R.string.reinit_dialog_msg)
                    dialog.show()
                }
            })
        }, delay)
    }

    private fun setNavigationMenuEnabled(enabled: Boolean) {
        navigation.menu.findItem(R.id.navigation_left).isEnabled = enabled
        navigation.menu.findItem(R.id.navigation_right).isEnabled = enabled
    }

    private inner class ReinitDialog(msg: String) {
        private val dialog = AlertDialog.Builder(ContextThemeWrapper(this@MainActivity, android.R.style.Theme_Dialog))
                .setMessage(msg)
                .setPositiveButton(R.string.reinit_dialog_positive, { _, _ ->
                    initKwejk()
                })
                .setNegativeButton(R.string.reinit_dialog_negative, { _, _ ->
                })
                .create()

        constructor(resId: Int) : this(getString(resId))

        fun show() {
            dialog.show()
        }
    }
}
