package com.example.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {

    private val webView: WebView by lazy {
        findViewById(R.id.webView)
    }

    private val addressBar: EditText by lazy {
        findViewById(R.id.addressBar)
    }

    private val forwardButton: ImageButton by lazy {
        findViewById(R.id.forwardButton)
    }

    private val backwardButton: ImageButton by lazy {
        findViewById(R.id.backwardButton)
    }

    private val homeButton: ImageButton by lazy {
        findViewById(R.id.homeButton)
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

    // WebView
   private fun bindWebView(){

        // addressBar 주소 입력 이벤트
        addressBar.setOnEditorActionListener{ view, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_GO){
                // 키보드 내리기
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(addressBar.windowToken, 0)
                // 웹 페이지 로딩
                webView.loadUrl(view.text.toString())
            }
            return@setOnEditorActionListener false
        }

        // home 버튼 이벤트
        homeButton.setOnClickListener {
            webView.loadUrl("https://www.google.com")
        }

        // forward 버튼 이벤트
        forwardButton.setOnClickListener {
            webView.goForward()
        }

        // backward 버튼 이벤트
        backwardButton.setOnClickListener{
            webView.goBack()
        }
    }


}