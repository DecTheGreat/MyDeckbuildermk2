package com.example.mydeckbuildermk2.ui.models

import android.util.Log
import com.example.mydeckbuildermk2.databinding.FragmentDecklistBinding


var lastId = 0L

internal fun getId(): Long {
    return lastId++
}
 class CardMemStore : CardStore
{
    val cards = ArrayList<CardModel>()


    override fun findAll(): List<CardModel> {
        return cards
    }

    override fun findByName(name: String): CardModel? {
        return cards.find { card -> card.name == name}
    }



    override fun create(card: CardModel) {
        cards.add(card)
        Log.i("CREATE", cards.toString())
    }

     fun increace(card: CardModel, num: Int){
        var additions = num
         card.quantity + additions
    }


    fun addOne(card: CardModel)
    {card.quantity++}



    fun decreace(card: CardModel) {
        card.quantity--
        if (card.quantity == 0)
            remove(card)
    }

     fun remove(card: CardModel)
    {
       var dex = cards.indexOf(card)
        cards.removeAt(dex)

    }

}