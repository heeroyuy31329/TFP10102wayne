package idv.tfp10102.wayne.ch30;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import idv.tfp10102.wayne.MainActivity;
import idv.tfp10102.wayne.R;

public class Ch30_Fragment extends Fragment {
    ActivityResultLauncher<Intent> pickLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                /*
                 * 解析Uri物件中的資料
                 * 用法與JDBC程式概念相同
                 * 設定欲查詢的欄位(Columns)，查詢後會取得結果集物件(Cursor)
                 * 再進而將結果集物件中的各欄位取出
                 */

                // 宣告欲查詢的欄位
                final String[] columns = {
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone.TYPE
                };

                // 取得Uri物件
                Uri uri = result.getData().getData();

                try(Cursor cursor = this.activity.getContentResolver().query(uri, columns, null, null, null);) {
                    if (cursor.moveToNext()) {
                        String name = cursor.getString(0);
                        String phone = cursor.getString(1);
                        int type = cursor.getInt(2);
                        // 將電話種類代碼(type) 解析成 電話種類文字
                        String typeString = getString(ContactsContract.CommonDataKinds.Phone.getTypeLabelResource(type));

                        this.txtName.setText(name);
                        this.txtPhone.setText(phone);
                        this.txtType.setText(typeString);
                    }
                }
            }
    );

    MainActivity activity;
    Button btnPick, btnCall;
    TextView txtName, txtPhone, txtType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();

        return inflater.inflate(R.layout.fragment_ch30, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnPick = view.findViewById(R.id.ch30_btnPick);
        btnCall = view.findViewById(R.id.ch30_btnCall);
        txtName = view.findViewById(R.id.ch30_txtName);
        txtPhone = view.findViewById(R.id.ch30_txtPhone);
        txtType = view.findViewById(R.id.ch30_txtType);

        // 打開電話簿App來挑選電話
        btnPick.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);

            if (isIntentAvailable(intent)) {
                // 設定Content Type
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                pickLauncher.launch(intent);
            }
        });

        // 跳到電話App
        btnCall.setOnClickListener(v -> {
            String phone = txtPhone.getText().toString();
            if (phone.isEmpty()) {
                Toast.makeText(activity, "請先挑選電話", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + phone));
            if (isIntentAvailable(intent)) {
                startActivity(intent);
            }
        });
    }

    // 檢查是否有內建的App
    private boolean isIntentAvailable (Intent intent) {
        PackageManager packageManager = activity.getPackageManager();
        return intent.resolveActivity(packageManager) != null;
    }
}