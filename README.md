# NavHostFragment&FragmentNavigator

 通过重写  FragmentNavigator 将原来的 FragmentTransaction.replace() 方法替换为 hide()/Show()

 再重写HostFragment使用重写后的FragmentNavigator。

Fragment `hide()`和`Show()`触发的时候会触发Fragment的状态改变 `onPause()`和`onResume()`函数。更好的管理Fragmnet状态。

避免Fragment的重建。运行Demo查看Log `"NavigationDemo"`

####  主要只有两个class

`java`

[FragmentNavigatorHideShow.java](https://github.com/Dboy233/HSNavHostFragment/blob/master/app/src/main/java/com/dboy/navigation/demo/javanav/FragmentNavigatorHideShow.java) 和 [NavHostFragmentHideShow.java](https://github.com/Dboy233/HSNavHostFragment/blob/master/app/src/main/java/com/dboy/navigation/demo/javanav/NavHostFragmentHideShow.java)

`kotlin`

[FragmentNavigatorHideShow.kt](https://github.com/Dboy233/HSNavHostFragment/blob/master/app/src/main/java/com/dboy/navigation/demo/kotlinnav/FragmentNavigatorHideShow.kt) 和 [NavHostFragmentHideShow.kt](https://github.com/Dboy233/HSNavHostFragment/blob/master/app/src/main/java/com/dboy/navigation/demo/kotlinnav/NavHostFragmentHideShow.kt)

#### 使用方式

1.粘贴 [FragmentNavigatorHideShow.java](https://github.com/Dboy233/HSNavHostFragment/blob/master/app/src/main/java/com/dboy/navigation/demo/javanav/FragmentNavigatorHideShow.java) 和 [NavHostFragmentHideShow.java](https://github.com/Dboy233/HSNavHostFragment/blob/master/app/src/main/java/com/dboy/navigation/demo/javanav/NavHostFragmentHideShow.java) 到你的项目目录中。

2.在FragmentContrainerView中name属性修改为 "{你的报名路径}.NavHostFragmentHideShow"


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

#### 优势

修改代码少，使用便捷，不会对Navigation框架有太大影响。

没有修改Navigation原有的Api调用方式。

