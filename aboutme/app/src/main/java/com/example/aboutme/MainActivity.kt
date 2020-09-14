package com.example.aboutme
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View;
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import com.example.aboutme.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding;
    private val myName = MyName("Daniel Ferraz do Prado");

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.myName = myName;
        binding.doneButton.setOnClickListener{
            setNickname(it);
        }
    }

    fun setNickname(view: View) {
        binding.apply {
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