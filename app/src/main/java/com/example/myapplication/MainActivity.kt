package com.example.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    private val webView: WebView by lazy {
        findViewById(R.id.webView)
    }

    private val addressBar: EditText by lazy {
        findViewById(R.id.addressBar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initWebView()
        bindWebView()
    }

    // 초기 웹뷰 실행
    private fun initWebView(){
        webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl("https://www.google.com")
        }
    }

    // 주소 입력 후 웹뷰 이동동
   private fun bindWebView(){
        addressBar.setOnEditorActionListener{ view, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_GO){
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(addressBar.windowToken, 0)
                webView.loadUrl(view.text.toString())
            }
            return@setOnEditorActionListener false
        }
    }


}