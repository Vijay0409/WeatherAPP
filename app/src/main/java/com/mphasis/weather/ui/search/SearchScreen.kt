package com.mphasis.weather.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mphasis.weather.R
import com.mphasis.weather.ui.theme.*

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onNavigateToWeather: (String) -> Unit
) {
    val city by viewModel.cityState.collectAsState()
    var isError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(screenPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(illustrationSize)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.weather_search_img)
                    .build(),
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.height(screenPadding))

        Text(
            text = stringResource(id = R.string.search_for_a_city),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(cardPadding))

        Text(
            text = stringResource(id = R.string.search_prompt),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(largeVerticalSpacing))

        OutlinedTextField(
            value = city,
            onValueChange = {
                viewModel.onCityChange(it)
                isError = false
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(cornerRadius),
            label = { Text(stringResource(id = R.string.enter_city_label)) },
            singleLine = true,
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(
                        text = stringResource(id = R.string.city_empty_error),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(verticalSpacing))

        Button(
            onClick = {
                if (city.isBlank()) {
                    isError = true
                } else {
                    viewModel.onSearchConfirmed(onNavigateToWeather)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(buttonHeight),
            shape = RoundedCornerShape(cornerRadius)
        ) {
            Text(stringResource(id = R.string.search))
        }
    }
}
