package edu.niu.cs.z1804855.gamedevelopment;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by vyshu on 10/27/2017.
 */

public class Background {

    private Bitmap image;
    private int x,y,dx;

    public Background(Bitmap res){
        image=res;
        dx=GamePanel.MOVESPEED;

    }

    //purpose of the update method is to do animation and the update the game screen
    public void update(){
        x=x+dx;

        if(x<- GamePanel.WIDTH)
        {
            x=0;
        }

    }

    public void draw(Canvas canvas){

        canvas.drawBitmap(image,x,y,null);

        if(x<0){
            canvas.drawBitmap(image,x+GamePanel.WIDTH,y,null);
        }
     }


}
