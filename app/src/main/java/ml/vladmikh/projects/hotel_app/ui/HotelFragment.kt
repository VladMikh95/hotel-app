package ml.vladmikh.projects.hotel_app.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginTop
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import ml.vladmikh.projects.hotel_app.R
import ml.vladmikh.projects.hotel_app.databinding.FragmentHotelBinding


@AndroidEntryPoint
class HotelFragment : Fragment() {

    private val viewModel by viewModels<HotelViewModel>()
    private lateinit var binding: FragmentHotelBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHotelBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getHotel()
        viewModel.state.observe(viewLifecycleOwner) { state ->


            if (state is HotelState.Loaded) {

                val hotel = state.hotel
                binding.ratingTextView.text = hotel.rating.toString()
                binding.ratingNameTextView.text = hotel.rating_name.toString()
                binding.hotelNameTextView.text = hotel.name
                binding.addressTextView.text = hotel.adress
                binding.priceTextView.text =
                    getString(R.string.price_text_view, hotel.minimal_price.toString())
                binding.priceForItTextView.text = hotel.price_for_it
                binding.descriptionTextView.text = hotel.about_the_hotel.description

                for(peculiarity in hotel.about_the_hotel.peculiarities) {
                    val chip = Chip(context).apply {
                        text = peculiarity
                        background = resources.getDrawable(R.drawable.chip_drawable)
                        setTextColor(resources.getColor(R.color.gray))

                    }
                    binding.peculiaritiesChipGroup.addView(chip)
                }
            }
        }
    }

}