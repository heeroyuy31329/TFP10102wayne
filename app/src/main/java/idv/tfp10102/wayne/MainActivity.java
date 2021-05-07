package idv.tfp10102.wayne;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;

import idv.tfp10102.wayne.ch09.Ch09;
import idv.tfp10102.wayne.ch10.Ch10;
import idv.tfp10102.wayne.ch11.Ch11;
import idv.tfp10102.wayne.ch18_1.Ch18_1;
import idv.tfp10102.wayne.ch22_1.Ch22_1;

public class MainActivity extends AppCompatActivity {
    Ch09 ch09;
    enum CH {
        TEST, CH_9, CH_10, CH_11, CH_18_1, CH_22_1, CH_28_3
    }

    CH ch;

    DrawerLayout drawerLayout;

    // ch30
    static int REQ_1 = 1;

    // ch36
    File file;
    static int REQ_CAMERA = 2;
    static int REQ_PICKPIC = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ch = CH.CH_28_3;

        switch (ch){
            case CH_9: {
                ch09 = new Ch09(this);
            }
                break;
            case CH_10: {
                Ch10 ch10 = new Ch10(this);
            }
                break;
            case CH_11: {
                Ch11 ch11 = new Ch11(this);
            }
            break;
            case CH_18_1: {
                Ch18_1 ch18_1 = new Ch18_1(this);
            }
            break;
            case CH_22_1: {
                Ch22_1 ch22_1 = new Ch22_1(this);
            }
            break;
            case CH_28_3: {
                setContentView(R.layout.ch28_3_layout);

                // ch 28-3 DrawerLayout
                // 連結抽屜
                NavigationView navigationView = findViewById(R.id.ch28_3_NavigationView);
                NavController navController = Navigation.findNavController(this, R.id.ch28_3_navihost);
                NavigationUI.setupWithNavController(navigationView, navController);

                // 設定選單功能
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setDisplayHomeAsUpEnabled(true);
                }

                drawerLayout = findViewById(R.id.ch28_3_DrawerLayout);
                ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.ch28_drawerOpen, R.string.ch28_drawerClose);
                drawerLayout.addDrawerListener(actionBarDrawerToggle);
                actionBarDrawerToggle.syncState();
            }
            break;
            default: {
                setContentView(R.layout.activity_main);

                //ch31-4
//                EditText etName = findViewById(R.id.etName);
//                EditText etPass = findViewById(R.id.etPass);
//                Button button = findViewById(R.id.button);

//                SharedPreferences sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
//                etName.setText(sharedPreferences.getString("username", ""));
//                etPass.setText(sharedPreferences.getString("password", ""));
//
//                button.setOnClickListener((btnView) -> {
//                    sharedPreferences
//                            .edit()
//                            .putString("username", String.valueOf(etName.getText()))
//                            .putString("password", String.valueOf(etPass.getText()))
//                            .apply();
//                });

                // ch31-5
//                try (
//                    FileInputStream fis = openFileInput("user_info");
//                    ObjectInputStream ois = new ObjectInputStream(fis);
//                ) {
//                    User user = (User) ois.readObject();
//                    if (user != null) {
//                        etName.setText(user.getUsername());
//                        etPass.setText(user.getPassword());
//                    }
//                } catch (Exception e) {
//                    Log.e("aaa", e.toString());
//                }
//
//                button.setOnClickListener((btnView) -> {
//                    String username = String.valueOf(etName.getText());
//                    String password = String.valueOf(etPass.getText());
//                    User user = new User(username, password);
//
//                    try (
//                        FileOutputStream fos = openFileOutput("user_info", Context.MODE_PRIVATE);
//                        ObjectOutputStream oos = new ObjectOutputStream(fos);
//                    ) {
//                        oos.writeObject(user);
//                    } catch (Exception e) {
//                        Log.e("aaa", e.toString());
//                    }
//                });
                
                // ch31-6
//                String state = Environment.getExternalStorageState();
//                if (!Environment.MEDIA_MOUNTED.equals(state)) {
//                    Toast.makeText(this, "not save status", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                File dir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
//                File file = new File(dir, "user_info.txt");
//
//                try (
//                    FileInputStream fis = new FileInputStream(file);
//                    ObjectInputStream ois = new ObjectInputStream(fis);
//                ) {
//                    User user = (User) ois.readObject();
//                    if (user != null) {
//                        etName.setText(user.getUsername());
//                        etPass.setText(user.getPassword());
//                    }
//                } catch (Exception e) {
//                    Log.e("aaa", e.toString());
//                }
//
//                button.setOnClickListener((btnView) -> {
//                    if (!Environment.MEDIA_MOUNTED.equals(state)) {
//                        Toast.makeText(this, "not save status", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//
//                    String username = String.valueOf(etName.getText());
//                    String password = String.valueOf(etPass.getText());
//                    User user = new User(username, password);
//
//                    try (
//                        FileOutputStream fos = new FileOutputStream(file);
//                        ObjectOutputStream oos = new ObjectOutputStream(fos);
//                    ) {
//                        oos.writeObject(user);
//                    } catch (Exception e) {
//                        Log.e("aaa", e.toString());
//                    }
//                });


                //ch31-3
//                AssetManager assetManager = getAssets();
//                try(
//                    InputStream inputStream = assetManager.open("Spring.txt");
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))
//                ) {
//                    String line;
//                    StringBuilder stringBuilder = new StringBuilder();
//                    while ((line = bufferedReader.readLine()) != null) {
//                        stringBuilder.append(line).append("\n");
//                    }
//
//                    TextView textView = findViewById(R.id.testTextView);
//                    textView.setText(stringBuilder);
//                } catch (Exception e) {
//                    Log.e("aaa", e.toString());
//                }

                // ch 36
