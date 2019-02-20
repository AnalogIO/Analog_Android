package dk.analog.digitalclipcard.profile

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import dk.analog.digitalclipcard.R
import dk.analog.digitalclipcard.backend.ApiErrorResponse
import dk.analog.digitalclipcard.backend.ApiSuccessResponse
import dk.analog.digitalclipcard.base.BaseActivity
import dk.analog.digitalclipcard.utils.StringUtils.addOrdinalIndicator
import dk.analog.digitalclipcard.utils.showToast
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : BaseActivity() {
    private var profileViewModel: ProfileViewModel? = null
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_profile
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setupListeners()

        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        observeViewModel(profileViewModel!!)
    }


    private fun observeViewModel(profileViewModel: ProfileViewModel) {
        profileViewModel.getAccount {
            when(it) {
                is ApiSuccessResponse -> {
                    val account = it.body
                    profileName.text = account.name
                    profileId.text = "ID: " + account.id.toString()
                    profileEmail.text = account.email
                    profileProgramme.text = "KK-DT" //TODO
                    profileAllTime.text = addOrdinalIndicator(account.rankAllTime)
                    profileMonth.text = addOrdinalIndicator(account.rankMonth)
                    profileSemester.text = addOrdinalIndicator(account.rankSemester)

                }
                is ApiErrorResponse -> {
                    showToast(it.errorMessage)
                }
            }
        }

    }

}