package com.steganography;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;

public class Main extends JFrame implements ActionListener {

    JTabbedPane tabs = new JTabbedPane();
    JPanel intro = new JPanel();
    JPanel encode = new JPanel();
    JPanel decode = new JPanel();

    int winWidth = 600;
    int winHeight = 600;

    JTextField heading = new JTextField();
    JTextField encodeHeading = new JTextField();
    JTextArea encodeInfoHeading = new JTextArea();
    JTextArea encodeInfoParagraph = new JTextArea();
    JTextField decodeHeading = new JTextField();
    JTextArea decodeInfoHeading = new JTextArea();
    JTextArea decodeInfoParagraph = new JTextArea();

    JButton encodeUploadImg = new JButton();
    JButton encodeInsertTxt = new JButton();
    JButton encodeBtn = new JButton();

    JButton decodeUploadImg = new JButton();
    JButton decodeSaveTxt = new JButton();
    JButton decodeBtn = new JButton();

    ImageIcon defaultImage=new ImageIcon(getClass().getResource("img.jpg"));
    JLabel defaultImg;


    // Fonts
    Font fontH = new Font("Arial", Font.BOLD, 32);
    Font fontH2 = new Font("Arial", Font.PLAIN, 24);
    Font fontP = new Font("Arial", Font.PLAIN, 18);


    Main() {
        // Window setups
        setBounds(200, 100, winWidth, winHeight);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        setBackground(Color.DARK_GRAY);
        add(BorderLayout.CENTER, tabs);

        tabs.addTab("  Introduction  ", intro());
        tabs.addTab("  Encode  ", encode());
        tabs.addTab("  Decode  ", decode());
        setVisible(true);
    }

    public JPanel intro() {
        intro.setBackground(Color.darkGray);
        intro.setFocusable(false);
        intro.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.darkGray));
        intro.setLayout(null);

        heading.setForeground(Color.white);
        heading.setBackground(Color.darkGray);
        heading.setBounds(0, 0, winWidth, 100);
        heading.setHorizontalAlignment(JTextField.CENTER);
        heading.setFont(fontH);
        heading.setFocusable(false);
        heading.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.darkGray));
        heading.setText("STEGANOGRAPHY");
        heading.setVisible(true);

        encodeInfoHeading.setForeground(Color.white);
        encodeInfoHeading.setBackground(Color.darkGray);
        encodeInfoHeading.setBounds(80, 160, 100, 30);
        encodeInfoHeading.setFont(fontH2);
        encodeInfoHeading.setFocusable(false);
        encodeInfoHeading.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.darkGray));
        encodeInfoHeading.setText("ENCODE");
        encodeInfoHeading.setVisible(true);

        encodeInfoParagraph.setForeground(Color.white);
        encodeInfoParagraph.setBackground(Color.darkGray);
        encodeInfoParagraph.setBounds(80, 200, 200, 300);
        encodeInfoParagraph.setFont(fontP);
        encodeInfoParagraph.setFocusable(false);
        encodeInfoParagraph.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.darkGray));
        encodeInfoParagraph.setText("To encode a message \ninto an image, choose\nthe image you want to\nuse, enter your text and\n" +
                "hit the Encode button.\n");
        encodeInfoParagraph.setVisible(true);

        decodeInfoHeading.setForeground(Color.white);
        decodeInfoHeading.setBackground(Color.darkGray);
        decodeInfoHeading.setBounds(340, 160, 100, 30);
        decodeInfoHeading.setFont(fontH2);
        decodeInfoHeading.setFocusable(false);
        decodeInfoHeading.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.darkGray));
        decodeInfoHeading.setText("DECODE");
        decodeInfoHeading.setVisible(true);

        decodeInfoParagraph.setForeground(Color.white);
        decodeInfoParagraph.setBackground(Color.darkGray);
        decodeInfoParagraph.setBounds(340, 200, 200, 300);
        decodeInfoParagraph.setFont(fontP);
        decodeInfoParagraph.setFocusable(false);
        decodeInfoParagraph.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.darkGray));
        decodeInfoParagraph.setText("To decode a hidden \nmessage from an image,\njust choose an image\nand hit the Decode\nbutton.");
        decodeInfoParagraph.setVisible(true);

        intro.add(heading, BorderLayout.CENTER);
        intro.add(encodeInfoHeading, BorderLayout.CENTER);
        intro.add(encodeInfoParagraph, BorderLayout.CENTER);
        intro.add(decodeInfoHeading, BorderLayout.CENTER);
        intro.add(decodeInfoParagraph, BorderLayout.CENTER);

        return intro;
    }

    public JPanel encode() {
        encode.setBackground(Color.darkGray);
        encode.setFocusable(false);
        encode.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.darkGray));
        encode.setLayout(null);
        encode.setVisible(true);

        encodeHeading.setForeground(Color.white);
        encodeHeading.setBackground(Color.darkGray);
        encodeHeading.setBounds(0, 0, winWidth, 100);
        encodeHeading.setHorizontalAlignment(JTextField.CENTER);
        encodeHeading.setFont(fontH);
        encodeHeading.setFocusable(false);
        encodeHeading.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.darkGray));
        encodeHeading.setText("ENCODE");
