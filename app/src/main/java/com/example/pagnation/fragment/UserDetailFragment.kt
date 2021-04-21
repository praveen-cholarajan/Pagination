package com.example.pagnation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pagnation.MainListener
import com.example.pagnation.databinding.FragmentUserDetailsBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment(val listener: MainListener) : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(listener: MainListener): UserDetailFragment {
            return UserDetailFragment(listener)
        }
    }

    private lateinit var binding: FragmentUserDetailsBinding
    private val viewModel by viewModels<UserDetailViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        initUserDetailFetch()
        return binding.root
    }

    private fun initUserDetailFetch() {
        if (listener.getUserDetailFetch() == null) {
            Toast.makeText(requireContext(), "data is empty", Toast.LENGTH_SHORT).show()
        } else {
            listener.getUserDetailFetch()?.let {
                Picasso.get().load(it.owner?.ImageUrl).into(binding.ivAvatar)
                viewModel.updateUI(it)
            }
        }
    }
}