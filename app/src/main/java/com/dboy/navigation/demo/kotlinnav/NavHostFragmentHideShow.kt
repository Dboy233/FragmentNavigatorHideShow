package com.dboy.navigation.demo.kotlinnav

import android.view.View
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import com.dboy.navigation.demo.R

/**
 * @author DBoy
 * @date 2020/12/13
 * Class 描述 : Hide - Show NavHostFragment
 */
class NavHostFragmentHideShow : NavHostFragment() {

    /**
     * @return 使用自己的FragmentNavigator 虽然是废弃的，但是源码实现最终都是调用这里返回Navigator
     */
    override fun createFragmentNavigator(): Navigator<out FragmentNavigator.Destination> {
        return FragmentNavigatorHideShow(requireContext(), childFragmentManager, containerId)
    }


    private val containerId: Int
        get() {
            val id = id
            return if (id != 0 && id != View.NO_ID) {
                id
            } else R.id.nav_host_fragment_container//这里可能会报错，但是编译是能通过的
            // Fallback to using our own ID if this Fragment wasn't added via
            // add(containerViewId, Fragment)
        }
}