package com.realestate.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.realestate.app.screens.DetailScreen
import com.realestate.app.screens.LoginScreen
import com.realestate.app.screens.ListScreen
import com.realestate.app.screens.RegisterScreen
import kotlinx.serialization.Serializable

@Serializable
object LoginDestination

@Serializable
object RegisterDestination

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
            NavHost(navController = navController, startDestination = LoginDestination) {
                composable<LoginDestination> {
                    LoginScreen(onLogin = {
                        navController.navigate(ListDestination)
                    }, onClickUserHasNoAccount = {
                        navController.navigate(RegisterDestination)
                    })
                }
                composable<RegisterDestination> {
                    RegisterScreen(onRegister = {
                        navController.navigate(ListDestination)
                    }, onClickUserAlreadyHasAccount = {
                        navController.navigate(LoginDestination)
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
