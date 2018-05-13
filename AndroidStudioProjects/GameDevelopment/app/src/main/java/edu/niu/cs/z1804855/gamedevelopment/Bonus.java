package edu.niu.cs.z1804855.gamedevelopment;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by vyshu on 10/27/2017.
 */

public class Bonus extends GameObject{
    /**
     *Vars we need for the enemy
     */
    //the score (when we hit an enemy increase score)
    private int score;

    //the speed of the enemy
    private int speed;
    //the random class (we want the enemies to have different speeds)
    private Random rand = new Random();
    //the animation class
    private Animation animation = new Animation();
    //the Bitmap ref of the image
    private Bitmap spritesheet;


    //we create the enemy in the constructor
    public Bonus(Bitmap res, int x, int y, int w, int h,  int numFrames)
    {
//we need to get the coordinates from the Abstract class GameObject
        super.x = x;
        super.y = y;
        width = w;
        height = h;


        //we set every gameloop a diff speed for the enemy
        speed = 4 + (int) (rand.nextDouble()*score/30);

        //this is the limit- max speed
        if(speed>40)speed = 40;

        //we create a bitmap obj so we can save later all the version of our image
        //to help us animate
        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;

        //the loop help us to save all the version of the enemy in the Bitmap table
        for(int i = 0; i<image.length;i++)
        {
            image[i] = Bitmap.createBitmap(spritesheet, i*width, 0, width, height);
        }

        //we animate the enemy
        animation.setFrames(image);
        //we set the delay of animation
        animation.setDelay(100-speed);



    }//end


    public void update(){

//we set the movement of the enemy
        //The enemy we will move every time closer to the hero
        x-=speed;
        animation.update();



    }

    public void draw(Canvas canvas){




//last we draw the Enemy
        try{
            canvas.drawBitmap(animation.getImage(),x,y,null);
        }catch(Exception e){}



    }//end draw


    @Override
    public int getWidth()
    {
        //offset slightly for more realistic collision detection
        return width-10;
    }

/**
 * In this lesson we will add the enemy on our screen (Gamepanel class)
 */

}//end class
