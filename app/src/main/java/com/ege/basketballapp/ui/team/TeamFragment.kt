package com.ege.basketballapp.ui.team

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
import com.ege.basketballapp.adapter.PlayersSecondaryAdapter
import com.ege.basketballapp.databinding.FragmentTeamBinding
import com.ege.basketballapp.model.Player
import com.ege.basketballapp.model.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TeamFragment : Fragment(),
    PlayersSecondaryAdapter.OnPlayerInteractSmall {

    private var _binding: FragmentTeamBinding? = null

    private val binding get() = _binding!!
    private val viewModel by viewModels<TeamViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTeamBinding.inflate(inflater, container, false)
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
                viewModel.players.collect { it ->
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

    private fun setupData(data: ArrayList<Player>) {

        binding.playersRV.adapter = PlayersSecondaryAdapter(data, this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onShowDetails(player: Player) {
        findNavController().navigate(
            TeamFragmentDirections.actionNavigationMyTeamToPlayerDetailsFragment(
                player.first_name + " " + player.last_name
            )
        )

    }


}