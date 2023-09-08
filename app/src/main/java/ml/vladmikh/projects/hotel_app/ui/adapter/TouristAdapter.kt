package ml.vladmikh.projects.hotel_app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ml.vladmikh.projects.hotel_app.R
import ml.vladmikh.projects.hotel_app.databinding.HotelRoomItemBinding
import ml.vladmikh.projects.hotel_app.databinding.TouristCardviewBinding
import ml.vladmikh.projects.hotel_app.ui.model.TouristUI

class TouristAdapter(): ListAdapter<TouristUI, TouristAdapter.TouristViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<TouristUI>() {
            override fun areItemsTheSame(oldItem: TouristUI, newItem: TouristUI): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: TouristUI, newItem: TouristUI): Boolean {
                return oldItem.isOpen == newItem.isOpen
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TouristViewHolder {
        val viewHolder = TouristViewHolder(
            TouristCardviewBinding.inflate(
                LayoutInflater.from( parent.context),
                parent,
                false
            )
        )
        return viewHolder
    }

    override fun onBindViewHolder(holder: TouristViewHolder, position: Int) {

        holder.title.text = when(position) {
            0 -> holder.title.context.getString(R.string.tourist_1)
            1 -> holder.title.context.getString(R.string.tourist_2)
            2 -> holder.title.context.getString(R.string.tourist_3)
            3 -> holder.title.context.getString(R.string.tourist_4)
            else -> holder.title.context.getString(R.string.tourist_5)
        }

        holder.bind(getItem(position))
        if (getItem(position).isOpen) {
            holder.button.setImageDrawable(holder.button.context.getDrawable(R.drawable.arrow_up))
        } else {
            holder.button.setImageDrawable(holder.button.context.getDrawable(R.drawable.arrow_down))
        }

        holder.button.setOnClickListener {
            if (getItem(position).isOpen) {
                holder.button.setImageDrawable(holder.button.context.getDrawable(R.drawable.arrow_down))
                getItem(position).isOpen = false
                holder.touristLayout.visibility = View.GONE
            } else {
                holder.button.setImageDrawable(holder.button.context.getDrawable(R.drawable.arrow_up))
                getItem(position).isOpen = true
                holder.touristLayout.visibility = View.VISIBLE
            }
        }
    }

    class TouristViewHolder(private var binding: TouristCardviewBinding): RecyclerView.ViewHolder(binding.root) {

        val touristLayout = binding.touristLayout
        val title = binding.tittle
        val button = binding.button
        val name = binding.editTextName

        fun bind(tourist: TouristUI) {

        }


    }
}