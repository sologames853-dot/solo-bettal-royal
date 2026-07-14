package com.solofirebettelroyal.ui.login

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
import com.solofirebettelroyal.model.LoginType
import com.solofirebettelroyal.ui.main.MainMenuActivity
import com.solofirebettelroyal.ui.theme.SoloFireTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SoloFireTheme {
                LoginScreen(
                    onLoginSuccess = { type, name ->
                        AuthManager.login(type, name)
                        val intent = Intent(this, MainMenuActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                )
            }
        }
    }
}

@Composable
fun LoginScreen(onLoginSuccess: (LoginType, String) -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Solo Fire Battle Royal", fontSize = 28.sp, modifier = Modifier.padding(bottom = 32.dp))

            Button(
                onClick = { onLoginSuccess(LoginType.GOOGLE, "GoogleUser") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text(text = "Login with Gmail")
            }

            Button(
                onClick = { onLoginSuccess(LoginType.FACEBOOK, "FBUser") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text(text = "Login with Facebook")
            }

            OutlinedButton(
                onClick = { onLoginSuccess(LoginType.GUEST, "Guest") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text(text = "Guest Login")
            }

            TextButton(onClick = { onLoginSuccess(LoginType.GUEST, "admin") }) {
                Text("Login as Admin (Secret)")
            }
        }
    }
}
