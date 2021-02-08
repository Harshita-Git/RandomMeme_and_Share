package com.harshita.randommemeshare
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
class MainActivity : AppCompatActivity() {
    var curentImageUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadmeme()
    }
   private fun loadmeme(){
       val pg: ProgressBar = findViewById(R.id.progressbar)
       pg.visibility= View.VISIBLE

        val url = "https://meme-api.herokuapp.com/gimme"
// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            {  response ->
curentImageUrl =response.getString("url")
                val imageView: ImageView = findViewById(R.id.imageViewid)
                Glide.with(this).load(curentImageUrl).listener(object:RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                      pg.visibility=View.GONE
                        return false
                    }
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pg.visibility=View.GONE
                        return false
                    }
                }).into(imageView);
            },
            {
            })
// Add the request to the RequestQueue.
     MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
    fun sharememe(view: View) {
        val intent= Intent (Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"check this cool meme $curentImageUrl")
        val chooser= Intent.createChooser(intent,"share this meme using...")
        startActivity(chooser)
    }
    fun nextmeme(view: View) {
loadmeme()
    }
}
