package idv.tfp10102.wayne.ch12;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;
import java.util.List;

import idv.tfp10102.wayne.MainActivity;
import idv.tfp10102.wayne.R;

public class Ch12_Fragment extends Fragment {
    MainActivity mainActivity;

    EditText edit_username;
    TextInputEditText edit_password;
    AutoCompleteTextView edit_country;
    Button btn_submit;
    TextView text_result;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity)getActivity();
        View view = inflater.inflate(R.layout.fragment_ch12, container, false);

        edit_username = view.findViewById(R.id.ch12_edit_username);
        edit_password = view.findViewById(R.id.ch12_edit_password);
        edit_country = view.findViewById(R.id.ch12_edit_country);
        btn_submit = view.findViewById(R.id.ch12_btn_submit);
        text_result = view.findViewById(R.id.ch12_result_text);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 綁定自動完成對話筐
        List<String> countryList = Arrays.asList("Taiwan", "Japan", "America");
        edit_country.setAdapter(new ArrayAdapter<String>(mainActivity, R.layout.ch12_listview, countryList));

        btn_submit.setOnClickListener((btnView) -> {
            // 判斷帳號至否為空
            String username = String.valueOf(edit_username.getText());
            if (username.isEmpty()) {
                edit_username.setError(mainActivity.getResources().getString(R.string.ch12_error_username));
                return;
            }

            // 判斷密碼是否為空
            String password = String.valueOf(edit_password.getText());
            if (password.isEmpty()) {
                edit_password.setError(mainActivity.getResources().getString(R.string.ch12_error_password));
                return;
            }

            // 判斷國家是否為空
            String country = String.valueOf(edit_country.getText());
            if (country.isEmpty()) {
                edit_country.setError(mainActivity.getResources().getString(R.string.ch12_error_country));
                return;
            }

            StringBuilder result = new StringBuilder()
                    .append(mainActivity.getString(R.string.ch12_text_username) + " : " + username + "\n")
                    .append(mainActivity.getString(R.string.ch12_text_password) + " : " + password + "\n")
                    .append(mainActivity.getString(R.string.ch12_text_country) + " : " + country + "\n");

            text_result.setText(result);
        });
    }
}