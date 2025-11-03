package com.prototype.model.apis;

import java.awt.image.BufferedImage;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;


public class GoogleApiZxing {

public BitMatrix createQR(String codeQR) throws Exception{
    BitMatrix bitMatrix = new MultiFormatWriter().encode(codeQR, BarcodeFormat.QR_CODE, 300, 300);
    return bitMatrix;
}
public BufferedImage generateImagen(BitMatrix bitMatrix){
    MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig();
   BufferedImage  image =  MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
   
    return image;
}
public void generateImagen(BitMatrix bitMatrix, String urlString)throws Exception{
    MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig();
    Path path =FileSystems.getDefault().getPath(urlString);
    MatrixToImageWriter.writeToPath(bitMatrix,"PNG", path, matrixToImageConfig);
}
}

