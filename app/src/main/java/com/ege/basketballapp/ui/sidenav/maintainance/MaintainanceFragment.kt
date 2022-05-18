package com.ege.basketballapp.ui.sidenav.maintainance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ege.basketballapp.MainActivity
import com.ege.basketballapp.databinding.FragmentMaintainanceBinding
import com.ege.basketballapp.utils.GlobalPreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MaintainanceFragment : Fragment() {

    private var _binding: FragmentMaintainanceBinding? = null


    private val binding get() = _binding!!
    private val viewModel by viewModels<MaintainanceViewModel>()

    @Inject
    lateinit var globalPreferences: GlobalPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMaintainanceBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.menu.setOnClickListener { (activity as MainActivity).openNavigationDrawer() }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}