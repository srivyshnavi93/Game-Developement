package edu.niu.cs.z1804855.gamedevelopment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

/**
 * In this lesson, we will not add the Borders.
 *I believe, that now, you have the knowledge
 *to add the borders on your own. Instead in this lesson we will create
 *a very similar class the Obstacle
 */

//YOU NEED TO PLACE THE BORDERS - I BELIEVE IN YOU!
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private Random rand = new Random();


    //Now we will set our game screen width and height
    public static final int WIDTH = 856;
    public static final int HEIGHT = 480;
    /**
     *2. And here we will create a new var for the speed
     */

    Bitmap hearta;
    Bitmap heartb;
    Bitmap heartc;

    private int hearts=3;


    Bitmap coinbonus;
    public int herocoins;
    private long bonusStartTime;
    private ArrayList<Bonus> mycoins;

    MediaPlayer mp;
    SoundPool coinsound;
    int coinsoundid;

    Bitmap mypanel;

    public static final int MOVESPEED = -5;


    //We also need an obj reference of the Background class
    private Background bg;

    //The same process as Background we create an object ref of our hero
    private Hero hero;
    //and now lets do some coding at the surfaceCreated method


    /**
     * 1st for our bullet we create an Arraylist obj refernce
     * and also a timer reference
     * ArrayList supports dynamic arrays that can grow as needed.
     * im creating an ArrayList cause i dont know how many frames
     * ur unique bullet image has
     */
    private ArrayList<Bullet> bullet;

    private long bulletStartTime;

    //step 1:  we set refs for an Arraylist and the enemy Timer

    private ArrayList<Enemy> alien;
    private long  alienStartTime;

    private ArrayList<Obstacle> obstacle;
    private long  obstacleStartTime;

    //add border ref
    private ArrayList<Borderbottom> botborder;
    private long  borderStartTime;

    private boolean newGameCreated;
    private long startReset;
    private boolean reset;
    private boolean dissapear;
    private boolean started;

    private Explosion explosion;

    private int best;





    /**Now we need a thread
     * what is this?
     * */

    /**Do you Remember when we created this Thread object? Now that we created the Mainthread class
     * we will use this object to start every time the game loop or destroy it....
     */
    //reference of the MainThread obj
    private MainThread thread;

    //lets create the constructor of our new class,that is going to help us calling objects and methods!
    public  GamePanel(Context context){
/**What is Context?
 //As the name suggests, it's the context of current state of the application/object.
 // It lets newly-created objects understand
 // what has been going on. Typically you call it to get information regarding another part of your program
 */
        super(context);

        //game music
        mp = MediaPlayer.create(context, R.raw.arcademusicloop);

        //coin sound
        coinsound = new SoundPool(99, AudioManager.STREAM_MUSIC,0);
        coinsoundid= coinsound.load(context, R.raw.pickedcoin,1);

        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);
/**
 * We forgot to create the thread object inside the GamePanel Constructor
 */
        //object


        //make gamePanel focusable so it can handle events
        setFocusable(true);




    }//end of content view construcron


    /**

     This is called immediately after the surface is first created.
     Implementations of this should start up whatever rendering code they desire.
     Note that only one thread can ever draw into a Surface,
     so you should not draw into the Surface here if your normal rendering will be in another thread.
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        /**
         * We need to move the thread object inside the created method
         * so we dont have lag
         */
        thread = new MainThread(getHolder(), this);
        //in our screen we draw our image
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background));
        //we create the hero obj
        hero = new Hero(BitmapFactory.decodeResource(getResources(), R.drawable.hero), 45, 45, 2);
        /**
         * 2nd We create bullet arraylist object
         * and we also set the bullet timer to the system's timer
         */
        bullet = new ArrayList<Bullet>();
        bulletStartTime=  System.nanoTime();

        //step 2: we create the enemy obj
        alien=new ArrayList<Enemy>();
        alienStartTime= System.nanoTime();

        obstacle=new ArrayList<Obstacle>();
        obstacleStartTime= System.nanoTime();

        //create obj bot border
        botborder=new ArrayList<Borderbottom>();
        borderStartTime= System.nanoTime();

        mycoins = new ArrayList<Bonus>();
        bonusStartTime = System.nanoTime();






        //the hard part now




        //lets set our dx -5 so our bg image slowly move off the screen with -5 speed
