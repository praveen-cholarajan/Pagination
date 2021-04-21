package com.example.pagnation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pagnation.MainActivity
import com.example.pagnation.MainListener
import com.example.pagnation.databinding.FragmentUserBinding
import com.example.pagnation.fragment.adapter.UserListAdapter
import com.example.pagnation.response.PaginationResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class UserListFragment : Fragment(), UserListListener {

    @Inject
    lateinit var adapter: UserListAdapter
    var listener: MainListener? = null

    companion object {
        @JvmStatic
        fun newInstance(listener: MainListener): UserListFragment {
            UserListFragment().apply {
                this.listener = listener
            }.also {
                return it
            }
        }
    }

    private lateinit var binding: FragmentUserBinding
    private val viewModel by viewModels<UserListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.listener = this
        initRecyclerView()
        viewModel.apply {
            progressBarVisibility.set(View.VISIBLE)
            getUserDataList()
        }
        return binding.root
    }

    private fun getUserLists() {
        lifecycleScope.launch {
            viewModel.apply {
                progressBarVisibility.set(View.GONE)
                charactersFlow.collectLatest {
                    Timber.d("getUserLists : $it")
                    adapter.submitData(it)
                    adapter.userListener(this@UserListFragment)
                }
            }
        }

    }

    private fun initRecyclerView() {
        binding.rvUserList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@UserListFragment.adapter
        }
        adapter.userListener(this)
    }

    override fun userData(data: PaginationResponse?, comments: String?) {
        Timber.d("Adapter position userData Clicked :$comments : data:$data")
        listener?.userDetailFetch(data!!)
        (activity as MainActivity).loadRequiredFragment(
            UserDetailFragment.newInstance(listener!!),
            1
        )
    }

    override fun onResume() {
        super.onResume()
        getUserLists()
    }

}