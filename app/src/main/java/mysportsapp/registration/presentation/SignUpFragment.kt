package mysportsapp.registration.presentation

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.mysportsapp.R
import com.example.mysportsapp.databinding.FragmentSignUpBinding
import kotlinx.coroutines.launch
import mysportsapp.MainActivity
import mysportsapp.registration.data.UserRepositoryImpl
import mysportsapp.registration.domain.SignUpUserUseCase
import mysportsapp.registration.presentation.viewmodel.SignUpViewModel

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
            viewModel.registerUser(getEmail(), getUsername(), getPassword())
        }

        binding.backButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        lifecycleScope.launch {
            onSuccessSignUp()
        }
        lifecycleScope.launch {
            errorMessage()
        }
    }

    private suspend fun errorMessage() {
        viewModel.errorMessage.collect { errorMessage ->
            showToast(errorMessage)
        }
    }

    private suspend fun onSuccessSignUp() {
        viewModel.onSignUpSuccess.collect {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }
    }

    private fun setUi() {
        setEmailEditTextSettings()
        setUserNameEditTextSettings()
        setPasswordEditTextSettings()
    }

    private fun initializeViewModel() {
        val userRepository = UserRepositoryImpl()
        val signUpUserUseCase = SignUpUserUseCase(userRepository)
        viewModel = SignUpViewModel(signUpUserUseCase)
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
    private fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG)
            .show()
    }

    companion object {
        fun newInstance() = SignUpFragment()
    }
}