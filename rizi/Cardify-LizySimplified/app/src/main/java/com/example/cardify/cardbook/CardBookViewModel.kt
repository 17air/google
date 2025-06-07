package com.example.cardify.cardbook

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.cardify.models.BusinessCard

/** Simple in-memory storage for business cards. */
class CardBookViewModel : ViewModel() {
    private val _cards = mutableStateListOf<BusinessCard>()
    val cards: List<BusinessCard> = _cards

    fun addCard(card: BusinessCard) {
        _cards.add(card)
    }

    fun updateCard(index: Int, card: BusinessCard) {
        if (index in _cards.indices) {
            _cards[index] = card
        }
    }
}
