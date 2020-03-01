package com.comsats.ars.ui.images;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.comsats.ars.R;
import com.comsats.ars.adapter.ImageListAdapter;
import com.comsats.ars.data.FSItem;
import com.comsats.ars.utils.AppSettings;

import java.util.List;

public class ImagesFragment extends Fragment {

    private ImagesViewModel galleryViewModel;
    private RecyclerView mRecyclerView;
    private ImageListAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = new ViewModelProvider(this).get(ImagesViewModel.class);
        String username = new AppSettings(getActivity()).getLoginUsername();
        if (username != null) {
            String path = username + "/image";
            galleryViewModel.setRefPath(path);
        }

        View root = inflater.inflate(R.layout.fragment_images, container, false);
        mRecyclerView = root.findViewById(R.id.gallery_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new ImageListAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);

        galleryViewModel.getImageUri().observe(getViewLifecycleOwner(), new Observer<List<FSItem>>() {
            @Override
            public void onChanged(List<FSItem> uris) {
                mAdapter.updateList(uris);
            }
        });
        return root;

    }
}