package idv.tfp10102.wayne;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment {
    MainActivity activity;
    EditText username;
    EditText password;
    Button btnLogin;

    // 測試用
    String string;
    public LoginFragment(String string) {
        this.string = string;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        username = view.findViewById(R.id.ch25_editText_name);
        password = view.findViewById(R.id.ch25_editText_pass);
        btnLogin = view.findViewById(R.id.ch25_btn_login);

        username.setText(string);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ch27 action bar
        ActionBar actionBar = activity.getSupportActionBar();

        btnLogin.setOnClickListener((btnView) -> {
            String userName = String.valueOf(username.getText());
            String passWord = String.valueOf(password.getText());

            Bundle bundle = new Bundle();
            bundle.putString("username", userName);
            bundle.putString("password", passWord);

            // 用來傳物件
            //bundle.putSerializable();

            NavController navController = Navigation.findNavController(btnView);
            navController.navigate(R.id.action_loginFragment_to_ch26ResultFragment, bundle);
        });
    }
}