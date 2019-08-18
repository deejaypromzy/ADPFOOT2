package com.projectwork.adp.adpfoot;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class UpdateLaw extends Fragment implements View.OnClickListener {
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

    public UpdateLaw() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mref= FirebaseDatabase.getInstance().getReference();
        mProgress=new ProgressDialog(getContext());
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        lawImages = FirebaseStorage.getInstance().getReference().child("lawImages");

        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = new Date();
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_law, container, false);

        title=view.findViewById(R.id.title);
        subtitle=view.findViewById(R.id.sub_title);
        video=view.findViewById(R.id.video);
        details=view.findViewById(R.id.details);
        audio=view.findViewById(R.id.audio);


        lawImage=view.findViewById(R.id.lawImgView);
        btnLawImage=view.findViewById(R.id.imgBtn);
        btn_audio=view.findViewById(R.id.btn_audio);
        Submit=view.findViewById(R.id.submit);

        btnLawImage.setOnClickListener(this);
        btn_audio.setOnClickListener(this);
        Submit.setOnClickListener(this);



        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
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
                                Toast.makeText(getContext(), resultUri.toString(), Toast.LENGTH_SHORT).show();

                                hideProgressDialog();
                                mref.child("ADP").child("laws").child(title.getText().toString()).setValue(dbUser);

                            }
                        });

                    }

                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(getContext(), "Error Updating , Check Internet Connectivity", Toast.LENGTH_SHORT).show();

                            }
                        })



                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


                            }
                        });

                break;



            case R.id.imgBtn:
                if(NoPermissions()){
                    requestPermission();
                    return;
                }


                if (storageTask !=null && storageTask.isInProgress())
                { Toast.makeText(getActivity(), "Uploading picture.....", Toast.LENGTH_SHORT).show();

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
            mProgressDialog = new ProgressDialog(getContext());
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
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET,Manifest.permission.CAMERA};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            requestPermissions(permissions, PERMS_REQUEST_CODE);
    }
    boolean NoPermissions() {
        int res = 0;
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET,Manifest.permission.CAMERA};

        for (String s : permissions) {
            res =getContext().checkCallingOrSelfPermission(s);
            if (!(res == PackageManager.PERMISSION_GRANTED))
                return true;
        }
        return false;

    }
}
