package dk.analog.digitalclipcard.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dk.analog.digitalclipcard.login.EMAIL_LOGIN
import dk.analog.digitalclipcard.login.LoginMainActivity
import dk.analog.digitalclipcard.start.StartActivity
import dk.analog.digitalclipcard.utils.getStoredEmail

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val email = getStoredEmail()
        val intent = if (email.isNotEmpty()) {
            Intent(this, LoginMainActivity::class.java).apply {
                putExtra(EMAIL_LOGIN, email)
            }
        } else {
            Intent(this, StartActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}
