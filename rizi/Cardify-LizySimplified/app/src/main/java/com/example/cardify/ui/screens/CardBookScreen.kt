package com.example.cardify.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.cardify.R
import com.example.cardify.cardbook.CardBookViewModel
import com.example.cardify.models.BusinessCard
import com.example.cardify.ui.components.BottomNavBar

@Composable
fun CardBookScreen(
    navController: NavController,
    viewModel: CardBookViewModel,
    onEdit: (Int, BusinessCard) -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomNavBar(
                currentRoute = "cardbook",
                onNavigateToMain = { navController.popBackStack() },
                onNavigateToCardBook = {},
                onNavigateToSettings = {}
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            itemsIndexed(viewModel.cards) { index, card ->
                CardRow(card = card, onEdit = { onEdit(index, card) })
            }
        }
    }
}

@Composable
private fun CardRow(card: BusinessCard, onEdit: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (card.imageUrl.isNotEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(card.imageUrl),
                contentDescription = null,
                modifier = Modifier.size(56.dp),
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier.size(56.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(card.name, style = MaterialTheme.typography.bodyLarge)
            Text(card.phone, style = MaterialTheme.typography.bodyMedium)
            Text(card.email, style = MaterialTheme.typography.bodyMedium)
        }
        IconButton(onClick = onEdit) {
            Icon(Icons.Default.Edit, contentDescription = "edit", tint = Color.Gray)
        }
    }
}
