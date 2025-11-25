package com.prototype.model.apis;

import org.bytedeco.javacv.FrameGrabber.Exception;

import java.awt.image.BufferedImage;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

/**
 * Provides utilities for working with the JavaCV API, including real-time QR code
 * scanning and capturing images from the system camera.
 * This class handles frame grabbing, camera preview display, and image conversion.
 * 
 * @version 1.3
 * @author Jorge Forero
 */
public class CvApi {

    /**
     * Continuously captures frames from the system camera using
     * {@link OpenCVFrameGrabber} and attempts to detect a QR code in each frame.
     * A camera preview window is displayed using {@link CanvasFrame}.
     * <p>
     * The method stops scanning once a valid QR code is detected or the preview
     * window is closed.
     * </p>
     *
     * @return the decoded text extracted from the QR code, or {@code null} if no QR code is found
     * @throws Exception if the frame grabber encounters an error
     * @throws InterruptedException if the thread is interrupted during execution
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
     * Captures a single frame from the system camera and converts it into a
     * {@link BufferedImage}. The captured image can be used for storing or
     * processing equipment photos.
     *
     * @return the captured image as a {@link BufferedImage}
     * @throws Exception if the frame grabber fails to start or read a frame
     * @throws InterruptedException if the thread is interrupted during capture
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
