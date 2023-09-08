package ml.vladmikh.projects.hotel_app.ui.booking

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ml.vladmikh.projects.hotel_app.R
import ml.vladmikh.projects.hotel_app.databinding.FragmentBookingBinding
import ml.vladmikh.projects.hotel_app.ui.adapter.TouristAdapter
import ml.vladmikh.projects.hotel_app.ui.custom_view.PhoneNumberMask
import ml.vladmikh.projects.hotel_app.ui.hotel.HotelState
import ml.vladmikh.projects.hotel_app.util.ErrorLoadingHotel


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

        binding.buttonPay.setOnClickListener {
            findNavController().navigate(R.id.action_bookingFragment_to_paidForFragment)
        }

        binding.buttonUp.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.getBooking()
        viewModel.state.observe(viewLifecycleOwner) { state ->

            binding.progressBar.visibility = if (state == BookingState.Loading) View.VISIBLE else View.GONE
            binding.scrollView.visibility = if (state is BookingState.Loaded) View.VISIBLE else View.GONE
            binding.textViewError.visibility = if (state is BookingState.Error) View.VISIBLE else View.GONE


            if (state is BookingState.Error) {

                binding.textViewError.text = when(state.error) {
                    ErrorLoadingHotel.CONNECTION_ERROR -> getString(R.string.text_error_connection_error)
                    ErrorLoadingHotel.ERROR_UNKNOWN -> getString(R.string.text_error_error_unknown)
                }
            }

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

                binding.editTextPhone.setText("+7 (***) ***-**-**")
                binding.editTextPhone.addTextChangedListener(PhoneNumberMask(binding.editTextPhone){})

                binding.editTextEmail.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
                    if (!hasFocus) {

                        if (checkEmail(binding.editTextEmail)) {
                            binding.textInputLayoutEmail.boxBackgroundColor =
                                context?.let { ContextCompat.getColor(it, R.color.light_gray) }!!
                        } else {
                            binding.textInputLayoutEmail.boxBackgroundColor = context?.let { ContextCompat.getColor(it, R.color.error_background) }!!
                        }
                    }
                }

                val adapter = TouristAdapter()
                viewModel.touristUIList.observe(viewLifecycleOwner) { tourists ->
                    adapter.submitList(tourists)

                }
                binding.recyclerViewTourist.adapter = adapter

                binding.addTourist.setOnClickListener {
                    viewModel.addTouristUI()
                }

                binding.textViewTourValue.text = getString(R.string.price, booking.tour_price.toString())
                binding.textViewFuelSurchargeTourValue.text = getString(R.string.price, booking.fuel_charge.toString())
                binding.textViewServiceFeeValue.text = getString(R.string.price, booking.service_charge.toString())
                binding.textViewForPaymentValue.text = getString(R.string.price,
                    (booking.tour_price + booking.service_charge + booking.service_charge).toString())
                binding.buttonPay.text = getString(R.string.button_pay,
                    (booking.tour_price + booking.service_charge + booking.service_charge).toString())

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

    //Проверка валидации Email в EditText
    private fun checkEmail(editText: EditText): Boolean {

        return !editText.text.isNullOrEmpty() && Patterns
            .EMAIL_ADDRESS.matcher(editText.text).matches()

    }

}