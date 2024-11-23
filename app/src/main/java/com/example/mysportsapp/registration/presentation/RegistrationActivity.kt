package com.example.mysportsapp.registration.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mysportsapp.R
import com.example.mysportsapp.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = LoginFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()
    }
}