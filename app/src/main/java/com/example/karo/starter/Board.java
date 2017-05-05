package com.example.karo.starter;

/**
 * Created by Karo on 3/25/17.
 */

//------------------------------------------------------------------//
// Board.java                                                       //
//                                                                  //
// Class used to represent a 2048 game board                        //
//                                                                  //
// Author:  Ujjwal Gulecha, Alan Kuo                             //
// Date:    01/22/17                                                //
//------------------------------------------------------------------//

/**
 * Karapet Topchyan
 * cs8bwat
 * ktopchya@ucsd.edu
 * January 31, 2017
 *
 * File *Updated
 * This file creates the box that will have a 2d array full of int
 * values that start with 2 or 4 and will eventually increase.
 * This file will also update the array based on people's choices
 *
 * Class Description *Updated
 * This class defines the Board object parameters. This class sets the
 * constructors number of tiles. It defines the probability set to
 * random with the final int. This is then used to determine whether
 * the addRandomTile adds a value of 2 or 4 to the board. This class
 * creates the board and updates it based on the players selction in the
 * method play found in GameManager. if up, it checks if up and moves the
 * array up. same for down, left, and right.
 * Furthermore, this class can save method.
 *
 *
 * Sample Board
 * <p/>
 * 0   1   2   3
 * 0   -   -   -   -
 * 1   -   -   -   -
 * 2   -   -   -   -
 * 3   -   -   -   -
 * <p/>
 * The sample board shows the index values for the columns and rows
 * Remember that you access a 2D array by first specifying the row
 * and then the column: grid[row][column]
 *
 *
 *
 */

import java.util.*;
import java.io.*;

public class Board {
    public final int NUM_START_TILES = 2;
    public final int TWO_PROBABILITY = 90;
    public final int GRID_SIZE;


    private final Random random;
    private int[][] grid;
    private int score;

    // TODO PSA3
    // Constructs a fresh board with random tiles
    public Board(Random random, int boardSize) {
        this.random = random; // sets the member variable of random
        GRID_SIZE = boardSize; // sets the size of the game board
        grid = new int[boardSize][boardSize];
        for(int i = 0; i < NUM_START_TILES; i++){ //runs method by the final value
            addRandomTile();
        }
    }

