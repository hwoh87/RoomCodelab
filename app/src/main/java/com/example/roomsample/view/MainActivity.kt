package com.example.roomsample.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.roomsample.R
import com.example.roomsample.WordsApplication
import com.example.roomsample.adapter.WordListAdapter
import com.example.roomsample.data.Word
import com.example.roomsample.databinding.ActivityMainBinding
import com.example.roomsample.viewModel.WordViewModel
import com.example.roomsample.viewModel.WordViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: WordViewModel by viewModels {
        WordViewModelFactory((application as WordsApplication).repository)
    }
    private val adapter = WordListAdapter()

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.getStringExtra(NewWordActivity.EXTRA_REPLY_WORD)?.let { reply ->
                    val word = Word(reply)
                    viewModel.insert(word)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.handler = EventHandler()
        binding.lifecycleOwner = this

        setUpViews()
        setUpViewModels()
    }

    private fun setUpViews() {
        binding.recyclerview.adapter = adapter
    }

    private fun setUpViewModels() {
        viewModel.allWords.observe(this, { words ->
            words?.let { adapter.submitList(it) }
        })
    }

    inner class EventHandler {
        fun addWordButtonClicked() {
            val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            startForResult.launch(intent)
        }
    }
}