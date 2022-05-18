package com.ege.basketballapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ege.basketballapp.MainActivity
import com.ege.basketballapp.databinding.FragmentHomeBinding
import com.ege.basketballapp.model.GameDirections
import com.ege.basketballapp.utils.GlobalPreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()

    @Inject
    lateinit var globalPreferences: GlobalPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.menu.setOnClickListener { (activity as MainActivity).openNavigationDrawer() }
        if (globalPreferences.players != null && !(globalPreferences.players.isEmpty()))
            binding.contin.visibility = View.VISIBLE
        else
            binding.contin.visibility = View.GONE
        binding.newGame.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionNavigationHomeToPickPlayersFragment(GameDirections.NEW),
            )
        }
        binding.contin.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionNavigationHomeToPickPlayersFragment(GameDirections.CONTINUE),
            )
        }
        binding.whatsappfab.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionNavigationHomeToMaintainanceFragment(),
            )
        }

        binding.exitBTN.setOnClickListener { requireActivity().finish() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}