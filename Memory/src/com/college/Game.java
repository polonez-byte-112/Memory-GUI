package com.college;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Game extends JFrame {

    public int SCREEN_WIDTH=800;
    public int SCREEN_HEIGHT=800;
    public int spacing=5;
    int total = 0;




    public int cardsToUncoverLeft=3;

    //tablice

    public int[] allTiles = {1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8,9,9,10,10,11,11,12,12,13,13,14,14,15,15,16,16,17,17,18,18,19,19,20,20,21,21,22,22,23,23,24,24,25,25,26,26,27,27,28,28,29,29,30,30,31,31,32,32};
    public int[][] tiles2D;

    public boolean[][] uncovered= new boolean[8][8];
    public boolean[][] visible = new boolean[8][8];






    public boolean evenClick=true;
    public boolean oddClick=false;

    public int currentEvenXClick=-1;
    public int currentEvenYClick=-1;
    public int currentOddXClick=-1;
    public int currentOddYClick=-1;







    //colory


    public Color color1= new Color(255,206,0);
    public Color color2= new Color(176,11,30);
    public Color color3= new Color(95,114,15);
    public Color color4= new Color(0,109,225);
    public Color color5= new Color(255,76,148);
    public Color color6= new Color(108,35,95);
    public Color color7= new Color(64,224,208);
    public Color color8= new Color(0,0,0);
    public Color color9= new Color(255,255,255);
    public Color color10= new Color(135,44,204);


    public Color color11= new Color(212,115,50);
    public Color color12= new Color(50,212,115);
    public Color color13= new Color(115,50,212);
    public Color color14= new Color(212,50,66);
    public Color color15= new Color(127,30,39);
    public Color color16= new Color(39,127,30);
    public Color color17= new Color(131,120,127);
    public Color color18= new Color(109,53,53);
    public Color color19= new Color(106,76,76);
    public Color color20= new Color(0,0,128);



    public Color color21= new Color(0,0,255);
    public Color color22= new Color(255,0,0);
    public Color color23= new Color(0,255,0);
    public Color color24= new Color(0,46,46);
    public Color color25= new Color(145,30,180);
    public Color color26= new Color(220,190,255);
    public Color color27= new Color(240,50,230);
    public Color color28= new Color(150,215,180);
    public Color color29= new Color(250,190,212);
    public Color color30= new Color(230,25,75);

    public Color color31 = new Color(128,128,0);
    public Color color32 = new Color(128,0,0);







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
       tiles2D = createDiagonalArray(allTiles,8,8);


        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
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

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if(mouseX>i*100+4 && mouseX<=i*100+100-2*spacing+4 && mouseY>j*100+30 && mouseY<=j*100+100-2*spacing+30){
                    return i;
                }
            }
        }


        return -1;
    }
    public int inBoxY(){

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if(mouseX>i*100+4 && mouseX<=i*100+100-2*spacing+4 && mouseY>j*100+30 && mouseY<=j*100+100-2*spacing+30){
                    return j;
                }
            }
        }

        return -1;
    }

    public int inBoxClickedX(){

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if(mouseClickedX>i*100+4 && mouseClickedX<=i*100+100-2*spacing+4 && mouseClickedY>j*100+30 && mouseClickedY<=j*100+100-2*spacing+30){
                    return i;
                }
            }
        }

        return -1;
    }
    public int inBoxClickedY(){

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

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
        tiles2D = createDiagonalArray(allTiles,8,8);

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

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                visible[i][j]=false;
                uncovered[i][j]=false;
            }
        }
    }


    public class Board extends JPanel{

        public void paintComponent(Graphics g){

            g.setColor(Color.lightGray);
            g.fillRect(0,0, SCREEN_WIDTH,SCREEN_HEIGHT);

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
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
                            if(tiles2D[i][j]==9){
                                g.setColor(color9);
                            }
                            if(tiles2D[i][j]==10){
                                g.setColor(color10);
                            }

                            if(tiles2D[i][j]==11){
                                g.setColor(color11);
                            }
                            if(tiles2D[i][j]==12){
                                g.setColor(color12);
                            }
                            if(tiles2D[i][j]==13){
                                g.setColor(color13);
                            }
                            if(tiles2D[i][j]==14){
                                g.setColor(color14);
                            }
                            if(tiles2D[i][j]==15){
                                g.setColor(color15);
                            }
                            if(tiles2D[i][j]==16){
                                g.setColor(color16);
                            }
                            if(tiles2D[i][j]==17){
                                g.setColor(color17);
                            }
                            if(tiles2D[i][j]==18){
                                g.setColor(color18);
                            }
                            if(tiles2D[i][j]==19){
                                g.setColor(color19);
                            }
                            if(tiles2D[i][j]==20){
                                g.setColor(color20);
                            }

                            if(tiles2D[i][j]==21){
                                g.setColor(color21);
                            }
                            if(tiles2D[i][j]==22){
                                g.setColor(color22);
                            }
                            if(tiles2D[i][j]==23){
                                g.setColor(color23);
                            }
                            if(tiles2D[i][j]==24){
                                g.setColor(color24);
                            }
                            if(tiles2D[i][j]==25){
                                g.setColor(color25);
                            }
                            if(tiles2D[i][j]==26){
                                g.setColor(color26);
                            }
                            if(tiles2D[i][j]==27){
                                g.setColor(color27);
                            }
                            if(tiles2D[i][j]==28){
                                g.setColor(color28);
                            }
                            if(tiles2D[i][j]==29){
                                g.setColor(color29);
                            }
                            if(tiles2D[i][j]==30){
                                g.setColor(color30);
                            }

                            if(tiles2D[i][j]==31){
                                g.setColor(color31);
                            }

                            if(tiles2D[i][j]==32){
                                g.setColor(color32);
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
                        if(tiles2D[i][j]==8){
                            g.setColor(color8);
                        }
                        if(tiles2D[i][j]==9){
                            g.setColor(color9);
                        }
                        if(tiles2D[i][j]==10){
                            g.setColor(color10);
                        }

                        if(tiles2D[i][j]==11){
                            g.setColor(color11);
                        }
                        if(tiles2D[i][j]==12){
                            g.setColor(color12);
                        }
                        if(tiles2D[i][j]==13){
                            g.setColor(color13);
                        }
                        if(tiles2D[i][j]==14){
                            g.setColor(color14);
                        }
                        if(tiles2D[i][j]==15){
                            g.setColor(color15);
                        }
                        if(tiles2D[i][j]==16){
                            g.setColor(color16);
                        }
                        if(tiles2D[i][j]==17){
                            g.setColor(color17);
                        }
                        if(tiles2D[i][j]==18){
                            g.setColor(color18);
                        }
                        if(tiles2D[i][j]==19){
                            g.setColor(color19);
                        }
                        if(tiles2D[i][j]==20){
                            g.setColor(color20);
                        }

                        if(tiles2D[i][j]==21){
                            g.setColor(color21);
                        }
                        if(tiles2D[i][j]==22){
                            g.setColor(color22);
                        }
                        if(tiles2D[i][j]==23){
                            g.setColor(color23);
                        }
                        if(tiles2D[i][j]==24){
                            g.setColor(color24);
                        }
                        if(tiles2D[i][j]==25){
                            g.setColor(color25);
                        }
                        if(tiles2D[i][j]==26){
                            g.setColor(color26);
                        }
                        if(tiles2D[i][j]==27){
                            g.setColor(color27);
                        }
                        if(tiles2D[i][j]==28){
                            g.setColor(color28);
                        }
                        if(tiles2D[i][j]==29){
                            g.setColor(color29);
                        }
                        if(tiles2D[i][j]==30){
                            g.setColor(color30);
                        }

                        if(tiles2D[i][j]==31){
                            g.setColor(color31);
                        }

                        if(tiles2D[i][j]==32){
                            g.setColor(color32);
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
                for (int i = 1; i < 33; i++) {
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
