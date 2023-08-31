package ml.vladmikh.projects.hotel_app.data.network

import ml.vladmikh.projects.hotel_app.data.network.model.Hotel
import retrofit2.http.GET

interface ApiService {

    @GET("v3/35e0d18e-2521-4f1b-a575-f0fe366f66e3")
    suspend fun getHotel(): Hotel
}