/**
 * lets delete that cause we delete the setVector method
 * and lets go to the top to create a new var for the speed
 */

        //if you like you can set dx different values and see what happens when we eventually run the game...
        //we can safely start the game loop


        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();
    }

    /**

     This is called immediately after any structural changes (format or size) have been made to the surface.
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }
    /**
     This is called immediately before a surface is being destroyed.
     After returning from this call, you should no longer try to access this surface.
     If you have a rendering thread that directly accesses the surface, you must ensure
     that thread is no longer touching the Surface before returning from this function.
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;

        int counter=0;
        /**
         *The join() method is used to hold the execution of currently running
         *thread until the specified thread is dead(finished execution).
         */
        while(retry && counter < 100)
        {
            try{
                thread.setRunning(false);
/**
 *What is a join thread..?
 */
                thread.join();

                /**
                 * here were we stop the thread  we need to set thread to null
                 * so that the garbage collector can pick the object
                 */
                retry=false;
                thread=null;

            }catch (InterruptedException e){e.printStackTrace();}




        }//end while

    }
    /**
     //Also we need Override onTouchEvent method to know if we touch or not the screen
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //and here we want our hero do smthing when we touch the screen


        if(event.getAction()==MotionEvent.ACTION_DOWN){
            if(!hero.getPlaying() && newGameCreated && reset)
            {
                hero.setPlaying(true);
                hero.setUp(true);
            }
            if(hero.getPlaying())
            {

                if(!started){
                    started=true;
                }
                reset=false;
                hero.setUp(true);
            }
            return true;
        }
        if(event.getAction()==MotionEvent.ACTION_UP)
        {
            hero.setUp(false);
            return true;
        }


        return super.onTouchEvent(event);
    }


    //in our content view (Gamepanel we must constantly update our image )

    /**
     *constantly? What do you mean by that....
     */
    /**
     *I hope that you remember that our Gamepanel cooperates with our thread
     *So our game run a new gameloop every second....
     */


    //update method
    public void update()
    {
        //here we will update the hero actions every sec
        if(hero.getPlaying()) {

            mp.start();

            bg.update();
            hero.update();


            //start the coin timer
            long bonustime = (System.nanoTime() - bonusStartTime)/1000000;

            //if we are good player then coins will be appeared more often on screen...
            if(((bonustime >(3000 - hero.getScore()/4)) ) ){

                //we create a coin
                mycoins.add(new Bonus((BitmapFactory.decodeResource(getResources(), R.drawable.coin)),
                        WIDTH + 1, (int) (rand.nextDouble() * (HEIGHT -200)), 20, 20, 1));
                bonusStartTime = System.nanoTime();

            }//end if

            //we need the for loop in case you have more than one sprites to animate...
            for(int i = 0; i<mycoins.size();i++)
            {
                mycoins.get(i).update();

                //every time we collide with a coin we increase the herocoin var
                if (collision(mycoins.get(i), hero)) {
                    coinsound.play(coinsoundid,5,5,1,0,1);

                    mycoins.remove(i);
                    herocoins+=1;
                    break;
                }

                //we remove the coins that are off the screen...
                if(mycoins.get(i).getX()<-100)
                {
                    mycoins.remove(i);

                    break;
                }

            }//end for






















            //add bot border behavior

            long borderElapsed = (System.nanoTime()-borderStartTime)/1000000;

            if(borderElapsed >100 ) {
                botborder.add(new Borderbottom(BitmapFactory.decodeResource(getResources(), R.drawable.borderbottom), WIDTH + 10,((HEIGHT -80)+rand.nextInt(10))));
                //add top border?
                botborder.add(new Borderbottom(BitmapFactory.decodeResource(getResources(), R.drawable.bordertop), WIDTH + 10,((HEIGHT -540)+rand.nextInt(10))));


                borderStartTime = System.nanoTime();


            }//end if

            //loop through every border block and check collision and remove
            for(int i = 0; i<botborder.size();i++) {
                //update obstacle
                botborder.get(i).update();


                if (collision(botborder.get(i), hero)) {

                    hero.setPlaying(false);

                    break;

                }


                //if statement to remove border if is of the screen limits
                if( botborder.get(i).getX()<10)
                {
                    botborder.remove(i);
                }
            }//end





















            //add bot obstacle

            long obstacleElapsed = (System.nanoTime()-obstacleStartTime)/1000000;




            if(obstacleElapsed >(15000 - hero.getScore()/4)){

                //bot obstacle appear.
                obstacle.add(new Obstacle(BitmapFactory.decodeResource(getResources(), R.drawable.obstacle),
                        WIDTH + 10,  HEIGHT -290+rand.nextInt(150) , 90, 300, hero.getScore(), 1));

                //the height of the obstacle is going to be between 190px and 340px
                //remember the game HEIGHT is 480px



                //reset timer
                obstacleStartTime = System.nanoTime();
            }

            //loop through every alien and check collision and remove
            for(int i = 0; i<obstacle.size();i++) {
                //update obstacle
                obstacle.get(i).update();


                if (collision(obstacle.get(i), hero)) {

                    hero.setPlaying(false);

                    break;
                }
            }


















/**
 *3rd we add the bullet on timer
 */

            long bullettimer = (System.nanoTime() - bulletStartTime)/1000000;

            //check the delay among bullets fired from the hero
            //in simple words when a bullet will appear on the screen
            //and we want every sec our next bullet to be faster than the previous one
            //that depends of how good our hero is (high score we fire faster)
            if(bullettimer >(2500 - hero.getScore()/4)){
                //positioning of amo fire from hero

                bullet.add(new Bullet((BitmapFactory.decodeResource(getResources(), R.drawable.bullet)),hero.getX()+60, hero.getY()+24,15,7,7));
                bulletStartTime = System.nanoTime();

            }
//this is the common for loop to animate and update the frames of the bullet image
            //bullet.png has 7 frames
            for(int i = 0; i<bullet.size();i++)
            {
                bullet.get(i).update();
                //if statement to remove bullet if is of the screen limits
                if(bullet.get(i).getX()<-10)
                {
                    bullet.remove(i);
                }
            }



//step 3: Behavior/movement of the enemy
            //add enemy aliens


//set alien timer to millis
            long alienElapsed = (System.nanoTime()-alienStartTime)/1000000;



//the first enemy will appear after 10 sec and then more often by the time
            if(alienElapsed >(10000 - hero.getScore()/4)){


                alien.add(new Enemy(BitmapFactory.decodeResource(getResources(), R.drawable.enemy),
                        WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT - 50)), 40, 60, hero.getScore(), 3));

                //reset timer
                alienStartTime = System.nanoTime();
            }

            //loop through every alien
            for(int i = 0; i<alien.size();i++) {
                //update alien
                alien.get(i).update();

                if (collision(alien.get(i), hero)) {

                    alien.remove(i);

                     hearts--;
                 //   hero.setPlaying(false);

                    break;
                }



                //remove alien if it is way off the screen
                if (alien.get(i).getX() < -100) {
                    alien.remove(i);
                    break;
                }

                //collision alien with bullet (fire)
                for (int j = 0; j < bullet.size(); j++) {


                    if(collision(alien.get(i),bullet.get(j)))
                    {

                        explosion=new Explosion(BitmapFactory.decodeResource(getResources(),R.drawable.explosion),alien.get(i).getX(),alien.get(i).getY(),100,100,15);

                        alien.remove(i);
                        bullet.remove(j);
                        best+=30;


                        break;
                    }
                    bullet.get(j).update();


                }//end alien bullet collision



            }//end enemy


        }//end if playing


        else{
            hero.resetDYA();
            if(!reset){
                newGameCreated=false;
                startReset=System.nanoTime();
                reset=true;
                dissapear=true;
                explosion=new Explosion(BitmapFactory.decodeResource(getResources(),R.drawable.explosion),hero.getX(),hero.getY(),100,100,15);
            }
            explosion.update();

            long resetElapsed=(System.nanoTime()-startReset)/1000000;

            if(resetElapsed >2500 && !newGameCreated) {
                newGame();
            }// end if
        }// end else

    }//end update


    //and now the hard part....We will develop the Gamepanel draw method in our next lesson

    //we need a draw method also...


    /**
     * In this lesson we will create the collision method
     *
     */

    public boolean collision(GameObject a, GameObject b)
    {
        if(Rect.intersects(a.getRectangle(), b.getRectangle()))
        {
            return true;
        }
        return false;
    }










    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        /**
         *What will happened if our game is gonna run on a devise or devises that have different
         * width and height(bigger screens or smaller from our emulator)?
         */
