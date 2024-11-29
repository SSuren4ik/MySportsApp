package mysportsapp.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.mysportsapp.R
import com.registration.presentation.RegistrationActivity
import mysportsapp.main.MainActivity

class SplashActivity : AppCompatActivity() {

    private val registrationLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val intent = Intent(this, RegistrationActivity::class.java)
        registrationLauncher.launch(intent)
    }
}