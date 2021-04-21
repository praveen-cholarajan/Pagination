package com.example.pagnation

import android.R.attr
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.pagnation.databinding.ActivityMainBinding
import com.example.pagnation.fragment.ThreeFragment
import com.example.pagnation.fragment.UserDetailFragment
import com.example.pagnation.fragment.UserListFragment
import com.example.pagnation.response.PaginationResponse
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener,
    MainListener {

    private lateinit var biniding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    private var userDetails: PaginationResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        biniding = ActivityMainBinding.inflate(layoutInflater)
        biniding.viewModel = viewModel
        setContentView(biniding.root)
        biniding.bottomNav.setOnNavigationItemSelectedListener(this)
        biniding.bottomNav.selectedItemId = R.id.navOne
    }


    fun loadRequiredFragment(fragment: Fragment?, type: Int): Boolean {
        val backStateName: String? = fragment?.javaClass?.name
        var requiredFragment: Fragment? = null

        supportFragmentManager.fragments.let {
            for (item in it) {
                when (type) {
                    0 -> {
                        if (item is UserListFragment) {
                            requiredFragment = item
                            Timber.d("loadRequireFragment type :$type: $requiredFragment")
                        }
                        break
                    }
                }
            }
        }
        Timber.d("loadRequireFragment : $requiredFragment")

        fragment?.let { frag ->
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.container,
                    if (requiredFragment != null && requiredFragment is UserListFragment) requiredFragment!! else frag
                )
                .addToBackStack(backStateName)
                .commit()
        }
        return false
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var type = 0
        val fragment: Fragment? = when (item.itemId) {
            R.id.navOne -> {
                type = 0
                UserListFragment.newInstance(this)
            }
            R.id.navTwo -> {
                type = 1
                UserDetailFragment.newInstance(this)
            }
            R.id.navThree -> {
                type = 2
                ThreeFragment.newInstance(this)
            }
            else -> null
        }
        return loadRequiredFragment(fragment, type)
    }

    override fun userDetailFetch(user: PaginationResponse) {
        userDetails = user
    }

    override fun getUserDetailFetch(): PaginationResponse? {
        return userDetails
    }

    override fun onBackPressed() {

        when (supportFragmentManager.findFragmentById(R.id.container)) {
            is UserListFragment -> moveTaskToBack(true)
            is UserDetailFragment -> loadRequiredFragment(UserListFragment.newInstance(this), 0)
            is ThreeFragment -> loadRequiredFragment(UserListFragment.newInstance(this), 0)
        }
    }
}