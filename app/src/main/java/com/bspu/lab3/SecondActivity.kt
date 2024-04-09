package com.bspu.lab3

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bspu.lab3.ui.theme.model.Product

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SecondMain(intent)
        }
    }
}

@Composable
fun SecondMain(intent: Intent) {

    val context = LocalContext.current
    val messageName = intent.getStringExtra("MESSAGE_NAME")

    val products = listOf(
        Product(1, "Finlandia Classic", R.drawable.classic),
        Product(2, "Finlandia Cucumber & Mint", R.drawable.cucumbermint),
        Product(3, "Finlandia Wildberry & Rose", R.drawable.wildberryrose),
        Product(4, "Finlandia Redberry", R.drawable.redberry),
    )

    val cartItems = remember { mutableStateListOf<Product>() }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Привет, $messageName! Что будете заказывать?",
        )
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            modifier = Modifier.padding(top = 20.dp)
        ) {

            items(products.chunked(2)) { rowOfProducts ->
                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    for (product in rowOfProducts) {
                        ProductItem(product = product){
                            // Обработка нажатия на кнопку "Добавить в корзину"
                            cartItems.add(product)
                        }
                    }
                }
            }
        }

        Button(onClick = {
            val intent2 = Intent(context, ThirdActivity::class.java)
            intent2.putExtra("CART_ITEMS", ArrayList(cartItems))
            context.startActivity(intent2)

        }) {
            Text(text = "Посмотреть корзину")
        }
    }
}

@Composable
fun ProductItem(product: Product, onAddToCart: () -> Unit) {
    Column(
        modifier = Modifier
            .width(180.dp)
            .border(border = BorderStroke(1.dp, Color.Black))
    ) {
        Image(
            painter = painterResource(id = product.imageRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
        Text(
            text = product.name,
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .heightIn(max = 20.dp),
            fontSize = 15.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Button(
            onClick = onAddToCart,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .align(Alignment.End)
        ) {
            Text(text = "Добавить в корзину", textAlign = TextAlign.Center)
        }
    }
}