package com.college;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Game extends JFrame {

    public int SCREEN_WIDTH=400;
    public int SCREEN_HEIGHT=400;
    public int spacing=5;
    int total = 0;




    public int cardsToUncoverLeft=3;

    //tablice

    public int[] allTiles = {1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8};
    public int[][] tiles2D;

    public boolean[][] uncovered= new boolean[4][4];
    public boolean[][] visible = new boolean[4][4];






    public boolean evenClick=true;
    public boolean oddClick=false;

    public int currentEvenXClick=-1;
    public int currentEvenYClick=-1;
    public int currentOddXClick=-1;
    public int currentOddYClick=-1;







    //colory


    public Color color1= new Color(255,206,0);
    public Color color2= new Color(176,11,30);
    public Color color3= new Color(226,226,226);
    public Color color4= new Color(0,109,225);
    public Color color5= new Color(255,76,148);
    public Color color6= new Color(122,37,134);
    public Color color7= new Color(136,186,116);
    public Color color8= new Color(116,136,186);







    public int mouseX=-100;
    public int mouseY=-100;
    public int mouseClickedX=-1;
    public int mouseClickedY=-1;

    public Game(){

        this.setSize(SCREEN_WIDTH+6,SCREEN_HEIGHT+29);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Memory  (Press ESC to Reset)");
        this.setLocationRelativeTo(null);

        this.setContentPane(new Board());
        this.addMouseMotionListener(new MouseMovement());
        this.addMouseListener(new MouseClickMovement());
        this.addKeyListener(new KeyboardListener());
        this.setResizable(false);
        this.setVisible(true);





        shuffleArray();
       tiles2D = createDiagonalArray(allTiles,4,4);


        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                uncovered[i][j]=false;
                visible[i][j]=false;
            }
        }
    }

    private static int[][] createDiagonalArray(int[] array, int p, int q) {

        int[][] input = new int[p][q];
        for (int j = 0; j < p; j++) {
            for (int i = 0; i < q; i++) {
                input[j][i] = array[j * q + i];
            }
        }

        final int numRows = input.length;
        final int numColumns = input[0].length;
        int[][] result = new int[numRows][numColumns];

        int rowIndex = 0;
        int columnIndex = 0;
        int currentRow = 0;
        int currentColumn = 0;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                result[currentRow][currentColumn] = input[i][j];
                if (currentRow == numRows - 1) {
                    if (numRows < numColumns && columnIndex < numColumns - 1) {
                        currentRow = 0;
                        currentColumn = ++columnIndex;
                    } else {
                        currentRow = ++rowIndex;
                        currentColumn = numColumns - 1;
                    }
                } else if (currentColumn == 0) {
                    if (columnIndex < numColumns - 1) {
                        currentRow = rowIndex;
                        currentColumn = ++columnIndex;
                    } else {
                        currentColumn = columnIndex;
                        currentRow = ++rowIndex;
                    }
                } else {
                    currentRow++;
                    currentColumn--;
                }

            }
        }
        return result;
    } //taken from internet becouse I didnt know how I can make 1d Array into 2d .


    public int inBoxX(){

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                if(mouseX>i*100+4 && mouseX<=i*100+100-2*spacing+4 && mouseY>j*100+30 && mouseY<=j*100+100-2*spacing+30){
                    return i;
                }
            }
        }


        return -1;
    }
    public int inBoxY(){

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                if(mouseX>i*100+4 && mouseX<=i*100+100-2*spacing+4 && mouseY>j*100+30 && mouseY<=j*100+100-2*spacing+30){
                    return j;
                }
            }
        }

        return -1;
    }

    public int inBoxClickedX(){

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                if(mouseClickedX>i*100+4 && mouseClickedX<=i*100+100-2*spacing+4 && mouseClickedY>j*100+30 && mouseClickedY<=j*100+100-2*spacing+30){
                    return i;
                }
            }
        }

        return -1;
    }
    public int inBoxClickedY(){

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                if(mouseClickedX>i*100+4 && mouseClickedX<=i*100+100-2*spacing+4 && mouseClickedY>j*100+30 && mouseClickedY<=j*100+100-2*spacing+30){
                    return j;
                }
            }
        }

        return -1;
    }

    public void shuffleArray() {
        for (int i = 0; i < allTiles.length; i++) {

            int index = (int) (Math.random() * allTiles.length);

           int temp = allTiles[i];

           allTiles[i]=allTiles[index];
           allTiles[index] = temp;
        }


    }

    public void resetter(){
        shuffleArray();
        tiles2D = createDiagonalArray(allTiles,4,4);

        total=0;
        currentEvenXClick=-1;
        currentEvenYClick=-1;
        currentOddXClick=-1;
        currentOddYClick=-1;
        mouseX=-100;
        mouseY=-100;
        mouseClickedX=-1;
        mouseClickedY=-1;
        evenClick=true;
        oddClick=false;
        cardsToUncoverLeft=3;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                visible[i][j]=false;
                uncovered[i][j]=false;
            }
        }
    }


    public class Board extends JPanel{

        public void paintComponent(Graphics g){

            g.setColor(Color.lightGray);
            g.fillRect(0,0, SCREEN_WIDTH,SCREEN_HEIGHT);

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    g.setColor(Color.darkGray);

                    if(uncovered[i][j]==true && cardsToUncoverLeft!=0 ){

                            if(tiles2D[i][j]==1){
                                g.setColor(color1);
                            }

                            if(tiles2D[i][j]==2){
                                g.setColor(color2);
                            }
                            if(tiles2D[i][j]==3){
                                g.setColor(color3);
                            }
                            if(tiles2D[i][j]==4){
                                g.setColor(color4);
                            }
                            if(tiles2D[i][j]==5){
                                g.setColor(color5);
                            }
                            if(tiles2D[i][j]==6){
                                g.setColor(color6);
                            }
                            if(tiles2D[i][j]==7){
                                g.setColor(color7);
                            }
                            if(tiles2D[i][j]==8){
                                g.setColor(color8);
                            }

                    }


                    if(cardsToUncoverLeft==0 && visible[i][j]==false){
                        g.setColor(Color.darkGray);

                    }

                    if(visible[i][j]==true){

                        if(tiles2D[i][j]==1){
                            g.setColor(color1);
                        }

                        if(tiles2D[i][j]==2){
                            g.setColor(color2);
                        }
                        if(tiles2D[i][j]==3){
                            g.setColor(color3);
                        }
                        if(tiles2D[i][j]==4){
                            g.setColor(color4);
                        }
                        if(tiles2D[i][j]==5){
                            g.setColor(color5);
                        }
                        if(tiles2D[i][j]==6){
                            g.setColor(color6);
                        }
                        if(tiles2D[i][j]==7){
                            g.setColor(color7);
                        }
                        if(tiles2D[i][j]==8) {
                            g.setColor(color8);
                        }
                    }
                    g.fillRect(i*100, j*100, 100-2*spacing, 100-2*spacing);

                    }


            }
        }

    }

    public class MouseMovement implements MouseMotionListener{
        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {






        //    System.out.println("odd: "+oddClick+", Even: "+evenClick);
        //    System.out.println("Even:  [X: "+currentEvenXClick+", Y:"+currentEvenYClick+"]    Odd:   [X: "+currentOddXClick+", Y: "+currentOddYClick+"]" );
            mouseX=e.getX();
            mouseY=e.getY();

        //    System.out.println("Cards to uncover left: "+cardsToUncoverLeft);


            //to jest tutaj by odsloniete karty nie znikaly jak sie je kliknie
            if(cardsToUncoverLeft<=1){

                cardsToUncoverLeft=0;
                for (int i = 1; i < 9; i++) {
                    if(tiles2D[currentOddXClick][currentOddYClick]!=i && tiles2D[currentEvenXClick][currentEvenYClick]!=i){
                        uncovered[currentOddXClick][currentOddYClick]=false;
                        uncovered[currentEvenXClick][currentEvenYClick]=false;

                    }

                    if(tiles2D[currentOddXClick][currentOddYClick]==i && tiles2D[currentEvenXClick][currentEvenYClick]==i ){

                        uncovered[currentOddXClick][currentOddYClick]=true;
                        uncovered[currentEvenXClick][currentEvenYClick]=true;

                        visible[currentOddXClick][currentOddYClick]=true;
                        visible[currentEvenXClick][currentEvenYClick]=true;
                    }


                }



            }


        }
    }

    public class MouseClickMovement implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

            mouseClickedX = e.getX();
            mouseClickedY = e.getY();




          if(uncovered[inBoxClickedX()][inBoxClickedY()]==false)  {

              //zmienianie odd i even na zmiane
            if (oddClick == true && evenClick == false) {
                oddClick = false;
                evenClick = true;
            } else if (oddClick == false && evenClick == true) {
                evenClick = false;
                oddClick = true;
            }


         //   System.out.println("X: " + inBoxClickedX() + ", Y:" + inBoxClickedY());

            if (oddClick == false && evenClick == true) {
                currentEvenXClick = inBoxClickedX();
                currentEvenYClick = inBoxClickedY();
            }else if (oddClick == true && evenClick == false) {
                currentOddXClick = inBoxClickedX();
                currentOddYClick = inBoxClickedY();
            }







            if(cardsToUncoverLeft==0){
                cardsToUncoverLeft=3;
            }


            if (cardsToUncoverLeft>0){
                cardsToUncoverLeft--;

                uncovered[inBoxX()][inBoxY()]=true;
            }



        }
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    public class KeyboardListener implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

            if(e.getKeyCode()==27){
                resetter();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
