package br.com.anderson.composefirstlook.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import br.com.anderson.composefirstlook.presentation.weather_detail.WeatherViewModel
import br.com.anderson.composefirstlook.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.anderson.composefirstlook.presentation.weather_detail.WeatherDetailDestination
import br.com.anderson.composefirstlook.presentation.weather_search.WeatherSearchDestination

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    Greeting("Android")
//                }
                WeatherApp()
            }
        }
    }
}

object WeatherNavHostController {
    @SuppressLint("StaticFieldLeak")
    lateinit var navController: NavHostController
}

@Composable
private fun WeatherApp() {
    WeatherNavHostController.navController = rememberNavController()
    NavHost(WeatherNavHostController.navController, startDestination = NavigationKeys.Route.WEATHER_SEARCH) {
        composable(route = NavigationKeys.Route.WEATHER_SEARCH) {
            WeatherSearchDestination(WeatherNavHostController.navController)
        }
        composable(
            route = NavigationKeys.Route.WEATHER_DETAIL,
            arguments = listOf(navArgument(NavigationKeys.Arg.CITY_NAME) {
                type = NavType.StringType
            })
        ) {
            WeatherDetailDestination(WeatherNavHostController.navController)
        }
    }
}