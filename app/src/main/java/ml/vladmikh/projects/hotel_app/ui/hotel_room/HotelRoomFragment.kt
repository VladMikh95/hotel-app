package ml.vladmikh.projects.hotel_app.ui.hotel_room

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ml.vladmikh.projects.hotel_app.R
import ml.vladmikh.projects.hotel_app.databinding.FragmentHotelRoomBinding
import ml.vladmikh.projects.hotel_app.ui.adapter.RoomAdapter
import ml.vladmikh.projects.hotel_app.util.ErrorLoadingHotel


@AndroidEntryPoint
class HotelRoomFragment : Fragment() {

    private val viewModel by viewModels<HotelRoomViewModel>()
    private lateinit var binding: FragmentHotelRoomBinding
    private val args: HotelRoomFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHotelRoomBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val adapter = RoomAdapter{
            //Так как в фрагменте бронирования используются загрузочные данны одинаковые, то
            //не нужно использовать данные комнаты которой выбрали из RecyclerViewAdapter
            findNavController().navigate(R.id.action_hotelRoomFragment_to_bookingFragment)
        }

        binding.title.text = args.hotelName

        binding.buttonUp.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.getHotelRooms()
        viewModel.state.observe(viewLifecycleOwner) { state ->

            binding.progressBar.visibility = if (state == HotelRoomState.Loading) View.VISIBLE else View.GONE
            binding.recyclerViewRooms.visibility = if (state is HotelRoomState.Loaded) View.VISIBLE else View.GONE
            binding.textViewError.visibility = if (state is HotelRoomState.Error) View.VISIBLE else View.GONE


            if (state is HotelRoomState.Error) {

                binding.textViewError.text = when(state.error) {
                    ErrorLoadingHotel.CONNECTION_ERROR -> getString(R.string.text_error_connection_error)
                    ErrorLoadingHotel.ERROR_UNKNOWN -> getString(R.string.text_error_error_unknown)
                }
            }

            if (state is HotelRoomState.Loaded) {

                Log.i("abc", state.rooms.toString())

                binding.recyclerViewRooms.adapter = adapter

                adapter.submitList(state.rooms)

            }
        }
    }

}