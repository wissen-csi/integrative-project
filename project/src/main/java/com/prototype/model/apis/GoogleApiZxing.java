package com.prototype.model.apis;

import java.awt.image.BufferedImage;

import java.nio.file.FileSystems;
import java.nio.file.Path;

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
 * Class responsible for encapsulating the logic for handling, creating, and
 * reading QR codes
 * 
 * @author Jorge Forero
 * @version 1.5
 */
public class GoogleApiZxing {

    /**
     * Method responsible for creating a {@link BitMatrix} for creating a QR code
     * 
     * @param codeQR text save in {@link BitMatrix}
     * @return Class {@link BitMatrix} with the information of the codeQR
     * @throws Exception
     */
    public static BitMatrix createQr(String codeQR) throws Exception {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(codeQR, BarcodeFormat.QR_CODE, 300, 300);
        return bitMatrix;
    }

    /**
     * Method responsible for converting a {@link BitMatrix} to a QR code image
     * 
     * @param bitMatrix Qr in binary
     * @return Image of the QR code in the Class {@link BufferedImage}
     */
    public static BufferedImage generateImagen(BitMatrix bitMatrix) {
        MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig();
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
        return image;
    }

    /**
     * Method responsible for converting a {@link BitMatrix} to a QR code image.
     * and save it in the folder the user specified.
     * 
     * @param bitMatrix Qr binary
     * @param urlString Path
     * @throws Exception
     */
    @Deprecated
    public static void generateImagen(BitMatrix bitMatrix, String urlString) throws Exception {
        MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig();
        Path path = FileSystems.getDefault().getPath(urlString + ".png");
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path, matrixToImageConfig);
    }

    /**
     * Method responsible for reading a QR code from an image.
     * 
     * @param image
     * @return Text save this qr or {@code null} if QR code not found
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
}
