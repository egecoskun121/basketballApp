package com.ege.basketballapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ege.basketballapp.R
import com.ege.basketballapp.model.Player


class PlayersSecondaryAdapter(
    private val dataSet: ArrayList<Player>,
    private val listner: OnPlayerInteractSmall
) :
    RecyclerView.Adapter<PlayersSecondaryAdapter.ViewHolder>() {
    private var context: Context? = null


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val playerName: TextView
        val playerPosition: TextView
        val height_feet: TextView


        init {
            playerName = view.findViewById(R.id.playerName)
            playerPosition = view.findViewById(R.id.playerPosition)
            height_feet = view.findViewById(R.id.playerHeight)

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        context = viewGroup.context
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_player_small, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val player = dataSet.get(position)
        viewHolder.height_feet.setText(
            "" + player.height_feet + "   " + context?.resources?.getString(
                R.string.ft
            )
        )


        viewHolder.playerName.text = "${player.first_name}  ${player.last_name}"
        viewHolder.playerPosition.text = player.position
        viewHolder.itemView.setOnClickListener { listner.onShowDetails(player) }
    }


    override fun getItemCount() = dataSet.size
    fun delete(player: Player) {
        dataSet.remove(player)
        notifyDataSetChanged()

    }

    fun addPlayers(player: Player) {
        dataSet.add(player)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): Player {
        return dataSet.get(position)
    }

    fun getList(): ArrayList<Player> {
        return dataSet
    }

    public interface OnPlayerInteractSmall {
        fun onShowDetails(player: Player)

    }

}