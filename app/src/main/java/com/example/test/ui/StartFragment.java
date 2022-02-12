package com.example.test.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.R;
import com.example.test.databinding.FragmentStartBinding;
import com.example.test.model.User;
import com.example.test.viewmodels.UserViewModel;

public class StartFragment extends Fragment {
    private UserViewModel userViewModel;
    private FragmentStartBinding binding;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_start,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        navController = Navigation.findNavController(getView());
        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        userViewModel.getUserLiveData().observe(getActivity(), user -> { });
        userViewModel.setUserLiveData(new User(getResources().getString(R.string.s_user_name)));
        binding.setUser(userViewModel);
        binding.setLifecycleOwner(getActivity());
        binding.btnNext.setOnClickListener(v -> {
            navController.navigate(R.id.action_startFragment_to_postFragment);
        });
    }
}