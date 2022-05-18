package com.ege.basketballapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ege.basketballapp.R
import com.ege.basketballapp.model.Player


class PlayersAdapter(
    private val dataSet: ArrayList<Player>,
    private val listner: OnPlayerInteract
) :
    RecyclerView.Adapter<PlayersAdapter.ViewHolder>() {
    private var context: Context? = null


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val PlayerImg: ImageView

        val playerPower: TextView
        val playerPosition: TextView


        init {
            PlayerImg = view.findViewById(R.id.playerIMG)
            playerPower = view.findViewById(R.id.playerPower)
            playerPosition = view.findViewById(R.id.playerPosition)

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        context = viewGroup.context
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_player, viewGroup, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val player = dataSet.get(position)

        viewHolder.itemView.setOnClickListener { listner.onPlayerClicked(player) }
    }


    override fun getItemCount() = dataSet.size


    public interface OnPlayerInteract {
        fun onPlayerClicked(player: Player)

    }
}