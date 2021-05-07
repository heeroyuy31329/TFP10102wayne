package idv.tfp10102.wayne;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Ch26ResultFragment extends Fragment {
    MainActivity activity;

    TextView textResult;

    String username;
    String password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        username = bundle.getString("username");
        password = bundle.getString("password");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();

        View view = inflater.inflate(R.layout.fragment_ch26_result, container, false);
        textResult = view.findViewById(R.id.ch26_textResult);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textResult.setOnClickListener((textView) -> {
            Navigation.findNavController(textView).popBackStack();
        });

        if ("wayne".equals(username) && "123456".equals(password)) {
            textResult.setText(username + "\n" + password);
        } else {
            textResult.setText("帳號或密碼錯誤");
        }
    }
}