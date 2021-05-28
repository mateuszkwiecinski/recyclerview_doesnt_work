package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

internal class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edittext)
        val pager = findViewById<ViewPager2>(R.id.pager)
        pager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 2

            override fun createFragment(position: Int) =
                if (position == 2) {
                    RecyclerviewFragment()
                } else {
                    RefreshableFragment()
                }

        }
        val tabs = findViewById<TabLayout>(R.id.tabs)
        TabLayoutMediator(tabs, pager) { tab, position ->
            tab.text = "Tab $position"
        }.attach()
        findViewById<RecyclerView>(R.id.recycler).setup(this)
    }
}

class RefreshableFragment : Fragment(R.layout.fragment_with_switch)

class RecyclerviewFragment : Fragment(R.layout.fragment_recyclerview) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<RecyclerView>(R.id.recycler).setup(requireContext())

    }
}

fun RecyclerView.setup(context: Context) {
    layoutManager = LinearLayoutManager(context)
    adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ) = object : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(viewType, parent, false)
        ) {

        }

        override fun getItemViewType(position: Int): Int {
            return if (position % 20 == 0) {
                R.layout.item_with_switch
            } else {
                R.layout.item_with_text
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val label = when (holder.itemViewType) {
                R.layout.item_with_switch -> holder.itemView.findViewById<SwitchMaterial>(R.id.item_switch)
                R.layout.item_with_text -> holder.itemView.findViewById<TextView>(R.id.item_text)
                else -> error("unsupported")
            }

            label.text = "Item $position"
        }

        override fun getItemCount(): Int = 100

    }
}

class SwiMatChTerial(context: Context, attrs: AttributeSet?) : SwitchMaterial(context, attrs) {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

    override fun setChecked(checked: Boolean) {
        super.setChecked(checked)
    }

    override fun requestLayout() {
        super.requestLayout()
    }
}
