package com.inno.yandexmusicsample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.inno.yandexmusicsample.ui.theme.YandexMusicSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YandexMusicSampleTheme {
                // A surface container using the 'background' color from the theme
                MainScreen("")
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(name: String, modifier: Modifier = Modifier) {


    val sheetState = rememberBottomSheetScaffoldState()



    val paddingButton = remember {
        mutableStateOf(290.dp)
    }


    val alpha = remember {
        mutableStateOf(0f)
    }

    //Log.d("121212", )

    if (sheetState.bottomSheetState.isExpanded) {
        when (sheetState.bottomSheetState.progress) {
            1f -> {}
            else -> {
                alpha.value = (0.5f - maxOf(0f, sheetState.bottomSheetState.progress - 0.5f)) / 0.5f
                paddingButton.value =
                    (290 - maxOf(0f, (0.6f - sheetState.bottomSheetState.progress)) * 130).dp
            }
        }
    } else {
        when (sheetState.bottomSheetState.progress) {
            1f -> {}
            else -> {
                alpha.value = maxOf(0f, sheetState.bottomSheetState.progress - 0.3f) / 0.3f
                paddingButton.value =
                    (290 - maxOf(0f, sheetState.bottomSheetState.progress - 0.6f) * 130).dp

            }
        }
    }




    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

//        ModalBottomSheetLayout(
//            sheetState = sheetState,
//            sheetContent = {
//                SheetContent()
//            },
//        ) {
//            //ToolbarContent(sheetState)
//        }
        BottomSheetScaffold(
            sheetContent = {
                SheetContent(alpha, paddingButton)
            },
            sheetBackgroundColor = Color.Transparent,
            drawerElevation = 0.dp,
            modifier = Modifier,
            sheetElevation = 0.dp,
            floatingActionButton = {


                Box {
                    if (paddingButton.value < 240.dp) {
                        Box(
                            modifier = Modifier
                                .padding(top = 150.dp)
                                .fillMaxWidth()
                                .height(110.dp)
                                .alpha(0.6f)
                                .background(Color.Black)
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(top = paddingButton.value)
                    ) {
                        Box(
                            modifier =
                            Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFFCDE5A))
                                .clickable { }
                        ) {
                            Icon(
                                painter =
                                painterResource(id = R.drawable.ic_play), contentDescription = null,
                                Modifier
                                    .size(20.dp)
                                    .align(Alignment.Center),
                                tint = Color.Black
                            )
                        }
                        Text(
                            text = "Слушать",
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            color = Color(0x84FFFFFF),
                            modifier = Modifier
                                .alpha(1f - alpha.value)
                                .padding(top = 6.dp)
                                .height(20.dp),

                            )
                    }
                }

            },
            sheetPeekHeight = 550.dp,
            floatingActionButtonPosition = FabPosition.Center,
            scaffoldState = sheetState
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painterResource(id = R.drawable.ic_lil_peep),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(alpha.value)
                        .background(Color.Black)
                )
            }
        }
        ToolbarContent(paddingButton, alpha)
    }
}

@Composable
fun ToolbarContent(padding: MutableState<Dp>, alpha: MutableState<Float>) {


    Box(
        Modifier
            .padding(top = 28.dp)
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 10.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(40.dp)
                .padding(5.dp),
        )

        AnimatedVisibility(
            visible = padding.value < 250.dp,
            modifier = Modifier
                .align(Alignment.Center),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Text(
                text = "Lil peep",
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = Bold,
                fontSize = 19.sp
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(40.dp)
                    .padding(8.dp),
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp),
            )
        }

    }
}

@Composable
fun SheetContent(alpha: MutableState<Float>, padding: MutableState<Dp>) {
    val arr =
        arrayOf(
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13,
            14,
            14,
            8,
            9,
            10,
            11,
            12,
            13,
            14,
            14,
            13,
            14,
            14,
            8,
            9,
            10,
            11,
            12,
            13,
            14,
            14
        )


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
    ) {
        Information(alpha, padding)

        Column(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                .background(Color.Black),

            ) {
            Spacer(
                modifier = Modifier
                    .padding(top = 15.dp, bottom = 35.dp)
                    .align(Alignment.CenterHorizontally)
                    .height(2.dp)
                    .width(60.dp)
                    .background(Color(0xFFB1B1AF))
            )
            for (i in 0 until 25) {
                Text(
                    text = "Gym Class",
                    color = Color.White,
                    fontWeight = SemiBold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .height(70.dp)
                )
            }
        }
    }
}

@Composable
fun Information(alpha: MutableState<Float>, padding: MutableState<Dp>) {
    Column(
        Modifier
            .height(
                if (padding.value == 290.dp) {
                    190.dp
                } else {
                    (190*(padding.value.value-238)/(290-238)).dp
                }
            )
            .alpha(1f - alpha.value),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Lil Peep",
            fontWeight = Bold,
            fontSize = 32.sp,
            color = Color.White,
            modifier = Modifier.height(50.dp)

        )
        Text(
            text = "886 413 слушателей за месяц",
            fontWeight = SemiBold,
            fontSize = 14.sp,
            color = Color(0xFFB1B1AF),
            modifier = Modifier.height(25.dp)
        )
        Row(
            Modifier
                .padding(horizontal = 60.dp)
                .fillMaxWidth()
                .padding(top = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier =
                    Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color(0x4DFFFFFF))
                        .clickable { }
                ) {
                    Image(
                        painter =
                        painterResource(id = R.drawable.ic_share),
                        contentDescription = null,
                        Modifier
                            .size(26.dp)
                            .align(Alignment.Center),
                    )
                }

                Text(
                    text = "Поделиться",
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Color(0x84FFFFFF),
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .height(20.dp),

                    )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier =
                    Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color(0x4DFFFFFF))
                        .clickable { }
                ) {
                    Image(
                        painter =
                        painterResource(id = R.drawable.ic_like),
                        contentDescription = null,
                        Modifier
                            .size(26.dp)
                            .align(Alignment.Center),
                    )
                }

                Text(
                    text = "1 124 354",
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Color(0x84FFFFFF),
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .height(20.dp),

                    )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    YandexMusicSampleTheme {
        MainScreen("Android")
    }
}