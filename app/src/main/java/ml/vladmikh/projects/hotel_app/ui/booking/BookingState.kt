package ml.vladmikh.projects.hotel_app.ui.booking

import ml.vladmikh.projects.hotel_app.data.network.model.Booking
import ml.vladmikh.projects.hotel_app.data.network.model.Room
import ml.vladmikh.projects.hotel_app.util.ErrorLoadingHotel

//Интерфейс состояния экрана для фрагмента BookingFragment, используется чтобы скрывать или отображать
//разные view в зависимости от результат загрузки данных
sealed interface BookingState {

    object Initial: BookingState
    object Loading : BookingState
    data class Loaded(val booking: Booking) : BookingState
    data class Error(val error: ErrorLoadingHotel) : BookingState
}