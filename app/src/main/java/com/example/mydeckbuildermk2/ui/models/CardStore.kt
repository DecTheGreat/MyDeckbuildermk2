package com.example.mydeckbuildermk2.ui.models

interface CardStore {
    fun findAll() :List<CardModel>
    fun findByName(name: String) : CardModel?
    fun create(card: CardModel)
}
