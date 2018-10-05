package dk.analog.digitalclipcard.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dk.analog.digitalclipcard.start.StartActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Start either StartActivity or LoginEmailActivity depending on if an e-mail is stored
        startActivity(Intent(this, StartActivity::class.java))
    }
}
