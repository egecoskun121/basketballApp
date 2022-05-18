package com.ege.basketballapp.ui.home.newgame

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ege.basketballapp.MainActivity
import com.ege.basketballapp.R
import com.ege.basketballapp.adapter.PlayersMainAdapter
import com.ege.basketballapp.adapter.PlayersSecondaryAdapter
import com.ege.basketballapp.databinding.FragmentDraftPlayerBinding
import com.ege.basketballapp.model.GameDirections
import com.ege.basketballapp.model.Player
import com.ege.basketballapp.model.Status
import com.ege.basketballapp.utils.GlobalPreferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class PickPlayersFragment : Fragment(), PlayersMainAdapter.OnPlayerInteract,
    PlayersSecondaryAdapter.OnPlayerInteractSmall {

    private var _binding: FragmentDraftPlayerBinding? = null
    private val args by navArgs<PickPlayersFragmentArgs>()


    private val binding get() = _binding!!
    private val viewModel by viewModels<PickPlayersViewModel>()
    lateinit var playersMainAdapter: PlayersMainAdapter
    lateinit var playersSecondaryAdapter: PlayersSecondaryAdapter

    @Inject
    lateinit var globalPreferences: GlobalPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDraftPlayerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
        binding.start.setOnClickListener {
            globalPreferences.storePlayers(arrayListOf())

        }
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
        playersMainAdapter = PlayersMainAdapter(data, this)
        when (args.game) {
            GameDirections.NEW -> playersSecondaryAdapter =
                PlayersSecondaryAdapter(ArrayList(), this)
            GameDirections.CONTINUE -> playersSecondaryAdapter =
                PlayersSecondaryAdapter(globalPreferences.players, this)
        }


        binding.myPlayersRV.adapter = playersSecondaryAdapter

        binding.allplayersRV.adapter = playersMainAdapter

        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {

                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    Log.e("TAG", "onSwiped: " + viewHolder.adapterPosition)

                    if (playersSecondaryAdapter.itemCount < 5) {
                        playersSecondaryAdapter.addPlayers(playersMainAdapter.getItem(viewHolder.adapterPosition))
                        playersMainAdapter.delete(playersMainAdapter.getItem(viewHolder.adapterPosition))

                        Toast.makeText(
                            requireContext(),
                            getString(R.string.playerAdded),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.youReachedMaximumPlayers),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    checkCount()

                }

            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.allplayersRV)


    }

    private fun checkCount() {
        if (playersSecondaryAdapter.itemCount == 5)
            binding.start.visibility = View.VISIBLE
        else
            binding.start.visibility = View.GONE


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPlayerClicked(player: Player) {

    }

    override fun onPause() {
        super.onPause()
        globalPreferences.storePlayers(playersSecondaryAdapter.getList())
    }

    override fun onShowDetails(player: Player) {

    }


}