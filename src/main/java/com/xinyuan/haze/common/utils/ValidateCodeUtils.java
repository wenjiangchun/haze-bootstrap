package com.xinyuan.haze.common.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;


public class ValidateCodeUtils {

    public static String FONT_FAMILY = "Arial Black";

    public static Integer FONT_SIZE = 18;

    
    public static String getCode(int width, int height, int num,
                                 OutputStream out) {
        width = width > 0 ? width : 60;
        height = height > 0 ? height : 18;
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Font font = new Font(FONT_FAMILY, Font.PLAIN, FONT_SIZE);
        Random random = new Random();
        Graphics g = image.getGraphics();
        g.setColor(getRandColor(200, 250));
        g.fillRect(1, 1, width - 1, height - 1);
        g.setColor(new Color(102, 102, 103));
        g.drawRect(0, 0, width - 1, height - 1);
        g.setFont(font);
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int x1 = random.nextInt(6) + 1;
            int y1 = random.nextInt(12) + 1;
            g.drawLine(x, y, x + x1, y + y1);
        }
        String sRand = "";
        for (int i = 0; i < num; i++) {
            String tmp = getRandomChar();
            sRand += tmp;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random
                    .nextInt(110), 20 + random.nextInt(110)));
            g.drawString(tmp, FONT_SIZE * i + 5, FONT_SIZE + 5);
        }
        g.dispose();
        try {
            ImageIO.write(image, "JPEG", out);
            out.close();
        } catch (IOException e) {
            sRand = null;
            e.printStackTrace();
        }
        return sRand;
    }

   
    public static Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);

        return new Color(r, g, b);
    }

    
    public static String getRandomChar() {
        int rand = (int) Math.round(Math.random() * 2);
        long itmp = 0;
        char ctmp = '\u0000';
        switch (rand) {
            case 1:
                itmp = Math.round(Math.random() * 25 + 65);
                ctmp = (char) itmp;
                return String.valueOf(ctmp);
            case 2:
                itmp = Math.round(Math.random() * 25 + 97);
                ctmp = (char) itmp;
                return String.valueOf(ctmp);
            default:
                itmp = Math.round(Math.random() * 9);
                return String.valueOf(itmp);
        }

    }

}
