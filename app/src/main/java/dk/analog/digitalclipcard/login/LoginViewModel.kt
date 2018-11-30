package dk.analog.digitalclipcard.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dk.analog.digitalclipcard.backend.ApiResponse

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val tokenLiveData = MutableLiveData<ApiResponse<LoginResponse>>()

    fun getTokenLiveData(): LiveData<ApiResponse<LoginResponse>> {
        return tokenLiveData
    }

    fun login(email: String, pin: String) {
        return LoginRepository.login(getApplication(), email, pin) {
            tokenLiveData.value = it
        }
    }
}