//                Button button = findViewById(R.id.testBtn);
//                button.setOnClickListener((btnView) -> {
//                    // ch36-1
////                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////                    if (isIntentAvailable(intent)) {
////                        // 指定圖片儲存路徑
////                        file = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
////                        file = new File(file, "ch36.jpg");
////
////                        Uri uri = FileProvider.getUriForFile(
////                                this,
////                                getPackageName() + ".fileProvider",
////                                file);
////                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
////
////                        startActivityForResult(intent, REQ_CAMERA);
////                    }
//
//                    //ch36-2
//                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//
//                    if (isIntentAvailable(intent)) {
//                        startActivityForResult(intent, REQ_PICKPIC);
//                    }
//
//                });

                // ch 29
//                Button button = findViewById(R.id.testBtn);
//                button.setOnClickListener((buttonView) -> {
//                    new AlertDialog.Builder(this)
//                            .setTitle("哈哈哈")
//                            .setIcon(android.R.drawable.ic_menu_myplaces)
//                            .setMessage("顆顆顆")
//                            .setPositiveButton("好", this::onDialogClick)
//                            .setNegativeButton("不", this::onDialogClick)
//                            .setNeutralButton("滾", this::onDialogClick)
//                            .setCancelable(false)
//                            .show();
//                });
//
//                Calendar calendar = Calendar.getInstance();
//                findViewById(R.id.testBtn2).setOnClickListener((buttonView) -> {
//                    // DatePicker
//                    DatePickerDialog datePickerDialog = new DatePickerDialog(
//                            this,
//                            (datePicker, year, month, dayOfMonth) -> {
//                                Toast.makeText(this, year + "/" + month + "/" + dayOfMonth, Toast.LENGTH_SHORT).show();
//                            },
//                            calendar.get(Calendar.YEAR),
//                            calendar.get(Calendar.MONTH),
//                            calendar.get(Calendar.DAY_OF_MONTH)
//                    );
//                    DatePicker datePicker = datePickerDialog.getDatePicker();
//                    datePicker.setMinDate(calendar.getTimeInMillis());
//                    calendar.add(Calendar.MONTH, 2);
//                    datePicker.setMaxDate(calendar.getTimeInMillis());
//                    calendar.add(Calendar.MONTH, -2);
//
//                    datePickerDialog.show();
//
//                    // timePicker
//                    new TimePickerDialog(
//                            this,
//                            (timePicker, hourOfDay, minute) -> {},
//                            calendar.get(Calendar.HOUR),
//                            calendar.get(Calendar.MILLISECOND),
//                            true
//                    ).show();
//
//                    // ch 30 intent
//                    PackageManager packageManager = getPackageManager();
//                    Intent intent = new Intent(Intent.ACTION_PICK);
//                    intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
//
//                    if (intent.resolveActivity(packageManager) != null) {
//                        startActivityForResult(intent, REQ_1);
//                    }
//                });

                // ch 28 BottomNavigationView
//                BottomNavigationView bottomNavigationView = findViewById(R.id.ch28_2_BottomNavi);
//                NavController navController = Navigation.findNavController(this, R.id.ch28_2_navihost);
//                NavigationUI.setupWithNavController(bottomNavigationView, navController);

                // ch 28 ViewPager2
//                ViewPager2 viewPager2 = findViewById(R.id.viewPager);
//                viewPager2.setAdapter(new MyAdaptewr(this));

                // -------------------------------------------------------------------------------
                // ch20
