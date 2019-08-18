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
    private String mtitle,msub_title,mvideo,mdetails,mimage;
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
       Submit=findViewById(R.id.submit);

        btnLawImage.setOnClickListener(this);
        Submit.setOnClickListener(this);




        if (getIntent().getStringExtra("title") != null) {
            mtitle = getIntent().getStringExtra("title");
            msub_title = getIntent().getStringExtra("sub_title");
            mimage = getIntent().getStringExtra("image");
            mvideo = getIntent().getStringExtra("video");
            mdetails = getIntent().getStringExtra("details");

title.setText(mtitle);
subtitle.setText(msub_title);
video.setText(mvideo);
details.setText(mdetails);


            GlideApp.with(lawImage)
                    .load(mimage)
                    .placeholder(R.drawable.no_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .into(lawImage);
        }
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


                final StorageReference filePath = lawImages.child(title.getText().toString()).child(date.toString() + ".jpg");
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
                                        "",
                                        video.getText().toString(),
                                        details.getText().toString()
                                );
                                mref.child("ADP").child("laws").child(title.getText().toString()).setValue(dbUser);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK && data!=null)
        {
            Uri ImageUri = data.getData();

            resultUri=ImageUri;



            GlideApp.with(lawImage)
                    .load(ImageUri)
                    .placeholder(R.drawable.no_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .into(lawImage);

        }
        else {
            super.onActivityResult(requestCode, resultCode, data);

        }

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
