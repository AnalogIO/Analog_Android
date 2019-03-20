package dk.analog.digitalclipcard.ticketHistory

import dk.analog.digitalclipcard.backend.ApiResponse
import dk.analog.digitalclipcard.backend.getBackendServiceInstance
import dk.analog.digitalclipcard.backend.makeCall
import dk.analog.digitalclipcard.base.BaseApplication
import java.util.*

data class TicketResponse(val id : Int, val dateCreated : Date, val dateUsed : Date,
                            val productName : String)

object TicketHistoryRepository {
    fun getTicketHistory(onResponse: (ApiResponse<List<TicketResponse>>) -> Unit) {
        getBackendServiceInstance(BaseApplication.context).getTickets(true).makeCall{onResponse(it)}

    }
}