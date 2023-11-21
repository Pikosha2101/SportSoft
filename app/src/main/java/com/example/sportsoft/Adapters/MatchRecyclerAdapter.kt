package com.example.sportsoft.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsoft.Listener
import com.example.sportsoft.Models.MatchModel
import com.example.sportsoft.R
import com.example.sportsoft.databinding.MatchRegisterRecyclerItemBinding

class MatchRecyclerAdapter(private val listener : Listener<MatchModel>) :
    RecyclerView.Adapter<MatchRecyclerAdapter.Holder>(){

    private var matchModels: List<MatchModel> = listOf()

    class Holder(item: View) : RecyclerView.ViewHolder(item)
    {
        private val binding = MatchRegisterRecyclerItemBinding.bind(item)

        fun bind(matchModel: MatchModel, listener: Listener<MatchModel>) = with(binding) {
            dateTextView.text = matchModel.date.toString()
            firstTeamNameTextView.text = matchModel.firstTeamName.toString()
            secondTeamNameTextView.text = matchModel.secondTeamName.toString()
            firstTeamScoreTextView.text = matchModel.firstTeamScore.toString()
            secondTeamScoreTextView.text = matchModel.secondTeamScore.toString()
            openImageButton.setOnClickListener {
                listener.onClickOpen(matchModel)
            }
            editImageButton.setOnClickListener {
                listener.onClickEdit(matchModel)
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(inflater.inflate(R.layout.match_register_recycler_item, parent, false))
    }

    override fun onBindViewHolder(holder: MatchRecyclerAdapter.Holder, position: Int) {
        holder.bind(
            matchModels[position], listener
        )
    }

    override fun getItemCount(): Int {
        return matchModels.size
    }


    fun setList(list:List<MatchModel>)
    {
        matchModels = list
    }
}