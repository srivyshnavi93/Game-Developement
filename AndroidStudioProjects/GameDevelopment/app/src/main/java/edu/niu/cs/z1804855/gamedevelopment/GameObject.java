package edu.niu.cs.z1804855.gamedevelopment;

import android.graphics.Rect;

/**
 * Created by vyshu on 10/27/2017.
 */

public abstract class GameObject {

    protected int x;
    protected int y;
    protected int dx;
    protected int dy;
    protected int width;
    protected int height;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    public int getWidth() {
        return width;
    }

   public int getHeight() {
        return height;
    }

    public Rect getRectangle(){
        return new Rect(x,y,x+width,y+height);
    }


}//end of class
