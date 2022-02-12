package com.example.test.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.MyApplication;
import com.example.test.R;
import com.example.test.databinding.FragmentPostDetailBinding;
import com.example.test.viewmodels.PostViewModel;


public class PostDetailFragment extends Fragment {
    private PostViewModel postViewModel;
    private FragmentPostDetailBinding binding;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_detail, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initViewModel();
    }

    private void initViewModel() {
        postViewModel = new ViewModelProvider(getActivity(),
                new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).
                get(PostViewModel.class);

        postViewModel.getPostLiveData().observe(getActivity(),postResult -> { });
        binding.setLifecycleOwner(getActivity());
        binding.setData(postViewModel);
    }

    private void initView() {
        navController = Navigation.findNavController(getView());
        binding.container.setOnClickListener(v ->{
            navController.popBackStack();
        });
    }
}