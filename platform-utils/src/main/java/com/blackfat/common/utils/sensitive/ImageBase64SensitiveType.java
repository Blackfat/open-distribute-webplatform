package com.blackfat.common.utils.sensitive;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-04-20 14:22
 * @since 1.0-SNAPSHOT
 */
public class ImageBase64SensitiveType extends BaseSensitiveType {
    static final String DATA_IMAGE_ = "data:image/";
    static final String _BASE64_ = ";base64,";


    public ImageBase64SensitiveType() {
        super("data:image/[a-z0-9-]+;base64,[a-zA-Z0-9+=/]+");
    }

    @Override
    public String name() {
        return "ImageBase64";
    }

    @Override
    public boolean match(String k, String s) {
        return s.length()>=INT_64 && s.startsWith(DATA_IMAGE_) && s.contains(_BASE64_);
    }

    @Override
    public String desensitize(String s) {
        if(s == null){ return null; }
        int len = s.length();
        if(len < INT_64){ return s; }
        return new StringBuilder()
                .append(s.substring(0, 32))
                .append("...").append(len).append("...")
                .append(s.substring(len-32))
                .toString();
    }
}
