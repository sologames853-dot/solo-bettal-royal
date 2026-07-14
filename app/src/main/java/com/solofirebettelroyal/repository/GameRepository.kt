package com.solofirebettelroyal.repository

import com.solofirebettelroyal.data.GameAssets
import com.solofirebettelroyal.data.GameItem
import com.solofirebettelroyal.model.GameCharacter
import com.solofirebettelroyal.network.GameEvent
import com.solofirebettelroyal.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object GameRepository {
    private val _characters = mutableListOf<GameCharacter>().apply { addAll(GameAssets.characters) }
    private val _events = mutableListOf<GameEvent>()
    private val _items = mutableListOf<GameItem>().apply { addAll(GameAssets.items) }

    // Fetch from MongoDB via API
    suspend fun refreshDataFromApi() = withContext(Dispatchers.IO) {
        try {
            val apiCharacters = RetrofitClient.instance.getCharacters()
            _characters.clear()
            _characters.addAll(apiCharacters)
            
            val apiEvents = RetrofitClient.instance.getEvents()
            _events.clear()
            _events.addAll(apiEvents)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getCharacters(): List<GameCharacter> = _characters
    fun getEvents(): List<GameEvent> = _events
    fun getItems(): List<GameItem> = _items

    suspend fun addCharacter(character: GameCharacter) = withContext(Dispatchers.IO) {
        try {
            RetrofitClient.instance.addCharacter(character)
            _characters.add(character)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun addEvent(event: GameEvent) = withContext(Dispatchers.IO) {
        try {
            RetrofitClient.instance.addEvent(event)
            _events.add(event)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addItem(item: GameItem) { _items.add(item) }
}
