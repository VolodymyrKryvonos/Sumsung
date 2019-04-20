package com.example.vova.matesha;

import android.content.Intent;
import android.support.v4.app.Fragment;

public class HolderActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
            return new TheoryFragment();
    }
}
