package com.example.roomsample.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.roomsample.R
import com.example.roomsample.databinding.ActivityNewWordBinding

class NewWordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewWordBinding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_word)
        binding.handler = EventHandler()
        binding.lifecycleOwner = this
    }

    inner class EventHandler {
        fun saveWordButtonClicked() {
            val replyIntent = Intent()
            val wordText = binding.editWord.text

            if (wordText.isEmpty()) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val word = wordText.toString()
                replyIntent.putExtra(EXTRA_REPLY_WORD, word)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY_WORD = "REPLY_WORD"
    }

}