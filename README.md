[https://android.jlelse.eu/parallax-scrolling-header-tabs-android-tutorial-2cc6e40aa257](https://android.jlelse.eu/parallax-scrolling-header-tabs-android-tutorial-2cc6e40aa257)

# XML

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.coordinatorlayout.widget.CoordinatorLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/htab_maincontent"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true">

    <com.google.android.material.appbar.AppBarLayout
        android:id="@+id/htab_appbar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="?attr/colorPrimary"
        android:fitsSystemWindows="true"
        android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar">

        <com.google.android.material.appbar.CollapsingToolbarLayout
            android:id="@+id/htab_collapse_toolbar"
            android:layout_width="match_parent"
            android:layout_height="256dp"
            android:fitsSystemWindows="true"
            app:contentScrim="?attr/colorPrimary"
            app:layout_scrollFlags="scroll|exitUntilCollapsed|snap"
            app:titleEnabled="false">

            <ImageView
                android:id="@+id/htab_header"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:background="@drawable/sexy"
                android:fitsSystemWindows="true"
                android:scaleType="centerCrop"
                app:layout_collapseMode="parallax"/>

            <!-- overay -->
            <View
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:alpha="0.3"
                android:background="@android:color/black"
                android:fitsSystemWindows="true"/>

            <com.google.android.material.appbar.MaterialToolbar
                android:id="@+id/htab_toolbar"
                android:layout_width="match_parent"
                android:layout_height="?attr/actionBarSize"
                android:layout_gravity="top"
                android:layout_marginBottom="48dp"
                app:layout_collapseMode="pin"
                app:popupTheme="@style/ThemeOverlay.AppCompat.Light"/>

            <com.google.android.material.tabs.TabLayout
                android:id="@+id/htab_tabs"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_gravity="bottom"
                app:tabIndicatorColor="@android:color/white"
                app:tabSelectedTextColor="@android:color/white"
                app:tabTextColor="@color/white"/>

        </com.google.android.material.appbar.CollapsingToolbarLayout>

    </com.google.android.material.appbar.AppBarLayout>

    <androidx.viewpager.widget.ViewPager
        android:id="@+id/htab_viewpager"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_behavior="@string/appbar_scrolling_view_behavior"/>

</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

app:contentScrim="?attr/colorPrimary"
            app:layout_scrollFlags="scroll|exitUntilCollapsed|snap"
            app:titleEnabled="false"

# ViewHierarchy

- CoordinatorLayout

    ⇒ `fitsSystemWindow=true`

    - AppBarLayout

        ⇒`fitsSystemWindow=true`

        - CollapsingToolbarLayout

            ⇒ `contentScrim=` : collapsig할 때 색깔을 바꿔줌

            ⇒ `layout_scrollFlags=` : toolbar가 어떻게 표시될지 정해줌 

            [layout_scrollFlags](https://www.notion.so/layout_scrollFlags-b5f94c8cef2e452b8f2cad70334bad0e)

            - ImageView

                ⇒ `app:layout_collapseMode="parallax"` 

                [Collapse Modes](https://www.notion.so/Collapse-Modes-bedb5ed2a48c4f089e9f89f6480c4c03)

            - ToolBar

                ⇒ `layout_collapseMode="pin"`

            - TabLayout
        - CollapsingToolbarLayout
    - AppBarLayout
- CoordinatorLayout
- ViewPager

    ⇒ `app:layout_behavior="@string/appbar_scrolling_view_behavior"`

    [ScrollingBehavior](https://www.notion.so/ScrollingBehavior-4a752fe3072e435090f1f73609b8d13b)

# Activity

- Dummy Fragment

```kotlin
//RecyclerView Setting
class DummyFragment(private val color: Int) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_dummy, container, false)

        val frameLayout = view.findViewById<View>(R.id.dummyfrag_bg) as FrameLayout
        frameLayout.setBackgroundColor(color)

        val recyclerView = view.findViewById<View>(R.id.dummyfrag_scrollableview) as RecyclerView

        val linearLayoutManager = LinearLayoutManager(activity!!.baseContext)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.setHasFixedSize(true)

        val adapter = DessertAdapter(context!!)
        recyclerView.adapter = adapter
        return view
//        return inflater.inflate(R.layout.fragment_dummy, container, false)
    }

}
```

- ViewPagerAdapter (FragmentViewPager)

```kotlin
class ViewPagerAdapter(fragmentManager: FragmentManager, behavior: Int): FragmentPagerAdapter(fragmentManager, behavior) {

    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()

    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    public fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]
    }

}
```

- MainActivity

⇒ Setup tabbar 

⇒ Palette for vibrant Animation

⇒ Setup FragmentViewPager

```kotlin
class MainActivity : AppCompatActivity() {

    private val TAG: String = "ab"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.htab_toolbar)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) supportActionBar!!.setTitle("Parallax Tabs")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val viewPager = findViewById<ViewPager>(R.id.htab_viewpager)
        setupViewPager(viewPager)

        val tabLayout = findViewById<TabLayout>(R.id.htab_tabs)
        tabLayout.setupWithViewPager(viewPager)

        val collapsingToolbarLayout =
            findViewById<View>(R.id.htab_collapse_toolbar) as CollapsingToolbarLayout

        //Using the Palette API, we’ll set colours for the Toolbar, TabLayout and Status Bar. This is easily done by the following code snippet:
        //Vibrant Color, animation 효과다
        try {
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.sexy)
            Palette.from(bitmap).generate { palette ->
                val vibrantColor: Int =
                    palette?.getVibrantColor(Color.parseColor("#3F51B5")) ?: Color.WHITE
                val vibrantDarkColor: Int =
                    palette?.getDarkVibrantColor(Color.parseColor("#303F9F")) ?: Color.WHITE
                collapsingToolbarLayout.setContentScrimColor(vibrantColor)
                collapsingToolbarLayout.setStatusBarScrimColor(vibrantDarkColor)
            }
        } catch (e: Exception) {
            // if Bitmap fetch fails, fallback to primary colors
            Log.e(TAG, "onCreate: failed to create bitmap from background", e.fillInStackTrace())
            collapsingToolbarLayout.setContentScrimColor(
                Color.parseColor("#3F51B5")
            )
            collapsingToolbarLayout.setStatusBarScrimColor(
                Color.parseColor("#303F9F")
            )
        }

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
                Log.d(TAG, "onTabSelected: pos: " + tab.position)
                when (tab.position) {
                    0 -> showToast("One")
                    1 -> showToast("Two")
                    2 -> showToast("Three")
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(
            supportFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        adapter.addFragment(
            DummyFragment(
                Color.parseColor("#00BCD4")
            ), "Cyan"
        )
        adapter.addFragment(
            DummyFragment(
                Color.parseColor("#FFC107")
            ), "Amber"
        )
        adapter.addFragment(
            DummyFragment(
                Color.parseColor("#F3E5F5")
            ), "Purple"
        )
        viewPager.adapter = adapter
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

}
```

# Additional Explanation

[Layout ScrollFlags](https://www.notion.so/Layout-ScrollFlags-b0957c6284944ceea0281f4520655ae3)

### ℹ️ Layout_scrollFlags in CollapsingToolbarlayout

- `enterAlways`: The view will become visible when scrolling up. This flag is useful in cases when scrolling from the bottom of a list and wanting to expose the Toolbar as soon as scrolling up takes place.
- `enterAlwaysCollapsed`: Normally, when only enterAlways is used, the Toolbar will continue to expand as you scroll down.Assuming enterAlways is declared and you have specified a minHeight, you can also specify enterAlwaysCollapsed. When this setting is used, your view will only appear at this minimum height. Only when scrolling reaches to the top will the view expand to its full height
- `exitUntilCollapsed`: When the scroll flag is set, scrolling down will normally cause the entire content to move.By specifying a minHeight and exitUntilCollapsed, the minimum height of the Toolbar will be reached before the rest of the content begins to scroll and exit from the screen
- `snap`: Using this option will determine what to do when a view only has been partially reduced. If scrolling ends and the view size has been reduced to less than 50% of its original, then this view to return to its original size. If the size is greater than 50% of its sized, it will disappear completely.

### ℹ️ Layout Collapse Mode

[https://tpom6oh.medium.com/managing-content-in-collapsingtoolbarlayout-e9afbc91be24](https://tpom6oh.medium.com/managing-content-in-collapsingtoolbarlayout-e9afbc91be24)

You’ve implemented a beautifully animating collapsing toolbar and now you want to add some useful content in the collapsing area. It’s easy to do, because **CollapsingToolbarLayout** is also a **FrameLayout**. Just add whatever you want inside. But what to do with the content when the toolbar is collapsed?

## **What are those collapse modes?**

Framework gives you a parameter called `**app:layout_collapseMode**` with possible values `**pin**`, `**parallax**` and `**none**`, which is the default. You can assign this parameters to **Toolbar** component.

- `**none**` - the content moves together with the CollapsingToolbarLayout

![https://miro.medium.com/max/2160/1*aKyjTKtd5I16rX9B0DQM-w.gif](https://miro.medium.com/max/2160/1*aKyjTKtd5I16rX9B0DQM-w.gif)

- `**pin**` - the content will stay in the same place while this place is still inside the collapsing toolbar. When toolbar bottom reaches view’s bottom it will become aligned with it and start moving.

![https://miro.medium.com/max/2160/1*SJUIm1Q13u4l-SW10N6KNA.gif](https://miro.medium.com/max/2160/1*SJUIm1Q13u4l-SW10N6KNA.gif)

- `**parallax**` - the content moves a bit slower than the **CollapsingToolbarLayout**.

![https://miro.medium.com/max/2160/1*k1HBI0S0riqRf6S-6XTo-Q.gif](https://miro.medium.com/max/2160/1*k1HBI0S0riqRf6S-6XTo-Q.gif)

## **Parallax + fade**

![https://miro.medium.com/max/2160/1*JbYEqYXkwrxWMLVHCdUpVQ.gif](https://miro.medium.com/max/2160/1*JbYEqYXkwrxWMLVHCdUpVQ.gif)

You probably noticed that it is required from **CollapsingToolbarLayout** to be inside an **AppBarLayout**. With this setup you can set a collapse listener. I think it would make more sense to be able to add a listener to **CollapsingToolbarLayout** , but that’s how it is.

# app_bar_layout.addOnOffsetChangedListener { appBarLayout, verticalOffset -> }

It’s not very straightforward, but with the provided parameters you can observe the moving of collapsing toolbar. The **verticalOffset** is how much the toolbar is collapsed and it’s going from **0**, when the toolbar is fully expanded, to (**-appBarLayout.totalScrollRange**) when the toolbar is fully collapsed. Knowing the total scroll range you can use the following code to achieve parallax + fade as in the image above.

# app_bar_layout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->toolbar_info.alpha = (appBarLayout.totalScrollRange + verticalOffset).toFloat() / appBarLayout.totalScrollRange}