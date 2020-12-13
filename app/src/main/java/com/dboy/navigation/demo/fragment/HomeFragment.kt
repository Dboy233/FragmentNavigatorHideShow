package com.dboy.navigation.demo.fragment

import android.view.View
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.dboy.navigation.demo.R
import com.dboy.navigation.demo.base.BaseFragment

/**
 *   @author DBoy
 *   @date 2020/9/2
 *   Class 描述 :首页
 */
class HomeFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_home

    override fun initViewAndData(view: View) {
        //其中一种跳转页面方式
//        view.findViewById<Button>(R.id.go_my_button).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.myFragment))

        //通过插件生成页面携参帮助类
        view.findViewById<Button>(R.id.go_my_button).setOnClickListener {
            //插件会生成一个包装类，这个类里面记录了当前页面到下个页面（具体看名字）所携带的变量参数
            //如果当前fragment可能有多个子页面节点 在改类中会生成多个静态方法用于传递参数
            val actionHomeFragmentToMyFragment =
                HomeFragmentDirections.actionHomeFragmentToMyFragment(name = "DBoy")
            findNavController().navigate(actionHomeFragmentToMyFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}