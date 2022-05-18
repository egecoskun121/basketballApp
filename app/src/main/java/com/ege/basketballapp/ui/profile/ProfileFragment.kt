package com.ege.basketballapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ege.basketballapp.MainActivity
import com.ege.basketballapp.R
import com.ege.basketballapp.databinding.FragmentProfileBinding
import com.ege.basketballapp.model.User
import com.ege.basketballapp.utils.GlobalPreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null


    private val binding get() = _binding!!
    private val viewModel by viewModels<ProfileViewModel>()

    @Inject
    lateinit var globalPreferences: GlobalPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (globalPreferences.user != null) {
            binding.teamName.editText?.setText(globalPreferences.user.teamName)
            binding.fullname.editText?.setText(globalPreferences.user.name)
        }

        binding.menu.setOnClickListener { (activity as MainActivity).openNavigationDrawer() }
        binding.saveBTN.setOnClickListener {
            globalPreferences.storeUserInfo(
                User(
                    binding.fullname.editText?.text.toString(),
                    binding.teamName.editText?.text.toString(),
                )
            )


            findNavController().navigate(R.id.navigation_home)
            (activity as MainActivity).showBottomNav()
            (activity as MainActivity).updateSideMenu()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}