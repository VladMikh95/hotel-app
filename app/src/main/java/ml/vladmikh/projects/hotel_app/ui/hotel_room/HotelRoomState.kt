package ml.vladmikh.projects.hotel_app.ui.hotel_room

import ml.vladmikh.projects.hotel_app.data.network.model.Room
import ml.vladmikh.projects.hotel_app.util.ErrorLoadingHotel

//Интерфейс состояния экрана для фрагмента HotelRoomFragment, используется чтобы скрывать или отображать
//разные view в зависимости от результат загрузки данных
sealed interface HotelRoomState {

    object Initial: HotelRoomState
    object Loading : HotelRoomState
    data class Loaded(val rooms: List<Room>) : HotelRoomState
    data class Error(val error: ErrorLoadingHotel) : HotelRoomState
}