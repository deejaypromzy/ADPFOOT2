package com.projectwork.adp.adpfoot;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CHK extends AppCompatActivity implements View.OnClickListener {
    private static final int PERMS_REQUEST_CODE = 213123;
    private Button btnLawImage,Submit,btn_audio;
    private EditText title,subtitle,video,details,audio;
    private ImageView lawImage;
    private FirebaseDatabase mfirebaseDatabase;
    private DatabaseReference mref;
    private ProgressDialog mProgress;
    final static int Gallery_Pick = 1;
    private StorageTask storageTask;
    private StorageReference lawImages;
    public static String markerString;
    private Uri resultUri;
    private SimpleDateFormat df;
    private Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chk);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mref= FirebaseDatabase.getInstance().getReference();
        mProgress=new ProgressDialog(this);
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        lawImages = FirebaseStorage.getInstance().getReference().child("lawImages");

        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = new Date();
        // Inflate the layout for this fragment

        title=findViewById(R.id.title);
        subtitle=findViewById(R.id.sub_title);
        video=findViewById(R.id.video);
        details=findViewById(R.id.details);
        audio=findViewById(R.id.audio);


        lawImage=findViewById(R.id.lawImgView);
        btnLawImage=findViewById(R.id.imgBtn);
        btn_audio=findViewById(R.id.btn_audio);
        Submit=findViewById(R.id.submit);

        btnLawImage.setOnClickListener(this);
        btn_audio.setOnClickListener(this);
        Submit.setOnClickListener(this);
    }

    private boolean validateForm() {
        String myfname = title.getText().toString();
        if (TextUtils.isEmpty(myfname)) {
            title.setError("Title be empty.");
            title.requestFocus();
            return true;
        } else {
            title.setError(null);
        }

        if (lawImage.getDrawable()== null) {
            Toast.makeText(this, "Image Cant be empty", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.submit:
                if (validateForm()){
                    return;
                }
                showProgressDialog();


                final StorageReference filePath = lawImages.child(title.getText().toString() + ".jpg");
                storageTask=filePath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Laws_Database dbUser= new Laws_Database(
                                        title.getText().toString(),
                                        subtitle.getText().toString(),
                                        uri.toString(),
                                        audio.getText().toString(),
                                        video.getText().toString(),
                                        details.getText().toString()
                                );
                                Toast.makeText(CHK.this, resultUri.toString(), Toast.LENGTH_SHORT).show();
                                mref.child("HFA").child("companies").child(title.getText().toString()).setValue(dbUser);
                            }
                        });

                        onBackPressed();
//                            try{
//                                new DownloadImage().execute(resultUri.toString());
//
//                            }catch (Exception ignored){
//
//                            }finally {
//                                GlideApp.with(AddServices.this)
//                                        .load(resultUri)
//                                        .placeholder(R.drawable.no_image)
//                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                        .priority(Priority.HIGH)
//                                        .into(proImage);
//                            }





                    }

                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(CHK.this, "Error Updating , Check Internet Connectivity", Toast.LENGTH_SHORT).show();

                            }
                        })



                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


                            }
                        });

                break;

            case R.id.imgBtn:
                Toast.makeText(CHK.this, "Uploading picture.....", Toast.LENGTH_SHORT).show();
                if(NoPermissions()){
                    requestPermission();
                    return;
                }

                if (storageTask !=null && storageTask.isInProgress())
                { Toast.makeText(CHK.this, "Uploading picture.....", Toast.LENGTH_SHORT).show();

                }else {

                    Intent galleryIntent = new Intent();
                    galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent, Gallery_Pick);

                }

                break;

        }
    }
    public ProgressDialog mProgressDialog;
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(("Please wait..."));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==Gallery_Pick && resultCode==RESULT_OK && data!=null)
        {
            Uri ImageUri = data.getData();
            CropImage.activity(ImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
//                    .setAspectRatio(1, 1)
                    .start(this);


        }

        else if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE &&  data!=null)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode == RESULT_OK)
            {
                resultUri = result.getUri();

                GlideApp.with(lawImage)
                        .load(resultUri)
                        .placeholder(R.drawable.no_image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.HIGH)
                        .into(lawImage);
            }
            else
            {
                Toast.makeText(this, "Error Occurred: Image can not be cropped. Try Again.", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);

        }

    }




    public Bitmap loadImageBitmap(Context context, String imageName) {
        Bitmap bitmap = null;
        FileInputStream fiStream;
        try {
            fiStream    = context.openFileInput(imageName);
            bitmap      = BitmapFactory.decodeStream(fiStream);
            fiStream.close();
        } catch (Exception e) {
            Log.d("saveImage", "Exception 3, Something went wrong!");
            e.printStackTrace();
        }
        return bitmap;
    }
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        private String TAG = "DownloadImage";
        private Bitmap downloadImageBitmap(String sUrl) {
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(sUrl).openStream();   // Download Image from URL
                bitmap = BitmapFactory.decodeStream(inputStream);       // Decode Bitmap
                inputStream.close();
            } catch (Exception e) {
                Log.d(TAG, "Exception 1, Something went wrong!");
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadImageBitmap(params[0]);
        }

        @Override
        protected void onPreExecute() {
            //  progressBar.setVisibility(View.VISIBLE);
        }

        protected void onPostExecute(Bitmap result) {
            //  progressBar.setVisibility(View.GONE);
            saveImage(getApplicationContext(), result, "hh" + ".jpg");
        }
    }
    public void saveImage(Context context, Bitmap b, String imageName) {
        FileOutputStream foStream;
        try {
            foStream = context.openFileOutput(imageName, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.JPEG, 100, foStream);
            foStream.close();
        } catch (Exception e) {
            Log.d("saveImage", "Exception 2, Something went wrong!");
            e.printStackTrace();
        }
    }



    public void whatsAppIntent(String phone_no,String str) {
        Uri mUri = Uri.parse("https://api.whatsapp.com/send?phone=" + phone_no + "&text=" + str);
        Intent mIntent = new Intent("android.intent.action.VIEW", mUri);
        mIntent.setPackage("com.whatsapp");
        startActivity(mIntent);

    }
    private void requestPermission() {
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET, Manifest.permission.CAMERA};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            requestPermissions(permissions, PERMS_REQUEST_CODE);
    }
    boolean NoPermissions() {
        int res = 0;
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET, Manifest.permission.CAMERA};

        for (String s : permissions) {
            res = CHK.this.checkCallingOrSelfPermission(s);
            if (!(res == PackageManager.PERMISSION_GRANTED))
                return true;
        }
        return false;

    }

}
