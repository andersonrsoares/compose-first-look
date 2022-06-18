package br.com.anderson.composefirstlook.presentation.weather_search

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
import androidx.navigation.compose.rememberNavController
import br.com.anderson.composefirstlook.R;
import br.com.anderson.composefirstlook.presentation.WeatherNavHostController
import br.com.anderson.composefirstlook.presentation.navigation.NavigationKeys
import br.com.anderson.composefirstlook.presentation.navigation.NavigationScreen
import br.com.anderson.composefirstlook.presentation.weather_detail.WeatherViewModel
import br.com.anderson.composefirstlook.ui.theme.*


@Composable
fun WeatherSearchDestination(navController: NavHostController) {
    WeatherSearchScreen(
        onNavigationRequested = { itemId ->
            navController.navigate("${NavigationScreen.WeatherDetail.route}/${itemId}")
        })
}

@Composable
private fun WeatherSearchScreen(
    onNavigationRequested: (itemId: String) -> Unit
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()

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
            WeatherSearchScreenCompose{
                ImageLogo()
                Spacer(modifier = Modifier
                    .height(20.dp))
                InputTextContent()
                Spacer(modifier = Modifier
                    .height(20.dp))
                HistoryButton {
                    WeatherNavHostController.navController.navigate(NavigationScreen.WeatherHistory.route)
                }
            }
        }
    }
}

@Composable
private fun WeatherSearchScreenCompose(childrenCompose: @Composable () -> Unit) {
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
private fun ImageLogo() {
    Image(
        painterResource(R.drawable.logo),
        contentDescription = "",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    )
}

@Composable
private fun InputTextContent() {
    Row {
        val textState = remember { mutableStateOf(TextFieldValue()) }
        TextField(
            modifier = Modifier
                .height(55.dp)
                .background(
                    shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp),
                    color = Color.White
                ),
            placeholder = {
                Text(text = stringResource(R.string.enter_city_name),
                    color = TextColor)
            },
            maxLines = 1,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = TextColor,
                disabledTextColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = TextColor

            ),
            value = textState.value,
            onValueChange = { textState.value = it }
        )

        Spacer(modifier = Modifier
            .width(10.dp))

        RegistrationButton {
            WeatherNavHostController.navController.navigate("${NavigationScreen.WeatherDetail.route}/${textState.component1().text}")
        }
    }
}


@Composable
private fun RegistrationButton(onClick: () -> Unit={}) {
    Button(modifier = Modifier
        .width(70.dp)
        .height(55.dp),
        shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp),
        colors = ButtonDefaults
            .buttonColors(Color.White),
        onClick = { onClick() }) {
        Icon(
            painter = painterResource(id = R.drawable.arrow),
            contentDescription = null,
            tint = IconColor
        )
    }
}

@Composable
private fun HistoryButton(onClick: () -> Unit={}) {
    Button(modifier = Modifier
        .wrapContentHeight()
        .wrapContentWidth(),
        shape = RoundedCornerShape(6.dp),
        elevation = null,
        colors = ButtonDefaults
            .buttonColors(ButtonBackgroundColor),
        onClick = { onClick() }) {
        Box(modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 5.dp)) {
            Text(color = Color.White, text = stringResource(id = R.string.view_history).uppercase())
        }
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