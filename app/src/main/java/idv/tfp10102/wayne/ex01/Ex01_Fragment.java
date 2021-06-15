package idv.tfp10102.wayne.ex01;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import idv.tfp10102.wayne.MainActivity;
import idv.tfp10102.wayne.R;
import idv.tfp10102.wayne.ch22_1.Book;
import idv.tfp10102.wayne.ch22_1.Ch22_1_Adapter;

public class Ex01_Fragment extends Fragment {
    MainActivity activity;

    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();

        return inflater.inflate(R.layout.fragment_ex01, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.ex01_RecyclerView);

        // recyclerView
        recyclerView.setAdapter(new Ch22_1_Adapter(activity, getBookList()));
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        view.findViewById(R.id.ex01_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.scrollToPosition(3);
                Toast.makeText(activity, "按了按了", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 取得書籍測試資料
     */
    private List<Book> getBookList() {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(R.drawable.book01, "突破困境：資安開源工具應用"));
        bookList.add(new Book(R.drawable.book02, "大話 AWS 雲端架構：雲端應用架構圖解輕鬆學"));
        bookList.add(new Book(R.drawable.book03, "為你自己學 Git"));
        bookList.add(new Book(R.drawable.book04, "無瑕的程式碼－整潔的軟體設計與架構篇"));
        bookList.add(new Book(R.drawable.book05, "大話設計模式"));
        bookList.add(new Book(R.drawable.book06, "無瑕的程式碼－敏捷軟體開發技巧守則"));
        bookList.add(new Book(R.drawable.book07, "iOS App 程式開發實務攻略：快速精通 SwiftUI"));
        bookList.add(new Book(R.drawable.book08, "獨角獸專案｜看IT部門如何引領百年企業振衰起敝，重返榮耀"));
        bookList.add(new Book(R.drawable.book09, "PowerShell 流程自動化攻略"));
        bookList.add(new Book(R.drawable.book10, "機器學習的數學基礎 : AI、深度學習打底必讀"));
        return bookList;
    }
}