package dk.analog.digitalclipcard.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dk.analog.digitalclipcard.backend.ApiResponse

class LoginViewModel : ViewModel() {

    private val tokenLiveData = MutableLiveData<ApiResponse<LoginResponse>>()

    fun getTokenLiveData(): LiveData<ApiResponse<LoginResponse>> {
        return tokenLiveData
    }

    fun login(email: String, pin: String) {
        return LoginRepository.login(email, pin) {
            tokenLiveData.value = it
        }
    }
}
