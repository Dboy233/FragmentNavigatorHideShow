package com.dboy.navigation.demo.fragment

import android.view.View
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.dboy.navigation.demo.R
import com.dboy.navigation.demo.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_setting.*

/**
 *   @author DBoy
 *   @date 2020/9/2
 *   Class 描述 :
 */
class SettingFragment : BaseFragment() {
    override val layoutId: Int
        get() = R.layout.fragment_setting

    override fun initViewAndData(view: View) {
       view.findViewById<Button>(R.id.go_back_to_home).setOnClickListener {
            //返回到HomeFragment 中间的MyFragment会被销毁 HomeFragment也会被重建
            //在nav_graph中设置
            //            app:popUpTo="@id/homeFragment"
            //            app:popUpToInclusive="true"
            //这两个属性起的作用 只会移除目的地fragment栈之上的fragment
            findNavController().navigate(SettingFragmentDirections.actionSettingFragmentToHomeFragment())
        }

        view.findViewById<Button>(R.id.open_myself).setOnClickListener{
            //Hide Show 导致的Bug弊端演示.
            findNavController().navigate(SettingFragmentDirections.actionSettingFragmentToMyself())
        }
    }
}