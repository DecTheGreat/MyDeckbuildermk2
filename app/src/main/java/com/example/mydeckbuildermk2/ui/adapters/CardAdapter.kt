package com.example.mydeckbuildermk2.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mydeckbuildermk2.R
import com.example.mydeckbuildermk2.databinding.FragmentDecklistCardBinding
import com.example.mydeckbuildermk2.ui.adapters.CardAdapter.MainHolder
import com.example.mydeckbuildermk2.ui.models.CardModel
import com.example.mydeckbuildermk2.ui.models.CardStore

class CardAdapter constructor(private var cards: List<CardModel>)
    : RecyclerView.Adapter<MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardAdapter.MainHolder {
        val binding = FragmentDecklistCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val card = cards[holder.adapterPosition]
        holder.bind(card)
    }
    override fun getItemCount(): Int = cards.size

    inner class MainHolder(val binding : FragmentDecklistCardBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(card: CardModel) {
            binding.quantity.text = card.quantity.toString()
            binding.cardName.text = card.name
//            binding.cardType.text = card.type

        }
    }
}


