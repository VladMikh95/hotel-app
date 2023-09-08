package ml.vladmikh.projects.hotel_app.ui.hotel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import ml.vladmikh.projects.hotel_app.R
import ml.vladmikh.projects.hotel_app.databinding.FragmentHotelBinding
import ml.vladmikh.projects.hotel_app.ui.adapter.ViewPagerAdapter
import ml.vladmikh.projects.hotel_app.util.ErrorLoadingHotel


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

        val adapter = ViewPagerAdapter()

        viewModel.getHotel()
        viewModel.state.observe(viewLifecycleOwner) { state ->

            binding.progressBar.visibility = if (state == HotelState.Loading) View.VISIBLE else View.GONE
            binding.scrollView.visibility = if (state is HotelState.Loaded) View.VISIBLE else View.GONE
            binding.textViewError.visibility = if (state is HotelState.Error) View.VISIBLE else View.GONE


            if (state is HotelState.Error) {

                binding.textViewError.text = when(state.error) {
                    ErrorLoadingHotel.CONNECTION_ERROR -> getString(R.string.text_error_connection_error)
                    ErrorLoadingHotel.ERROR_UNKNOWN -> getString(R.string.text_error_error_unknown)
                }
            }

            if (state is HotelState.Loaded) {

                val hotel = state.hotel
                binding.viewPager.adapter = adapter

                TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                }.attach()
                adapter.submitList(hotel.image_urls)

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

                binding.buttonSelectHotelRoom.setOnClickListener {
                    val action = HotelFragmentDirections.actionHotelFragmentToHotelRoomFragment(hotel.name)
                    findNavController().navigate(action)
                }
            }
        }
    }

}