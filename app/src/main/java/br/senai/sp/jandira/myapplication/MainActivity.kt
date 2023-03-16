package br.senai.sp.jandira.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {

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
            text = "Create Account"
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
                accountCreate(emailState, passwordState, context)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Create"
            )
        }
        Text(
            text = "You have account? Join!",
            modifier = Modifier.clickable {
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            }
        )

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Greeting()
    }
}

fun accountCreate(email: String, password: String, context: Context) {


    // OBTER UMA INSTANCIA DO FIREBASE AUTH
    val auth = FirebaseAuth.getInstance()

    auth.createUserWithEmailAndPassword(email, password)
        .addOnSuccessListener {
            Log.i("ds3m", "${it.user!!.uid}")

            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
        .addOnFailureListener {

            try {
                //lançar o it
                throw it
            }
            catch (e: FirebaseAuthUserCollisionException){
                Log.i("ds3m", "${e.message}")
            }
            catch (e: FirebaseAuthInvalidCredentialsException){
                Log.i("ds3m", "${e.message}")
            }
            catch (e: FirebaseAuthInvalidUserException){
                Log.i("ds3m", "${e.message}")
            }
            catch (e: FirebaseAuthException){
                Log.i("ds3m", "${e.message}")
            }

        }

}