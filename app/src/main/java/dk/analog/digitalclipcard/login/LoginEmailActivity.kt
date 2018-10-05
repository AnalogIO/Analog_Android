package dk.analog.digitalclipcard.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.widget.PopupMenu
import dk.analog.digitalclipcard.R
import dk.analog.digitalclipcard.base.BaseActivity
import dk.analog.digitalclipcard.register.RegisterActivity
import dk.analog.digitalclipcard.utils.EmailUtils
import dk.analog.digitalclipcard.utils.IntentUtils
import dk.analog.digitalclipcard.utils.makeToast
import kotlinx.android.synthetic.main.activity_login_email.*
import kotlinx.android.synthetic.main.top_login_screen.*

class LoginEmailActivity : BaseActivity() {

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_login_email
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        continueButton.isEnabled = emailField.text.isNotEmpty()

        setupListeners()
    }

    private fun setupListeners() {
        emailField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                continueButton.isEnabled = emailField.text.toString().trim().isNotEmpty()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        continueButton.setOnClickListener {
            val email = emailField.text.toString()
            if (EmailUtils.isValidEmail(email)) {
                val intent = Intent(this, LoginMainActivity::class.java)
                intent.putExtra(EMAIL_LOGIN, email)
                startActivity(intent)
            } else {
                makeToast(R.string.enterValidEmail)
            }
        }

        registerUserButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        helpButton.setOnClickListener(this::showPopup)
    }

    private fun showPopup(v: View) {
        val popup = PopupMenu(this, v)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.createUserMenuItem -> {
                    startActivity(Intent(this, RegisterActivity::class.java))
                    true
                }
                R.id.reportErrorMenuItem -> {
                    IntentUtils.sendErrorEmail(this)
                    true
                }
                else -> false
            }
        }
        popup.inflate(R.menu.help_menu_popup)
        popup.menu.findItem(R.id.forgotPinMenuItem).isVisible = false
        popup.show()
    }
}
