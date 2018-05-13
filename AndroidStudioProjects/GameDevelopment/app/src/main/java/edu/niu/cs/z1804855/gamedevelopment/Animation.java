package edu.niu.cs.z1804855.gamedevelopment;

import android.graphics.Bitmap;

/**
 * Created by vyshu on 10/27/2017.
 */

public class Animation {

    private Bitmap[] frames;

    private int currentFrame;

    private long startTime;

    private long delay;

    private boolean playedOnce;

    public void setFrames(Bitmap[] frames){
        this.frames=frames;
        currentFrame=0;
        startTime=System.nanoTime();
    }

    public void setDelay(long d){
        delay=d;
    }
    public void setFrame(int i){
        currentFrame=1;
    }

    public void update(){
        long elapsed=(System.nanoTime()-startTime)/1000000;

        if(elapsed>delay){
            currentFrame++;
            startTime=System.nanoTime();
        }
        if(currentFrame==frames.length){
            currentFrame=0;
            playedOnce=true;
        }
    }//end update


    public Bitmap getImage(){
        return frames[currentFrame];
    }

    public int getFrame(){
        return currentFrame;
    }

    public boolean playedOnce(){
        return playedOnce;
        
    }

}//end class
