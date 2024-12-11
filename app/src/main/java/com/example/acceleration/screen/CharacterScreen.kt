package com.example.acceleration.screen

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import com.example.acceleration.R
import com.example.acceleration.ui.theme.AccelerationTheme

class CharacterScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AccelerationTheme {
                CharacterScreenContent()
            }
        }
    }

    @Composable
    fun CharacterScreenContent() {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // 戻るボタン（左上に配置）
            IconButton(
                onClick = {
                    val intent = Intent(this@CharacterScreen, HomeScreen::class.java)
                    startActivity(intent)
                    finish()
                },
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "戻る")
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
            ) {
                Image(painter = painterResource(id = R.drawable.character1), contentDescription = "Character 1")
                Image(painter = painterResource(id = R.drawable.character2), contentDescription = "Character 2")
                Image(painter = painterResource(id = R.drawable.character3), contentDescription = "Character 3")
            }
        }
    }
}
