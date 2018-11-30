package dk.analog.digitalclipcard.login

import android.content.Context
import dk.analog.digitalclipcard.BuildConfig
import dk.analog.digitalclipcard.backend.getLoginBackendServiceInstance
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class LoginRepository {
    private val compositeDisposable = CompositeDisposable()

    fun login(context: Context, email: String, pin: String) {
        val service = getLoginBackendServiceInstance(context)
        val res = service.login(Login(email, pin, BuildConfig.VERSION_NAME))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<LoginResponse> {
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }

                    override fun onSuccess(t: LoginResponse) {

                    }

                    override fun onError(e: Throwable) {

                    }

                })
    }
}