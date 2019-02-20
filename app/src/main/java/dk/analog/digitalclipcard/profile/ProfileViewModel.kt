package dk.analog.digitalclipcard.profile

import androidx.lifecycle.ViewModel
import dk.analog.digitalclipcard.backend.ApiResponse

class ProfileViewModel : ViewModel(){

    fun getAccount(onResponse: ((ApiResponse<AccountResponse>) -> Unit)) {
        ProfileRepository.getAccount{
            onResponse(it)
        }
    }
}