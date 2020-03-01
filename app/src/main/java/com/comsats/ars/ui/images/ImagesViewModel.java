package com.comsats.ars.ui.images;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;

public class ImagesViewModel extends ViewModel {

    private MutableLiveData<Uri> mImageRepo;

    public ImagesViewModel() {
        mImageRepo = new MutableLiveData<>();
        //mText.setValue("This is gallery fragment");
    }

    public LiveData<Uri> getImageUri() {
        if (mImageRepo.getValue() == null) {
            FirebaseStorage.getInstance()
                    .getReferenceFromUrl("gs://arsfirebaseproject-f638b.appspot.com/1logo.png")
                    .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    mImageRepo.postValue(uri);
                }
            });
        }
        return mImageRepo;
    }

}