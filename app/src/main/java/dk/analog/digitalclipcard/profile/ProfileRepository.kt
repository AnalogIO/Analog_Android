package dk.analog.digitalclipcard.profile

import dk.analog.digitalclipcard.backend.ApiResponse
import dk.analog.digitalclipcard.backend.getBackendServiceInstance
import dk.analog.digitalclipcard.backend.makeCall
import dk.analog.digitalclipcard.base.BaseApplication

data class AccountResponse(val id: Int, val name: String, val email: String,
                           val privacyActivated : Boolean, val programmeId : Int, val level : Int,
                           val requiredExp : Int, val rankAllTime : Int, val rankSemester: Int,
                           val rankMonth : Int)

object ProfileRepository {
    fun getAccount(onResponse: (ApiResponse<AccountResponse>) -> Unit) {
        getBackendServiceInstance(BaseApplication.context).getAccountDetails().makeCall{onResponse(it)}
    }
}
