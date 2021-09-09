package com.zx.test.hilt.demo3;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * ZhangSuiXu
 * 2021/9/6
 */
@HiltViewModel
public final class TestHiltViewModel extends ViewModel {

    private final TestHiltRepository repository;

//    @Inject
//    TestHiltViewModel(TestHiltRepository repository, SavedStateHandle savedStateHandle) {
//        this.repository = repository;
//    }

    @Inject
    TestHiltViewModel(TestHiltRepository repository) {
        this.repository = repository;
    }

    public void test() {
        repository.test();
    }


}
