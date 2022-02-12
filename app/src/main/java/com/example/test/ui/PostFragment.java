package com.example.test.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.test.R;
import com.example.test.model.PostResult;
import com.example.test.databinding.FragmentPostBinding;
import com.example.test.viewmodels.PostViewModel;


public class PostFragment extends Fragment {
    private FragmentPostBinding binding;
    private PostViewModel postViewModel;
    private PostAdapter postAdapter;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        getData();
    }

    private void initView() {
        postViewModel = new ViewModelProvider(getActivity(),
                new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).
                get(PostViewModel.class);

        navController = Navigation.findNavController(getView());

        postAdapter = new PostAdapter();
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        binding.recyclerView.setHasFixedSize(true);
        postAdapter.setOnItemClick(new PostAdapter.OnItemClick() {
            @Override
            public void onClick(PostResult postResult) {
                Toast.makeText(getContext(), String.valueOf(postResult.getId()), Toast.LENGTH_SHORT).show();
                postViewModel.setPost(postResult);
                navController.navigate(R.id.action_postFragment_to_postDetailFragment);
            }
        });
    }

    private void getData() {
        postViewModel.getPostList().observe(getActivity(), postResults -> {
            binding.recyclerView.setAdapter(postAdapter);
            postAdapter.setData(postResults);
        });

        postViewModel.getPostLiveData().observe(getActivity(), PostResult -> {
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        postViewModel.save();
    }
}