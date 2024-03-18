package com.example.memory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {

    private lateinit var buttons: List<ImageButton>
    private lateinit var cards: List<MemoryCard>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val images = mutableListOf(
            R.drawable.fire,
            R.drawable.flash,
            R.drawable.rollerskates,
            R.drawable.motorcycle
        )

        images.addAll(images)
        images.shuffle()

        buttons = listOf(
            findViewById(R.id.imageButton1),
            findViewById(R.id.imageButton2),
            findViewById(R.id.imageButton3),
            findViewById(R.id.imageButton4),
            findViewById(R.id.imageButton5),
            findViewById(R.id.imageButton6),
            findViewById(R.id.imageButton7),
            findViewById(R.id.imageButton8)
        )

        cards = buttons.indices.map { idx ->
            MemoryCard(images[idx])
        }

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                updateViews()
                updateModels(index)

                val card = cards[index]
                if (!card.isFaceUp) {
                    button.setImageResource(images[index])
                    card.isFaceUp = true
                }

                checkWin()
            }
        }
    }

    private fun updateViews() {
        if (cards.filter { e -> e.isFaceUp && !e.isMatched }.size >= 2) {
            cards.forEachIndexed { index, card ->
                if (card.isFaceUp && !card.isMatched) {
                    buttons[index].setImageResource(R.drawable.ic_android)
                    card.isFaceUp = false
                }
            }
        }
    }

    private fun updateModels(idx: Int) {
        cards.forEachIndexed { index, card ->
            if (card.isFaceUp && card.id == cards[idx].id && idx != index) {
                card.isMatched = true
                cards[idx].isMatched = true
            }
        }
    }

    private fun checkWin(){
        var won = true
        for(card in cards){
            if(!card.isFaceUp){
                won = false
                break
            }
        }
        if(won){
            setContentView(R.layout.win)
        }
    }
}
