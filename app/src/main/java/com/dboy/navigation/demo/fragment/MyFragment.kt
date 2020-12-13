package com.dboy.navigation.demo.fragment

import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.dboy.navigation.demo.R
import com.dboy.navigation.demo.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_my.*

/**
 *   @author DBoy
 *   @date 2020/9/2
 *   Class 描述 :首页
 */
class MyFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_my

    override fun initViewAndData(view: View) {
        //插件会生成 一个 fragment+Args字样的帮助类 通过它可以获取传递的Bundle值
        val name = MyFragmentArgs.fromBundle(requireArguments()).name
        Toast.makeText(context, "arguments $name", Toast.LENGTH_SHORT).show()
        view.findViewById<Button>(R.id.go_setting_btn).setOnClickListener {
            findNavController().navigate(MyFragmentDirections.actionMyFragmentToSettingFragment())
        }
    }
}