package com.example.cardify.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cardify.cardbook.CardBookViewModel
import com.example.cardify.models.BusinessCard
import com.example.cardify.navigation.Screen
import com.example.cardify.ocr.OcrHelper
import com.example.cardify.utils.ImageUtils

@Composable
fun OcrResultScreen(
    navController: NavController,
    viewModel: CardBookViewModel,
    bitmap: Bitmap
) {
    var loading by remember { mutableStateOf(true) }
    var result by remember { mutableStateOf(OcrHelper.Result()) }

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var company by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }
    var etc by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        result = OcrHelper.recognize(navController.context, bitmap)
        name = result.name
        phone = result.phone
        email = result.email
        company = result.company
        position = result.position
        etc = result.etc
        loading = false
    }

    Scaffold {
        if (loading) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("분석 중...")
            }
        } else {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("이름") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("전화번호") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("이메일") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = company, onValueChange = { company = it }, label = { Text("회사") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = position, onValueChange = { position = it }, label = { Text("직책") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = etc, onValueChange = { etc = it }, label = { Text("기타") }, modifier = Modifier.fillMaxWidth())
                Button(
                    onClick = {
                        val imageBase64 = try {
                            ImageUtils.bitmapToBase64(bitmap)
                        } catch (e: Exception) {
                            ""
                        }
                        viewModel.addCard(
                            BusinessCard(
                                name = name,
                                phone = phone,
                                email = email,
                                company = company,
                                position = position,
                                sns = etc,
                                imageUrl = imageBase64
                            )
                        )
                        com.example.cardify.ocr.CapturedImageHolder.bitmap = null
                        navController.popBackStack(Screen.Main.route, false)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("저장")
                }
            }
        }
    }
}
