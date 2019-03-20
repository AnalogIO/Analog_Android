package dk.analog.digitalclipcard.ticketHistory

import androidx.lifecycle.ViewModel
import dk.analog.digitalclipcard.backend.ApiResponse
import dk.analog.digitalclipcard.ticketHistory.TicketHistoryRepository

class TicketHistoryViewModel : ViewModel(){

    fun getTicketHistory(onResponse: ((ApiResponse<List<TicketResponse>>) -> Unit)) {
        TicketHistoryRepository.getTicketHistory{
            onResponse(it)
        }
    }
}