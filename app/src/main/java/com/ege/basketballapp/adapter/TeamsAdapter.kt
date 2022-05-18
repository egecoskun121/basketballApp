package com.ege.basketballapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ege.basketballapp.R
import com.ege.basketballapp.model.Team


class TeamsAdapter(
    private val dataSet: ArrayList<Team>,
    private val listner: OnTeamInteract
) :
    RecyclerView.Adapter<TeamsAdapter.ViewHolder>() {
    private var context: Context? = null


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val full_name: TextView
        val division: TextView
        val city: TextView


        init {
            full_name = view.findViewById(R.id.playerName)
            division = view.findViewById(R.id.playerPosition)
            city = view.findViewById(R.id.playerHeight)

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
        viewHolder.city.setText(player.city)


        viewHolder.full_name.text = player.full_name
        viewHolder.division.text = player.division
        viewHolder.itemView.setOnClickListener { listner.onShowDetails(player) }
    }

    public interface OnTeamInteract {
        fun onShowDetails(team: Team)

    }

    override fun getItemCount() = dataSet.size


}