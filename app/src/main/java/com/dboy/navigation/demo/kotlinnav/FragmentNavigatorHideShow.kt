package com.dboy.navigation.demo.kotlinnav

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator

/**
 * 使用Hide/Show处理Fragment，使Fragment执行 onPause/onResume.
 * 避免页面重建.
 * Use Hide/Show to process Fragment and make Fragment execute onPause/onResume.
 * Avoid page reconstruction.
 */
@Navigator.Name("fragment")
class FragmentNavigatorHideShow(
    private val mContext: Context,
    private val mFragmentManager: FragmentManager,
    private val mContainerId: Int
) : FragmentNavigator(mContext, mFragmentManager, mContainerId) {

    private var savedIds: MutableSet<String>? = null

    init {
        try {
            val field = FragmentNavigator::class.java.getDeclaredField("savedIds")
            field.isAccessible = true
            savedIds = field[this] as MutableSet<String>
        } catch (e: Exception) {
            Log.d(TAG, "反射获取SavedIds失败: $e")
        }
    }

    override fun navigate(
        entries: List<NavBackStackEntry>,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ) {
        if (mFragmentManager.isStateSaved) {
            Log.i(
                TAG, "Ignoring navigate() call: FragmentManager has already saved its state"
            )
            return
        }
        for (entry in entries) {
            navigate(entry, navOptions, navigatorExtras)
        }
    }

    private fun navigate(
        entry: NavBackStackEntry,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ) {
        val initialNavigation = state.backStack.value.isEmpty()
        val restoreState = (
                navOptions != null && !initialNavigation &&
                        navOptions.shouldRestoreState() &&
                        savedIds?.remove(entry.id) == true
                )
        if (restoreState) {
            // 还原回堆栈完成还原条目的所有工作
            mFragmentManager.restoreBackStack(entry.id)
            state.push(entry)
            return
        }
        val ft = createFragmentTransaction(entry, navOptions)


        if (!initialNavigation) {
            ft.addToBackStack(entry.id)
        }

        if (navigatorExtras is Extras) {
            for ((key, value) in navigatorExtras.sharedElements) {
                ft.addSharedElement(key, value)
            }
        }
        ft.commit()
        // The commit succeeded, update our view of the world
        //todo: 需要判断是否要加入到回退栈。要和createFragmentTransaction中添加fragment的操作同步。当添加fragment失败的时候不应该再增加回退栈。
        state.push(entry)
    }


    override fun onLaunchSingleTop(backStackEntry: NavBackStackEntry) {
        if (mFragmentManager.isStateSaved) {
            Log.i(
                TAG,
                "Ignoring onLaunchSingleTop() call: FragmentManager has already saved its state"
            )
            return
        }
        //重写此方法主要是让他调用修改后的这个方法
        val ft = createFragmentTransaction(backStackEntry, null)
        if (state.backStack.value.size > 1) {
            // If the Fragment to be replaced is on the FragmentManager's
            // back stack, a simple replace() isn't enough so we
            // remove it from the back stack and put our replacement
            // on the back stack in its place
            mFragmentManager.popBackStack(
                backStackEntry.id,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            ft.addToBackStack(backStackEntry.id)
        }
        ft.commit()
        // The commit succeeded, update our view of the world
        state.onLaunchSingleTop(backStackEntry)
    }


    private fun createFragmentTransaction(
        entry: NavBackStackEntry,
        navOptions: NavOptions?
    ): FragmentTransaction {
        val destination = entry.destination as Destination
        val args = entry.arguments
        var targetFragmentClassName = destination.className
        if (targetFragmentClassName[0] == '.') {
            targetFragmentClassName = mContext.packageName + targetFragmentClassName
        }
//          注释掉replace逻辑的两行代码，这里是创建了新的fragment 我们要在原有的fragments中查找已经存在的fragment
//          val frag = mFragmentManager.fragmentFactory.instantiate(mContext.classLoader, className)
//          frag.arguments = args
        val ft = mFragmentManager.beginTransaction()
        var enterAnim = navOptions?.enterAnim ?: -1
        var exitAnim = navOptions?.exitAnim ?: -1
        var popEnterAnim = navOptions?.popEnterAnim ?: -1
        var popExitAnim = navOptions?.popExitAnim ?: -1
        if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
            enterAnim = if (enterAnim != -1) enterAnim else 0
            exitAnim = if (exitAnim != -1) exitAnim else 0
            popEnterAnim = if (popEnterAnim != -1) popEnterAnim else 0
            popExitAnim = if (popExitAnim != -1) popExitAnim else 0
            ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        }

        //region 添加的代码

        var frag: Fragment? = mFragmentManager.primaryNavigationFragment //查找当前导航栈顶的fragment

        //判断是否需要重新创建一个新的Fragment
        var needRecreate = false
        //提前判断。如果当栈顶Fragment 等于 目的地Fragment。在逻辑上属于自己打开自己。
        //所以当逻辑是这样的，就需要重新创建一个自己。
        if (frag?.javaClass?.name == targetFragmentClassName) {
            needRecreate = true
        }

        //如果栈顶存在Fragment，就hide。
        if (frag != null) {
            ft.setMaxLifecycle(frag, Lifecycle.State.STARTED)
            ft.hide(frag)
        }
        //查找目标导航fragment 如果查找到了就show这个fragment，如果没有查找到就创建一个新的fragment。
        val tag = destination.id.toString()
        frag = mFragmentManager.findFragmentByTag(tag)
        //这里判断是否需要重建，如果需要重建就不show。而是重新创建一个。
        if (frag != null && !needRecreate) {

            //fragment 已经存在显示
            ft.setMaxLifecycle(frag, Lifecycle.State.RESUMED)
            ft.show(frag)
        } else {
            //fragment 不存在创建，添加
            frag = mFragmentManager.fragmentFactory.instantiate(mContext.classLoader, targetFragmentClassName)
            frag.arguments = args//设置参数.
            ft.add(mContainerId, frag, tag)
        }
        //endregion

        //ft.replace(mContainerId, frag) //注释掉原有逻辑
        ft.setPrimaryNavigationFragment(frag)//将新的目标fragment标记为栈顶。可以这么理解。
        ft.setReorderingAllowed(true)
        return ft
    }


    companion object {
        private const val TAG = "HSFragmentNavigator"
    }
}