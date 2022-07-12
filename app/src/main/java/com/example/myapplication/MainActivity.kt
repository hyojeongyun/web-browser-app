package com.example.myapplication

import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.webkit.URLUtil
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageButton
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

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

    private val swipeRefresh: SwipeRefreshLayout by lazy {
        findViewById(R.id.refreshLayout)
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
            webViewClient = WebViewClientClass()
            webChromeClient = WebChromeClient()
            settings.javaScriptEnabled = true
            loadUrl(DEFAULT_URL)
        }
    }

   // WebView
   private fun bindWebView(){

        // addressBar 주소 입력 이벤트
        addressBar.setOnEditorActionListener{ view, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_GO){
                //URL 체크
                var url = view.text.toString()
                if (URLUtil.isNetworkUrl(url)) {
                    // 웹 페이지 로딩
                    webView.loadUrl(view.text.toString())
                } else {
                    webView.loadUrl("https://$url")
                }

                // 키보드 내리기
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(addressBar.windowToken, 0)
            }
            return@setOnEditorActionListener false
        }

        // home 버튼 이벤트
        homeButton.setOnClickListener {
            webView.loadUrl(DEFAULT_URL)
        }

        // forward 버튼 이벤트
        forwardButton.setOnClickListener {
            webView.goForward()
        }

        // backward 버튼 이벤트
        backwardButton.setOnClickListener{
            webView.goBack()
        }

        // swipe refresh 이벤트
        swipeRefresh.setOnRefreshListener{
            webView.reload()
        }
    }

    inner class WebViewClientClass: WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)

            // refresh 창 없애기
            swipeRefresh.isRefreshing = false

            // forward, backward 가능할 경우 button 활성화
            forwardButton.isEnabled = webView.canGoForward()
            backwardButton.isEnabled = webView.canGoBack()

            // text애 현재 웹 주소 표시
            addressBar.setText(url)
        }
    }

    companion object {
        const val DEFAULT_URL = "https://www.google.com"
    }
}