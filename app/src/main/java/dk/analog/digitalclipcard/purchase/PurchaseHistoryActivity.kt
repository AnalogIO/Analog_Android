package dk.analog.digitalclipcard.purchase

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import dk.analog.digitalclipcard.R
import dk.analog.digitalclipcard.backend.ApiErrorResponse
import dk.analog.digitalclipcard.backend.ApiSuccessResponse
import dk.analog.digitalclipcard.base.BaseActivity
import dk.analog.digitalclipcard.utils.showToast

class PurchaseHistoryActivity : BaseActivity() {
    private var purchaseViewModel: PurchaseHistoryViewModel? = null
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_profile
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setupListeners()

        purchaseViewModel = ViewModelProviders.of(this).get(PurchaseHistoryViewModel::class.java)
        observeViewModel(purchaseViewModel!!)
    }


    private fun observeViewModel(purchaseViewModel: PurchaseHistoryViewModel) {
        purchaseViewModel.getPurchaseHistory {
            when(it) {
                is ApiSuccessResponse -> {
                    showToast("IMPLEMENT THIS")

                }
                is ApiErrorResponse -> {
                    showToast(it.errorMessage)
                }
            }
        }

    }
}