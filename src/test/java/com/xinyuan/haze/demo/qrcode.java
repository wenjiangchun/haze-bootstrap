package com.xinyuan.haze.demo;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.Code128Reader;

import javax.imageio.ImageIO;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class qrcode {
    public static void main(String[] args) {
        Reader reader = new MultiFormatReader();
        try {
            BufferedImage bi = ImageIO.read(new File("d:\\QQ截图20170413000807.png"));
            LuminanceSource source = new BufferedImageLuminanceSource(bi);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Result result = reader.decode(bitmap);
            result.getText();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

    }
}
