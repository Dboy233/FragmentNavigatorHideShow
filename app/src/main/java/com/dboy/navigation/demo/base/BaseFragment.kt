package com.dboy.navigation.demo.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 *   @author DBoy
 *   @date 2020/9/2
 *   Class 描述 :
 */
abstract class BaseFragment : Fragment() {

    val TAG = "NavigationDemo"

    abstract val layoutId: Int


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewAndData(view)
        Log.d(TAG, "onViewCreated " + javaClass.simpleName)

    }
    abstract fun initViewAndData(view: View)


    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume " + javaClass.simpleName)
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause " + javaClass.simpleName)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView " + javaClass.simpleName)

    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy " + javaClass.simpleName)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState: " + javaClass.simpleName)
    }

    override fun setInitialSavedState(state: SavedState?) {
        super.setInitialSavedState(state)
        Log.d(TAG, "setInitialSavedState: " + javaClass.simpleName)
    }

}