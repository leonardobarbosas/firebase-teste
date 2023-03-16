package br.senai.sp.jandira.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.senai.sp.jandira.myapplication.ui.theme.MyApplicationTheme
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //FIREBASE
        val auth = FirebaseAuth.getInstance()

        //se estiver logado vai para tela de home
        if(auth.currentUser != null){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }



        //o ?. é para deixar de ser nulo
        Log.i("ds3m", "${auth.currentUser?.uid}")

        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting2()
                }
            }
        }
    }
}

@Composable
fun Greeting2() {

    val context = LocalContext.current

    var emailState by remember {
        mutableStateOf("")
    }

    var passwordState by remember {
        mutableStateOf("")
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Login"
        )

        OutlinedTextField(
            value = emailState,
            onValueChange = { emailState = it },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = "E-mail"
                )
            }
        )

        OutlinedTextField(
            value = passwordState,
            onValueChange = { passwordState = it },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = "Password"
                )
            }
        )

        Button(
            onClick = {
                authenticate(emailState, passwordState, context)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Entrar"
            )
        }

        Text(
            text = "No have account? Create!",
            modifier = Modifier.clickable {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            }
        )

    }
}

fun authenticate(email: String, password: String, context: Context) {

    //obter instancia do firebase auth
    val auth = FirebaseAuth.getInstance()

    //autenticacao
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener {
            Log.i("ds3m","${it.isSuccessful}")
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }


}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview2() {
    MyApplicationTheme {
        Greeting2()
    }
}