//                Spinner spinner = findViewById(R.id.ch21Spinner1);
//                spinner.setSelection(2, true);
//
//                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        Toast.makeText(MainActivity.this, "onItemSelected : " + position, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//                        Toast.makeText(MainActivity.this, "onNothingSelected", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                // 程式建立選項
//                List<String> itemList = Arrays.asList("11111", "22222", "33333", "44444");
//
//                // 設定下拉的樣式(選項)
//                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemList);
//                // 設定下拉的樣式(展開背景)
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//                Spinner spinner2 = findViewById(R.id.ch21Spinner2);
//                spinner2.setAdapter(adapter);
//                spinner2.setSelection(1);
//
//                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        Toast.makeText(MainActivity.this, "onItemSelected : " + position, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//                        Toast.makeText(MainActivity.this, "onNothingSelected", Toast.LENGTH_SHORT).show();
//                    }
//                });

            }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (ch09 != null && ch09.webBack(keyCode, event)) {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    // ch20
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.ch20_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.ch20_item01) {
//            Log.d("aaa", "" + item.getTitle());
//        } else if (id == R.id.ch20_item02) {
//            Log.d("aaa", "" + item.getTitle());
//        } else if (id == R.id.ch20_item03) {
//            Log.d("aaa", "" + item.getTitle());
//        } else {
//            return super.onOptionsItemSelected(item);
//        }
//
//        return true;
//    }

    // ch 28 ViewPager2
    private static class MyAdaptewr extends FragmentStateAdapter {

        public MyAdaptewr(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return new LoginFragment(position + "");
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

    // ch 28_3 DrawerLayout
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // ch29 方法參考
    private  void  onDialogClick(DialogInterface dialog, int witch) {
        switch (witch) {
            case DialogInterface.BUTTON_POSITIVE:
                Toast.makeText(this, "aaa", Toast.LENGTH_SHORT).show();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                Toast.makeText(this, "bbb", Toast.LENGTH_SHORT).show();
                break;
            case DialogInterface.BUTTON_NEUTRAL:
                Toast.makeText(this, "ccc", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    // 30
    private boolean isIntentAvailable(Intent intent) {
        PackageManager packageManager = getPackageManager();
        return intent.resolveActivity(packageManager) != null;
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (requestCode == REQ_1) {
//            // ch30
//            Uri uri = data.getData();
//            // 宣告欲查詢的欄位
//            final String[] columns = {
//                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
//                ContactsContract.CommonDataKinds.Phone.NUMBER,
//                ContactsContract.CommonDataKinds.Phone.TYPE
//            };
//            // 查詢
//            try (Cursor cursor = getContentResolver().query(uri, columns, null, null, null)) {
//                // 如果有查詢到資料
//                if (cursor.moveToNext()) {
//                    // 取出資料
//                    final String name = cursor.getString(0);
//                    final String number = cursor.getString(1);
//                    final int type = cursor.getInt(2);
//                    // 將電話種類代碼(type) 解析成 電話種類文字
//                    final String typeString = getString(
//                            ContactsContract.CommonDataKinds.Phone.getTypeLabelResource(type)
//                    );
//
//                    Toast.makeText(this, "name : " + name + "\n" + "number : " + number + "\n" + "typeString : " + typeString, Toast.LENGTH_SHORT).show();
//
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + number));
//                    //檢查使否存在APP
//                    startActivity(intent);
//                }
//            }
//        } else if (requestCode == REQ_CAMERA) {
//            // ch 36
//            // 原圖
//            try {
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
//                    ImageDecoder.Source source = ImageDecoder.createSource(file);
//                    Bitmap bitmap = ImageDecoder.decodeBitmap(source);
//
//                    ImageView imageView = findViewById(R.id.testImageView);
//                    imageView.setImageBitmap(bitmap);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            // 縮圖
////            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
////            ImageView imageView = findViewById(R.id.testImageView);
////            imageView.setImageBitmap(bitmap);
//        } else if (resultCode == RESULT_OK && requestCode == REQ_PICKPIC) {
//            // 取消時resultCode會是其他數值，可以檢查是否為RESULT_OK
//            //ch36-2
//            Uri uri = data.getData();
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
//                try {
//                    ImageDecoder.Source source = ImageDecoder.createSource(
//                            getContentResolver(),
//                            uri);
//                    Bitmap bitmap = ImageDecoder.decodeBitmap(source);
//
//                    ImageView imageView = findViewById(R.id.testImageView);
//                    imageView.setImageBitmap(bitmap);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            //ch36-4
//            Uri srcUri = data.getData();
//
//        } else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//
//    }


}