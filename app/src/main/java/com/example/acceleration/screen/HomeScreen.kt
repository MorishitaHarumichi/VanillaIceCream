package com.example.acceleration.screen

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.acceleration.ui.theme.AccelerationTheme

class HomeScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AccelerationTheme {
                HomeScreenContent(
                    onNavigateToFlashScreen = {
                        val intent = Intent(this, FlashScreen::class.java)
                        startActivity(intent)
                    },
                    onNavigateToCharacterScreen = {
                        val intent = Intent(this, CharacterScreen::class.java)
                        startActivity(intent)
                    }
                )
            }
        }
    }

    @Composable
    fun HomeScreenContent(onNavigateToFlashScreen: () -> Unit, onNavigateToCharacterScreen: () -> Unit) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Button(
                onClick = onNavigateToFlashScreen,
                modifier = Modifier
                    .padding(16.dp) // ボタンに余白を追加
            ) {
                Text("FlashScreenに遷移")
            }

            Button(
                onClick = onNavigateToCharacterScreen,
                modifier = Modifier
                    .padding(16.dp) // ボタンに余白を追加
            ) {
                Text("CharacterScreenに遷移")
            }
        }
    }
}
