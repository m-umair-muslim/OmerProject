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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.comsats.ars.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ImagesFragment extends Fragment {

    private ImagesViewModel galleryViewModel;
    private ImageView mImageView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = new ViewModelProvider(this).get(ImagesViewModel.class);

        View root = inflater.inflate(R.layout.fragment_images, container, false);
        mImageView = root.findViewById(R.id.img);

        galleryViewModel.getImageUri().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                Glide.with(ImagesFragment.this)
                        .load(uri)
                        .into(mImageView);
            }
        });

        return root;

    }
}