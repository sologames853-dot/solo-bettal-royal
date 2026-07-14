package com.solofirebettelroyal.ui.game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.solofirebettelroyal.ui.theme.SoloFireTheme
import io.github.sceneview.SceneView
import io.github.sceneview.node.ModelNode
import kotlinx.coroutines.launch

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mode = intent.getStringExtra("GAME_MODE") ?: "SOLO"
        
        setContent {
            SoloFireTheme {
                GameScreen(mode)
            }
        }
    }
}

@Composable
fun GameScreen(mode: String) {
    val scope = rememberCoroutineScope()
    
    Box(modifier = Modifier.fillMaxSize()) {
        // --- 3D World (Integrating SceneView) ---
        AndroidView(
            factory = { context ->
                SceneView(context).apply {
                    val modelNode = ModelNode(engine)
                    // Asynchronously load the 3D model (Map/Character)
                    scope.launch {
                        modelNode.loadModelGlb(context, "models/bermuda.glb")
                    }
                    addChild(modelNode)
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // Overlay text for development
        Column(modifier = Modifier.align(Alignment.TopCenter).padding(top = 50.dp)) {
            Text("3D Map: Bermuda (Active)", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text("Mode: $mode", color = Color.Yellow, fontSize = 14.sp)
        }

        // --- Free Fire Style HUD (Heads-Up Display) ---
        
        // Mini-Map
        Surface(
            modifier = Modifier.size(100.dp).padding(8.dp).align(Alignment.TopStart),
            color = Color.Black.copy(alpha = 0.5f)
        ) {
            Text("MAP", color = Color.Yellow, modifier = Modifier.padding(4.dp), fontSize = 10.sp)
        }

        // Player Health & Armor
        Column(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LinearProgressIndicator(
                progress = 0.8f,
                modifier = Modifier.width(200.dp).height(8.dp),
                color = Color.Green,
                trackColor = Color.Gray
            )
            Text("HP 200 / 200", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }

        // Weapons & Ammo
        Surface(
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
            color = Color.Black.copy(alpha = 0.5f)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text("AK-47", color = Color.White, fontWeight = FontWeight.Bold)
                Text("Ammo: 30 / 120", color = Color.Yellow)
            }
        }

        // Controls
        Row(
            modifier = Modifier.align(Alignment.BottomStart).padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { /* Prone/Crouch */ }) { Text("C") }
            Button(onClick = { /* Jump */ }) { Text("J") }
        }

        // Fire Button (Large)
        IconButton(
            onClick = { /* Shoot */ },
            modifier = Modifier.align(Alignment.CenterEnd).padding(end = 40.dp).size(80.dp)
        ) {
            Surface(shape = androidx.compose.foundation.shape.CircleShape, color = Color.Red.copy(alpha = 0.7f)) {
                Box(contentAlignment = Alignment.Center) {
                    Text("FIRE", color = Color.White, fontWeight = FontWeight.ExtraBold)
                }
            }
        }
    }
}
