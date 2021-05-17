import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Minesweeper implements ActionListener{
    JFrame frame = new JFrame("Minesweeper");//New jframe
    JButton reset = new JButton("Reset");//reset button
    JButton[][] buttons = new JButton[20][20];//grid of buttons
    int[][] counts = new int[20][20];//counts
    Container grid = new Container();//container for the grid
    final int MINE = 10;//mine

    public Minesweeper(){
        frame.setSize(1000, 700);//set the frame size
        frame.setLayout(new BorderLayout());//set the layout
        frame.add(reset, BorderLayout.NORTH);//add the reset button to the top
        reset.addActionListener(this);//add an actionlistener
        grid.setLayout(new GridLayout(20,20));//set the layout of the grid
        for (int i = 0; i < buttons.length; i++){//Loop through length of buttons
            for (int j = 0; j < buttons[0].length; j++){//loop through length of buttons[0]
                buttons[i][j] = new JButton();//new buttons
                buttons[i][j].addActionListener(this);//add action listeners to each one
                grid.add(buttons[i][j]);//add the buttons to the grid
            }
        }
        frame.add(grid, BorderLayout.CENTER);//add the grid of buttons to the center
        createRandomMines();//create the mines
    
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//close
        frame.setVisible(true);//make the frame visible
    }

    public void createRandomMines(){//create mines
        ArrayList<Integer> list = new ArrayList<Integer>();//array list of integers
        for (int x = 0; x < counts.length; x++){//loop through length of couts
            for (int y = 0; y < counts[0].length; y++){//loop through length of counts[0]
                list.add(x*100+y);//add to the list
            }
        }
        counts = new int[20][20];//20x20 grid
        for (int i = 0; i < 30; i++){//loop through until total mines are placed
            int choice = (int)(Math.random()*list.size());//random placement
            counts[list.get(choice)/100][list.get(choice)%100] = MINE;//get squre it's placed on
            list.remove(choice);//remove the mine from the list of options
        }
        for (int x = 0; x < counts.length; x++){//loop through length of counts
            for (int y = 0; y < counts[0].length; y++){//loop through length of counts[0]
                if (counts[x][y] != MINE){//If the current spot isn't a mine, check for mines around it
                    int neighborcount = 0;//# of mines
                    if (x > 0 && y > 0 && counts[x-1][y-1] == MINE){//Upper left
                        neighborcount++;//Add 1 to mine count
                    }
                    if (y > 0 && counts[x][y-1] == MINE){//top
                        neighborcount++;//Add 1 to mine count
                    }
                    if (x < counts.length-1 && y > 0 && counts[x+1][y-1] == MINE){//upper right
                        neighborcount++;//Add 1 to mine count
                    }
                    if (x > 0 && counts[x-1][y] == MINE){//left
                        neighborcount++;
                    }
                    if (x < counts.length-1 && counts[x+1][y] == MINE){//right
                        neighborcount++;
                    }
                    if (x > 0 && y < counts[0].length-1 && counts[x-1][y+1] == MINE){//bottom left
                        neighborcount++;
                    }
                    if (y < counts[0].length-1 && counts[x][y+1] == MINE){//bottom
                        neighborcount++;
                    }
                    if (x < counts.length-1 && y < counts[0].length-1 && counts[x+1][y+1] == MINE){//bottom right
                        neighborcount++;
                    }
                    counts[x][y] = neighborcount;//Show how many mines are around the current spot
                }
            }
        }
    }
  
    public void lostGame(){//lost
        for (int x = 0; x < buttons.length; x++){//loop through buttons length
            for (int y = 0; y < buttons[0].length; y++){//loop through buttons[0] length
                if (buttons[x][y].isEnabled()){//if the button is still enabled
                    if (counts[x][y] != MINE){//if it's not a mine
                        buttons[x][y].setText(counts[x][y] + "");//clear the square
                        buttons[x][y].setEnabled(false);//disable the button
                    }
                    else{//anything else
                        buttons[x][y].setText("X");//draw an x
                        buttons[x][y].setEnabled(false);//disable the button
                    }
                }
            }
        }
    }
  
    public void clearZeros(ArrayList<Integer> toClear){//clear zeros
        if (toClear.size() == 0){//if there's none to clear
            return;//return
        }
        else{//anything else
            int x = toClear.get(0)/100;//x of what's needed to be cleared
            int y = toClear.get(0)%100;//y of what's needed to be cleared
            toClear.remove(0);//remove it
            if (x > 0 && y > 0 && buttons[x-1][y-1].isEnabled()){//upper left
                buttons[x-1][y-1].setText(counts[x-1][y-1] + "");
                buttons[x-1][y-1].setEnabled(false);
                if (counts[x-1][y-1] == 0){
                    toClear.add((x-1) * 100 + (y-1));
                }
            }
            if (y > 0 && buttons[x][y-1].isEnabled()){//top
                buttons[x][y-1].setText(counts[x][y-1] + "");
                buttons[x][y-1].setEnabled(false);
                if (counts[x][y-1] == 0){
                    toClear.add(x * 100 + (y-1));
                }
            }
            if (x < counts.length - 1 && y > 0 && buttons[x+1][y-1].isEnabled()){//upper right
                buttons[x+1][y-1].setText(counts[x+1][y-1] + "");
                buttons[x+1][y-1].setEnabled(false);
                if (counts[x+1][y-1] == 0){
                    toClear.add((x+1) * 100 + (y-1));
                }
            }
            if (x > 0 && buttons[x-1][y].isEnabled()){//left
                buttons[x-1][y].setText(counts[x-1][y] + "");
                buttons[x-1][y].setEnabled(false);
                if (counts[x-1][y] == 0){
                    toClear.add((x-1) * 100 + y);
                }
            }
            if (x < counts.length - 1 && buttons[x+1][y].isEnabled()){//right
                buttons[x+1][y].setText(counts[x+1][y] + "");
                buttons[x+1][y].setEnabled(false);
                if (counts[x+1][y] == 0){
                    toClear.add((x+1) * 100 + y);
                }
            }
            if (x > 0 && y < counts[0].length - 1 && buttons[x-1][y+1].isEnabled()){//bottom left
                buttons[x-1][y+1].setText(counts[x-1][y+1] + "");
                buttons[x-1][y+1].setEnabled(false);
                if (counts[x-1][y+1] == 0){
                    toClear.add((x-1) * 100 + (y+1));
                }
            }
            if (y < counts[0].length - 1 && buttons[x][y+1].isEnabled()){//bottom
                buttons[x][y+1].setText(counts[x][y+1] + "");
                buttons[x][y+1].setEnabled(false);
                if (counts[x][y+1] == 0){
                    toClear.add(x * 100 + (y+1));
                }
            }
            if (x < counts.length - 1 && y < counts[0].length - 1 && buttons[x+1][y+1].isEnabled()){//bottom right
                buttons[x+1][y+1].setText(counts[x+1][y+1] + "");
                buttons[x+1][y+1].setEnabled(false);
                if (counts[x+1][y+1] == 0){
                    toClear.add((x+1) * 100 + (y+1));
                }
            }
        }
        clearZeros(toClear);
    }
  
    public void checkWin(){//check for wins
        boolean won = false;//not won yet
        for (int x = 0; x < counts.length; x++){//loop through counts length
            for (int y = 0; y < counts[0].length; y++){//loop through counts[0] length
                if (counts[x][y] != MINE && buttons[x][y].isEnabled() == true){//if there's squares that aren't mines or disabled
                    won = false;//not won yet
                }
            }
        }
        if (won == true){//if won
            JOptionPane.showMessageDialog(frame, "You Win!");//you won
        }
    }
  
    @Override
    public void actionPerformed(ActionEvent event){//action performed
        if (event.getSource().equals(reset)){//if reset button is clicked
            for (int x = 0; x < buttons.length; x++){//loop through buttons
                for (int y = 0; y < buttons[0].length; y++){//loop through buttons[0]
                    buttons[x][y].setEnabled(true);//enable buttons
                    buttons[x][y].setText("");//clear them
                }
            }
            createRandomMines();//create random mines
        }
        else{//anything else
            for (int x = 0; x < buttons.length; x++){//loop through buttons
                for (int y = 0; y < buttons[0].length; y++){//loop through buttons[0]
                    if (event.getSource().equals(buttons[x][y])){//if it's a button in play
                        if (counts[x][y] == MINE){//if it's a mine
                            buttons[x][y].setForeground(Color.red);//change color to red
                            buttons[x][y].setText("X");//draw an x
                            lostGame();//game over
                        }
                        else if (counts[x][y] == 0){//if it's a 0
                            buttons[x][y].setText(counts[x][y] + "");//draw a 0
                            buttons[x][y].setEnabled(false);//disable the button
                            ArrayList<Integer> toClear = new ArrayList<Integer>();//clear around it
                            toClear.add(x*100+y);//add to clear
                            clearZeros(toClear);//clear zeros
                            checkWin();//check for wins
                        }
                        else{//anything else
                            buttons[x][y].setText(counts[x][y] + "");//draw # of mines around it
                            buttons[x][y].setEnabled(false);//disable it
                            checkWin();//check for wins
                        }
                    }
                }
            }
        }
    }
    
    public static void main(String[] args) {
        new Minesweeper();
    }
}
