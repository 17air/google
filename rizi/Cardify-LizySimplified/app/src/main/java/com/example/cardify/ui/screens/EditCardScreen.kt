package com.example.cardify.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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

@Composable
fun EditCardScreen(
    navController: NavController,
    viewModel: CardBookViewModel,
    index: Int,
    card: BusinessCard
) {
    var name by remember { mutableStateOf(card.name) }
    var phone by remember { mutableStateOf(card.phone) }
    var email by remember { mutableStateOf(card.email) }
    var company by remember { mutableStateOf(card.company) }
    var position by remember { mutableStateOf(card.position) }
    var etc by remember { mutableStateOf(card.sns) }

    Scaffold { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("이름") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("전화번호") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("이메일") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = company, onValueChange = { company = it }, label = { Text("회사") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = position, onValueChange = { position = it }, label = { Text("직책") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = etc, onValueChange = { etc = it }, label = { Text("기타") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.padding(8.dp))
            Button(onClick = {
                viewModel.updateCard(index, card.copy(name = name, phone = phone, email = email, company = company, position = position, sns = etc))
                navController.popBackStack()
            }, modifier = Modifier.fillMaxWidth()) {
                Text("저장")
            }
        }
    }
}
