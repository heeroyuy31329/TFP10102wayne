package idv.tfp10102.wayne.ch36_1;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import idv.tfp10102.wayne.MainActivity;
import idv.tfp10102.wayne.R;

import static android.app.Activity.RESULT_OK;

public class Ch36_Fragment extends Fragment {
    private MainActivity activity;

    private Button btnCamera, btnPick, btnScale;
    private EditText editScale;
    private TextView txtPicSize;
    private ImageView imgPic;

    private Uri uriPicture;
    private Uri resultUri;

    ActivityResultLauncher<Intent> takePicLauncher =registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    corp(uriPicture);
//                    try {
//                        Bitmap bitmap;
//
//                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
//                            ImageDecoder.Source source = ImageDecoder.createSource(activity.getContentResolver(), uriPicture);
//                            bitmap = ImageDecoder.decodeBitmap(source);
//                        } else {
//                            InputStream is = activity.getContentResolver().openInputStream(uriPicture);
//                            bitmap = BitmapFactory.decodeStream(is);
//                        }
//
//                        imgPic.setImageBitmap(bitmap);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }
            }
    );

    ActivityResultLauncher<Intent> pickPicLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri uri = result.getData().getData();
                    corp(uri);

//                    try {
//                        Bitmap bitmap;
//
//                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
//                            ImageDecoder.Source source = ImageDecoder.createSource(
//                                    activity.getContentResolver(),
//                                    uri
//                            );
//
//                            bitmap = ImageDecoder.decodeBitmap(source);
//                        } else {
//                            InputStream is = activity.getContentResolver().openInputStream(uri);
//                            bitmap = BitmapFactory.decodeStream(is);
//                        }
//
//                        imgPic.setImageBitmap(bitmap);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }
            }
    );

    ActivityResultLauncher<Intent> cropPictureLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    try {
                        resultUri = UCrop.getOutput(result.getData());
                        Bitmap bitmap;

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                            ImageDecoder.Source source = ImageDecoder.createSource(
                                    activity.getContentResolver(),
                                    resultUri
                            );

                            bitmap = ImageDecoder.decodeBitmap(source);
                        } else {
                            InputStream is = activity.getContentResolver().openInputStream(resultUri);
                            bitmap = BitmapFactory.decodeStream(is);
                        }

                        imgPic.setImageBitmap(bitmap);
                        showPicSize(bitmap.getWidth(), bitmap.getHeight());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();

        return inflater.inflate(R.layout.fragment_ch36, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnCamera = view.findViewById(R.id.ch36_btnCamera);
        btnPick = view.findViewById(R.id.ch36_btnPick);
        btnScale = view.findViewById(R.id.ch36_btnScale);
        editScale = view.findViewById(R.id.ch36_editScale);
        txtPicSize = view.findViewById(R.id.ch36_txtPicSize);
        imgPic = view.findViewById(R.id.ch36_imgPic);

        // ???????????????
        btnCamera.setOnClickListener(v -> {
            // ??????
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (isIntentAvailable(intent)) {
                // ????????????????????????????????????
                File file = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                file = new File(file, "Pic.jpg");
                // ??????Uri
                uriPicture = FileProvider.getUriForFile(
                        activity,
                        activity.getPackageName() + ".fileProvider",
                        file
                );

                // ??????????????????????????????
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uriPicture);

                // ????????????
                takePicLauncher.launch(intent);
            }
        });

        // ????????????
        btnPick.setOnClickListener(v -> {
            // ??????
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            if (isIntentAvailable(intent)) {
                pickPicLauncher.launch(intent);
            }
        });

        // ??????
        btnScale.setOnClickListener(v -> {
            double targetDim = Double.parseDouble(editScale.getText().toString());
            Bitmap newBitmap;

            try {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                    ImageDecoder.Source source = ImageDecoder.createSource(activity.getContentResolver(), resultUri);

                    // ??????ImageDecoder.OnHeaderDecodedListener ??????????????????????????????
                    ImageDecoder.OnHeaderDecodedListener listener = new ImageDecoder.OnHeaderDecodedListener() {
                        @Override
                        public void onHeaderDecoded(@NonNull ImageDecoder decoder, @NonNull ImageDecoder.ImageInfo info, @NonNull ImageDecoder.Source source) {
                            // ??????????????????
                            int srcWidth = info.getSize().getWidth();
                            int srcHeight = info.getSize().getHeight();
                            // ????????????????????????
                            int longer = Math.max(srcWidth, srcHeight);
                            double scale = targetDim / longer;
                            int newWidth = (int) (srcWidth * scale);
                            int newHeight = (int) (srcHeight * scale);
                            // ??????????????????
                            decoder.setTargetSize(newWidth, newHeight);
                        }
                    };

                    newBitmap = ImageDecoder.decodeBitmap(source, listener);
                } else {
                    InputStream is = activity.getContentResolver().openInputStream(resultUri);
                    Bitmap srcBitmap = BitmapFactory.decodeStream(is);

                    // ??????????????????
                    int srcWidth = srcBitmap.getWidth();
                    int srcHeight = srcBitmap.getHeight();
                    // ????????????????????????
                    int longer = Math.max(srcWidth, srcHeight);
                    double scale = targetDim / longer;
                    int newWidth = (int) (srcWidth * scale);
                    int newHeight = (int) (srcHeight * scale);

                    newBitmap = Bitmap.createScaledBitmap(srcBitmap, newWidth, newHeight, true);
                }

                imgPic.setImageBitmap(newBitmap);
                showPicSize(newBitmap.getWidth(), newBitmap.getHeight());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    // ????????????????????????App
    private boolean isIntentAvailable (Intent intent) {
        PackageManager packageManager = activity.getPackageManager();
        return intent.resolveActivity(packageManager) != null;
    }

    // ????????????
    private void corp(Uri sourceUri) {
        // ??????????????????
        File file = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        file = new File(file, "Pic_corp.jpg");

        Uri destinationUri = Uri.fromFile(file);
        Intent intent = UCrop.of(sourceUri, destinationUri).getIntent(activity);
        cropPictureLauncher.launch(intent);
    }

    // ??????????????????
    private void showPicSize(int width, int height) {
        txtPicSize.setText(new StringBuilder()
                .append(width)
                .append(" * ")
                .append(height));
    }
}