package ml.vladmikh.projects.hotel_app.data.repository

import ml.vladmikh.projects.hotel_app.data.network.ApiService
import javax.inject.Inject

class BookingRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getBooking()  =apiService.getBooking()
}