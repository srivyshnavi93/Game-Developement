package edu.niu.cs.z1804855.gamedevelopment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by vyshu on 10/30/2017.
 */

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        Thread myThread=new Thread(){
            @Override
            public void run() {
                try{

                    sleep(4000);
                    Intent startgame =new Intent(getApplicationContext(),GameMenu.class);
                    startActivity(startgame);
                    finish();




                }catch(InterruptedException e){e.printStackTrace();}

            }//end run
        };//end thread
        myThread.start();

    }//end method




}//end class

