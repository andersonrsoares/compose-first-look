package br.com.anderson.composefirstlook.presentation.weather_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.com.anderson.composefirstlook.presentation.NavigationKeys
import br.com.anderson.composefirstlook.R;
import br.com.anderson.composefirstlook.presentation.weather_detail.WeatherViewModel
import br.com.anderson.composefirstlook.ui.theme.*

@Composable
fun WeatherDetailDestination(navController: NavHostController) {
    WeatherSearchScreen(
        onNavigationRequested = { itemId ->
            navController.navigate("${NavigationKeys.Route.WEATHER_DETAIL}/${itemId}")
        })
}
private lateinit var viewModel: WeatherViewModel

@Composable
private fun WeatherSearchScreen(
    onNavigationRequested: (itemId: String) -> Unit
) {
    viewModel = hiltViewModel()
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    // Listen for side effects from the VM

    LaunchedEffect(true) {
        viewModel.fetchWeatherFlow.collect { state ->
            print(state)
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        WeatherSearchScreenBody()
    }

}

@Preview(showBackground = true)
@Composable
private fun WeatherSearchScreenBody() {
    Box(
        Modifier
            .fillMaxHeight()
            .fillMaxHeight()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        BackgroundColor1,
                        BackgroundColor2
                    )
                )
            )) {
        Box(
            Modifier
                .padding(20.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            WeatherDetailScreenCompose {
                LoadingBar()
            }
        }
    }
}

@Composable
private fun WeatherDetailScreenCompose(childrenCompose: @Composable () -> Unit) {
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        childrenCompose()
    }
}

@Composable
private fun LoadingBar() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}