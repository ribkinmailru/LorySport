package com.example.lorysport;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

import androidx.annotation.Nullable;

public class CustomEditText extends androidx.appcompat.widget.AppCompatEditText {
    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            dispatchKeyEvent(event);
            return false;
        }
        return super.onKeyPreIme(keyCode, event);
    }
}