//
//        defaultImg = new JLabel(defaultImage);
//        defaultImg.setBounds(20, 60, 540, 360);
//        defaultImg.setVisible(true);
//        encodeHeading.setVisible(true);

        encodeUploadImg.setBounds(80, 460, 120, 30);
        encodeUploadImg.setText("Upload Image");
        encodeUploadImg.addActionListener(this);
        encodeUploadImg.setFocusable(false);
        encodeUploadImg.setVisible(true);

        encodeInsertTxt.setBounds(220, 460, 120, 30);
        encodeInsertTxt.setText("Insert Text");
        encodeInsertTxt.addActionListener(this);
        encodeInsertTxt.setFocusable(false);
        encodeInsertTxt.setVisible(true);

        encodeBtn.setBounds(360, 460, 120, 30);
        encodeBtn.setText("Encrypt");
        encodeBtn.addActionListener(this);
        encodeBtn.setFocusable(false);
        encodeBtn.setVisible(true);



        encode.add(encodeHeading);
       // encode.add(defaultImg);
        encode.add(encodeUploadImg);
        encode.add(encodeInsertTxt);
        encode.add(encodeBtn);

        return encode;
    }

    public JPanel decode() {
        decode.setBackground(Color.darkGray);
        decode.setFocusable(false);
        decode.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.darkGray));
        decode.setLayout(null);
        decode.setVisible(true);

        decodeHeading.setForeground(Color.white);
        decodeHeading.setBackground(Color.darkGray);
        decodeHeading.setBounds(0, 0, winWidth, 100);
        decodeHeading.setHorizontalAlignment(JTextField.CENTER);
        decodeHeading.setFont(fontH);
        decodeHeading.setFocusable(false);
        decodeHeading.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.darkGray));
        decodeHeading.setText("DECODE");
//
//        defaultImg = new JLabel(defaultImage);
//        defaultImg.setBounds(20, 60, 540, 360);
//        defaultImg.setVisible(true);
//        decodeHeading.setVisible(true);

        decodeUploadImg.setBounds(80, 460, 120, 30);
        decodeUploadImg.setText("Upload Image");
        decodeUploadImg.addActionListener(this);
        decodeUploadImg.setFocusable(false);
        decodeUploadImg.setVisible(true);

        decodeSaveTxt.setBounds(220, 460, 120, 30);
        decodeSaveTxt.setText("Save Text");
        decodeSaveTxt.addActionListener(this);
        decodeSaveTxt.setFocusable(false);
        decodeSaveTxt.setVisible(true);

        decodeBtn.setBounds(360, 460, 120, 30);
        decodeBtn.setText("Decrypt");
        decodeBtn.addActionListener(this);
        decodeBtn.setFocusable(false);
        decodeBtn.setVisible(true);



        decode.add(decodeHeading);
        //decode.add(defaultImg);
        decode.add(decodeUploadImg);
        decode.add(decodeSaveTxt);
        decode.add(decodeBtn);

        return decode;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==decodeUploadImg || e.getSource()==encodeUploadImg){
            System.out.println("upload");
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image file", "jpg");
            fileChooser.setFileFilter(filter);

            int response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                File path=fileChooser.getSelectedFile().getAbsoluteFile();


            }
        }
    }

    public static void main(String[] args) {
        new Main();
    }


}