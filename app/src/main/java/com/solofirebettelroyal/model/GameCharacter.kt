package com.solofirebettelroyal.model

data class GameCharacter(
    val id: String,
    val name: String,
    val description: String,
    val modelUrl: String, // URL to download the 3D model
    val speed: Float,
    val health: Int
)
