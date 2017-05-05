package com.example.karo.starter;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    private LinearLayout game;
    protected GestureDetector.SimpleOnGestureListener gesture;
    private GestureDetectorCompat mDetector;
    private static final String COLOR_2 = "#EEE4DA";
    private static final String COLOR_4 = "#ede0c8";
    private static final String COLOR_8 = "#f2b121";
    private static final String COLOR_16 = "#f59563";
    private static final String COLOR_32 = "#f67c5f";
    private static final String COLOR_64 = "#f65e3b";
    private static final String COLOR_128 = "#edcf72";
    private static final String COLOR_256 = "#edcc61";
    private static final String COLOR_512 = "#EDC850";
    private static final String COLOR_1024 = "#edc53f";
    private static final String COLOR_2048 = "#EDC22E";
    //private static final int COLOR_OTHER = Color.BLACK;
    //private static final int COLOR_GAME_OVER = Color.rgb(238, 228, 218, 0.73 */
    protected ImageButton[][] box;
    protected Integer[][] id;
    protected final String empty = "#cdc1b5";
    protected int size;
    protected int[][] grid;
    protected Random rand = new Random();
    protected Board board = new Board(rand, 4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        size = board.GRID_SIZE;
        grid = board.getGrid();
        box = new ImageButton[size][size];
        id = new Integer[size][size];
        id[0][0] = R.id.button00;
        id[0][1] = R.id.button01;
        id[0][2] = R.id.button02;
        id[0][3] = R.id.button03;
        id[1][0] = R.id.button10;
        id[1][1] = R.id.button11;
        id[1][2] = R.id.button12;
        id[1][3] = R.id.button13;
        id[2][0] = R.id.button20;
        id[2][1] = R.id.button21;
        id[2][2] = R.id.button22;
        id[2][3] = R.id.button23;
        id[3][0] = R.id.button30;
        id[3][1] = R.id.button31;
        id[3][2] = R.id.button32;
        id[3][3] = R.id.button33;
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                box[i][j] = (ImageButton)findViewById(id[i][j]);
                box[i][j].setBackgroundColor(Color.parseColor(empty));
                box[i][j].setClickable(false);
            }
        }
        update();

        //TextView text = (TextView)findViewById(R.id.textView);
        //text.setText(board.toString());
        game = (LinearLayout)findViewById(R.id.gameOver);
        //setContentView(R.layout.activity_main);
        mDetector = new GestureDetectorCompat(this, new OnTouch());
        /*linear.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = MotionEventCompat.getActionMasked(event);
                    switch(action) {
                        case (MotionEvent.ACTION_DOWN) :
                            MotionEvent eve = event;
                            //check shit for when down and when move
                            return true;
                        case (MotionEvent.ACTION_MOVE) :
                    }
                    new OnTouch().onTouch(v, event);
                    return true;
                }
        });*/
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
    class OnTouch extends OnSwipeListener {
        @Override
        public boolean onSwipe(Direction direction) {
            //if(direction.equals(Direction.left))
            if(!board.isGameOver()) {
                if (direction == Direction.up) {
                    //Toast.makeText(MainActivity.this, "Up", Toast.LENGTH_SHORT).show();
                    if (board.move("UP")) {
                        //board.move(Direction.UP);
                        board.addRandomTile();
                        System.out.println("Moving UP");
                        update();
                        //Toast.makeText(MainActivity.this, direction.toString(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                } if (direction == Direction.down) {
                    //Toast.makeText(MainActivity.this, "Up", Toast.LENGTH_SHORT).show();
                    if (board.move("DOWN")) {
                        //board.move(Direction.UP);
                        board.addRandomTile();
                        System.out.println("Moving UP");
                        update();
                        //Toast.makeText(MainActivity.this, direction.toString(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                } if (direction == Direction.left) {
                    //Toast.makeText(MainActivity.this, "Up", Toast.LENGTH_SHORT).show();
                    if (board.move("LEFT")) {
                        //board.move(Direction.UP);
                        board.addRandomTile();
                        //System.out.println("Moving UP");
                        update();
                        //Toast.makeText(MainActivity.this, direction.toString(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                } if (direction == Direction.right) {
                    //Toast.makeText(MainActivity.this, "Up", Toast.LENGTH_SHORT).show();
                    if (board.move("RIGHT")) {
                        //board.move(Direction.UP);
                        board.addRandomTile();
                        //System.out.println("Moving UP");
                        update();
                        //Toast.makeText(MainActivity.this, direction.toString(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                if(board.isGameOver()) {
                    game.setVisibility(View.VISIBLE);
                }
            }
            else
                game.setVisibility(View.VISIBLE);
            //Toast.makeText(MainActivity.this, "You"+direction.toString(), Toast.LENGTH_SHORT).show();
            return false;
        }

        //Toast.makeText(MainActivity.this, "You swiped LEFT "+direction.toString(), Toast.LENGTH_SHORT).show();
        public void onTouch(View v, MotionEvent event) {
            OnSwipeListener p = new OnTouch();
            p.onFling(event, event, x1, x2);
        }
    }
    protected void update() {
        //we start by removing the text score
            grid = board.getGrid();
            for(int i = 0; i < size; i++) {
                for(int j = 0; j< size; j++) {
                    //checks the grid values and assigns it based on that
                    if (grid[i][j] == 2) {
                        box[i][j].setAdjustViewBounds(true);
                        box[i][j].setScaleType(ImageView.ScaleType.CENTER_CROP);
                        box[i][j].setImageResource(R.drawable.image2);
                        box[i][j].setBackgroundColor(Color.parseColor(COLOR_2));
                    }
                    else if (grid[i][j] == 4) {
                        box[i][j].setImageResource(R.drawable.image4);
                        box[i][j].setAdjustViewBounds(true);
                        box[i][j].setScaleType(ImageView.ScaleType.CENTER_CROP);
                        box[i][j].setBackgroundColor(Color.parseColor(COLOR_4));
                    }
                    else if (grid[i][j] == 8) {
                        box[i][j].setAdjustViewBounds(true);
                        box[i][j].setScaleType(ImageView.ScaleType.CENTER_CROP);
                        box[i][j].setImageResource(R.drawable.image8);
                        box[i][j].setBackgroundColor(Color.parseColor(COLOR_8));
                    }
                    else if (grid[i][j] == 16) {
                        box[i][j].setAdjustViewBounds(true);
                        box[i][j].setScaleType(ImageView.ScaleType.CENTER_CROP);
                        box[i][j].setImageResource(R.drawable.image16);
                        box[i][j].setBackgroundColor(Color.parseColor(COLOR_16));
                    }
                    else if (grid[i][j] == 32) {
                        box[i][j].setAdjustViewBounds(true);
                        box[i][j].setScaleType(ImageView.ScaleType.CENTER_CROP);
                        box[i][j].setImageResource(R.drawable.image32);
                        box[i][j].setBackgroundColor(Color.parseColor(COLOR_32));
                    }

                    else if (grid[i][j] == 64) {
                        box[i][j].setAdjustViewBounds(true);
                        box[i][j].setScaleType(ImageView.ScaleType.CENTER_CROP);
                        box[i][j].setImageResource(R.drawable.image64);
                        box[i][j].setBackgroundColor(Color.parseColor(COLOR_64));
                    }
                    else if (grid[i][j] == 128) {
                        box[i][j].setAdjustViewBounds(true);
                        box[i][j].setScaleType(ImageView.ScaleType.CENTER_CROP);
                        box[i][j].setImageResource(R.drawable.image128);
                        box[i][j].setBackgroundColor(Color.parseColor(COLOR_128));
                    }
                    else if (grid[i][j] == 256) {
                        box[i][j].setAdjustViewBounds(true);
                        box[i][j].setScaleType(ImageView.ScaleType.CENTER_CROP);
                        box[i][j].setImageResource(R.drawable.image256);
                        box[i][j].setBackgroundColor(Color.parseColor(COLOR_256));
                    }
                    else if (grid[i][j] == 512) {
                        box[i][j].setAdjustViewBounds(true);
                        box[i][j].setScaleType(ImageView.ScaleType.CENTER_CROP);
                        box[i][j].setImageResource(R.drawable.image512);
                        box[i][j].setBackgroundColor(Color.parseColor(COLOR_512));
                    }
                    else if (grid[i][j] == 1024) {
                        box[i][j].setAdjustViewBounds(true);
                        box[i][j].setScaleType(ImageView.ScaleType.CENTER_CROP);
                        box[i][j].setImageResource(R.drawable.image1024);
                        box[i][j].setBackgroundColor(Color.parseColor(COLOR_1024));
                    }
                    else if (grid[i][j] == 2048) {
                        box[i][j].setAdjustViewBounds(true);
                        box[i][j].setScaleType(ImageView.ScaleType.CENTER_CROP);
                        box[i][j].setImageResource(R.drawable.image2048);
                        box[i][j].setBackgroundColor(Color.parseColor(COLOR_2048));
                    }
                    else {
                        box[i][j].setImageResource(R.drawable.imdp);
                        box[i][j].setBackgroundColor(Color.parseColor(empty));
                        //box[i][j].setImageResource(R);
                    }
                }
            }


    }
}
