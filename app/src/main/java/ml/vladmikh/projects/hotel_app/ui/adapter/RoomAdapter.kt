package ml.vladmikh.projects.hotel_app.ui.adapter

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayoutMediator
import ml.vladmikh.projects.hotel_app.R
import ml.vladmikh.projects.hotel_app.data.network.model.Room
import ml.vladmikh.projects.hotel_app.databinding.HotelRoomItemBinding

class RoomAdapter() : ListAdapter<Room, RoomAdapter.RoomViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Room>() {
            override fun areItemsTheSame(oldRoom: Room, newRoom: Room): Boolean {
                return oldRoom == newRoom
            }

            override fun areContentsTheSame(oldRoom: Room, newRoom: Room): Boolean {
                return oldRoom.id == newRoom.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val viewHolder = RoomViewHolder(
            HotelRoomItemBinding.inflate(
                LayoutInflater.from( parent.context),
                parent,
                false
            )
        )
        return viewHolder
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {

        for(peculiarity in getItem(position).peculiarities) {
            val chip = Chip(holder.chipGroup.context).apply {
                text = peculiarity
                background = resources.getDrawable(R.drawable.chip_drawable)
                setTextColor(resources.getColor(R.color.gray))

            }
            holder.chipGroup.addView(chip)
        }

        holder.priceTextView.text =
            holder.priceTextView.context.getString(R.string.price_room_text_view, getItem(position).price.toString())

        holder.bind(getItem(position))
    }

    class RoomViewHolder(private var binding: HotelRoomItemBinding): RecyclerView.ViewHolder(binding.root) {

        val chipGroup = binding.peculiaritiesChipGroup
        val priceTextView = binding.priceTextView
        fun bind(room: Room) {

            val adapter = ViewPagerAdapter()
            binding.viewPager.adapter = adapter

            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            }.attach()
            adapter.submitList(room.image_urls)

            binding.roomNameTextView.text = room.name
            binding.priceForTextView.text = room.price_per

        }
    }
}