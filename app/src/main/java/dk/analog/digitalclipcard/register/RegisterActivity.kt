package dk.analog.digitalclipcard.register

import android.content.Intent
import android.os.Bundle
import dk.analog.digitalclipcard.R
import dk.analog.digitalclipcard.backend.ApiErrorResponse
import dk.analog.digitalclipcard.backend.ApiSuccessResponse
import dk.analog.digitalclipcard.base.BaseActivity
import dk.analog.digitalclipcard.login.LoginEmailActivity
import dk.analog.digitalclipcard.login.LoginRepository
import dk.analog.digitalclipcard.utils.StringUtils.sha256
import dk.analog.digitalclipcard.utils.showToast
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {

    override fun getLayoutResourceId() = R.layout.activity_register

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        registerButton.setOnClickListener {
            LoginRepository.register(
                    enterEmailField.text.toString(),
                    enterNameField.text.toString(),
                    sha256(enterPinField.text.toString())
            ) {
                when (it) {
                    is ApiSuccessResponse -> {
                        Intent(this, LoginEmailActivity::class.java)
                                .apply {
                                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                    startActivity(this)
                                }
                    }
                    is ApiErrorResponse -> {
                        showToast(it.errorMessage)
                    }
                }
            }
        }
    }
}
