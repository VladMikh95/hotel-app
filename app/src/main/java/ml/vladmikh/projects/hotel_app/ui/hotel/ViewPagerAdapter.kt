package ml.vladmikh.projects.hotel_app.ui.hotel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import coil.load
import ml.vladmikh.projects.hotel_app.databinding.ImageItemBinding

class ViewPagerAdapter() : ListAdapter<String, ViewPagerAdapter.ViewPagerViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldImage: String, newImage: String): Boolean {
                return oldImage == newImage
            }

            override fun areContentsTheSame(oldImage: String, newImage: String): Boolean {
                return oldImage == newImage
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val viewHolder = ViewPagerViewHolder(
            ImageItemBinding.inflate(
                LayoutInflater.from( parent.context),
                parent,
                false
            )
        )
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {

        holder.bind(getItem(position))
    }

    class ViewPagerViewHolder(private var binding: ImageItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(image: String) {


            binding.imageView.load(image.toUri().buildUpon().scheme("https").build())


        }
    }
}