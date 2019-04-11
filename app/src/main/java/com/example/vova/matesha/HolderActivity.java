package com.example.vova.matesha;

import android.support.v4.app.Fragment;
import android.util.Log;

public class HolderActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new TheoryFragment();
    }
}
