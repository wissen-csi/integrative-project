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
 * Provides functionality for interacting with the Cloudinary API, including
 * uploading images from local files or in-memory buffered images.
 * This class manages the Cloudinary client configuration and image upload operations.
 * 
 * @author Jorge Forero
 * @version 1.2
 */
public class ApiCloudinary {

    /**
     * Cloudinary client instance initialized with the required credentials.
     * Used to authenticate and perform upload operations.
     */
    private static final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "duxjcqgdx",
            "api_key", "522365592968961",
            "api_secret", "sB2dtbhcBG2ti74QmAKkiE3xB-k",
            "secure", true));

    /**
     * Uploads an image stored locally on the device to Cloudinary.
     * <p>
     * This method is deprecated and should be replaced with the version that accepts
     * a {@link BufferedImage}.
     * </p>
     *
     * @param url the local file path of the image to upload
     * @return the URL of the uploaded image on Cloudinary, or {@code null} if an error occurs
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
     * Uploads a {@link BufferedImage} to Cloudinary by creating
     * a temporary file in the system. The temporary file is removed after upload.
     *
     * @param bufferedImage the in-memory image to upload
     * @return the URL of the uploaded image on Cloudinary
     * @throws IOException if an error occurs while writing or uploading the temporary file
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
