package com.ege.basketballapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ege.basketballapp.R
import com.ege.basketballapp.model.Player


class PlayersMainAdapter(
    private val dataSet: ArrayList<Player>,
    private val listner: OnPlayerInteract
) :
    RecyclerView.Adapter<PlayersMainAdapter.ViewHolder>() {
    private var context: Context? = null


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val playerName: TextView
        val playerPosition: TextView
        val weight_lb: TextView
        val height_feet: TextView


        init {
            playerName = view.findViewById(R.id.playername)
            playerPosition = view.findViewById(R.id.position)
            height_feet = view.findViewById(R.id.height_feet)
            weight_lb = view.findViewById(R.id.weight_lb)

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        context = viewGroup.context
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_player_draft, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val player = dataSet.get(position)
        viewHolder.height_feet.setText(
            context?.resources?.getString(R.string.height) + "   " + player.height_feet + "   " + context?.resources?.getString(
                R.string.ft
            )
        )
        viewHolder.weight_lb.setText(
            context?.resources?.getString(R.string.weight) + "   " + player.weight_pounds + "   " + context?.resources?.getString(
                R.string.lb
            )
        )

        viewHolder.playerName.text = "${player.first_name}  ${player.last_name}"
        viewHolder.playerPosition.text = player.position

        viewHolder.itemView.setOnClickListener { listner.onPlayerClicked(player) }
    }


    override fun getItemCount() = dataSet.size
    fun delete(player: Player) {
        dataSet.remove(player)
        notifyDataSetChanged()

    }

    fun getItem(position: Int): Player {
        return  dataSet.get(position)
    }


    public interface OnPlayerInteract {
        fun onPlayerClicked(player: Player)

    }


}