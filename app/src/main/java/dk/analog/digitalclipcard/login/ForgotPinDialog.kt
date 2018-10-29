package dk.analog.digitalclipcard.login

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import dk.analog.digitalclipcard.R
import dk.analog.digitalclipcard.utils.EmailUtils
import dk.analog.digitalclipcard.utils.makeToast
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
            if (EmailUtils.isValidEmail(email)) {
                // TODO: Send forgot password
                dismiss()
            } else {
                context.makeToast(R.string.enterValidEmail)
            }
        }
    }
}
