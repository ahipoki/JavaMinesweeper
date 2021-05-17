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
    JFrame frame = new JFrame("Minesweeper");
    JButton reset = new JButton("Reset");
    JButton[][] buttons = new JButton[20][20];
    int[][] counts = new int[20][20];
    Container grid = new Container();
    final int MINE = 10;

    public Minesweeper(){
        frame.setSize(1000, 700);
        frame.setLayout(new BorderLayout());
        frame.add(reset, BorderLayout.NORTH);
        reset.addActionListener(this);
        grid.setLayout(new GridLayout(20,20));
        for (int i = 0; i < buttons.length; i++){
            for (int j = 0; j < buttons[0].length; j++){
                buttons[i][j] = new JButton();
                buttons[i][j].addActionListener(this);
                grid.add(buttons[i][j]);
            }
        }
        frame.add(grid, BorderLayout.CENTER);
        createRandomMines();
    
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void createRandomMines(){
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int x = 0; x < counts.length; x++){
            for (int y = 0; y < counts[0].length; y++){
                list.add(x*100+y);
            }
        }
        counts = new int[20][20];
        for (int i = 0; i < 30; i++){
            int choice = (int)(Math.random()*list.size());
            counts[list.get(choice)/100][list.get(choice)%100] = MINE;
            list.remove(choice);
        }
        for (int x = 0; x < counts.length; x++){
            for (int y = 0; y < counts[0].length; y++){
                if (counts[x][y] != MINE){//If the current spot isn't a mine, check for mines around it
                    int neighborcount = 0;//# of mines
                    if (x > 0 && y > 0 && counts[x-1][y-1] == MINE){//Upper left
                        neighborcount++;//Add 1 to mine count
                    }
                    if (y > 0 && counts[x][y-1] == MINE){//Up
                        neighborcount++;//Add 1 to mine count
                    }
                    if (x < counts.length-1 && y < counts[0].length-1 && counts[x+1][y-1] == MINE){//upper right
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
                    if (y < counts[0].length-1 && counts[x][y+1] == MINE){//Down
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
  
    public void lostGame(){
        for (int x = 0; x < buttons.length; x++){
            for (int y = 0; y < buttons[0].length; y++){
                if (buttons[x][y].isEnabled()){
                    if (counts[x][y] != MINE){
                        buttons[x][y].setText(counts[x][y] + "");
                        buttons[x][y].setEnabled(false);
                    }
                    else{
                        buttons[x][y].setText("X");
                        buttons[x][y].setEnabled(false);
                    }
                }
            }
        }
    }
  
    @Override
    public void actionPerformed(ActionEvent event){
        if (event.getSource().equals(reset)){
            for (int x = 0; x < buttons.length; x++){
                for (int y = 0; y < buttons[0].length; y++){
                    buttons[x][y].setEnabled(true);
                    buttons[x][y].setText("");
                }
            }
            createRandomMines();
        }
        else{
            for (int x = 0; x < buttons.length; x++){
                for (int y = 0; y < buttons[0].length; y++){
                    if (event.getSource().equals(buttons[x][y])){
                        if (counts[x][y] == MINE){
                            buttons[x][y].setForeground(Color.red);
                            buttons[x][y].setText("X");
                            lostGame();
                        }
                        else{
                            buttons[x][y].setText(counts[x][y] + "");
                            buttons[x][y].setEnabled(false);
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