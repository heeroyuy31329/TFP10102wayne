package idv.tfp10102.wayne.ch09;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import idv.tfp10102.wayne.MainActivity;
import idv.tfp10102.wayne.R;

public class Ch09_Fragment extends Fragment {
    MainActivity activity;

    private TextView textView;
    private ImageView imageView;
    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_ch09, container, false);

        // 抓取UI元件
        textView = view.findViewById(R.id.ch09TextViewName);
        imageView = view.findViewById(R.id.ch09ImageView);
        webView = view.findViewById(R.id.ch09WebView);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TextView
        textView.setOnClickListener((v) -> {
            textView.append("!");
            textView.setTextColor(activity.getResources().getColor(R.color.black));
        });

        // ImageView
        imageView.setOnClickListener((v) -> {
            Toast.makeText(activity, "圖片片片！！！", Toast.LENGTH_SHORT).show();

            imageView.setImageResource(R.drawable.mountain_night);
        });

        // WebView
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

    // 尚未完成，須問老師如何製作
    public boolean webBack(int keyCode, KeyEvent event) {
        // 按了返回 且 有上一頁
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }

        return false;
    }
}