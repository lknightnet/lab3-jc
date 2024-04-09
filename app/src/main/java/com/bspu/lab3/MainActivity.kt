package com.bspu.lab3

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Main()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun Main() {
    val context = LocalContext.current
    val messagePass = rememberSaveable {
        mutableStateOf("")
    }
    val messageName = rememberSaveable {
        mutableStateOf("")
    }

    val messageMail = rememberSaveable {
        mutableStateOf("")
    }

    val listErrors = mapOf(
        "textWarningAll" to "Внимание! Заполните все поля!",
        "textWarningName" to "Внимание! Заполните имя!",
        "textWarningPass" to "Внимание! Заполните пароль!",
        "textWarningMail" to "Внимание! Заполните почту!",

        "textWarningNamePass" to "Внимание! Заполните пароль и имя!",
        "textWarningNameMail" to "Внимание! Заполните имя и почту!",
        "textWarningPassMail" to "Внимание! Заполните пароль и почту!",
    )

    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    if (showErrorDialog){
        Alert(dr = { showErrorDialog = false}, errorMessage = errorMessage)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.vodka_background),
                contentScale = ContentScale.Crop
            )
            .padding(bottom = 200.dp),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = "Добро пожаловать!",
            fontSize = 36.sp
        )

        FieldText(
            message = messageName, text = "Введите ваше имя:", padding = 100.dp
        )

        FieldText(
            message = messageMail, text = "Введите вашу почту:", padding = 200.dp
        )

        FieldText(
            message = messagePass, text = "Введите ваш пароль:", padding = 300.dp
        )
        Button(
            onClick = {
                      if (messageName.value.isEmpty() && messagePass.value.isNotEmpty() && messageMail.value.isNotEmpty()){
                          showErrorDialog = true
                          errorMessage = listErrors["textWarningName"].toString()
                      } else if (messageName.value.isNotEmpty() && messagePass.value.isEmpty() && messageMail.value.isNotEmpty()){
                          showErrorDialog = true
                          errorMessage = listErrors["textWarningPass"].toString()
                      } else if (messageName.value.isNotEmpty() && messagePass.value.isNotEmpty() && messageMail.value.isEmpty()){
                          showErrorDialog = true
                          errorMessage = listErrors["textWarningMail"].toString()
                      } else if (messageName.value.isEmpty() && messagePass.value.isEmpty() && messageMail.value.isEmpty()){
                          showErrorDialog = true
                          errorMessage = listErrors["textWarningAll"].toString()
                      } else if (messageName.value.isEmpty() && messagePass.value.isEmpty() && messageMail.value.isNotEmpty()) {
                          showErrorDialog = true
                          errorMessage = listErrors["textWarningNamePass"].toString()
                      } else if (messageName.value.isEmpty() && messagePass.value.isNotEmpty() && messageMail.value.isEmpty()) {
                          showErrorDialog = true
                          errorMessage = listErrors["textWarningNameMail"].toString()
                      } else if (messageName.value.isNotEmpty() && messagePass.value.isEmpty() && messageMail.value.isEmpty()) {
                          showErrorDialog = true
                          errorMessage = listErrors["textWarningPassMail"].toString()
                      } else if (messageName.value.isNotEmpty() && messagePass.value.isNotEmpty() && messageMail.value.isNotEmpty()){
                          val intent = Intent(context, SecondActivity::class.java)
                          intent.putExtra("MESSAGE_NAME", messageName.value)
                          context.startActivity(intent)
                      }
            },
            modifier = Modifier.padding(top = 425.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color(0xff000000),
                containerColor = Color(0xff9ed6df))
        ) {
            Text(text = "Создать заказ")
        }
    }
}


@Composable
fun Alert(dr: () -> Unit, errorMessage: String){
    AlertDialog(
        onDismissRequest = dr,
        title = {
            Text(text = "Ошибка введенных данных")
        },
        text = {
            Text(errorMessage)
        },
        confirmButton = {
            Button(
                onClick = dr
            ) {
                Text("Ок")
            }
        }
    )
}

@Composable
fun FieldText(message: MutableState<String>, text: String, padding: Dp){
    TextField(
        value = message.value,
        onValueChange = { newText -> message.value = newText },
        placeholder = { Text(text = text) },
        colors = OutlinedTextFieldDefaults.colors(),
        modifier = Modifier.padding(top = padding)
    )
}