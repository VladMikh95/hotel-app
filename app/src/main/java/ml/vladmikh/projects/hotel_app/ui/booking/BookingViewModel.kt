package ml.vladmikh.projects.hotel_app.ui.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ml.vladmikh.projects.hotel_app.data.repository.BookingRepository
import ml.vladmikh.projects.hotel_app.util.ErrorLoadingHotel
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor (private val repository : BookingRepository): ViewModel() {

    private var _state = MutableLiveData<BookingState>().apply {
        value = BookingState.Initial
    }

    val state: LiveData<BookingState> get() = _state

    fun getBooking() {
        viewModelScope.launch {

            _state.value = BookingState.Loading

            try {
                val booking = repository.getBooking()
                _state.value = BookingState.Loaded(booking)
            } catch (e: IOException) {
                _state.value = BookingState.Error(ErrorLoadingHotel.CONNECTION_ERROR)
            } catch (e: HttpException) {
                _state.value = BookingState.Error(ErrorLoadingHotel.ERROR_UNKNOWN)

            }
        }
    }
}