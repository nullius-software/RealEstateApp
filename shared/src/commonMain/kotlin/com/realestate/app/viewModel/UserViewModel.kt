package com.realestate.app.viewModel

import androidx.lifecycle.ViewModel
import com.realestate.app.data.model.UserData
import com.rickclephas.kmp.nativecoroutines.NativeCoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserViewModel : ViewModel() {
    private val _userData = MutableStateFlow<UserData?>(null)

    @NativeCoroutineScope
    val userData: StateFlow<UserData?> = _userData

    fun setUserData(data: UserData) {
        _userData.value = data
    }
}