package com.dboy.navigation.demo.javanav;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.Navigator;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;

import com.dboy.navigation.demo.R;

/**
 * @author DBoy
 * @date 2020/12/13
 * Class 描述 : Hide - Show NavHostFragment
 */
public class NavHostFragmentHideShow extends NavHostFragment {
    /**
     * @return 使用自己的FragmentNavigator
     */
    @NonNull
    @Override
    protected Navigator<? extends FragmentNavigator.Destination> createFragmentNavigator() {
        return new FragmentNavigatorHideShow(requireContext(), getChildFragmentManager(), getContainerId());
    }

    private int getContainerId() {
        int id = getId();
        if (id != 0 && id != View.NO_ID) {
            return id;
        }
        // Fallback to using our own ID if this Fragment wasn't added via
        // add(containerViewId, Fragment)
        return R.id.nav_host_fragment_container;
    }
}
