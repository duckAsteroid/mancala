package com.cs.games.mancala.ui;

import com.cs.games.mancala.model.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author <A
 * HREF="mailto:chris.senior@teradyne.com?subject=com.cs.games.mancala.model.ui.BoardDisplayer">Chris
 * Senior </A>
 */
public class BoardDisplayer extends Panel {

    private JButton[] cups = new JButton[14];
    private Board board;
    public BoardDisplayer() {
        for (int i = 0; i < cups.length; i++) {
            cups[i] = new JButton("" + i);
            cups[i].setFont(new Font("Verdana", Font.BOLD, 48));
            cups[i].setBackground(Color.darkGray);
            //cups[i].addActionListener(new TurnAction(i));
        }
        setLayout(new GridBagLayout());
        for (int i = 0; i < 6; i++) {
            cups[i].setForeground(Color.green.darker());
            add(cups[i], new GridBagConstraints(i + 1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 10, 100));
        }
        int n = 6;
        for (int i = 7; i < 13; i++) {
            // index 7 - 13 @ ui 6 - 1
            cups[i].setForeground(Color.blue);
            add(cups[i], new GridBagConstraints(n--, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 10, 10));
        }
        add(cups[6], new GridBagConstraints(7, 0, 1, 2, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
                0, 0, 0, 0), 5, 5));
        add(cups[13], new GridBagConstraints(0, 0, 1, 2, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
                0, 0, 0, 0), 5, 5));
    }

    public BoardDisplayer(Board init) {
        this();
        setBoard(init);
    }

    public static void main(String[] args) {
        JFrame test = new JFrame("Test UI");
        test.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }
        });
        // FIXME Add a board displayer
        //test.getContentPane().add(new BoardDisplayer(new Board(beads)));
        test.setSize(640, 480);
        test.setVisible(true);
    }

    public void setBoard(Board b) {
        this.board = b;
        // FIXME reflect cup counts in text boxes
    }

    //FIXME Implement cup change logic (listen to game)
    public void cupChanged(Board source, int cupIndex) {
        animateCup(cupIndex);
        this.setBoard(source);
    }

    private void animateCup(int index) {
        boolean red = true;
        Color putMeBack = cups[index].getForeground();
        for (int i = 0; i < 10; i++) {
            if (red)
                cups[index].setForeground(Color.RED);
            else
                cups[index].setForeground(Color.BLACK);

            red = !red;
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }
        }
        cups[index].setForeground(putMeBack);
    }


}