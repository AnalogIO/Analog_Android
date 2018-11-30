package dk.analog.digitalclipcard.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import dk.analog.digitalclipcard.BuildConfig
import dk.analog.digitalclipcard.R

object IntentUtils {
    fun sendErrorEmail(context: Context) {
        val i = Intent(Intent.ACTION_SENDTO)
        i.setDataAndType(Uri.parse("mailto:"), "text/plain")
        i.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>(context.getString(R.string.feedbackEmail)))
        i.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.errorReport))
        i.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.feedbackEmailBody, Build.VERSION.RELEASE, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE))
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // this will make such that when user returns to your app, your app is displayed, instead of the email app.
        try {
            context.startActivity(Intent.createChooser(i, context.getString(R.string.sendMail)))
        } catch (ex: ActivityNotFoundException) {
            context.showToast(R.string.noEmailClientsInstalled)
        }
    }
}
