package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.example.newsapp.ui.theme.NewsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    homeScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun homeScreen(modifier: Modifier = Modifier) {
    var searchQuery by remember { mutableStateOf("") }

    val tabs = listOf("Noticias", "Eventos", "Clima")
    var selectedTabIndex by remember { mutableStateOf(0) }
    
    val sampleNews = listOf(
        NewsItem("El presidente de EE.UU. no muestra signos de arrepentimiento...", "febrero 08 - 2024", ""),
        NewsItem("Bañarse en la piscina de Cleopatra en el desierto...", "febrero 10 - 2024", ""),
        NewsItem("Gigantes tecnológicos lanzan nueva IA...", "febrero 12 - 2024", "")
    )
    
    val worldNews = listOf(
        NewsItem("El presidente de EE.UU. no muestra signos de arrepentimiento...", "febrero 08 - 2024", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR8DYI2L4PujntPQv9aFLJqSeWCHKqsuwoJ2A&s"),
        NewsItem("Bañarse en la piscina del desierto de Cleopatra", "febrero 10 - 2024", "https://images.unsplash.com/photo-1539650116574-8efeb43e2750?q=80&w=2670&auto=format&fit=crop"),
        NewsItem("Gigantes tecnológicos", "febrero 12 - 2024", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRgIY9n-Nhbn6DkW4QZFLMEq4McoTSjEx_5Ow&s"),
        NewsItem("El rover de Marte envía", "febrero 14 - 2024", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSF5w9WyL6a7J-LUmltfa3FzFvAM-HT52LOOA&s")
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxSize()
    ) {
        item(span = { GridItemSpan(2) }) {
            Column {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Buscar", color = Color.Gray, fontWeight = FontWeight.Bold) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Home,
                            contentDescription = "Buscar Icon"
                        )
                    },
                    shape = RoundedCornerShape(24.dp)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Tabs
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    tabs.forEachIndexed { index, title ->
                        val isSelected = selectedTabIndex == index
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clickable { selectedTabIndex = index }
                        ) {
                            Text(
                                text = title,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.Black else Color.LightGray
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            if (isSelected) {
                                Box(
                                    modifier = Modifier
                                        .width(40.dp)
                                        .height(4.dp)
                                        .background(Color(0xFF6200EE), shape = RoundedCornerShape(2.dp))
                                )
                            } else {
                                Spacer(modifier = Modifier.height(4.dp))
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Text(
                    text = "Ultimas noticias",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(sampleNews) { news ->
                        NewsCard(news = news)
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Text(
                    text = "Alrededor del mundo",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
        
        items(worldNews) { news ->
            WorldNewsCard(news = news)
        }
    }
}

data class NewsItem(val title: String, val date: String, val imageUrl: String)

@Composable
fun NewsCard(news: NewsItem) {
    Box(
        modifier = Modifier
            .size(width = 280.dp, height = 190.dp)
            .background(Color(0xFF6246EA), shape = RoundedCornerShape(20.dp))
            .padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = news.title,
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 5,
                lineHeight = 28.sp,
                overflow = TextOverflow.Ellipsis
            )
            
            Text(
                text = news.date,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun WorldNewsCard(news: NewsItem) {
    Box(
        modifier = Modifier
            .height(240.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFD9D9D9))
    ) {
        if (news.imageUrl.isNotEmpty()) {
            AsyncImage(
                model = news.imageUrl,
                contentDescription = news.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(Color(0xFFCFCFCF))
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = news.title,
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 22.sp,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun homeScreenPreview() {
    NewsAppTheme {
        homeScreen()
    }
}