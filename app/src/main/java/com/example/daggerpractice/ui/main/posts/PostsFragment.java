package com.example.daggerpractice.ui.main.posts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daggerpractice.R;
import com.example.daggerpractice.models.Post;
import com.example.daggerpractice.ui.main.Resource;
import com.example.daggerpractice.util.VerticalSpacingItemDecoration;
import com.example.daggerpractice.viewModels.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class PostsFragment extends DaggerFragment {
    private static final String TAG = "PostsFragment";


    private PostsViewModel viewModel;

    @Inject
    PostsRecyclerAdapter mPostRecyclerAdaper;

    @Inject
    ViewModelProviderFactory providerFactory;

    private RecyclerView mRecycerView;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mRecycerView = view.findViewById(R.id.recycler_view);

        viewModel = ViewModelProviders.of(this,providerFactory).get(PostsViewModel.class);

        initRecyclerView();
        subscribeObservers();
    }

    private void subscribeObservers() {
        viewModel.observePosts().removeObservers(getViewLifecycleOwner());
        viewModel.observePosts().observe(getViewLifecycleOwner(), new Observer<Resource<List<Post>>>() {
            @Override
            public void onChanged(Resource<List<Post>> listResource) {
                if (listResource != null) {
                    switch (listResource.status) {
                        case ERROR:
                            Log.e(TAG, "onChanged: On ERROR" + listResource.message);
                            break;

                        case SUCCESS:
                            if (listResource.data != null) {
                                mPostRecyclerAdaper.setPosts(listResource.data);
                                Log.d(TAG, "onChanged: ON SUCCESS" + listResource.data);
                            }

                            break;

                        case LOADING:
                            Log.d(TAG, "onChanged: LOADING");
                            break;
                    }
                }
            }
        });
    }

    private void initRecyclerView() {
        mRecycerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        VerticalSpacingItemDecoration itemDecoration = new VerticalSpacingItemDecoration(15);
        mRecycerView.addItemDecoration(itemDecoration);
        mRecycerView.setAdapter(mPostRecyclerAdaper);
    }
}
