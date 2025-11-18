package com.prototype.model.apis;

import org.bytedeco.javacv.FrameGrabber.Exception;

import java.awt.image.BufferedImage;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

/**
 * Class that encapsulates the logic of the Java CV API for reading QR codes and
 * taking photos
 * 
 * @author Jorge Forero
 * @version 1.3
 */
public class CvApi {
    /**
     * Method responsible for reading the QR code {@link OpenCVFrameGrabber} by
     * capturing multiple frames on camera;
     * this in turn opens a canvas for the camera view with the Class
     * {@link CanvasFrame}
     * 
     * @return returns the text stored within the QR code
     * @throws Exception
     * @throws InterruptedException
     */
    public static String readQr() throws Exception, InterruptedException {
        OpenCVFrameGrabber openCVFrameGrabber = new OpenCVFrameGrabber(0);
        CanvasFrame canvasFrame = new CanvasFrame("null", CanvasFrame.getDefaultGamma());
        canvasFrame.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        Java2DFrameConverter java2dFrameConverter = new Java2DFrameConverter();
        String code = null;
        openCVFrameGrabber.start();
        while (canvasFrame.isVisible()) {
            Frame frame = openCVFrameGrabber.grab();
            if (frame == null) {
                continue;
            }
            canvasFrame.showImage(frame);
            BufferedImage bufferedImage = java2dFrameConverter.convert(frame);
            code = GoogleApiZxing.bufferQr(bufferedImage);
            if (code != null) {

                break;
            }
            Thread.sleep(33);
        }
        java2dFrameConverter.close();
        openCVFrameGrabber.stop();
        openCVFrameGrabber.close();
        canvasFrame.dispose();

        return code;
    }

    /**
     * Method responsible for taking a picture of a {@link Equiement}
     * 
     * @return Return a photo in the class {@link BufferedImage}
     * @throws Exception
     * @throws InterruptedException
     */
    public static BufferedImage takePicture() throws Exception, InterruptedException {
        OpenCVFrameGrabber openCVFrameGrabber = new OpenCVFrameGrabber(0);
        CanvasFrame canvasFrame = new CanvasFrame("null", CanvasFrame.getDefaultGamma());
        canvasFrame.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        Java2DFrameConverter java2dFrameConverter = new Java2DFrameConverter();
        openCVFrameGrabber.start();
        Frame frame = openCVFrameGrabber.grab();
        canvasFrame.showImage(frame);
        BufferedImage bufferedImage = java2dFrameConverter.convert(frame);
        Thread.sleep(33);
        java2dFrameConverter.close();
        openCVFrameGrabber.stop();
        openCVFrameGrabber.close();
        canvasFrame.dispose();
        return bufferedImage;
    }

}