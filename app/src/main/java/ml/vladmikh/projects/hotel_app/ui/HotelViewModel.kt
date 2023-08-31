package ml.vladmikh.projects.hotel_app.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ml.vladmikh.projects.hotel_app.data.repository.HotelRepository
import ml.vladmikh.projects.hotel_app.util.ErrorLoadingHotel
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class HotelViewModel @Inject constructor(private val repository: HotelRepository): ViewModel() {

    private var _state = MutableLiveData<HotelState>().apply {
        value = HotelState.Initial
    }

    val state: LiveData<HotelState> get() = _state

    fun getHotel() {
        viewModelScope.launch {

            _state.value = HotelState.Loading

            try {
                val hotel = repository.getHotel()
                _state.value = HotelState.Loaded(hotel)
            } catch (e: IOException) {
                _state.value = HotelState.Error(ErrorLoadingHotel.CONNECTION_ERROR)
            } catch (e: HttpException) {
                _state.value = HotelState.Error(ErrorLoadingHotel.ERROR_UNKNOWN)

            }
        }
    }
}