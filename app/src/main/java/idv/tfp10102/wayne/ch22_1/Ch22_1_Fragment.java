package idv.tfp10102.wayne.ch22_1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import idv.tfp10102.wayne.MainActivity;
import idv.tfp10102.wayne.R;

public class Ch22_1_Fragment extends Fragment {
    MainActivity activity;

    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();

        View view = inflater.inflate(R.layout.fragment_ch22_1, container, false);

        recyclerView = view.findViewById(R.id.ch22_1_RecyclerView);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setAdapter(new Ch22_1_Adapter(activity, getBookList()));
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
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