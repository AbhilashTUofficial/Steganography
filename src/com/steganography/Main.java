package com.steganography;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main extends JFrame implements ActionListener {
    JPanel window = new JPanel();
    JPanel btnPanel = new JPanel();
    JTextArea description = new JTextArea();
    JButton uploadImgBtn = new JButton();
    JButton uploadTextBtn = new JButton();
    JButton encodeBtn = new JButton();
    JButton decodeBtn = new JButton();

    File imagePath = null;
    String textPath = "";
    String mess="";

    JTextField heading = new JTextField();

    BufferedImage sourceImage = null;
    BufferedImage embeddedImage = null;

    Main() {
        setBounds(100, 200, 500, 260);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        window.setBackground(Color.darkGray);
        window.setForeground(Color.white);
        window.setLayout(new GridLayout(3, 1));
        window.setFocusable(false);

        window.add(setHeading());
        window.add(setButtonPanel());
        window.add(setDescription());
        window.setVisible(true);
        add(window);
        setVisible(true);
    }

    public JTextField setHeading() {
        heading.setHorizontalAlignment(JTextField.CENTER);
        heading.setText("STEGANOGRAPHY");
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        heading.setEditable(false);
        heading.setFocusable(false);
        heading.setBackground(Color.darkGray);
        heading.setForeground(Color.white);
        heading.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.darkGray));
        return heading;
    }

    public JPanel setButtonPanel() {

        btnPanel.setBackground(Color.darkGray);

        uploadImgBtn.setText("Upload Image");
        uploadImgBtn.setBounds(new Rectangle(100, 40));
        uploadImgBtn.setFocusable(false);
        uploadImgBtn.addActionListener(this);
        uploadImgBtn.setVisible(true);

        uploadTextBtn.setText("Upload Text");
        uploadTextBtn.setBounds(new Rectangle(100, 40));
        uploadTextBtn.setFocusable(false);
        uploadTextBtn.addActionListener(this);
        uploadTextBtn.setVisible(true);

        encodeBtn.setText("Encrypt");
        encodeBtn.setBounds(new Rectangle(100, 40));
        encodeBtn.setFocusable(false);
        encodeBtn.addActionListener(this);
        encodeBtn.setVisible(true);

        decodeBtn.setText("Decrypt");
        decodeBtn.setBounds(new Rectangle(100, 40));
        decodeBtn.setFocusable(false);
        decodeBtn.addActionListener(this);
        decodeBtn.setVisible(true);


        btnPanel.add(uploadImgBtn);
        btnPanel.add(uploadTextBtn);
        btnPanel.add(encodeBtn);
        btnPanel.add(decodeBtn);
        return btnPanel;
    }

    public JTextArea setDescription() {
        description.setBackground(Color.darkGray);
        description.setForeground(Color.white);
        description.setFont(new Font("Arial", Font.PLAIN, 18));
        description.setText("Encrypt your test to a image by clicking the Encrypt \nbutton," +
                "Decode the encrypted message\nfrom image" +
                "by clicking the Decrypt button");
        description.setFocusable(false);
        description.setEditable(false);
        description.setBorder(BorderFactory.createEmptyBorder(3, 56, 10, 3));
        description.setVisible(true);
        return description;
    }


    private void openImage() {
        try {
            sourceImage = ImageIO.read(imagePath);
            JLabel l = new JLabel(new ImageIcon(sourceImage));
            System.out.println(sourceImage);
            this.validate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void embedMessage() {

        embeddedImage = sourceImage.getSubimage(0, 0,
                sourceImage.getWidth(), sourceImage.getHeight());
        embedMessage(embeddedImage, mess);
       // JLabel l = new JLabel(new ImageIcon(embeddedImage));
        //embeddedPane.getViewport().add(l);
        this.validate();
    }

    private void embedMessage(BufferedImage img, String mess) {
        int messageLength = mess.length();

        int imageWidth = img.getWidth(), imageHeight = img.getHeight(),
                imageSize = imageWidth * imageHeight;
        if (messageLength * 8 + 32 > imageSize) {
            JOptionPane.showMessageDialog(this, "Message is too long for the chosen image",
                    "Message too long!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        embedInteger(img, messageLength, 0, 0);

        byte b[] = mess.getBytes();
        for (int i = 0; i < b.length; i++)
            embedByte(img, b[i], i * 8 + 32, 0);
    }

    private void embedInteger(BufferedImage img, int n, int start, int storageBit) {
        int maxX = img.getWidth(), maxY = img.getHeight(),
                startX = start / maxY, startY = start - startX * maxY, count = 0;
        for (int i = startX; i < maxX && count < 32; i++) {
            for (int j = startY; j < maxY && count < 32; j++) {
                int rgb = img.getRGB(i, j), bit = getBitValue(n, count);
                rgb = setBitValue(rgb, storageBit, bit);
                img.setRGB(i, j, rgb);
                count++;
            }
        }
    }

    private void embedByte(BufferedImage img, byte b, int start, int storageBit) {
        int maxX = img.getWidth(), maxY = img.getHeight(),
                startX = start / maxY, startY = start - startX * maxY, count = 0;
        for (int i = startX; i < maxX && count < 8; i++) {
            for (int j = startY; j < maxY && count < 8; j++) {
                int rgb = img.getRGB(i, j), bit = getBitValue(b, count);
                rgb = setBitValue(rgb, storageBit, bit);
                img.setRGB(i, j, rgb);
                count++;
            }
        }
    }
    private int getBitValue(int n, int location) {
        int v = n & (int) Math.round(Math.pow(2, location));
        return v == 0 ? 0 : 1;
    }

    private int setBitValue(int n, int location, int bit) {
        int toggle = (int) Math.pow(2, location), bv = getBitValue(n, location);
        if (bv == bit)
            return n;
        if (bv == 0 && bit == 1)
            n |= toggle;
        else if (bv == 1 && bit == 0)
            n ^= toggle;
        return n;
    }
    private void saveImage() {
        if (embeddedImage == null) {
            JOptionPane.showMessageDialog(this, "No message has been embedded!",
                    "Nothing to save", JOptionPane.ERROR_MESSAGE);
            return;
        }
        java.io.File f = showFileDialog(false);
        String name = f.getName();
        String ext = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
        if (!ext.equals("png") && !ext.equals("bmp") && !ext.equals("dib")) {
            ext = "png";
            f = new java.io.File(f.getAbsolutePath() + ".png");
        }
        try {
            if (f.exists()) f.delete();
            ImageIO.write(embeddedImage, ext.toUpperCase(), f);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private java.io.File showFileDialog(final boolean open) {
        JFileChooser fc = new JFileChooser("Open an image");
        javax.swing.filechooser.FileFilter ff = new javax.swing.filechooser.FileFilter() {
            public boolean accept(java.io.File f) {
                String name = f.getName().toLowerCase();
                if (open)
                    return f.isDirectory() || name.endsWith(".jpg") || name.endsWith(".jpeg") ||
                            name.endsWith(".png") || name.endsWith(".gif") || name.endsWith(".tiff") ||
                            name.endsWith(".bmp") || name.endsWith(".dib");
                return f.isDirectory() || name.endsWith(".png") || name.endsWith(".bmp");
            }

            public String getDescription() {
                if (open)
                    return "Image (*.jpg, *.jpeg, *.png, *.gif, *.tiff, *.bmp, *.dib)";
                return "Image (*.png, *.bmp)";
            }
        };
        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter(ff);

        java.io.File f = null;
        if (open && fc.showOpenDialog(this) == fc.APPROVE_OPTION)
            f = fc.getSelectedFile();
        else if (!open && fc.showSaveDialog(this) == fc.APPROVE_OPTION)
            f = fc.getSelectedFile();
        return f;
    }

    public static void main(String[] args) {
        new Main();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == uploadImgBtn) {
            FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Only Image Files", "JPG", "JPEG", "PNG", "GIF");
            JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(extensionFilter);

            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                imagePath = fileChooser.getSelectedFile();
            }
            openImage();
        }
        if (e.getSource() == uploadTextBtn) {
            FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Text Files", "txt");
            JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(extensionFilter);

            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                textPath = String.valueOf(fileChooser.getSelectedFile());
                BufferedReader in = null;
                try {
                    in = new BufferedReader(new FileReader(textPath));
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                String line = null;
                while (true) {
                    try {
                        if (!((line = in.readLine()) != null)) break;
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    mess.concat(line);
                }
                try {
                    in.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        if (e.getSource() == encodeBtn) {
            embedMessage();
            saveImage();
        }
    }
}