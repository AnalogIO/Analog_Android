package dk.analog.digitalclipcard.login

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import dk.analog.digitalclipcard.R
import dk.analog.digitalclipcard.utils.StringUtils
import dk.analog.digitalclipcard.utils.showToast
import kotlinx.android.synthetic.main.dialog_forgot_pin.*

class ForgotPinDialog @JvmOverloads constructor(context: Context, themeResId: Int = 0) :
        AlertDialog(context, themeResId) {

    var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_forgot_pin)

        emailField.setText(email)

        setupListeners()
    }

    private fun setupListeners() {
        cancelButton.setOnClickListener {
            dismiss()
        }
        sendButton.setOnClickListener {
            val email = emailField.text.toString()
            if (StringUtils.isValidEmail(email)) {
                // TODO: Send forgot password
                dismiss()
            } else {
                context.showToast(R.string.enterValidEmail)
            }
        }
    }
}
