package com.example.sportsoft.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsoft.Listener
import com.example.sportsoft.models.EventModel
import com.example.sportsoft.databinding.GoalEventRecyclerviewItemBinding
import com.example.sportsoft.databinding.RedCardEventRecyclerviewItemBinding
import com.example.sportsoft.databinding.YellowCardEventRecyclerviewItemBinding

class EventsRecyclerAdapter(private val listener: Listener<EventModel>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var eventsList : List<EventModel>
    class GoalEventViewHolder(item: View): RecyclerView.ViewHolder(item){
        private val binding = GoalEventRecyclerviewItemBinding.bind(item)
        fun bind(eventModel: EventModel, listener: Listener<EventModel>) = with(binding){

        }
    }

    class YellowCardEventViewHolder(item: View): RecyclerView.ViewHolder(item){
        private val binding = YellowCardEventRecyclerviewItemBinding.bind(item)
        fun bind(eventModel: EventModel, listener: Listener<EventModel>) = with(binding){

        }
    }

    class RedCardEventViewHolder(item: View): RecyclerView.ViewHolder(item){
        private val binding = RedCardEventRecyclerviewItemBinding.bind(item)
        fun bind(eventModel: EventModel, listener: Listener<EventModel>) = with(binding){

        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }



    override fun getItemCount(): Int {
        return eventsList.size
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }



    fun setEventList(list: List<EventModel>){
        eventsList = list
    }
}
