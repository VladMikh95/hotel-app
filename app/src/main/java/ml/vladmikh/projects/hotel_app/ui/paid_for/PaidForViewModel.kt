package ml.vladmikh.projects.hotel_app.ui.paid_for

import androidx.lifecycle.ViewModel


class PaidForViewModel: ViewModel() {
    val orderNumber = (0..1000000).random()

}