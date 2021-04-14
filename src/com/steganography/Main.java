package com.steganography;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    JPanel imagePanel=new JPanel();
    JFrame win=new JFrame();
    Main(){
        win.setTitle("Embed your secret message in image");
        win.setBounds(100,100,600,600);
        win.setResizable(false);
        win.setDefaultCloseOperation(EXIT_ON_CLOSE);
        win.setLayout(null);
        //imagePanel.setBounds(50,100,550,300);
        //imagePanel.setVisible(true);
        win.add(imagePanel);
        win.setVisible(true);
    }
    public void paint(Graphics g){
        g.setColor(Color.darkGray);
        g.setFont(new Font("Arial",Font.BOLD,20));

        g.drawString("STEGANOGRAPHY",210,222);
    }
    public static void main(String[] args){
        Main win =new Main();

    }
}