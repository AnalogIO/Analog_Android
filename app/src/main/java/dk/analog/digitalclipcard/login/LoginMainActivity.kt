package dk.analog.digitalclipcard.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import dk.analog.digitalclipcard.NumericKeyboard.CustomKeyboardListener
import dk.analog.digitalclipcard.NumericKeyboard.NumericKeyboardFragment
import dk.analog.digitalclipcard.R
import dk.analog.digitalclipcard.base.BaseActivity
import dk.analog.digitalclipcard.register.RegisterActivity
import dk.analog.digitalclipcard.utils.IntentUtils
import kotlinx.android.synthetic.main.activity_login_main.*
import kotlinx.android.synthetic.main.top_login_screen.*

const val EMAIL_LOGIN = "EMAIL_LOGIN"

class LoginMainActivity : BaseActivity(), CustomKeyboardListener {

    private var password: String = ""

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_login_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val email = intent.getStringExtra(EMAIL_LOGIN)
        loginEmail.text = email
        insertNumericKeyboard()
        updatePasswordBoxes(password)
        setupListeners()
    }

    private fun insertNumericKeyboard() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.numericKeyboardContainer, NumericKeyboardFragment())
                .commit()
    }

    private fun setupListeners() {
        helpButton.setOnClickListener(this::showPopup)
        registerUserButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun showPopup(v: View) {
        val popup = PopupMenu(this, v)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.createUserMenuItem -> {
                    startActivity(Intent(this, RegisterActivity::class.java))
                    true
                }
                R.id.forgotPinMenuItem -> {
                    showForgotPinPopup()
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
        popup.show()
    }

    private fun showForgotPinPopup() {
        val dialogView = ForgotPinDialog(this)
        dialogView.email = loginEmail.text.toString()
        dialogView.show()
    }

    override fun onCKeyEvent(key: Int) {
        if (key in 0..9) {
            password += key
            updatePasswordBoxes(password)
        } else if (key == NumericKeyboardFragment.CASE_KEY_DEL && password.isNotEmpty()) {
            password = password.substring(0, password.length - 1)
            updatePasswordBoxes(password)
        }
    }

    private fun updatePasswordBoxes(pin: String) {
        val pw1 = (passwordField1 as TextView)
        val pw2 = (passwordField2 as TextView)
        val pw3 = (passwordField3 as TextView)
        val pw4 = (passwordField4 as TextView)
        val pws = arrayOf(pw1, pw2, pw3, pw4)
        for (i in 0..3) {
            pws[i].isEnabled = i == pin.length
            if (i < pin.length) {
                pws[i].text = "*"
            } else {
                pws[i].text = ""
            }
        }
    }
}
