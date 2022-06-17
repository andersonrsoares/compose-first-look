package br.com.anderson.composefirstlook.presentation.weather_search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.rotationMatrix
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.com.anderson.composefirstlook.presentation.NavigationKeys
import br.com.anderson.composefirstlook.R;
import br.com.anderson.composefirstlook.ui.theme.*

@Composable
fun WeatherSearchDestination(navController: NavHostController) {
    WeatherSearchScreen(
        onNavigationRequested = { itemId ->
            navController.navigate("${NavigationKeys.Route.WEATHER_DETAIL}/${itemId}")
        })
}
private lateinit var viewModel:WeatherViewModel

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
            WeatherSearchScreenCompose{
                ImageLogo()
                Spacer(modifier = Modifier
                    .height(20.dp))
                InputTextContent()
                Spacer(modifier = Modifier
                    .height(20.dp))
                HistoryButton()
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
                .height(50.dp)
                .background(
                    shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp),
                    color = Color.White
                ),
            maxLines = 1,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colors.onSecondary,
                disabledTextColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colors.onSecondary

            ),
            value = textState.value,
            onValueChange = { textState.value = it }
        )

        Spacer(modifier = Modifier
            .width(10.dp))

        RegistrationButton {
            viewModel.onWeatherSearchClick(textState.component1().text)
        }
    }
}


@Composable
private fun RegistrationButton(onClick: () -> Unit={}) {
    Button(modifier = Modifier
        .width(70.dp)
        .height(50.dp),
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