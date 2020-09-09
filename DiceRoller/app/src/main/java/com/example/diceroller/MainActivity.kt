package com.example.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button;
import android.view.View;
import android.widget.Toast;
import android.widget.ImageView;

class MainActivity : AppCompatActivity() {

    lateinit private var diceImage: ImageView;
    private var dice: Dice = Dice(6);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);
        diceImage = findViewById(R.id.dice_image);
        val rollButton: Button = findViewById(R.id.roll_button);
        rollButton.setOnClickListener(fun(v: View) {
            Toast.makeText(this, "Dice rolled", Toast.LENGTH_SHORT).show();
            rollButton();
        });

        /*
        Outras formas de fazer o callback do OnClickListener:

        // lambda com argumento
        rollButton.setOnClickListener({v: View -> Unit
            Toast.makeText(this, "Button clicked", Toast.LENGTH_SHORT).show();
        });
         */

        /*
        // lambda com argumento implícito (it)
        rollButton.setOnClickListener {
            val t: Toast = Toast.makeText(this, "Button Clicked", Toast.LENGTH_SHORT);
            t.show();
        }
         */
    }

    private fun rollButton() {
        val rollResult: Int = dice.roll();
        val diceImageDrawable: Int = when(rollResult) {
            1 -> R.drawable.dice_1;
            2 -> R.drawable.dice_2;
            3 -> R.drawable.dice_3;
            4 -> R.drawable.dice_4;
            5 -> R.drawable.dice_5;
            else -> R.drawable.dice_6;
        };
        diceImage.setImageResource(diceImageDrawable);
    }
}

class Dice(val faces: Int) {
    private var facesRange: IntRange = (1..faces);
    public fun roll(): Int {
        return facesRange.random();
    }
}