package com.solofirebettelroyal.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solofirebettelroyal.auth.AuthManager
import com.solofirebettelroyal.ui.admin.AdminPanelActivity
import com.solofirebettelroyal.ui.game.GameActivity
import com.solofirebettelroyal.ui.theme.SoloFireTheme

class MainMenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SoloFireTheme {
                MainMenuScreen(
                    onStartGame = { mode ->
                        val intent = Intent(this, GameActivity::class.java)
                        intent.putExtra("GAME_MODE", mode)
                        startActivity(intent)
                    },
                    onOpenAdmin = {
                        startActivity(Intent(this, AdminPanelActivity::class.java))
                    }
                )
            }
        }
    }
}

@Composable
fun MainMenuScreen(onStartGame: (String) -> Unit, onOpenAdmin: () -> Unit) {
    val isAdmin = AuthManager.isAdmin()
    val user = AuthManager.getCurrentUser()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Welcome, ${user?.username}", fontSize = 20.sp)
            Text(text = "Game Modes", fontSize = 24.sp, modifier = Modifier.padding(bottom = 24.dp))

            Button(onClick = { onStartGame("SOLO") }, modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                Text("Solo")
            }
            Button(onClick = { onStartGame("DUO") }, modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                Text("Duo")
            }
            Button(onClick = { onStartGame("SQUAD") }, modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                Text("4 Player Team")
            }

            if (isAdmin) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { onOpenAdmin() },
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Text("Admin Panel")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = { /* Navigate to Character Selection */ }, modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                Text("Select Character")
            }
        }
    }
}
