package com.example.mysportsapp.registration.presentation

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mysportsapp.MainActivity
import com.example.mysportsapp.R
import com.example.mysportsapp.databinding.FragmentSignUpBinding
import com.example.mysportsapp.registration.data.UserDataModel
import com.example.mysportsapp.registration.data.UserRepositoryImpl
import com.example.mysportsapp.registration.domain.SignUpUserUseCase
import com.example.mysportsapp.registration.presentation.viewModel.SignUpViewModel


class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        setUi()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewModel()

        binding.signUpBtn.setOnClickListener {
            if (!areFieldsEmpty()) {
                registerUser()
            } else {
                Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_LONG)
                    .show()
            }
        }

        binding.backButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

    }

    private fun registerUser() {
        val userDataModel = UserDataModel(getEmail(), getPassword(), getUsername())

        viewModel.registerUser(userDataModel, {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }, { error ->
            Toast.makeText(requireContext(), "Error: $error", Toast.LENGTH_LONG).show()
        })
    }

    private fun initializeViewModel() {
        val userRepository = UserRepositoryImpl()
        val registerUserUseCase = SignUpUserUseCase(userRepository)
        viewModel = SignUpViewModel(registerUserUseCase)
    }

    private fun setUi() {
        setEmailEditTextSettings()
        setUserNameEditTextSettings()
        setPasswordEditTextSettings()
    }

    private fun areFieldsEmpty(): Boolean {
        return getEmail().isEmpty() || getUsername().isEmpty() || getPassword().isEmpty()
    }

    private fun setPasswordEditTextSettings() {
        with(binding.passwordLinear) {
            setImageResource(R.drawable.password_icon)
            setHint(resources.getString(R.string.password_edit_text_hint))
            setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)
        }
    }

    private fun setUserNameEditTextSettings() {
        with(binding.usernameLinear) {
            setImageResource(R.drawable.username_icon)
            setHint(resources.getString(R.string.username_edit_text_hint))
            setInputType(InputType.TYPE_CLASS_TEXT)
        }
    }

    private fun setEmailEditTextSettings() {
        with(binding.emailLinear) {
            setImageResource(R.drawable.email_icon)
            setHint(resources.getString(R.string.email_edit_text_hint))
            setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
        }
    }

    private fun getEmail(): String = binding.emailLinear.getText()
    private fun getUsername(): String = binding.usernameLinear.getText()
    private fun getPassword(): String = binding.passwordLinear.getText()

    companion object {
        fun newInstance() = SignUpFragment()
    }
}
