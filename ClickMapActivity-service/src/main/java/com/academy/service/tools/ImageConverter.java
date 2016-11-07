package com.academy.service.tools;

import com.academy.model.dto.PointsDTO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by Daniel Palonek on 2016-10-21.
 */
public class ImageConverter {

    public static BufferedImage grayScale(BufferedImage original) {

        int alpha, red, green, blue;
        int newPixel;

        BufferedImage avg_gray = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
        int[] avgLUT = new int[766];
        for(int i=0; i<avgLUT.length; i++) avgLUT[i] = (int) (i / 3);

        for(int i=0; i<original.getWidth(); i++) {
            for(int j=0; j<original.getHeight(); j++) {

                // Get pixels by R, G, B
                alpha = new Color(original.getRGB(i, j)).getAlpha();
                red = new Color(original.getRGB(i, j)).getRed();
                green = new Color(original.getRGB(i, j)).getGreen();
                blue = new Color(original.getRGB(i, j)).getBlue();

                newPixel = red + green + blue;
                newPixel = avgLUT[newPixel];
                // Return back to original format
                newPixel = colorToRGB(alpha, newPixel, newPixel, newPixel);

                // Write pixels into image
                avg_gray.setRGB(i, j, newPixel);
            }
        }
        return avg_gray;
    }

    public static BufferedImage fillPointsMap(BufferedImage img, int[][] points) {
        final Color pointColor = new Color(255,0,0,80);
        Graphics2D graphics = img.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setPaint(pointColor);
        for(int i=0; i<img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                if (points[i][j] > 0) {
                    drawPoint(graphics, i, j, points[i][j]);
                }
            }
        }
        graphics.dispose();
        return img;
    }

    private static void drawPoint(Graphics2D graphics, int x, int y, int loop) {
        for(int i = 0; i<loop; i++) {
            graphics.fillOval(x + 5, y + 5, 10, 10);
        }
    }

    public static int colorToRGB(int alpha, int red, int green, int blue) {

        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red; newPixel = newPixel << 8;
        newPixel += green; newPixel = newPixel << 8;
        newPixel += blue;

        return newPixel;

    }

    public static int [][] makeClickMatrix(List points, int width, int height) {
        int [][] clickMatrix = new int[width][height];
        points.forEach( p -> {
            String indexes [] = ((PointsDTO)p).getPairValue().split(";");
            clickMatrix[Integer.parseInt(indexes[0])][Integer.parseInt(indexes[1])] += 1;
        });
//        for(int i=0 ; i<300; i++) {
//            for(int j = 0; j<400;j++) {
//                clickMatrix[i][j] = true;
//            }
//        }
        return clickMatrix;
    }

}
