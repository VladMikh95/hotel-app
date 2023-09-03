package ml.vladmikh.projects.hotel_app.ui.hotel_room

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ml.vladmikh.projects.hotel_app.databinding.FragmentHotelRoomBinding
import ml.vladmikh.projects.hotel_app.ui.adapter.RoomAdapter


@AndroidEntryPoint
class HotelRoomFragment : Fragment() {

    private val viewModel by viewModels<HotelRoomViewModel>()
    private lateinit var binding: FragmentHotelRoomBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHotelRoomBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val adapter = RoomAdapter()

        viewModel.getHotelRooms()
        viewModel.state.observe(viewLifecycleOwner) { state ->


            if (state is HotelRoomState.Loaded) {

                Log.i("abc", state.rooms.toString())

                binding.recyclerViewRooms.adapter = adapter

                adapter.submitList(state.rooms)

            }
        }
    }

}