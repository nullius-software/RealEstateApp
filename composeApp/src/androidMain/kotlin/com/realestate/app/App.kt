package com.realestate.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.realestate.app.ui.screen.ActivateUserScreen
import com.realestate.app.ui.screen.DetailScreen
import com.realestate.app.ui.screen.LoginScreen
import com.realestate.app.ui.screen.ListScreen
import com.realestate.app.ui.screen.RegisterScreen
import com.realestate.app.viewModel.LoginViewModel
import com.realestate.app.viewModel.RegisterViewModel
import com.realestate.app.viewModel.UserViewModel
import kotlinx.serialization.Serializable

@Serializable
object LoginDestination

@Serializable
object RegisterDestination

@Serializable
object ActivateUserDestination

@Serializable
object ListDestination

@Serializable
data class DetailDestination(val objectId: Int)

@Composable
fun App() {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    ) {
        Surface {
            val navController = rememberNavController()
            val loginViewModel: LoginViewModel = viewModel()
            val registerViewModel: RegisterViewModel = viewModel()
            val userViewModel: UserViewModel = viewModel()

            NavHost(navController = navController, startDestination = LoginDestination) {
                composable<LoginDestination> {
                    LoginScreen(viewModel = loginViewModel, onLogin = {
                        navController.navigate(ListDestination)
                    }, onClickUserHasNoAccount = {
                        navController.navigate(RegisterDestination)
                    })
                }
                composable<RegisterDestination> {
                    RegisterScreen(viewModel = registerViewModel, userViewModel = userViewModel, onRegister = {
                        navController.navigate(ActivateUserDestination)
                    }, onClickUserAlreadyHasAccount = {
                        navController.navigate(LoginDestination)
                    })
                }
                composable<ActivateUserDestination> {
                    ActivateUserScreen(userViewModel.userData.value, userVerified = {
                        navController.navigate(ListDestination) {
                            popUpTo(ActivateUserDestination) { inclusive = true }
                        }
                    })
                }
                composable<ListDestination> {
                    ListScreen(navigateToDetails = { objectId ->
                        navController.navigate(DetailDestination(objectId))
                    })
                }
                composable<DetailDestination> { backStackEntry ->
                    DetailScreen(
                        objectId = backStackEntry.toRoute<DetailDestination>().objectId,
                        navigateBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
