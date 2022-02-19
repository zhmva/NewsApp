package com.example.newsapp.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsapp.MainActivity;
import com.example.newsapp.R;

import javax.annotation.Nullable;

public abstract class BaseFragment<VB extends ViewBinding> extends Fragment {

    protected VB binding;
    protected NavController controller;
    protected MainActivity activity;
    protected abstract VB bind();

    public BaseFragment() {
        // Required empty public constructor
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = bind();
        activity = (MainActivity) requireActivity();
        controller = Navigation.findNavController(requireActivity(), R.id.nav_host);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        setupViews();
        setupObservers();
    }

    protected abstract void init();
    protected abstract void setupViews();
    protected abstract void setupObservers();
}

