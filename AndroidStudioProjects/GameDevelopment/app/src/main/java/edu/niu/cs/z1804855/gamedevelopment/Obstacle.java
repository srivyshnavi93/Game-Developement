package edu.niu.cs.z1804855.gamedevelopment;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by vyshu on 10/27/2017.
 */

public class Obstacle extends GameObject {

    private int score;
    private int speed;
    private Random rand=new Random();
    private Animation animation=new Animation();

    private Bitmap spritesheet;



    public Obstacle(Bitmap res,int x,int y,int w,int h,int s,int numFrames){

        super.x=x;
        super.y=y;
        width=w;
        height=h;
        score=s;
        speed=11;
        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;

        for(int i = 0; i<image.length;i++)
        {
            image[i] = Bitmap.createBitmap(spritesheet, i*width,0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(100-speed);
    }




    public void update(){
        x-=speed;
        animation.update();

    }

    public void draw(Canvas canvas){

        try{

            canvas.drawBitmap(animation.getImage(),x-30,y,null);
        }catch(Exception e){}
    }

    @Override
    public int getWidth(){
        return width-10;
    }
}
