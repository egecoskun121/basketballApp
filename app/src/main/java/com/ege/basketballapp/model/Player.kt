package com.ege.basketballapp.model

data class Player(
    var id: Int,
    val first_name: String,
    val last_name: String,
    val position: String,
    val height_feet: Int,
    val weight_pounds: Int,
    val team: Team,

)
