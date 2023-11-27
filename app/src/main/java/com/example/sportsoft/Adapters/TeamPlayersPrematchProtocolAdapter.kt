package com.example.sportsoft.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsoft.Models.PlayerModel
import com.example.sportsoft.R
import com.example.sportsoft.databinding.PlayerRecyclerItemBinding
import com.squareup.picasso.Picasso

class TeamPlayersPrematchProtocolAdapter() :
    RecyclerView.Adapter<TeamPlayersPrematchProtocolAdapter.Holder>(){

    private var playerModels: List<PlayerModel> = listOf()

    class Holder(item: View) : RecyclerView.ViewHolder(item)
    {
        private val binding = PlayerRecyclerItemBinding.bind(item)

        fun bind(playerModel: PlayerModel) = with(binding) {
            numberPlayerTextView.text = playerModel.number.toString()
            Picasso.get().
            load(playerModel.photo).
            into(photoPlayerImageView)
            namePlayerTextView.text = playerModel.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(inflater.inflate(R.layout.player_recycler_item, parent, false))
    }



    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(
            playerModels[position]
        )
    }



    override fun getItemCount(): Int {
        return playerModels.size
    }



    fun setList(list:List<PlayerModel>) {
        playerModels = list
    }

    fun getList() : List<PlayerModel> {
        return playerModels
    }
}