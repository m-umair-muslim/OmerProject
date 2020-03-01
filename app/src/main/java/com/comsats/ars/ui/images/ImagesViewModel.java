package com.comsats.ars.ui.images;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ImagesViewModel extends ViewModel {

    private MutableLiveData<List<Uri>> mImageRepo;

    public ImagesViewModel() {
        mImageRepo = new MutableLiveData<>();
    }

    public LiveData<List<Uri>> getImageUri() {
        if (mImageRepo.getValue() == null) {
            FirebaseStorage.getInstance()
                    .getReference()
                    .listAll()
                    .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                        @Override
                        public void onSuccess(ListResult listResult) {
                            List<StorageReference> allReferences = listResult.getItems();
                            final List<Uri> allUris = new ArrayList<>();
                            for (StorageReference ref : allReferences) {
                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        allUris.add(uri);
                                        mImageRepo.postValue(allUris);
                                    }
                                });
                            }
                        }
                    });
        }
        return mImageRepo;
    }

}