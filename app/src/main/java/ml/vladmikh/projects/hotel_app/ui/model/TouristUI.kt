package ml.vladmikh.projects.hotel_app.ui.model

//Сласс для хранения информации нужной для отображение в cardview
data class TouristUI(
    var isOpen: Boolean,
    var name : String,
    var surname: String,
    var birthday: String,
    var citizenship: String,
    var passportNumber: String,
    var passportValidity: String
)
