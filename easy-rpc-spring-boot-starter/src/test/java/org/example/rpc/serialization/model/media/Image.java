package org.example.rpc.serialization.model.media;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author Roc
 * @Date 2024/11/14 11:34
 */
@Getter
@Setter
public class Image implements Serializable {
    private static final long serialVersionUID = 1L;
    public String uri;
    public String title;  // Can be null
    public int width;
    public int height;
    public Size size;

    public Image() {
    }

    public Image(String uri, String title, int width, int height, Size size) {
        this.height = height;
        this.title = title;
        this.uri = uri;
        this.width = width;
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        if (height != image.height) return false;
        if (width != image.width) return false;
        if (size != image.size) return false;
        if (title != null ? !title.equals(image.title) : image.title != null) return false;
        if (uri != null ? !uri.equals(image.uri) : image.uri != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uri != null ? uri.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + width;
        result = 31 * result + height;
        result = 31 * result + (size != null ? size.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[Image ");
        sb.append("uri=").append(uri);
        sb.append(", title=").append(title);
        sb.append(", width=").append(width);
        sb.append(", height=").append(height);
        sb.append(", size=").append(size);
        sb.append("]");
        return sb.toString();
    }

    public enum Size {
        SMALL, LARGE
    }
}
