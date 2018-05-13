package edu.niu.cs.z1804855.gamedevelopment;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by vyshu on 10/27/2017.
 */

public class Hero extends  GameObject {

    //hero vars we will need

    //we need a bitmap var cause our image player is gonna be 2 images actually
    private Bitmap spritesheet;

    //we need a score
    private int score;

    //A var that has the y value every time we touch the screen
    private double dya;

    //a boolean var to know if hero is going up or down
    private boolean up;

    //another boolean var to know if we start  the game..
    private boolean playing;

    private Animation animation=new Animation();

    private long startTime;


    public Hero(Bitmap res,int w,int h,int numFrames){

        x=100;
        y=GamePanel.HEIGHT/2;

        dy=0;
        score=0;
        width=w;
        height=h;

        Bitmap[] image=new Bitmap[numFrames];
        spritesheet=res;

        for(int i=0;i<image.length;i++){
            image[i]=Bitmap.createBitmap(spritesheet,i*width,0,width,height);
        }

        animation.setFrames(image);
        animation.setDelay(10);
        startTime=System.nanoTime();

    }//end of hero constructor


    public void setUp(boolean b){
        up=b;
    }

    public void update(){

        long elapsed=(System.nanoTime()-startTime)/1000000;
        if(elapsed>100){
            score++;
            startTime=System.nanoTime();
        }
        animation.update();

        if(up){
            dy=(int)(dya-=1.1);

        }else{
            dy=(int)(dya+=1.1);
        }

        if(dy>14){
            dy=14;
        }
        if(dy<-14){
            dy=-14;
        }

        y+=dy*2;
        dy=0;
    }// end of update method

    public void draw(Canvas canvas){
        canvas.drawBitmap(animation.getImage(),x,y,null);

    }//end draw


    public int getScore(){
        return score;
    }
    public boolean getPlaying(){
        return playing;

    }

    public void setPlaying(boolean b){
        playing=b;
    }

    public void resetDYA(){
        dya=0;
    }

    public void resetScore(){
        score=0;
    }
}//end class
