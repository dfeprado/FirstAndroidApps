package com.example.colormyviews

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.view.View;

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners();
    }

    private fun setListeners() {
        val views = listOf<View>(box_one_text, box_two_text, box_three_text, box_four_text, box_five_text, background, yellow_button, red_button, green_button);
        for (v in views) {
            v.setOnClickListener {
                colorMyView(it);
            }
        }
    }

    private fun colorMyView(view: View) {
        when (view.id) {
            R.id.box_one_text -> view.setBackgroundColor(Color.DKGRAY);
            R.id.box_two_text -> view.setBackgroundColor(Color.GRAY);
            R.id.box_three_text -> view.setBackgroundResource(android.R.color.holo_green_light);
            R.id.box_four_text -> view.setBackgroundResource(android.R.color.holo_green_dark);
            R.id.box_five_text -> view.setBackgroundResource(android.R.color.holo_green_light);
            R.id.green_button -> view.setBackgroundResource(R.color.green);
            R.id.yellow_button -> view.setBackgroundResource(R.color.yellow);
            R.id.red_button -> view.setBackgroundResource(R.color.red);
            else -> view.setBackgroundColor(Color.LTGRAY);
        }
    }
}