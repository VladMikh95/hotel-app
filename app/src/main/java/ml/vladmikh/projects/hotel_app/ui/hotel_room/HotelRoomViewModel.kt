package ml.vladmikh.projects.hotel_app.ui.hotel_room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ml.vladmikh.projects.hotel_app.data.repository.RoomRepository
import ml.vladmikh.projects.hotel_app.util.ErrorLoadingHotel
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HotelRoomViewModel @Inject constructor(private val repository: RoomRepository): ViewModel() {

    private var _state = MutableLiveData<HotelRoomState>().apply {
        value = HotelRoomState.Initial
    }

    val state: LiveData<HotelRoomState> get() = _state

    fun getHotelRooms() {
        viewModelScope.launch {

            _state.value = HotelRoomState.Loading

            try {
                val hotelRooms = repository.getHotelRoom()
                _state.value = HotelRoomState.Loaded(hotelRooms.rooms)
            } catch (e: IOException) {
                _state.value = HotelRoomState.Error(ErrorLoadingHotel.CONNECTION_ERROR)
            } catch (e: HttpException) {
                _state.value = HotelRoomState.Error(ErrorLoadingHotel.ERROR_UNKNOWN)

            }
        }
    }
}