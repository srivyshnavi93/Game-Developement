package edu.niu.cs.z1804855.gamedevelopment;

import android.graphics.Canvas;
import android.provider.Settings;
import android.view.SurfaceHolder;

/**
 * Created by vyshu on 10/26/2017.
 */

public class MainThread extends Thread {



    private int FPS=30;
    private double averageFPS;
    private boolean running;

    private SurfaceHolder surfaceHolder;

    private Canvas canvas;

    private GamePanel gamePanel;

    public MainThread(SurfaceHolder surfaceHolder,GamePanel gamePanel){

        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;

    }// end of constructor
    @Override
    public  void run(){

        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime=0;

        int frameCount=0;
        long targetTime=1000/FPS;

        while(running){

            startTime= System.nanoTime();

            canvas=null;

            try{

                canvas=this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){

                 this.gamePanel.update();

                    this.gamePanel.draw(canvas);

                }

            }catch(Exception e){

            }
            finally {
                if(canvas!=null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch(Exception e){
                       e.printStackTrace();
                    }
                }
            }

            timeMillis=(System.nanoTime()-startTime)/1000000;
            waitTime=targetTime-timeMillis;
            try{
                this.sleep(waitTime);

            }catch (Exception e){

            }
            totalTime=totalTime+System.nanoTime()-startTime;
            frameCount++;

            if(frameCount==FPS){
                averageFPS =1000/((totalTime/frameCount)/1000000);
                frameCount=0;
                totalTime=0;
                System.out.println("Average frames per second speed" + averageFPS);
            }
        }
        

    }// end of run method

    public void setRunning(boolean b){

        running=b;
    }

}// end of Thread class
