package com.example.newsapp.ui.news;

import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.newsapp.base.BaseFragment;
import com.example.newsapp.common.OnSearchListener;
import com.example.newsapp.common.Resource;
import com.example.newsapp.data.models.MainResponse;
import com.example.newsapp.databinding.FragmentNewsBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NewsFragment extends BaseFragment<FragmentNewsBinding> implements OnSearchListener {


    private NewsAdapter adapter;
    private NewsViewModel viewModel;

    @Override
    protected FragmentNewsBinding bind() {
        return FragmentNewsBinding.inflate(getLayoutInflater());
    }


    @Override
    protected void init() {
        activity.setListener(this);
        adapter = new NewsAdapter();
        viewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
        viewModel.getTopNews();
    }

    @Override
    protected void setupViews() {
        binding.recycler.setAdapter(adapter);
    }

    @Override
    protected void setupObservers() {
        viewModel.newsLiveData.observe(getViewLifecycleOwner(), new Observer<Resource<MainResponse>>() {
                    @Override
                    public void onChanged(Resource<MainResponse> resource) {
                        switch (resource.status) {
                            case LOADING: {
                                binding.progress.setVisibility(View.VISIBLE);
                                binding.recycler.setVisibility(View.GONE);
                                break;
                            }
                            case SUCCESS: {
                                binding.progress.setVisibility(View.GONE);
                                binding.recycler.setVisibility(View.VISIBLE);
                                adapter.setNews(resource.data.getArticles());
                                break;
                            }
                            case ERROR: {
                                binding.progress.setVisibility(View.GONE);
                                binding.recycler.setVisibility(View.GONE);
                                Snackbar.make(binding.getRoot(), resource.msg, BaseTransientBottomBar.LENGTH_LONG).show();
                                break;
                            }
                        }
                    }
                });
    }

    @Override
    public void onSubmit(String s) {
        viewModel.getNewsByKeyWord(s);
    }

    @Override
    public void onTextChanged(String s) {
        if (s.isEmpty()) {
            viewModel.getTopNews();
        } else {
            viewModel.getNewsByKeyWord(s);
        }
    }
}