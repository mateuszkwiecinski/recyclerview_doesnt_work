package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.coroutines.delay

internal class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual_detaching)

        val container = findViewById<AllowsDetaching>(R.id.container)

        val redView = findViewById<View>(R.id.red_view)
        val greenView = findViewById<View>(R.id.green_view)
        val whiteView = findViewById<View>(R.id.white_view)
        val button = findViewById<MaterialButton>(R.id.button)
        val isAttachedSwitch = findViewById<SwitchMaterial>(R.id.attached_switch)
        val isAddedSwitch = findViewById<SwitchMaterial>(R.id.added_switch)
        val statusText = findViewById<TextView>(R.id.status)

        isAttachedSwitch.isChecked = true
        isAttachedSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                container.attachViewToParent(redView, 0, redView.layoutParams)
            } else {
                container.detachViewFromParent(redView)
            }
        }

        isAddedSwitch.isChecked = true
        isAddedSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                container.addView(redView)
            } else {
                container.removeView(redView)
            }
        }

        button.text = "greenView.requestLayout()"
        button.setOnClickListener { greenView.requestLayout() }

        lifecycleScope.launchWhenResumed {
            while (true) {
                statusText.text = """
                    red.isLaidOut = ${redView.isLaidOut}, isAttachedToWindow = ${redView.isAttachedToWindow}
                    green.isLaidOut = ${greenView.isLaidOut}, isAttachedToWindow = ${greenView.isAttachedToWindow}
                    switch.isLaidOut = ${whiteView.isLaidOut}, isAttachedToWindow = ${whiteView.isAttachedToWindow}
                """.trimIndent()
                delay(200)
            }
        }
    }
}

class AllowsDetaching(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {


    public override fun attachViewToParent(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        super.attachViewToParent(child, index, params)
    }

    public override fun detachViewFromParent(child: View?) {
        super.detachViewFromParent(child)
    }
}
