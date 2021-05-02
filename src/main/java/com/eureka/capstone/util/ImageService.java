package com.eureka.capstone.util;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class ImageService {

    public static byte[] compress(byte[] imageBytes) throws IOException {
        try (
                var inputStream = new ByteArrayInputStream(imageBytes);
                var outputStream = new ByteArrayOutputStream();
                var imageOutputStream = ImageIO.createImageOutputStream(outputStream);
        ) {
            var imageQuality = 0.3f;
            var bufferedImage = ImageIO.read(inputStream);
            Iterator<ImageWriter> imageWriters;

            if (bufferedImage.getColorModel().hasAlpha()) {
                var newImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
                newImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
                bufferedImage = newImage;
            }

            imageWriters = ImageIO.getImageWritersByFormatName("jpg");

            if (!imageWriters.hasNext())
                throw new IllegalStateException("Writers Not Found!!");

            var imageWriter = (ImageWriter) imageWriters.next();

            imageWriter.setOutput(imageOutputStream);

            var imageWriteParam = imageWriter.getDefaultWriteParam();

            imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            imageWriteParam.setCompressionQuality(imageQuality);
            imageWriter.write(null, new IIOImage(bufferedImage, null, null), imageWriteParam);
            imageWriter.dispose();

            return outputStream.toByteArray();
        }
    }

    public static byte[] extractImageFromFile(MultipartFile file) throws IOException {
        var byteObjects = new byte[file.getBytes().length];
        int i = 0;

        for (byte b : file.getBytes()) {
            byteObjects[i++] = b;
        }

        return byteObjects;
    }

}