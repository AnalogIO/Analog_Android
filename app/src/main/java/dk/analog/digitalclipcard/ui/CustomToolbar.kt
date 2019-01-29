package dk.analog.digitalclipcard.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.DrawableRes
import dk.analog.digitalclipcard.R
import kotlinx.android.synthetic.main.custom_toolbar.view.*

@Suppress("MemberVisibilityCanBePrivate")
class CustomToolbar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) :
        RelativeLayout(context, attrs, defStyleAttr, defStyleRes) {

    init {
        View.inflate(context, R.layout.custom_toolbar, this)
        context.theme.obtainStyledAttributes(
                attrs, R.styleable.CustomToolbar, 0, 0
        ).apply {
            try {
                setToolbarTitle(getString(R.styleable.CustomToolbar_toolbarTitle))
                setIcon(getResourceId(R.styleable.CustomToolbar_toolbarIcon, 0))
            } finally {
                recycle()
            }
        }
    }

    fun setToolbarTitle(title: String?) {
        toolbarTitle.text = title
    }

    fun setIcon(@DrawableRes iconResId: Int) {
        toolbarIcon.setImageResource(iconResId)
    }
}
