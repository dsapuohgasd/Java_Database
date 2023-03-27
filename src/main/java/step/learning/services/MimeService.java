package step.learning.services;

import com.google.inject.Singleton;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class MimeService {
    private final Map<String,String> imageTypes;
    public MimeService(){
        imageTypes = new HashMap<>();
        imageTypes.put(".bmp","image/bmp");
        imageTypes.put(".gif","image/gif");
        imageTypes.put(".jpg","image/jpeg");
        imageTypes.put(".jpeg","image/jpeg");
        imageTypes.put(".png","image/png");
    }

    /**
     * checks if extension given corresponds to image type
     * @param extension
     * @return boolean
     */
    public boolean isImage(String extension){
        return imageTypes.containsKey(extension);
    }

    public String getMimeByExtension(String extension){
        if (imageTypes.containsKey(extension)){
            return imageTypes.get(extension);
        }
        return null;
    }
}
