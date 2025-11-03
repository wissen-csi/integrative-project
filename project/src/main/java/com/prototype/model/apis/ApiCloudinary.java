package com.prototype.model.apis;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class ApiCloudinary {


private static final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
"cloud_name", "duxjcqgdx",
"api_key", "522365592968961",
"api_secret", "sB2dtbhcBG2ti74QmAKkiE3xB-k",
"secure", true));
@SuppressWarnings("rawtypes")
public static String saveimage(String url){
    File file = new File(url);
    try {
        Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        return (String) uploadResult.get("url");
    } catch (IOException e) {
        e.printStackTrace();
    }

    return null;
}


}