    // TODO PSA3
    // Construct a board based off of an input file
    public Board(Random random, String inputBoard) throws IOException {
        this.random = random;// FIXME
        Scanner input = new Scanner(new File(inputBoard));//use scanner
        GRID_SIZE = input.nextInt();//finds the next Int ad assigns it to Grid Size
        score = input.nextInt();//inputs score
        grid = new int[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = input.nextInt();//assigns grid value to each
            }
        }
    }

    // TODO PSA3
    // Saves the current board to a file
    //This method takes the String inputed and saves it as a file
    //with the information of the game stored like a string.
    public void saveBoard(String outputBoard) throws IOException {
        File file = new File(outputBoard);
        PrintWriter save = new PrintWriter(file);
        save.write(""+GRID_SIZE+"\n");
        save.write(""+score+"\n");
        for(int i = 0;i < GRID_SIZE; i++) {
            for(int j = 0; j < GRID_SIZE; j++) {
                save.write((""+grid[i][j]+" "));
            }
            save.write("\n");
        }
        save.close();
    }
  /*
   Scanner input = new Scanner(new File(outputBoard));
   int i = 0;
   String s = "";
   while(input.hasNext()) {
   if(i==0) {
   s += input.next()+"\n";
   }
   else if(i==1)
   s+= input.next()+"\n";
   else if((i-2%(this.GRID_SIZE+1))==GRID_SIZE)
   s+= input.next()+"\n";
   else
   s+= input.next();
   i++;
   }
   save.println(s);
   save.close();
   */

    // TODO PSA3
    // Adds a random tile (of value 2 or 4) to a
    // random empty space on the board
    //this method creates a temp that will be changed to
    //find the total 0's then change the 0's
    //it will then assign values by the ranom value
    //zeroLocation to the empty box in the board based on
    //an array row by row 0-location.
    public void addRandomTile() {
        int numZero = 0;
        for(int i = 0; i < GRID_SIZE; i++) {
            for(int j = 0; j < GRID_SIZE; j++) {
                if(grid[i][j]==0)
                    numZero+=1;
            }
        }
        int zeroLocation = random.nextInt(numZero);
        int value = random.nextInt(98);
        int rando = 0;
        if (value < TWO_PROBABILITY) {
            rando = 2;
        }
        else
            rando = 4;
        numZero = 0; //reset the count for zeros
        for(int i = 0; i < GRID_SIZE; i++) {
            for(int j = 0; j <GRID_SIZE; j++) {
                if(grid[i][j]==0) {
                    if(numZero==zeroLocation) {
                        grid[i][j]=rando;
                    }
                    numZero++;
                }
            }
        }
    }


    // TODO PSA3
    // Flip the board horizontally or vertically,
    // Rotate the board by 90 degrees clockwise or 90 degrees counter-clockwise.
    public void flip(int change) {
        int temp = 0;
        //rotates the 2d array horizontally
        if(change==1) {
            for(int i = 0;i < GRID_SIZE; i++) {
                for(int j = 0;j < GRID_SIZE/2; j++) {
                    temp = grid[i][j];
                    grid[i][j] = grid[i][GRID_SIZE-1-j];
                    grid[i][GRID_SIZE-1-j] = temp;
                }
            }
        }
        //rotates the 2d array Vertically
        else if(change==2) {
            for(int i = 0; i <GRID_SIZE/2; i++) {
                for(int j = 0;j < GRID_SIZE; j++) {
                    temp = grid[i][j];
                    grid[i][j] = grid[GRID_SIZE-1-i][j];
                    grid[GRID_SIZE-1-i][j]=temp;
                }
            }
        }
        //rotates the 2d array clockwise by first
        //switching the values of i and j and then flipping it
        //horizontally. by using method overiding. Calling it again
        else if(change==3) {
            for(int i = 0; i < GRID_SIZE; i++) {
                for(int j = i; j < GRID_SIZE;j++) {
                    temp = grid[i][j];
                    grid[i][j] = grid[j][i];
                    grid[j][i] = temp;
                }
            }
            this.flip(1);//method overiding horiontal flip
        }

    /*for(int i = 0; i < GRID_SIZE; i++) {
     for(int j = 0; j <GRID_SIZE/2; j++) {
     int temp = grid[i][j];
     grid[i][j] = grid[i][GRID_SIZE-j-1];
     grid[i][GRID_SIZE-j-1] = temp;
     }
     }
     }  */
        //This does the same thing at first except
        //it will switch it vertically instead of horizontally
        //this code is identical other than the fact that it overides
        //the method flip(2) (vertical).
        else if(change==4) {
            for(int i = 0; i <GRID_SIZE; i++) {
                for(int j = i; j <GRID_SIZE; j++) {
                    temp = grid[i][j];
                    grid[i][j] = grid[j][i];
                    grid[j][i] = temp;
                }
            }
      /* for(int i = 0; i <GRID_SIZE; i++){
       for(int j = 0; j<GRID_SIZE/2; j++){
       int temp = grid[i][j];
       grid[i][j] = grid[i][GRID_SIZE-j-1];
       grid[i][GRID_SIZE-j-1] = temp;
       }
       }*/
            this.flip(2);//method overiding vertical flip
        }
    }

    //Complete this method ONLY if you want to attempt at getting the extra credit
    //Returns true if the file to be read is in the correct format, else return
    //false
    public static boolean isInputFileCorrectFormat(String inputFile) {
        //The try and catch block are used to handle any exceptions
        //Do not worry about the details, just write all your conditions inside the
        //try block
        try {
            //write your code to check for all conditions and return true if it satisfies
            //all conditions else return false
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // No need to change this for PSA3
    // Performs a move Operation
    public boolean canMove(String direction) {
        if(direction.equals("UP")) {
            return canMoveUp();
        }
        return false;
    }
    //This method checks if the grid 2d array can move up and if
    //it can this method will return true. if not it will return false
    public boolean canMoveUp() {
        for(int y = 0; y < grid.length-1; y++) {
            for(int x = 0; x < grid[y].length; x++) {
                if(grid[y][x]==0) { //checks if there is any nonzeros
                    if(grid[y+1][x]!=0)//if there is then you can move left
                        //System.out.println("it worked");
                        return true;
                }
                else {
                    //for(int z = x + 1; z <grid[y].length; z++) {
                    if(grid[y+1][x]!=0&&grid[y+1][x]==grid[y][x]) {
                        //System.out.println("goodjob");
                        return true;
                    }
                }
            }
        }
        return false;

    }

    //This method checks if the grid 2d array can move down and if
    //it can this method will return true. if not it will return false
    public boolean canMoveDown() {
        System.out.println("working");
        for(int y = grid.length-1; y > 0; y--) {
            for(int x = 0; x < grid[y].length; x++) {
                if(grid[y][x]==0) { //checks if there is any zeros
                    if(grid[y-1][x]!=0) {//if there are no zero's after that it works
                        //System.out.println("Yay!");
                        return true;
                    }
                }
                else { //ifthe code does not have a 0 but it can be grouped return true
                    if(grid[y-1][x]!=0&&grid[y][x]==grid[y-1][x]) {
                        //System.out.println("not working");
                        return true;
                    }
                }
            }
        }
        //System.out.println("failing");
        return false;
    }

    //This method checks if the grid 2d array can move left and if
    //it can this method will return true. if not it will return false
    public boolean canMoveLeft() {
        int tempVal = 0;
        //System.out.println("hello");
        for(int y = 0; y < grid.length; y++) {
            for(int x = 0; x < grid[y].length-1; x++) {
                if(grid[y][x]==0) { //checks if there is any zeros!
                    if(grid[y][x+1]!=0)//if there nozeros after then you can move left
                        //System.out.println("it worked");
                        return true;
                }
                else { //ifthe code does not have a 0 but it can be grouped return true
                    if(grid[y][x+1]!=0&&grid[y][x+1]==grid[y][x]) {
                        //System.out.println("goodjob");
                        return true;
                    }
                }
            }
        }
        return false;

    }

    //This method checks if the grid 2d array can move right and if
    //it can this method will return true. if not it will return false
    public boolean canMoveRight() {
        int tempVal = 0;
        //System.out.println("running");
        for(int y = 0; y < grid.length; y++) {
            for(int x = grid[y].length-1; x > 0; x--) {
                if(grid[y][x]==0) { //checks if there is any nonzeros
                    if(grid[y][x-1]!=0) {//if there is then you can move rigth
                        //System.out.println("Yay!");
                        return true;
                    }
                }
                else {
                    if(grid[y][x-1]!=0&&grid[y][x]==grid[y][x-1]) {
                        return true;
                    }
                }
            }
        }
        //System.out.println("failing");
        return false;
    }
    //This method will run the canMove and if true
    //will initiate the m (move) mehtods and return
    //true if it does, and if not it does not.
    public boolean move(String direction) {
        if(direction.equals("UP")) {
            //System.out.println("H");
            if(canMoveUp()){
                //System.out.println("pass");
                mUp();
                // System.out.println("done");
                return true;
            }
        }
        if(direction.equals("DOWN")) {
            if(canMoveDown()) {
                mDown();
                return true;
            }
        }
        if(direction.equals("LEFT")) {
            if(canMoveLeft()) {
                mLeft();
                return true;
            }
        }
        if(direction.equals("RIGHT")) {
            if(canMoveRight()) {
                mRight();
                return true;
            }
        }

        return false;
    }
    //Moves the 2d array, grid, up if it is
    //called upon. It does this by first groupping and downward and
    //then shifting back up. During the shift I use a different nested for loop
    //to move the array all the way up.
    private void mUp() {
        flip(3);//use the clockwise rotation to rotate clockwise to group right
        int temp = 0;
        for(int x = 0; x < grid.length; x++) {
            for(int y = grid[x].length-1; y>0; y--) {
                if(grid[x][y]!=0) {
                    if(grid[x][y] == grid[x][y-1]){
                        grid[x][y] +=grid[x][y-1];
                        grid[x][y-1] = 0;
                        score +=grid[x][y];
                    }
                    else if (grid[x][y-1]==0) {
                        grid[x][y-1] = grid[x][y];
                        grid[x][y] = 0;
                    }
                }
        /*else {
         grid[x][y] +=grid[x][y+1];
         grid[x][y+1] = 0;
         }*/
            }
        }
        flip(4);//revert so that the groupings are down
        //then shift up
        for(int x = 0; x < grid.length; x++) {
            for(int y = 0, a = 0; y < grid[x].length-1; y++) {
                //System.out.println(x+ ", "+ y+ "is :"+ grid[x][y]);
                if(grid[y][x] == 0&&grid[y+1][x]>0) {
                    for(int z = y+1; z > a; z--) {
                        if(grid[z-1][x]==0){
                            grid[z-1][x]= grid[z][x];
                            grid[z][x]=0;
                        }
                    }
                    a++;
                }
        /*
         else if((x-2>=0)&&grid[x-2][y] == 0) {
         grid[x-2][y]=grid[x-1][y];
         grid[x-1][y]=grid[x][y];
         grid[x][y]=0;
         }*/
            }
        }
    }
    private void mDown() {
        //Moves the 2d array, grid, down if it is
        //called upon. It does this by first groupping and upward and
        //then shifting back down. During the shift I use a different nested for loop
        //to move the array all the way down.
        //flip(3);//use the clockwise rotation to rotate clockwise to group left
        int temp = 0;
        for(int x = grid.length-1; x > 0; x--) {
            for(int y = 0;  y <  grid[x].length; y++) {
                if(grid[x][y]!=0) {
                    if(grid[x][y] == grid[x-1][y]){
                        grid[x][y] +=grid[x-1][y];
                        grid[x-1][y] = 0;
                        score +=grid[x][y];
                    }
                    else if (grid[x-1][y]==0) {
                        grid[x-1][y] = grid[x][y];
                        grid[x][y] = 0;
                    }
                }
        /*else {
         grid[x][y] +=grid[x][y+1];
         grid[x][y+1] = 0;
         }*/
            }
        }

        //flip(4);//revert grouping with counterclockwise so it is up now
        //then shift down
        for(int x = 0; x < grid.length; x++) {
            for(int y = grid[x].length-1, a = 0; y > 0; y--) {
                //System.out.println(x+ ", "+ y+ "is :"+ grid[x][y]);
                if(grid[y][x] == 0&&grid[y-1][x]>0) {
                    grid[y][x] = grid[y-1][x];
                    grid[y-1][x] = 0;
                }
                for(int z = 0; z < grid.length-1; z++) {
                    if(grid[z+1][x]==0){
                        grid[z+1][x]= grid[z][x];
                        grid[z][x]=0;
                    }

                    a++;
                }/*
         for(int x = 0; x < grid.length-1; x++) {
         for(int y = 0; y < grid[x].length; y++) {
         if(grid[x+1][y] == 0) {
         grid[x+1][y]=grid[x][y];
         grid[x][y]=0;
         }
         else if((x+2<grid.length)&&grid[x+2][y] == 0) {
         grid[x+2][y]=grid[x+1][y];
         grid[x+1][y]=grid[x][y];
         grid[x][y]=0;
         }*/
            }
        }


    }
    //Moves the 2d array, grid, right if it is
    //called upon. It does this by first groupping and moving left and
    //then shifting all to right. During the shift I use a different nested for loop
    //to move the array all the way right.
    private void mRight() {
        int temp = 0;
        for(int x = 0; x < grid.length; x++) {
            for(int y = grid.length-1; y > 0; y--) {
                if(grid[x][y]!=0) {
                    if(grid[x][y] == grid[x][y-1]){
                        grid[x][y] +=grid[x][y-1];
                        grid[x][y-1] = 0;
                        score +=grid[x][y];
                    }
                    else if (grid[x][y-1]==0) {
                        grid[x][y-1] = grid[x][y];
                        grid[x][y] = 0;
                    }
                }
            }
        }

    /* for(int x = 0; x < grid.length; x++) {
     for(int y = grid.length-1; y > 0; y--) {
     if(grid[x][y]==0) {
     grid[x][y]+=grid[x][y-1];
     grid[x][y-1]=0;
     }
     }*/

        for(int y = 0; y < grid.length; y++) {
            for(int x = 0; x < grid[y].length-1; x++)
            {
                for(int z = grid[y].length-1; z > 0; z--) {
                    if(grid[y][z]==0) {
                        grid[y][z]=grid[y][z-1];
                        grid[y][z-1]=0;
                    }
                }
            }
        }
    }
    //Moves the 2d array, grid, left if it is
    //called upon. It does this by first groupping and moving right and
    //then shifting back left. During the shift I use a different nested for loop
    //to move the array all the way left.
    private void mLeft() {
        int temp = 0;
        //System.out.println("Working");
        for(int x = 0; x < grid.length; x++) {
            for(int y = 0; y < grid.length-1; y++) {
                if(grid[x][y]!=0) {
                    if(grid[x][y] == grid[x][y+1]){
                        grid[x][y] +=grid[x][y+1];
                        grid[x][y+1] = 0;
                        score +=grid[x][y];
                    }
                    else if (grid[x][y+1]==0) {
                        grid[x][y+1] = grid[x][y];
                        grid[x][y] = 0;
                    }
                }
            }
        }
        for(int y = 0; y < grid.length; y++) {
            for(int x = grid[y].length-1, a = 0; x > 0; x--)
            {
                for(int z = 0; z < grid[y].length-1; z++) {
                    if(grid[y][z]==0) {
                        grid[y][z]=grid[y][z+1];
                        grid[y][z+1]=0;
                    }

          /*
           if(grid[y][x-1]==0) {
           //System.out.println(x + " " + y);
           grid[y][x-1]=grid[y][x];
           grid[y][x] = 0;
           }
           /*else if((x-3)>0&&grid[y][x-2]==0) {
           temp = grid[y][x];
           grid[y][x]=0;
           grid[y][x-3]=grid[y][x-1];
           grid[y][x-2]=temp;
           grid[y][x-1]=0;
           }*/
                }
            }
        }
    }


    /*
     *
     * If we can find the places with non zeros we can move those nubmbers all the way to the left or right
     *    for(int i = 0; i < GRID_SIZE; i++) {
     for(int j = 0; j < GRID_SIZE; j++) { //if we can count the number of zeros we can identify how much we have to move
     if(grid[i][j]==0)
     numZero+=1;
     }
     }
     * int temp = 0;
     for(int x = 0; x < grid.length; x++) {
     for(int y = grid.length-1; y > 0; y--) {
     if(grid[x][y]!=0) {
     if(grid[x][y] == grid[x][y-1]){
     grid[x][y] +=grid[x][y-1];
     grid[x][y-1] = 0;
     score +=grid[x][y];
     }
     else if (grid[x][y-1]==0) {
     grid[x][y-1] = grid[x][y];
     grid[x][y] = 0;
     }
     }
     }
     }
     /* for(int x = 0; x < grid.length; x++) {
     for(int y = grid.length-1; y > 0; y--) {
     if(grid[x][y]==0) {
     grid[x][y]+=grid[x][y-1];
     grid[x][y-1]=0;
     }
     }/*
     }

     for(int y = 0; y < grid.length; y++) {
     for(int x = 0; x < grid[y].length-1; x++)
     {
     if(grid[y][x+1]==0) {
     //System.out.println(x + " " + y);
     grid[y][x+1]=grid[y][x];
     grid[y][x] = 0;
     }
     else if((x+2)<grid[y].length&&grid[y][x+2]==0) {
     temp = grid[y][x];
     grid[y][x]=0;
     grid[y][x+2]=grid[y][x+1];
     grid[y][x+1]=temp;
     }
     }
     }*/
    // No need to change this for PSA3
    // Check to see if we have a game over
    //by using all the canMove methods
    public boolean isGameOver() {
        if(!canMoveUp()&&!canMoveDown()&&!canMoveLeft()&&!canMoveRight())
            return true;//if all the methods return false then return true
        //meaning end the code
        return false;
    }

    // Return the reference to the 2048 Grid
    public int[][] getGrid() {
        return grid;
    }

    // Return the score
    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        StringBuilder outputString = new StringBuilder();
        outputString.append(String.format("Score: %d\n", score));
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++)
                outputString.append(grid[row][column] == 0 ? "    -" :
                        String.format("%5d", grid[row][column]));

            outputString.append("\n");
        }
        return outputString.toString();
    }
}

