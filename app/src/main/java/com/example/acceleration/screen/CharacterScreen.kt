package com.example.acceleration.screen

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Alignment
import android.content.SharedPreferences
import com.example.acceleration.R
import com.example.acceleration.ui.theme.AccelerationTheme

class CharacterScreen : AppCompatActivity() {

    private var selectedImageId: Int? = null

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
        val showDialog = remember { mutableStateOf(false) }
        val confirmAction = remember { mutableStateOf({}) }

        Box(modifier = Modifier.fillMaxSize()) {
            // 戻るボタン（左上に配置）
            IconButton(
                onClick = {
                    val intent = Intent(this@CharacterScreen, HomeScreen::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    finish()
                },
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "戻る")
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // 画像をタップ可能にし、選択された画像のIDを記録
                Image(
                    painter = painterResource(id = R.drawable.character1),
                    contentDescription = "Character 1",
                    modifier = Modifier.clickable {
                        selectedImageId = R.drawable.character1
                        showDialog.value = true
                        confirmAction.value = {
                            // 「はい」を選択した場合、画像IDだけを保存
                            saveSelectedImage(selectedImageId)
                            showDialog.value = false
                        }
                    }
                )
                Image(
                    painter = painterResource(id = R.drawable.character2),
                    contentDescription = "Character 2",
                    modifier = Modifier.clickable {
                        selectedImageId = R.drawable.character2
                        showDialog.value = true
                        confirmAction.value = {
                            // 「はい」を選択した場合、画像IDだけを保存
                            saveSelectedImage(selectedImageId)
                            showDialog.value = false
                        }
                    }
                )
                Image(
                    painter = painterResource(id = R.drawable.character3),
                    contentDescription = "Character 3",
                    modifier = Modifier.clickable {
                        selectedImageId = R.drawable.character3
                        showDialog.value = true
                        confirmAction.value = {
                            // 「はい」を選択した場合、画像IDだけを保存
                            saveSelectedImage(selectedImageId)
                            showDialog.value = false
                        }
                    }
                )
            }

            // ダイアログを表示
            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { showDialog.value = false },
                    title = { androidx.compose.material3.Text("この画像にしますか？") },
                    confirmButton = {
                        Button(onClick = {
                            // 「はい」を選択した場合、画像IDを保存してダイアログを閉じる
                            confirmAction.value.invoke()
                        }) {
                            androidx.compose.material3.Text("はい")
                        }
                    },
                    dismissButton = {
                        Button(onClick = {
                            // 「いいえ」を選択した場合、ダイアログを閉じる
                            showDialog.value = false
                        }) {
                            androidx.compose.material3.Text("いいえ")
                        }
                    }
                )
            }
        }
    }

    // 画像IDを保存する関数
    private fun saveSelectedImage(imageId: Int?) {
        val sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt("selectedImage", imageId ?: R.drawable.character1)  // デフォルト値を指定
        editor.apply()
    }
}
