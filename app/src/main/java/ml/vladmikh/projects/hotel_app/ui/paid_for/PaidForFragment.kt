package ml.vladmikh.projects.hotel_app.ui.paid_for

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ml.vladmikh.projects.hotel_app.R
import ml.vladmikh.projects.hotel_app.databinding.FragmentHotelRoomBinding
import ml.vladmikh.projects.hotel_app.databinding.FragmentPaidForBinding
import ml.vladmikh.projects.hotel_app.ui.adapter.RoomAdapter
import ml.vladmikh.projects.hotel_app.ui.hotel_room.HotelRoomState
import ml.vladmikh.projects.hotel_app.ui.hotel_room.HotelRoomViewModel

@AndroidEntryPoint
class PaidForFragment : Fragment() {

    private val viewModel by viewModels<PaidForViewModel>()
    private lateinit var binding: FragmentPaidForBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPaidForBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.textViewDescription.text = getString(R.string.paid_for_description, viewModel.orderNumber.toString())
        binding.buttonUp.setOnClickListener{
            findNavController().navigateUp()
        }

    }

}