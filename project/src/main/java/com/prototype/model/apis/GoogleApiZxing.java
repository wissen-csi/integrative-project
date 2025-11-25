package com.prototype.model.apis;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatWriter;

import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

/**
 * Provides utility methods for generating, converting, saving, and reading QR codes
 * using the ZXing library. This class encapsulates the complete logic for creating
 * QR matrices, producing images, decoding QR content, and storing QR images.
 * 
 * @version 1.5
 * author Jorge Forero
 */
public class GoogleApiZxing {

    /**
     * Creates a {@link BitMatrix} representation of a QR code from a given string.
     *
     * @param codeQR the text to encode into the QR code
     * @return a {@link BitMatrix} containing the encoded QR data
     * @throws Exception if encoding the QR code fails
     */
    public static BitMatrix createQr(String codeQR) throws Exception {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(codeQR, BarcodeFormat.QR_CODE, 300, 300);
        return bitMatrix;
    }

    /**
     * Converts a {@link BitMatrix} into a rendered QR code image.
     *
     * @param bitMatrix the QR code represented as binary matrix data
     * @return a {@link BufferedImage} containing the rendered QR code
     */
    public static BufferedImage generateImagen(BitMatrix bitMatrix) {
        MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig();
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
        return image;
    }

    /**
     * Converts a {@link BitMatrix} into a QR code image and saves it to a file path
     * selected by the user.
     *
     * @param bitMatrix the binary matrix containing the QR code
     * @param urlString the destination path (without extension)
     * @throws Exception if writing the file fails
     * @deprecated replaced by improved image handling methods
     */
    @Deprecated
    public static void generateImagen(BitMatrix bitMatrix, String urlString) throws Exception {
        MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig();
        Path path = FileSystems.getDefault().getPath(urlString + ".png");
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path, matrixToImageConfig);
    }

    /**
     * Attempts to decode a QR code from a provided {@link BufferedImage}.
     *
     * @param image the image containing the QR code
     * @return the decoded QR text, or {@code null} if no valid QR code is found
     */
    public static String bufferQr(BufferedImage image) {
        LuminanceSource bufferedImageLuminanceSource = new BufferedImageLuminanceSource(image);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(bufferedImageLuminanceSource));
        QRCodeReader qrCodeReader = new QRCodeReader();
        try {
            Result result = qrCodeReader.decode(binaryBitmap);
            return result.getText();
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    /**
     * Saves a QR code image to the user's default Pictures directory.
     *
     * @param image the QR code image to save
     * @param fileName the name of the output file, excluding extension
     * @throws Exception if writing the image file fails
     */
    public static void saveQrToPictures(BufferedImage image, String fileName) throws Exception {
        String pictures = System.getProperty("user.home") + "/Pictures";
        File file = new File(pictures, fileName + ".png");
        ImageIO.write(image, "png", file);
    }
}