/**
 *We need to scale our image for all devises...hope so
 */
/**
 *Ok we did some mistakes
 *Well no mistakes exactly our game runs but no as we want..
 *What we need to do?
 *First We will create a nodbi folder for our  images
 */
        /**
         *You can check more ifo about dpi folder at the android site
         *
         */

/**
 *Secondly Our image didnt scale properly because we set  float vars to get the X and Y but
 *the result would be always integer(getWidth() an WIDTH is integer)
 *We
 */
        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEIGHT*1.f);

        //so if smthing appears on our screen we must scale it

        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            //and last we draw the hero
            if(!dissapear) {
                hero.draw(canvas);
            }
            /**
             * then we draw the bullet
             * we need a for loop here
             * because we used the Arraylist table
             * so we need to draw all the versions of the bullet
             */
            for(Bullet fp: bullet)
            {
                fp.draw(canvas);
            }

            //step 4: we draw the enemy
            //draw alien slug
            for(Enemy aln: alien)
            {
                aln.draw(canvas);
            }

            //draw bot obstacle
            for(Obstacle obsb: obstacle)
            {
                obsb.draw(canvas);
            }

            //draw bot border
            for(Borderbottom brb: botborder)
            {
                brb.draw(canvas);
            }

            for(Bonus mb:mycoins)
            {
                mb.draw(canvas);
            }


            if(started){
                explosion.draw(canvas);
            }


            drawText(canvas);

            canvas.restoreToCount(savedState);
        }//end canvas


