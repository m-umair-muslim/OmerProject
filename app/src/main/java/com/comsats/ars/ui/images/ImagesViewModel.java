package com.comsats.ars.ui.images;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.comsats.ars.data.FSItem;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ImagesViewModel extends ViewModel {

    private MutableLiveData<List<FSItem>> mImageRepo;
    private String mRefPath;

    public ImagesViewModel() {
        mImageRepo = new MutableLiveData<>();
    }

    public void setRefPath(String path) {
        mRefPath = path;
    }

    public LiveData<List<FSItem>> getImageUri() {
        if (mImageRepo.getValue() == null) {
            FirebaseStorage.getInstance()
                    .getReference()
                    .child(mRefPath)
                    .listAll()
                    .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                        @Override
                        public void onSuccess(ListResult listResult) {
                            List<StorageReference> allReferences = listResult.getItems();
                            final List<FSItem> allUris = new ArrayList<>();
                            for (StorageReference ref : allReferences) {
                                Task<Uri> uri = ref.getDownloadUrl();
                                Task<StorageMetadata> meta = ref.getMetadata();
                                try {
                                    Tasks.whenAllSuccess(uri, meta).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
                                        @Override
                                        public void onSuccess(List<Object> objects) {
                                            FSItem item = new FSItem();
                                            for (Object obj : objects) {
                                                if (obj instanceof Uri) {
                                                    item.uri = (Uri) obj;
                                                } else if (obj instanceof StorageMetadata) {
                                                    item.creationDate = ((StorageMetadata) obj).getCreationTimeMillis();
                                                }
                                            }
                                            allUris.add(item);
                                            mImageRepo.postValue(allUris);
                                        }
                                    });
                                } catch (Exception ignored) {
                                }
                            }
                        }
                    });
        }
        return mImageRepo;
    }

}