package bitmap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class bitmap {
    private int height;
    private int width;
    BufferedImage img;

    public void setHeight(int h) {
        this.height = h;
    }

    public void setWidth(int w) {
        this.width = w;
    }

    public void ReadMyImg(String path) {
        File file = new File(path);
        try {
            img = ImageIO.read(file);
            height = img.getHeight();
            width = img.getWidth();
            System.out.println("Height : " + height + "\n" + "Width : " + width);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void writeImage(String path) {
        try {
            ImageIO.write(img, "bmp", new File(path));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Invert() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = img.getRGB(x, y);
                int a = (p >> 24) & 0xff;
                int R = (p >> 16) & 0xff;
                int G = (p >> 8) & 0xff;
                int B = p & 0xff;

                R = 255 - R;
                G = 255 - G;
                B = 255 - B;

                p = (a << 24) | (R << 16) | (G << 8) | B;
                img.setRGB(x, y, p);
            }
        }
    }

    public void BlackandWhite() {
        BufferedImage bwImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        bwImage.createGraphics().drawImage(img, 0, 0, null);

    }

    public void DarkenorLighten() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(img.getRGB(x, y));
                color = color.darker();
                img.setRGB(x, y, color.getAlpha());
            }
        }

    }

    public void Pixilate() {
        final int sizePixilate = 15;
        Raster rasterData = img.getData();
        WritableRaster identicalSize = rasterData.createCompatibleWritableRaster();

        for (int y = 0; y < rasterData.getHeight(); y += sizePixilate) {
            for (int x = 0; x < rasterData.getWidth(); x += sizePixilate) {
                double[] copyPix = new double[3];
                copyPix = rasterData.getPixel(x, y, copyPix);

                for (int yd = y; (yd < y + sizePixilate) && (yd < identicalSize.getHeight()); yd++) {
                    for (int xd = x; (xd < x + sizePixilate) && (xd < identicalSize.getWidth()); xd++) {
                        identicalSize.setPixel(xd, yd, copyPix);
                    }
                }
            }

        }
        img.setData(identicalSize);
    }

    public BufferedImage rotate(BufferedImage img)
    {
         width = img.getWidth();
         height = img.getHeight();

        BufferedImage rotateImg = new BufferedImage(
                img.getWidth(), img.getHeight(), img.getType());

        Graphics2D graphImg = rotateImg.createGraphics();

        graphImg.rotate(Math.toRadians(90), width / 2,
                height / 2);
        graphImg.drawImage(img, null, 0, 0);
        return rotateImg;
    }
}
