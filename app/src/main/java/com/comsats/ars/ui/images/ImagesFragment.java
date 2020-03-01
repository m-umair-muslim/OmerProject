package com.comsats.ars.ui.images;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.comsats.ars.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ImagesFragment extends Fragment {

//    ImageView imageView;
    //  private FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
    //private DatabaseReference databaseReference = firebaseDatabase.getReference();
    // private DatabaseReference first = databaseReference.child("image");


    private ImagesViewModel galleryViewModel;
    private ImageView mImageView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        galleryViewModel =
//                ViewModelProviders.of(this).get(ImagesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_images, container, false);
//        final TextView textView = root.findViewById(R.id.text_gallery);
//        galleryViewModel.getText().observe(this, new Observer<String>() {
//
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//
//        });

        mImageView = root.findViewById(R.id.img);

        return root;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mImageView != null) {
            StorageReference reference = FirebaseStorage.getInstance()
                    .getReferenceFromUrl("gs://arsfirebaseproject-f638b.appspot.com/1logo.png");

            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {

                    Glide.with(ImagesFragment.this)
                            .load(uri)
                            .into(mImageView);
                }
            });

        }
    }
}