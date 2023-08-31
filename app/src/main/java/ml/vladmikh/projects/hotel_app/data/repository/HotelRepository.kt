package ml.vladmikh.projects.hotel_app.data.repository

import ml.vladmikh.projects.hotel_app.data.network.ApiService
import javax.inject.Inject

class HotelRepository @Inject constructor(private val apiSevice: ApiService){

    suspend fun getHotel() = apiSevice.getHotel()
}