package com.example.testgage.Network;

import androidx.annotation.NonNull;

public interface AntibioticCallbacks {
    void onSuccess(@NonNull String data);

    void onError(@NonNull Throwable throwable);
}
