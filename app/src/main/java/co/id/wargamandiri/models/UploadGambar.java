package co.id.wargamandiri.models;

import android.graphics.Bitmap;

public class UploadGambar {
    Bitmap image;
    String path;

    public UploadGambar() {
    }

    public UploadGambar(Bitmap image, String path) {
        this.image = image;
        this.path = path;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
