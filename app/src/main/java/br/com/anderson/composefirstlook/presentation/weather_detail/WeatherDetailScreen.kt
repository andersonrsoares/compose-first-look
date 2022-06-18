package br.com.anderson.composefirstlook.presentation.weather_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.anderson.composefirstlook.R;
import br.com.anderson.composefirstlook.domain.model.Weather
import br.com.anderson.composefirstlook.presentation.UiState
import br.com.anderson.composefirstlook.ui.theme.BackgroundColor1
import br.com.anderson.composefirstlook.ui.theme.BackgroundColor2
import br.com.anderson.composefirstlook.ui.theme.CardBackgroundColor
import coil.compose.AsyncImage

@Composable
fun WeatherDetailDestination(cityName:String?) {
    WeatherDetailScreen()
}
private lateinit var viewModel: WeatherViewModel

@Composable
private fun WeatherDetailScreen(
    onNavigationRequested: (itemId: String) -> Unit
) {
    viewModel = hiltViewModel()
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    // Listen for side effects from the VM

    LaunchedEffect(true) {

    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {

    }

}


@Composable
fun WeatherDetailScreen() {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    viewModel = hiltViewModel()
    // Listen for side effects from the VM

    val weatherState by viewModel.fetchWeatherFlow.collectAsState(initial = UiState.Loading())

    LaunchedEffect(true) {
        viewModel.onWeatherSearchClick("dublin")
    }

    Scaffold(
        scaffoldState = scaffoldState,
    ) {
        WeatherDetailBody(weatherState)
    }

}

@Preview(showBackground = true)
@Composable
fun WeatherDetailBody(
    weatherState: UiState<List<Weather>> =
        UiState.Success(listOf(Weather(
            "good",
            "http://openweathermap.org/img/wn/02d@2x.png",
            temperature = 28.7,
            "curitiba",
            date = 1655568638L)))
) {
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

        Column() {
            Box(Modifier.padding(15.dp)) {
                Icon(
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp)
                        .rotate(180f),
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = null,
                    tint = Color.White
                )
            }
            Box(modifier = Modifier.padding(20.dp)) {
                when(weatherState) {
                    is UiState.Loading -> LoadingBar()
                    is UiState.Success -> {
                        WeatherList(weatherItems = weatherState.data.orEmpty())
                    }
                    is UiState.Failure -> LoadingBar()
                }
            }
        }
    }
}
@Composable
fun WeatherList(
    weatherItems: List<Weather> = arrayListOf()
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(weatherItems) { item ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(CardBackgroundColor)
            ) {
                Box(modifier = Modifier
                    .padding(15.dp)
                    .fillMaxSize()
                    .background(Color.Red)
                ) {
                    Row {
                        AsyncImage(
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp),
                            contentScale = ContentScale.Fit,
                            model = item.urlIcon,
                            contentDescription = null
                        )
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            fontSize = 15.sp,
                            color = Color.White,
                            text = item.description)
                    }


                    Text(color = Color.White,
                        modifier = Modifier.align(Alignment.BottomStart),
                        fontSize = 20.sp,
                        text = item.city)

                    Text(color = Color.White,
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 40.sp,
                        text = item.formatTemperature())

                    Text(color = Color.White,
                        modifier = Modifier.align(Alignment.BottomEnd),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        text = item.formatDate())
                }
            }
        }
    }
}

@Composable
private fun LoadingBar() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(color = Color.White)
    }
}