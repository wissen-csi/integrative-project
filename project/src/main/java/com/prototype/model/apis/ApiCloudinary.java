package com.prototype.model.apis;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.prototype.model.config.UUIDGenerator;

/**
 * Class responsible for storing the activation and use of the Cloudinary API
 * 
 * @author Jorge Forero
 * @version 1.2
 */
public class ApiCloudinary {

    /**
     * Constant responsible for activating the API with its respective credentials
     * value {@value}
     */
    private static final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "duxjcqgdx",
            "api_key", "522365592968961",
            "api_secret", "sB2dtbhcBG2ti74QmAKkiE3xB-k",
            "secure", true));

    /**
     * A method that searches for a locally stored image.
     * Then, uploading it to the cloud.
     * 
     * @param url
     * @return URL of the image saved in the Cloudinary cloud space
     */
    @SuppressWarnings("rawtypes")
    @Deprecated
    public static String saveimage(String url) {
        File file = new File(url);
        try {
            Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            return (String) uploadResult.get("url");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * A method to create a temporary file to save an image.
     * In the cloud, for later deletion from the device's memory.
     * 
     * @param bufferedImage
     * @return URL of the image saved in the Cloudinary cloud space
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    public static String saveimage(BufferedImage bufferedImage) throws IOException {
        File file = File.createTempFile(UUIDGenerator.generate(), "png");
        ImageIO.write(bufferedImage, "png", file);
        Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        file.delete();
        return (String) uploadResult.get("url");
    }
}
