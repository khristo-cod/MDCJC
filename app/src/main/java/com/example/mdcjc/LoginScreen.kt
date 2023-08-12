package com.example.mdcjc

import android.app.Activity
import android.util.Log
import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun  LoginScreen( navController: NavHostController) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Header(Modifier.align(Alignment.TopEnd))
        Body(navController, Modifier.align(Alignment.Center))
        Footer(Modifier.align(Alignment.BottomCenter))

    }
}

@Composable
fun Footer(modifier: Modifier) {
    Column(modifier = modifier.fillMaxWidth()){
        Divider(
            Modifier
                .background(Color(0xFFF9F9F9))
                .height(1.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(24.dp))
        CallMe()
        Spacer(modifier = Modifier.size(12.dp))
    }
}

@Composable
fun CallMe() {
    var show by remember { mutableStateOf(false)}
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(text = "¿Aun no estas registrado?",
            fontSize = 12.sp,
            color = Color(0xFFB5B5B5))
            Text(text = "¡CONTACTANOS!",
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { show = true },
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color= Color(0XFF4EA8E9)
            )

    }

    myDialogCall(show = show, onDismiss = { show=false }, onConfirm = {show=false})
}

@Composable
fun myDialogCall(show:Boolean, onDismiss:() -> Unit, onConfirm:() ->Unit ) {
    var name by remember { mutableStateOf("")}
    var email by remember { mutableStateOf("")}
    var phone by remember { mutableStateOf("")}
    var addres by remember { mutableStateOf("")}
    if (show) AlertDialog(onDismissRequest = {onDismiss()},
            title = { Text(text = "Formulario de Contacto")},
            text = {
                Column(modifier = Modifier.padding(16.dp)) {
                    TextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text(text = "Nombre") })
                    Spacer(modifier = Modifier.padding(bottom = 8.dp))
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(text = "E-mail") })
                    Spacer(modifier = Modifier.size(8.dp))
                    TextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text(text = "Telefono") })
                    Spacer(modifier = Modifier.size(8.dp))
                    TextField(
                        value = addres,
                        onValueChange = { addres = it },
                        label = { Text(text = "Direccion") })
                }},

            confirmButton = {
                TextButton(onClick = {onConfirm()}) {
                    Text(text = "Enviar")
                }
            },
            dismissButton = {
                TextButton(onClick = {onDismiss()}) {
                    Text(text = "Cancelar")
                }
            })

}

@Composable
fun Body( modifier: Modifier) {
    var email by rememberSaveable{ mutableStateOf("") }
    var password by rememberSaveable{ mutableStateOf("") }
    var isLoginEnable by rememberSaveable { mutableStateOf(false)}
    Column(modifier = modifier) {
        ImageLogo(Modifier.align(Alignment.CenterHorizontally))
        Email(email) {
            email=it
            isLoginEnable=enableLogin(email, password)
        }
        Spacer(modifier = Modifier.size(8.dp))
        Password(password) {
            password=it
            isLoginEnable=enableLogin(email, password)
        }
        Spacer(modifier = Modifier.size(8.dp))
        ForgotPassword(Modifier.align(Alignment.End))
        Spacer(modifier = Modifier.size(16.dp))
        LoginButton(isLoginEnable)

    }
}

fun enableLogin(email: String, password: String) =
    Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length > 6

@Composable
fun LoginButton(navController: NavHostController, loginEnable: Boolean) {
    Button(
        onClick = { navController.navigate(Routes.PropertyScreen.id) },
        enabled = loginEnable,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF4EA8E9),
            disabledBackgroundColor = Color(0xFF78C8F9),
            contentColor = Color.White,
            disabledContentColor = Color.White
        ),

        )  {
        Text(text = "Iniciar Sesión", modifier = Modifier)

    }
}

@Composable
fun ForgotPassword(modifier: Modifier) {
    var show by remember { mutableStateOf(false)}
    TextButton(onClick = { show = true }) {
        Text(text = "Restablecer Contraseña",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4FA8E9),
            modifier = modifier)
    }

    myDialog(show = show, onDismiss = {show = false}, onConfirm = { Log.i("khrist", "Click") })
}

@Composable
fun myDialog(show:Boolean, onDismiss:() -> Unit, onConfirm:() ->Unit ) {
    if (show) {
        AlertDialog(onDismissRequest = {onDismiss()},
            title = { Text(text = "Restablecer")},
            text = { Text(text = "Està segur@ que desea restablecer la contraseña?")},
            confirmButton = {
                TextButton(onClick = {onConfirm()}) {
                    Text(text = "SI")
                }
            },
            dismissButton = {
                TextButton(onClick = {onDismiss()}) {
                    Text(text = "NO")
                }
            })
    }

}

@Composable
fun Email(email:String, onTextChanged:(String) -> Unit) {
    TextField(
        value = email,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Email")},
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color (0XFFB2B2B2),
            backgroundColor = Color( 0XFFFAFAFA),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}

@Composable
fun Password(password:String, onTextChanged:(String) -> Unit) {
    var passwordVisibility by remember { mutableStateOf(false)}
    TextField(
        value = password,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "password")},
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color (0XFFB2B2B2),
            backgroundColor = Color( 0XFFFAFAFA),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val imagen = if(passwordVisibility){
                Icons.Filled.VisibilityOff
            }else{
                Icons.Filled.Visibility
            }
            IconButton(onClick = {passwordVisibility = !passwordVisibility }) {
                Icon(imageVector = imagen, contentDescription = "Ver Contraseña")
            }
        },
        visualTransformation = if (passwordVisibility){
            VisualTransformation.None
        }else{
            PasswordVisualTransformation()
        }
    )
}

@Composable
fun ImageLogo(modifier: Modifier) {
    Image(painter = painterResource(id = R.drawable.aliadosda), contentDescription = "logoApp", modifier = modifier)
}

@Composable
fun Header(modifier: Modifier) {
    val activity = LocalContext.current as Activity
    Icon(imageVector = Icons.Default.Close, contentDescription = "close app", modifier = modifier.clickable { activity.finish() })
}
