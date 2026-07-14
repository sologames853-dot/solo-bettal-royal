package com.solofirebettelroyal.data

import com.solofirebettelroyal.model.GameCharacter

object GameAssets {
    val characters = listOf(
        GameCharacter("1", "Alok", "Healing Heartbeat", "models/alok.glb", 1.2f, 200),
        GameCharacter("2", "Chrono", "Time Turner", "models/chrono.glb", 1.1f, 200),
        GameCharacter("3", "Kelly", "Dash", "models/kelly.glb", 1.5f, 180),
        GameCharacter("4", "Andrew", "Armor Specialist", "models/andrew.glb", 1.0f, 220)
    )

    val maps = listOf(
        GameMap("1", "Bermuda", "Classic Map with Rivers and Hills", "models/bermuda.glb"),
        GameMap("2", "Purgatory", "Mountainous terrain", "models/purgatory.glb"),
        GameMap("3", "Kalahari", "Desert Map", "models/kalahari.glb"),
        GameMap("4", "Alpine", "Snowy Map", "models/alpine.glb")
    )

    val items = listOf(
        GameItem("1", "AK-47", "Assault Rifle", ItemType.GUN),
        GameItem("2", "M1887", "Shotgun", ItemType.GUN),
        GameItem("3", "Frag Grenade", "Explosive", ItemType.GRENADE),
        GameItem("4", "Gloo Wall", "Instant Cover", ItemType.GLOOWALL),
        GameItem("5", "Smoke Grenade", "Tactical", ItemType.GRENADE)
    )
}

data class GameMap(
    val id: String,
    val name: String,
    val description: String,
    val modelPath: String
)

data class GameItem(
    val id: String,
    val name: String,
    val description: String,
    val type: ItemType
)

enum class ItemType {
    GUN, GRENADE, GLOOWALL, MELEE
}
