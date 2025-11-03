package com.prototype;

import com.prototype.model.apis.GoogleApiZxing;

public class Main {
    public static void main(String[] args) throws Exception {
        GoogleApiZxing googleApiZxing = new GoogleApiZxing();
        googleApiZxing.generateImagen(googleApiZxing.createQR("PORNO"), "C:\\Users\\ADMIN\\Pictures\\project\\src\\main\\java\\com\\prototype\\model\\imagen_test"); 


    }
}
