package dk.analog.digitalclipcard.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import dk.analog.digitalclipcard.R
import kotlinx.android.synthetic.main.custom_toolbar.view.*

@Suppress("MemberVisibilityCanBePrivate")
class CustomToolbar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) :
        RelativeLayout(context, attrs, defStyleAttr, defStyleRes) {

    enum class BackButtonType {
        MENU, BACK
    }

    init {
        View.inflate(context, R.layout.custom_toolbar, this)
        context.theme.obtainStyledAttributes(
                attrs, R.styleable.CustomToolbar, 0, 0
        ).apply {
            try {
                setToolbarTitle(getString(R.styleable.CustomToolbar_toolbarTitle))
                setBackButtonIcon(getInteger(R.styleable.CustomToolbar_toolbarBackIconType, 0))
            } finally {
                recycle()
            }
        }
    }

    fun setToolbarTitle(title: String?) {
        toolbarTitle.text = title
    }

    private fun setBackButtonIcon(integer: Int) {
        toolbarMenuIcon.setImageResource(when (integer) {
            BackButtonType.MENU.ordinal -> R.drawable.ic_menu
            BackButtonType.BACK.ordinal -> R.drawable.ic_arrow_back
            else -> R.drawable.ic_menu
        })
    }
}
