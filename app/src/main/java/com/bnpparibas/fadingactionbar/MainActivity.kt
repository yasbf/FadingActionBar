package com.bnpparibas.fadingactionbar

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Build
import android.support.v7.app.ActionBar
import android.support.v7.widget.AppCompatTextView
import android.view.*
import android.view.WindowManager
import android.widget.RelativeLayout
import android.util.TypedValue
import android.view.Gravity











class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val cd = ColorDrawable(Color.BLACK)
        cd.alpha = 0

        supportActionBar?.setBackgroundDrawable(cd)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = cd.color
        }

        if (supportActionBar != null) {
            val title = AppCompatTextView(applicationContext)
            val lp = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            title.layoutParams = lp
            title.setText("Matrix")
            title.gravity = Gravity.START
            title.setTextColor(Color.WHITE)
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25f)
            title.setTypeface(title.getTypeface(), Typeface.BOLD)
            supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
            supportActionBar?.setCustomView(title)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(false)

        scroll_view.setOnScrollViewListener(object : ScrollViewX.OnScrollViewListener {
            override fun onScrollChanged(v: ScrollViewX?, l: Int, t: Int, oldl: Int, oldt: Int) {

                cd.alpha = getNewAlpha(t)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = cd.color
                }
            }
        })
    }

    private fun getNewAlpha(t: Int): Int {
        val headerHeight = header_cover.height - supportActionBar!!.height
        val ratio = Math.min(Math.max(t, 0), headerHeight).toFloat() / headerHeight
        val newAlpha = (ratio * 255).toInt()
        return newAlpha
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }
}
