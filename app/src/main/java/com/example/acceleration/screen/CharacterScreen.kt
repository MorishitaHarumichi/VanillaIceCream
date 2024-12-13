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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.example.acceleration.R
import com.example.acceleration.ui.theme.AccelerationTheme

class CharacterScreen : AppCompatActivity() {

    private var selectedImageId: Int? = null
    private var selectedSoundId: Int? = null  // 音声ID
    private var selectedVideoId: Int? = null  // 動画IDを追加

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

        // SharedPreferencesから選択されたコンテンツを読み込む
        val savedImageId = remember { getSelectedImageFromPreferences() }
        val savedSoundId = remember { getSelectedSoundFromPreferences() }
        val savedVideoId = remember { getSelectedVideoFromPreferences() }

        // LaunchedEffectで選択された設定を即時反映
        LaunchedEffect(savedImageId, savedSoundId, savedVideoId) {
            selectedImageId = savedImageId
            selectedSoundId = savedSoundId
            selectedVideoId = savedVideoId
        }

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
                // 画像と音声のセット
                Image(
                    painter = painterResource(id = R.drawable.character1), // CharacterScreenで表示される画像
                    contentDescription = "Character 1",
                    modifier = Modifier.clickable {
                        selectedImageId = R.drawable.character1 //画像
                        selectedSoundId = R.raw.a_n //音声
                        selectedVideoId = null // 動画(このイメージでは使用しない)
                        showDialog.value = true
                        confirmAction.value = {
                            saveSelectedContent(selectedImageId, selectedSoundId, selectedVideoId)
                            showDialog.value = false
                        }
                    }
                )

                Image(
                    painter = painterResource(id = R.drawable.character2),
                    contentDescription = "Character 2",
                    modifier = Modifier.clickable {
                        selectedImageId = R.drawable.character2
                        selectedSoundId = R.raw.deeeen
                        selectedVideoId = null
                        showDialog.value = true
                        confirmAction.value = {
                            saveSelectedContent(selectedImageId, selectedSoundId, selectedVideoId)
                            showDialog.value = false
                        }
                    }
                )

                Image(
                    painter = painterResource(id = R.drawable.character3),
                    contentDescription = "Character 3",
                    modifier = Modifier.clickable {
                        selectedImageId = R.drawable.character3
                        selectedSoundId = R.raw.one_up
                        selectedVideoId = null
                        showDialog.value = true
                        confirmAction.value = {
                            saveSelectedContent(selectedImageId, selectedSoundId, selectedVideoId)
                            showDialog.value = false
                        }
                    }
                )
                //動画の例
                Image(
                    painter = painterResource(id = R.drawable.face_tracking),
                    contentDescription = "Character face_tracking",
                    modifier = Modifier.clickable {
                        selectedImageId = R.drawable.face_tracking
                        selectedSoundId = null
                        selectedVideoId = R.raw.face_traking
                        showDialog.value = true
                        confirmAction.value = {
                            saveSelectedContent(selectedImageId, selectedSoundId, selectedVideoId)
                            showDialog.value = false
                        }
                    }
                )
            }

            // 確認ダイアログ
            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { showDialog.value = false },
                    title = { androidx.compose.material3.Text("この画像にしますか？") },
                    dismissButton = {
                        Button(onClick = confirmAction.value) {
                            androidx.compose.material3.Text("はい")
                        }
                    },
                    confirmButton = {
                        Button(onClick = { showDialog.value = false }) {
                            androidx.compose.material3.Text("いいえ")
                        }
                    }
                )
            }
        }
    }

    // 画像ID、音声ID、動画IDを保存する関数
    private fun saveSelectedContent(imageId: Int?, soundId: Int?, videoId: Int?) {
        val sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt("selectedImage", imageId ?: R.drawable.character1) // デフォルト画像
        editor.putInt("selectedSound", soundId ?: R.raw.one_up) // デフォルト音声
        editor.putInt("selectedVideo", videoId ?: -1) // デフォルト動画
        editor.apply()
    }

    // SharedPreferencesから画像IDを取得
    private fun getSelectedImageFromPreferences(): Int {
        val sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        return sharedPref.getInt("selectedImage", R.drawable.character1)
    }

    // SharedPreferencesから音声IDを取得
    private fun getSelectedSoundFromPreferences(): Int {
        val sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        return sharedPref.getInt("selectedSound", R.raw.one_up)
    }

    // SharedPreferencesから動画IDを取得
    private fun getSelectedVideoFromPreferences(): Int {
        val sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        return sharedPref.getInt("selectedVideo", -1)
    }
}
