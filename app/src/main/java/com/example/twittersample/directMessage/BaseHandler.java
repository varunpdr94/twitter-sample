package com.example.twittersample.directMessage;

import android.view.View;

public interface BaseHandler<T> {
    void onClick(View view, T data);
}
