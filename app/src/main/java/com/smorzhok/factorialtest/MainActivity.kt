package com.smorzhok.factorialtest

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.smorzhok.factorialtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observeViewModel()
        binding.buttonCalculate.setOnClickListener {
            viewModel.calculate(binding.editTextNumber.text.toString())
        }

    }

    private fun observeViewModel() {
        viewModel.state.observe(this) {
            binding.buttonCalculate.isEnabled = true
            binding.progressBarLoading.visibility = View.GONE
            when (it) {
                is Error -> {
                    Toast.makeText(
                        this,
                        "You did not enter the value",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is Loading -> {
                    binding.progressBarLoading.visibility = View.VISIBLE
                    binding.buttonCalculate.isEnabled = false
                }

                is Factorial -> {
                    binding.textViewFactorial.text = it.factorial
                }
            }
        }
    }
}