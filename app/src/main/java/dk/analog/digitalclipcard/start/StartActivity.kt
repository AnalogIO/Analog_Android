package dk.analog.digitalclipcard.start

import android.content.Intent
import android.os.Bundle
import dk.analog.digitalclipcard.R
import dk.analog.digitalclipcard.base.BaseActivity
import dk.analog.digitalclipcard.login.LoginEmailActivity
import dk.analog.digitalclipcard.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : BaseActivity() {

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_start;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        registerButton.setOnClickListener { startActivity(Intent(this, RegisterActivity::class.java)) }
        logInButton.setOnClickListener { startActivity(Intent(this, LoginEmailActivity::class.java)) }
    }
}