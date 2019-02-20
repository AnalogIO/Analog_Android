package dk.analog.digitalclipcard.purchase

import dk.analog.digitalclipcard.backend.ApiResponse
import dk.analog.digitalclipcard.backend.getBackendServiceInstance
import dk.analog.digitalclipcard.backend.makeCall
import dk.analog.digitalclipcard.base.BaseApplication
import java.util.*

data class PurchaseResponse(val id : Int, val productName : String, val productId : Int, val price : Int,
                    val numberOfTickets : Int, val dateCreated : Date, val completed : Boolean,
                    val orderId : String, val transactionId : String)

object PurchaseRepository {
    fun getPurchases(onResponse: (ApiResponse<PurchaseResponse>) -> Unit) {
        getBackendServiceInstance(BaseApplication.context).getPurchasese().makeCall{onResponse(it)}

    }
}