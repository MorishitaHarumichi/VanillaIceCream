package com.example.acceleration.screen

import android.content.Context.MODE_PRIVATE
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.example.acceleration.R
import com.example.acceleration.ui.theme.AccelerationTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

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

        val scrollState = rememberScrollState()

        Box(modifier = Modifier.fillMaxSize()) {

            // 画像を2列に並べてスクロールできるようにする
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState) // スクロール可能にする
                    .padding(0.dp) // Columnの周囲の余白を追加
            ) {
                val imageModifier = Modifier
                    .aspectRatio(0.5f) // 正方形にする　サイズの変更
                    .padding(0.dp) // 画像間の余白を均等に設定

                // 2つの画像を横に並べるRow
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(-7.dp), // 画像間の間隔
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.kao1),
                        contentDescription = "Character face_tracking",
                        modifier = imageModifier.then(Modifier.weight(1f)).clickable {
                            selectedImageId = R.drawable.kao1
                            selectedSoundId = null
                            selectedVideoId = R.raw.kao1v
                            showDialog.value = true
                            confirmAction.value = {
                                saveSelectedContent(selectedImageId, selectedSoundId, selectedVideoId, 0)
                                showDialog.value = false
                            }
                        }
                    )

                    Image(
                        painter = painterResource(id = R.drawable.kao2),
                        contentDescription = "Character face_tracking",
                        modifier = imageModifier.then(Modifier.weight(1f)).clickable {
                            selectedImageId = R.drawable.kao2
                            selectedSoundId = null
                            selectedVideoId = R.raw.kao2v
                            showDialog.value = true
                            confirmAction.value = {
                                saveSelectedContent(selectedImageId, selectedSoundId, selectedVideoId, 0)
                                showDialog.value = false
                            }
                        }
                    )
                }

                // もう一組の2つの画像を横に並べるRow
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(-7.dp), // 画像間の間隔
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.kao3),
                        contentDescription = "Character face_tracking",
                        modifier = imageModifier.then(Modifier.weight(1f)).clickable {
                            selectedImageId = R.drawable.kao3
                            selectedSoundId = null
                            selectedVideoId = R.raw.kao3v
                            showDialog.value = true
                            confirmAction.value = {
                                saveSelectedContent(selectedImageId, selectedSoundId, selectedVideoId, 0)
                                showDialog.value = false
                            }
                        }
                    )

                    Image(
                        painter = painterResource(id = R.drawable.kao4),
                        contentDescription = "Character face_tracking",
                        modifier = imageModifier.then(Modifier.weight(1f)).clickable {
                            selectedImageId = R.drawable.kao4
                            selectedSoundId = null
                            selectedVideoId = R.raw.kao4v
                            showDialog.value = true
                            confirmAction.value = {
                                saveSelectedContent(selectedImageId, selectedSoundId, selectedVideoId, 0)
                                showDialog.value = false
                            }
                        }
                    )
                }
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




    // 画像ID、音声ID、動画ID、バイブレーションの周期を保存する関数
    private fun saveSelectedContent(imageId: Int?, soundId: Int?, videoId: Int?, vibrationDuration: Long) {
        val sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt("selectedImage", imageId ?: R.drawable.character1) // デフォルト画像
        editor.putInt("selectedSound", soundId ?: R.raw.one_up) // デフォルト音声
        editor.putInt("selectedVideo", videoId ?: -1) // デフォルト動画
        editor.putLong("vibrationDuration", vibrationDuration) //バイブレーション処理
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
