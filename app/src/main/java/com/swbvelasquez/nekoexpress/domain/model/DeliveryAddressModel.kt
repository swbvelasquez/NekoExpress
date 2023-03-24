package com.swbvelasquez.nekoexpress.domain.model

data class DeliveryAddressModel(
    val department:String,
    val province:String,
    val district:String,
    val address:String,
    val phone:String
)