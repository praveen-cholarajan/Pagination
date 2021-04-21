package com.example.pagnation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pagnation.MainListener
import com.example.pagnation.databinding.FragmentThreeBinding

class ThreeFragment(val listener: MainListener) : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(listener: MainListener): ThreeFragment {
            return ThreeFragment(listener)
        }
    }

    private val viewModel by viewModels<ThreeViewModel>()
    private lateinit var binding: FragmentThreeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentThreeBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }
}