package dk.analog.digitalclipcard.purchase

import androidx.lifecycle.ViewModel
import dk.analog.digitalclipcard.backend.ApiResponse

class PurchaseHistoryViewModel : ViewModel(){

    fun getPurchaseHistory(onResponse: ((ApiResponse<List<PurchaseResponse>>) -> Unit)) {
        PurchaseRepository.getPurchases{
            onResponse(it)
        }
    }
}