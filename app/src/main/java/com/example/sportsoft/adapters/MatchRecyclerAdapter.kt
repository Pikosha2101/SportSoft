package com.example.sportsoft.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsoft.api.apiModels.MatchInfo
import com.example.sportsoft.Listener
import com.example.sportsoft.R
import com.example.sportsoft.databinding.MatchRegisterRecyclerItemBinding

class MatchRecyclerAdapter(private val listener : Listener<MatchInfo>) :
    RecyclerView.Adapter<MatchRecyclerAdapter.Holder>(){

    private var matchModels: List<MatchInfo> = listOf()

    class Holder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = MatchRegisterRecyclerItemBinding.bind(item)

        fun bind(matchModel: MatchInfo, listener: Listener<MatchInfo>) = with(binding) {
            dateTextView.text = matchModel.start_dt
            firstTeamNameTextView.text = matchModel.team1_shortname
            secondTeamNameTextView.text = matchModel.team2_shortname
            if (matchModel.gf == null){
                firstTeamScoreTextView.text = "-"
            } else{
                firstTeamScoreTextView.text = matchModel.gf.toString()
            }
            if (matchModel.ga == null){
                secondTeamScoreTextView.text = "-"
            } else {
                secondTeamScoreTextView.text = matchModel.ga.toString()
            }
            openMatchProgressCardView.setOnClickListener {
                listener.onClickStart(matchModel)
            }
            openPrematchProtocolCardView.setOnClickListener {
                listener.onClickEdit(matchModel)
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(inflater.inflate(R.layout.match_register_recycler_item, parent, false))
    }



    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(
            matchModels[position], listener
        )
    }



    override fun getItemCount(): Int {
        return matchModels.size
    }



    @SuppressLint("NotifyDataSetChanged")
    fun setList(list:List<MatchInfo>) {
        matchModels = list
        notifyDataSetChanged()
    }
}