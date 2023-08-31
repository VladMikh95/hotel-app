package ml.vladmikh.projects.hotel_app.ui

import ml.vladmikh.projects.hotel_app.data.network.model.Hotel
import ml.vladmikh.projects.hotel_app.util.ErrorLoadingHotel


//Интерфейс состояния экрана для фрагмента HotelFragment, используется чтобы скрывать или отображать
//разные view в зависимости от результат загрузки данных
sealed interface HotelState {

    object Initial: HotelState
    object Loading : HotelState
    data class Loaded(val hotel: Hotel) : HotelState
    data class Error(val error: ErrorLoadingHotel) : HotelState
}