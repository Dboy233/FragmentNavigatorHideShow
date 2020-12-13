# NavHostFragmentHideShow

 通过重写  FragmentNavigator 将原来的 FragmentTransaction.replace() 方法替换为 hide()/Show()

 再重写HostFragment使用重写后的FragmentNavigator。

fragment `hide()`和`Show()`的时候会执行自己的`onPause()`和`onResume()`函数。避免Fragment的重建。

####  主要只有两个class

`java`

[FragmentNavigatorHideShow.java](https://github.com/Dboy233/HSNavHostFragment/blob/master/app/src/main/java/com/dboy/navigation/demo/javanav/FragmentNavigatorHideShow.java) 和 [NavHostFragmentHideShow.java](https://github.com/Dboy233/HSNavHostFragment/blob/master/app/src/main/java/com/dboy/navigation/demo/javanav/NavHostFragmentHideShow.java)

`kotlin`

[FragmentNavigatorHideShow.kt](https://github.com/Dboy233/HSNavHostFragment/blob/master/app/src/main/java/com/dboy/navigation/demo/kotlinnav/FragmentNavigatorHideShow.kt) 和 [NavHostFragmentHideShow.kt](https://github.com/Dboy233/HSNavHostFragment/blob/master/app/src/main/java/com/dboy/navigation/demo/kotlinnav/NavHostFragmentHideShow.kt)

#### 使用方式

``` xml

<?xml version="1.0" encoding="utf-8"?>
<androidx.fragment.app.FragmentContainerView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/fragment"
    android:name="com.dboy.navigation.demo.javanav.NavHostFragmentHideShow"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:defaultNavHost="true"
    app:navGraph="@navigation/nav_graph" />

```

将 name 的值设置为 NavHostFragmentHideShow 即可


