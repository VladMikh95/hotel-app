package ml.vladmikh.projects.hotel_app.ui.booking

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ml.vladmikh.projects.hotel_app.R
import ml.vladmikh.projects.hotel_app.databinding.FragmentBookingBinding

@AndroidEntryPoint
class BookingFragment : Fragment() {

    private val viewModel by viewModels<BookingViewModel>()
    private lateinit var binding: FragmentBookingBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentBookingBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        viewModel.getBooking()
        viewModel.state.observe(viewLifecycleOwner) { state ->


            if (state is BookingState.Loaded) {

                val booking = state.booking

                binding.ratingTextView.text = booking.horating.toString()
                binding.ratingNameTextView.text = booking.rating_name
                binding.hotelNameTextView.text = booking.hotel_name
                binding.addressTextView.text = booking.hotel_adress
                binding.departureValueTextView.text = booking.departure
                binding.countryValueTextView.text = booking.arrival_country
                binding.dateValueTextView.text  =
                    getString(R.string.date_value, booking.tour_date_start, booking.tour_date_stop)
                binding.numberOfNightsValueTextView.text = selectNumberOfNightsString(booking.number_of_nights)
                binding.hotelValueTextView.text = booking.hotel_name
                binding.roomValueTextView.text = booking.room
                binding.nutritionValueTextView.text = booking.nutrition
            }
        }
    }

    //Метод возвращает текст где окончание слова "Ночь" выбирается в зависимости от числа
    private fun selectNumberOfNightsString(numberOfNight: Int) : String {

        val remainder = numberOfNight % 10

        if (remainder == 1) {
            return getString(R.string.night_one, numberOfNight.toString())
        } else if (remainder == 2 || remainder == 3 || remainder == 4) {
            return getString(R.string.night_two, numberOfNight.toString())
        } else {
            return getString(R.string.night_five, numberOfNight.toString())
        }
    }

}