package br.com.anderson.composefirstlook.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import br.com.anderson.composefirstlook.presentation.weather_search.WeatherViewModel
import br.com.anderson.composefirstlook.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.anderson.composefirstlook.presentation.weather_search.WeatherSearchDestination

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModels()

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

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}

@Composable
private fun WeatherApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = NavigationKeys.Route.WEATHER_SEARCH) {
        composable(route = NavigationKeys.Route.WEATHER_SEARCH) {
            WeatherSearchDestination(navController)
        }
        composable(
            route = NavigationKeys.Route.WEATHER_DETAIL,
            arguments = listOf(navArgument(NavigationKeys.Arg.WEATHER_ID) {
                type = NavType.StringType
            })
        ) {
           // FoodCategoryDetailsDestination()
        }
    }
}