/**
 * What happened why we cant see nothing?
 * Well we didnt draw our image our bg image is a blank png
 * so in our bext lesson we will open gimp and draw smthing :)
 */








    }//end draw

    public void newGame(){

        dissapear=false;
        alien.clear();
        obstacle.clear();

        botborder.clear();
        bullet.clear();
        mycoins.clear();

        hero.resetDYA();
        hero.resetScore();
        hero.setY(HEIGHT/2);

        newGameCreated=true;

    }// end method

    public void drawText(Canvas canvas){
        Paint paint=new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(30);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
        canvas.drawText("Distance: "+(hero.getScore()*2),10,HEIGHT-10,paint);
        canvas.drawText("Score: "+best,WIDTH-215,HEIGHT-10,paint);

        coinbonus = BitmapFactory.decodeResource(getResources(), R.drawable.coin);
        canvas.drawBitmap(coinbonus, WIDTH - 130, 0, null);
        canvas.drawText(" " + herocoins, WIDTH - 90, 25, paint);

        if (hearts==3) {
            hearta = BitmapFactory.decodeResource(getResources(), R.drawable.lifea);
            canvas.drawBitmap( hearta, WIDTH/2 -120, 0, null);
            heartb = BitmapFactory.decodeResource(getResources(), R.drawable.lifeb);
            canvas.drawBitmap( heartb, WIDTH/2 -80, 0, null);
            heartc = BitmapFactory.decodeResource(getResources(), R.drawable.lifec);
            canvas.drawBitmap( heartc, WIDTH/2 -40, 0, null);

        }
        if (hearts==2) {
            hearta = BitmapFactory.decodeResource(getResources(), R.drawable.lifea);
            canvas.drawBitmap( hearta, WIDTH/2 -120, 0, null);
            heartb = BitmapFactory.decodeResource(getResources(), R.drawable.lifeb);
            canvas.drawBitmap( heartb, WIDTH/2 -80, 0, null);

        }

        if (hearts==1) {
            hearta = BitmapFactory.decodeResource(getResources(), R.drawable.lifea);
            canvas.drawBitmap( hearta, WIDTH/2 -120, 0, null);

        }
        if (hearts==0) {
            hero.setPlaying(false);
            hearts=3;

        }

        if(!hero.getPlaying()&&newGameCreated&&reset)
        {
            Paint paint1 = new Paint();
            paint1.setTextSize(25);
            paint1.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

            mypanel = BitmapFactory.decodeResource(getResources(), R.drawable.panel);
            canvas.drawBitmap(mypanel, WIDTH/2-110,HEIGHT/2-120, null);

            canvas.drawText("PRESS TO START", WIDTH/2-100, HEIGHT/2-70, paint1);

            canvas.drawText("PRESS AND HOLD TO GO UP", WIDTH/2-75, HEIGHT/2 - 20, paint1);

            canvas.drawText("RELEASE TO GO DOWN", WIDTH/2-75, HEIGHT/2 + 20, paint1);







        }//end panel



    }//end method


}//end of class
