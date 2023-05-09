package br.senai.sp.jandira.rickandmorty

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.rickandmorty.model.Info
import br.senai.sp.jandira.rickandmorty.model.Origin
import br.senai.sp.jandira.rickandmorty.model.characterList
import br.senai.sp.jandira.rickandmorty.service.RetrofitFactory
import br.senai.sp.jandira.rickandmorty.ui.theme.RickAndMortyTheme
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {

    var listCharacter by remember {
        mutableStateOf(listOf<br.senai.sp.jandira.rickandmorty.model.Character>())
    }
    var info by remember {
        mutableStateOf(Info())
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = {

            // cria uma chamada para o endpoint
            val call = RetrofitFactory().getCharacterService().getCharacterByNameAndStatus(name = "Rick", status = "")

            // Executar a chamada
            call.enqueue(object : Callback<characterList >{
                override fun onResponse(
                    call: Call<characterList>,
                    response: Response<characterList>
                ) {
                    listCharacter =  response.body()!!.results
                    info = response.body()!!.info
                }

                override fun onFailure(call: Call<characterList>, t: Throwable) {
                    Log.i("Ds2-T", "onFailure: ${t.message}")
                }

            })

        }) {
            Text(text = "Listar personagens")
        }
        Row() {
            Text(text = "Count", modifier = Modifier.size(width = 60.dp, height = 20.dp))

            Text(text = "${info.count}", modifier = Modifier.size(width = 40.dp, height = 20.dp), textAlign = TextAlign.End)
        }
        Row() {
            Text(text = "pages ", modifier = Modifier.size(width = 60.dp, height = 20.dp))
            Text(text = "${info.pages}", modifier = Modifier.size(width = 40.dp, height = 20.dp), textAlign = TextAlign.End)
        }
        LazyColumn(){
            items(listCharacter){
                Card(
                    backgroundColor = Color.Green,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 0.dp,
                            vertical = 4.dp
                        )
                ) {
                    Row(modifier = Modifier.padding(8.dp)) {
                        AsyncImage(model = it.image, contentDescription = "Character avatar", modifier = Modifier.clip(shape = CircleShape))

                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(
                                text = it.name,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(text = it.species)
                            Text(text = it.origin.name)
                        }
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    RickAndMortyTheme {
        Greeting("Android")
    }
}