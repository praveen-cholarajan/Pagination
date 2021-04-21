package com.example.pagnation


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

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener, MainListener {

    private lateinit var biniding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private var userDetails: PaginationResponse? = null

    private var fragmentArgs: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        biniding = ActivityMainBinding.inflate(layoutInflater)
        biniding.viewModel = viewModel
        setContentView(biniding.root)
        biniding.bottomNav.setOnNavigationItemSelectedListener(this)
        biniding.bottomNav.selectedItemId = R.id.navOne
    }

    fun loadRequiredFragment(fragment: Fragment?): Boolean {

        fragment?.let { frag ->
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.container,
                    frag
                )
                .addToBackStack(frag.tag)
                .commit()
        }
        return false
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragment: Fragment? = when (item.itemId) {
            R.id.navOne -> UserListFragment.newInstance(this)
            R.id.navTwo -> UserDetailFragment.newInstance(this)
            R.id.navThree -> ThreeFragment.newInstance(this)
            else -> null
        }
        return loadRequiredFragment(fragment)
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
            is UserDetailFragment -> loadRequiredFragment(UserListFragment.newInstance(this))
            is ThreeFragment -> loadRequiredFragment(UserListFragment.newInstance(this))
        }
    }

    fun saveStateInstance(arg: Bundle){
        fragmentArgs = arg
    }

    fun getStateInstance() :Bundle?{
        return fragmentArgs
    }

}