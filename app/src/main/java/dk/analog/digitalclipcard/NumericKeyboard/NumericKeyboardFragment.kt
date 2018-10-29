package dk.analog.digitalclipcard.NumericKeyboard

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dk.analog.digitalclipcard.R
import kotlinx.android.synthetic.main.numeric_keyboard.*

class NumericKeyboardFragment : Fragment() {

    companion object {
        const val CASE_KEY_0 = 0
        const val CASE_KEY_1 = 1
        const val CASE_KEY_2 = 2
        const val CASE_KEY_3 = 3
        const val CASE_KEY_4 = 4
        const val CASE_KEY_5 = 5
        const val CASE_KEY_6 = 6
        const val CASE_KEY_7 = 7
        const val CASE_KEY_8 = 8
        const val CASE_KEY_9 = 9
        const val CASE_KEY_DEL = 10
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.numeric_keyboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        button1.setOnClickListener(buttonPressed(CASE_KEY_1))
        button2.setOnClickListener(buttonPressed(CASE_KEY_2))
        button3.setOnClickListener(buttonPressed(CASE_KEY_3))
        button4.setOnClickListener(buttonPressed(CASE_KEY_4))
        button5.setOnClickListener(buttonPressed(CASE_KEY_5))
        button6.setOnClickListener(buttonPressed(CASE_KEY_6))
        button7.setOnClickListener(buttonPressed(CASE_KEY_7))
        button8.setOnClickListener(buttonPressed(CASE_KEY_8))
        button9.setOnClickListener(buttonPressed(CASE_KEY_9))
        button10.setOnClickListener(buttonPressed(CASE_KEY_DEL))
        button11.setOnClickListener(buttonPressed(CASE_KEY_0))
    }

    private fun buttonPressed(key: Int): View.OnClickListener {
        return View.OnClickListener {
            val vb = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= 26) {
                vb.vibrate(VibrationEffect.createOneShot(18, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                vb.vibrate(18)
            }

            if (activity is CustomKeyboardListener) {
                (activity as CustomKeyboardListener).onCKeyEvent(key)
            } else if (parentFragment is CustomKeyboardListener) {
                (parentFragment as CustomKeyboardListener).onCKeyEvent(key)
            } else {
                Log.e("NumericKeyboard", "Activity or parent fragment does not implement CustomKeyBoardListener!")
            }
        }
    }
}
