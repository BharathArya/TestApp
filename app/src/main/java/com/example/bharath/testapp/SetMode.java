package com.example.bharath.testapp;

import android.media.AudioManager;
import android.widget.TextView;

/**
 * Created by Bharath on 06-10-2017.
 */

public class SetMode {

    public void setMode(TextView res, String result, String finalCurrentTime, AudioManager audioManager)
    {
        res.setText(result.toString());
        if(finalCurrentTime.equals(res.getText().toString())){
            audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        }
    }

}
