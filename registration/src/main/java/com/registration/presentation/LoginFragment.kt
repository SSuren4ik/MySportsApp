package com.registration.presentation

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.registration.R
import com.registration.data.UserRepositoryImpl
import com.registration.databinding.FragmentLoginBinding
import com.registration.domain.usecase.LoginUserUseCase
import com.registration.presentation.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

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
//            viewModel.loginUser(getEmail(), getPassword())
            viewModel.loginUser("Test@mail.ru", "Test123")
        }

        lifecycleScope.launch {
            errorMessage()
        }
        lifecycleScope.launch {
            onLoginSuccess()
        }
    }

    private suspend fun onLoginSuccess() {
        viewModel.onLoginSuccess.collect {
            (activity as RegistrationActivity).onRegistrationComplete()
        }
    }

    private suspend fun errorMessage() {
        viewModel.errorMessage.collect { errorMessage ->
            showToast(errorMessage)
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
    private fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG)
            .show()
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}