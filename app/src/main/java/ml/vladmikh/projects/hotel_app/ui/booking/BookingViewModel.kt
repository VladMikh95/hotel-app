package ml.vladmikh.projects.hotel_app.ui.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ml.vladmikh.projects.hotel_app.data.repository.BookingRepository
import ml.vladmikh.projects.hotel_app.ui.model.TouristUI
import ml.vladmikh.projects.hotel_app.util.ErrorLoadingHotel
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor (private val repository : BookingRepository): ViewModel() {

    private var _state = MutableLiveData<BookingState>().apply {
        value = BookingState.Initial
    }

    private var _phoneNumber = MutableLiveData<String>()
    private val phoneNumber: LiveData<String> get() = _phoneNumber


    private val _touristUIList = MutableLiveData<ArrayList<TouristUI>>()
    val touristUIList: LiveData<ArrayList<TouristUI>> get() = _touristUIList

    val state: LiveData<BookingState> get() = _state

    init {
        val tourists = ArrayList<TouristUI>()
        tourists.add(TouristUI( true, "", "", "", "", "", ""))
        _touristUIList.value = tourists
    }

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

    fun addTouristUI() {
        var tourists = _touristUIList.value
        var newTourists  = ArrayList<TouristUI>()

        if (tourists != null) {
            for (tourist in tourists) {
                newTourists.add(tourist)
            }
        }

        newTourists.add(TouristUI(true, "", "", "", "", "", ""))
        _touristUIList.value = newTourists
    }

}