package com.mycompany.gostrong;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.TimerTask;

/**
 * Created by Artur on 13/04/15.
 */
class IntroTimer extends TimerTask {

    Context context;

    public  IntroTimer(Context c){
        super();
        context = c;
    }

    public void run() {
        Intent intent = new Intent();
        intent.setClass(context, Principal.class);
        context.startActivity(intent);
    }
}
