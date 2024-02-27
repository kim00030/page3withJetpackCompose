package com.dan.page3withjepackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dan.page3withjepackcompose.ui.theme.Page3WithJepackComposeTheme

/*
Page3 with Jekpack compose
-use unsplash api to fetch images
-Use Room library to store
-Use remote mediator with remote key to cache data from the server
into Room local DB
-Dark mode support

<Tech spec>
Page3:
paging source that will retrieve the data from data source like network or local DB
Paging source takes 2 parameters. Syntax looks

PagingSource<Key, value> which key: Identifier used to load data. Usually a page number
value is type of data that will be loaded and displayed to user

In this case, we will load images from unsplash server so
PageSource<Int, UnsplashImage>

Room DB supports Page3 library by default. only thing you need to do is to
add a pagingSource wrapper class as return type of the function that is reading your database
for example,
@Query("SELECT * FROM my_table ORDER BY id ASC")
fun getAllItems(): PagingSource<Int, UnsplashImage>

An instance of paging source is used to load the pages of data for an instance of paging data.
Paging Data is a container for paged data from a single generation of loads. Paging Data queries
data from its paging source in response to a loading hints which can be generated as the user scrolls
in the lazy column.

Paging Config class is used to configure behaviour within a pager as it loads the content from PageSource.
Paging accepts multiple parameters:
first parameter: Page size

Remote Mediator: To combine a local storage with a remote queries in order to provide a consistent
data flow, regardless if your network is available or not. using this, we can easily implement off-line
caching
See a diagram showing its role in MVVM (screen_shot_remote_mediator_diagram.png)

so PagingSource has 2 scenarios

<PagingSource>
Data from single source(database, remote, file, etc)
<PagingSource with RemoteMediator>
Data from layered source. It means Network source + database cache

There is one more important thing that goes along with Remote Mediator is "Remote Keys"
Remote keys are keys that is using to tell the back-end service which data to load next

 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Page3WithJepackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Page3WithJepackComposeTheme {
        Greeting("Android")
    }
}