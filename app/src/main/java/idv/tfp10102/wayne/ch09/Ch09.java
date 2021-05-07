package idv.tfp10102.wayne.ch09;

import android.app.Activity;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import idv.tfp10102.wayne.R;

public class Ch09 {
    private TextView textView;
    private ImageView imageView;
    private WebView webView;

    public Ch09(Activity activity) {
        // 加入畫面
        activity.setContentView(R.layout.ch09_layout);

        // TextView
        textView = activity.findViewById(R.id.ch09TextViewName);
        textView.setOnClickListener((v) -> {
            textView.append("!");
            textView.setTextColor(R.color.black);
            //textView.setTextColor(activity.getResources().getColor(R.color.black));
        });

        // ImageView
        imageView = activity.findViewById(R.id.ch09ImageView);
        imageView.setOnClickListener((v) -> {
            Toast.makeText(activity, "圖片片片！！！", Toast.LENGTH_SHORT).show();

            imageView.setImageResource(R.drawable.mountain_night);
        });

        // WebView
        webView = activity.findViewById(R.id.ch09WebView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.google.com.tw/");

        // 避免使用者點了網頁後會開啟外部瀏覽器，所以要複寫下面兩個方法
        webView.setWebViewClient(new WebViewClient() {
            // API - 24 以前使用
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            // API - 24(包含) 以後使用
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
    }

    public boolean webBack(int keyCode, KeyEvent event) {
        // 按了返回 且 有上一頁
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }

        return false;
    }
}
