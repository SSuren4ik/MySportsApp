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
import com.example.mysportsapp.databinding.FragmentLoginBinding
import com.example.mysportsapp.registration.data.UserDataModel
import com.example.mysportsapp.registration.data.UserRepositoryImpl
import com.example.mysportsapp.registration.domain.LoginUserUseCase
import com.example.mysportsapp.registration.presentation.viewModel.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        setUi()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViewModel()

        binding.goToRegisterActivtityTv.setOnClickListener {
            val signUpFragment = SignUpFragment.newInstance()

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, signUpFragment)
                .addToBackStack(null)
                .commit()
        }

        binding.loginBtn.setOnClickListener {
            if (!areFieldsEmpty()) {
                loginUser()
            } else {
                Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun initializeViewModel() {
        val userRepository = UserRepositoryImpl()
        val loginUserUseCase = LoginUserUseCase(userRepository)
        viewModel = LoginViewModel(loginUserUseCase)
    }

    private fun setUi() {
        setEmailEditTextSettings()
        setPasswordEditTextSettings()
    }

    private fun areFieldsEmpty(): Boolean {
        return getEmail().isEmpty() || getPassword().isEmpty()
    }

    private fun loginUser() {
        val userDataModel = UserDataModel(getEmail(), getPassword(), "")

        viewModel.loginUser(userDataModel, {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }, { error ->
            Toast.makeText(requireContext(), "Error: $error", Toast.LENGTH_LONG).show()
        })
    }


    private fun setPasswordEditTextSettings() {
        with(binding.passwordLinear) {
            setImageResource(R.drawable.password_icon)
            setHint(resources.getString(R.string.password_edit_text_hint))
            setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)
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
    private fun getPassword(): String = binding.passwordLinear.getText()


    companion object {
        fun newInstance() = LoginFragment()
    }
}