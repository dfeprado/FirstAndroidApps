package com.example.aboutme

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View;
import android.view.inputmethod.InputMethodManager
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import androidx.databinding.DataBindingUtil
import com.example.aboutme.databinding.ActivityMainBinding
import com.example.aboutme.MyName;

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding;
    private val myName = MyName("Daniel Ferraz do Prado");

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.doneButton.setOnClickListener{
            setNickname(it);
        }

        binding.myName = myName;
        //setContentView(R.layout.activity_main);
        //findViewById<Button>(R.id.done_button).setOnClickListener {
        //    setNickname(it);
        //};
    }

    fun setNickname(view: View) {
        //val edit: EditText = findViewById(R.id.nickname_edit);
        //val nickname: TextView = findViewById(R.id.nickname_text);

        binding.apply {
            myName?.nickname = nicknameEdit.text.toString();
            nicknameText.visibility = View.VISIBLE;
            nicknameEdit.visibility = View.GONE;
            doneButton.visibility = View.GONE;
            invalidateAll();
        }

        // hides the keyboard
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager;
        imm.hideSoftInputFromWindow(view.windowToken, 0);
    }
}