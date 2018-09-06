package com.parabird.uvds.dataLake.onboarding.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
public class ImageMedia extends Media {
    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @Column(name = "format")
    private String format;

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return "ImageMedia{" +
                "width=" + width +
                ", height=" + height +
                ", format='" + format + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageMedia)) return false;
        if (!super.equals(o)) return false;
        ImageMedia that = (ImageMedia) o;
        return Objects.equals(width, that.width) &&
                Objects.equals(height, that.height) &&
                Objects.equals(format, that.format);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), width, height, format);
    }

    public static ImageMediaBuilder newImageMediaBuilder() {
        return new ImageMediaBuilder();
    }

    public static final class ImageMediaBuilder {
        private Integer width;
        private Integer height;
        private String format;
        private Long dataId;
        private Source source;
        private Timestamp insertTime;
        private String uid;
        private String filePath;
        private String fileName;
        private Map<String, String> tags = new HashMap<>();

        private ImageMediaBuilder() {
        }

        public static ImageMediaBuilder anImageMedia() {
            return new ImageMediaBuilder();
        }

        public ImageMediaBuilder setWidth(Integer width) {
            this.width = width;
            return this;
        }

        public ImageMediaBuilder setHeight(Integer height) {
            this.height = height;
            return this;
        }

        public ImageMediaBuilder setFormat(String format) {
            this.format = format;
            return this;
        }

        public ImageMediaBuilder setDataId(Long dataId) {
            this.dataId = dataId;
            return this;
        }

        public ImageMediaBuilder setSource(Source source) {
            this.source = source;
            return this;
        }

        public ImageMediaBuilder setInsertTime(Timestamp insertTime) {
            this.insertTime = insertTime;
            return this;
        }

        public ImageMediaBuilder setUid(String uid) {
            this.uid = uid;
            return this;
        }

        public ImageMediaBuilder setFilePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        public ImageMediaBuilder setFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public ImageMediaBuilder setTags(Map<String, String> tags) {
            this.tags = tags;
            return this;
        }

        public ImageMedia build() {
            ImageMedia imageMedia = new ImageMedia();
            imageMedia.setWidth(width);
            imageMedia.setHeight(height);
            imageMedia.setFormat(format);
            imageMedia.setDataId(dataId);
            imageMedia.setSource(source);
            imageMedia.setInsertTime(insertTime);
            imageMedia.setUid(uid);
            imageMedia.setFilePath(filePath);
            imageMedia.setFileName(fileName);
            imageMedia.setTags(tags);
            return imageMedia;
        }
    }
}
