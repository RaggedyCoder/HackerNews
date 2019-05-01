package com.hacker.news.ui.activity.home

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import com.hacker.news.R
import com.hacker.news.ui.activity.HackerNewsBaseActivity
import com.hacker.news.ui.fragment.item.HackerNewsItemListFragmentDirections
import com.hacker.news.viewmodel.HackerNewsHomeViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_hacker_news_home.*

class HackerNewsHomeActivity : HackerNewsBaseActivity() {

    override fun getViewModelClass() = HackerNewsHomeViewModel::class.java

    private val hackerNewsHomeViewModel by lazy {
        getViewModel() as HackerNewsHomeViewModel
    }

    private val navController by lazy {
        findNavController(R.id.fragment_navigation_container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hacker_news_home)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            if (it.isChecked) {
                return@setNavigationItemSelectedListener false
            } else {
                navController.navigate(
                    HackerNewsItemListFragmentDirections
                        .actionToGoHackerNewsItemListFragment(
                            storyName = hackerNewsHomeViewModel.getStoryNameFromNavigationMenuId(it.itemId)
                        )
                )
                return@setNavigationItemSelectedListener true
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}