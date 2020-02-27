package com.comsats.ars.ui.images;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.FirebaseApp;

public class ImagesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ImagesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");


    }

    public LiveData<String> getText() {
        return mText;
    }

}