package com.ege.basketballapp.ui.sidenav.teams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.ege.basketballapp.MainActivity
import com.ege.basketballapp.adapter.TeamsAdapter
import com.ege.basketballapp.databinding.FragmentAllTeamBinding
import com.ege.basketballapp.model.Status
import com.ege.basketballapp.model.Team
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllTeamsFragment : Fragment(),
    TeamsAdapter.OnTeamInteract {

    private var _binding: FragmentAllTeamBinding? = null


    private val binding get() = _binding!!
    private val viewModel by viewModels<AllTeamViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAllTeamBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.menu.setOnClickListener { (activity as MainActivity).openNavigationDrawer() }
        setupObserver()
    }

    private fun setupObserver() {


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.teams.collect { it ->
                    when (it.status) {
                        Status.SUCCESS -> {
                            
                            if (it.data != null)
                                setupData(it.data.data)

                        }
                        Status.LOADING -> {
                        

                        }
                        Status.ERROR -> {

                            
                            it.message?.let { message ->
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupData(data: ArrayList<Team>) {

        binding.playersRV.adapter = TeamsAdapter(data,this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    override fun onShowDetails(team: Team) {
        findNavController().navigate(AllTeamsFragmentDirections.actionAllTeamsFragmentToTeamPlayersFragment(team.id))

    }


}