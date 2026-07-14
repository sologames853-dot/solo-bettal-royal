package com.solofirebettelroyal.ui.admin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solofirebettelroyal.model.GameCharacter
import com.solofirebettelroyal.network.GameEvent
import com.solofirebettelroyal.repository.GameRepository
import com.solofirebettelroyal.ui.theme.SoloFireTheme
import kotlinx.coroutines.launch

class AdminPanelActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SoloFireTheme {
                AdminDashboard()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboard() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Characters", "Events", "Items")

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Solo Fire Admin Panel") })
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(selected = selectedTab == index, onClick = { selectedTab = index }, text = { Text(title) })
                }
            }

            when (selectedTab) {
                0 -> CharacterManager()
                1 -> EventManager()
                2 -> ItemManager()
            }
        }
    }
}

@Composable
fun CharacterManager() {
    val characters = remember { mutableStateOf(GameRepository.getCharacters()) }
    val scope = rememberCoroutineScope()
    
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = {
            scope.launch {
                val newChar = GameCharacter(
                    id = "${characters.value.size + 1}",
                    name = "New Hero",
                    description = "Custom Hero",
                    modelUrl = "",
                    speed = 1.0f,
                    health = 200
                )
                GameRepository.addCharacter(newChar)
                characters.value = GameRepository.getCharacters()
            }
        }) {
            Text("Add Character")
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(characters.value) { char ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("${char.name} (HP: ${char.health})")
                        Button(onClick = { /* Edit */ }) { Text("Edit") }
                    }
                }
            }
        }
    }
}

@Composable
fun EventManager() {
    val events = remember { mutableStateOf(GameRepository.getEvents()) }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = {
            scope.launch {
                val newEvent = GameEvent(
                    id = "${events.value.size + 1}",
                    title = "New Event ${events.value.size + 1}",
                    description = "Big Battle Event",
                    imageUrl = "",
                    expiryDate = System.currentTimeMillis() + 86400000
                )
                GameRepository.addEvent(newEvent)
                events.value = GameRepository.getEvents()
            }
        }) {
            Text("Push New Event")
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(events.value) { event ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(event.title, fontWeight = FontWeight.Bold)
                        Text(event.description)
                    }
                }
            }
        }
    }
}

@Composable
fun ItemManager() {
    val items = remember { mutableStateOf(GameRepository.getItems()) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Guns, Grenades & GlooWalls", modifier = Modifier.padding(bottom = 8.dp), fontWeight = FontWeight.Bold)
        
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items.value) { item ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text(item.name, fontWeight = FontWeight.Bold)
                            Text(item.type.name, fontSize = 12.sp)
                        }
                        Button(onClick = { /* Remove or Edit */ }) { Text("Edit") }
                    }
                }
            }
        }
    }
}
