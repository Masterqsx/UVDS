package com.parabird.uvds.dataLake.onboarding.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

@Entity
public class ImageMedia extends Media implements Serializable {
    private static final long serialVersionUID = -334976281690242352L;

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

    public ImageMedia clone() {
        ImageMedia cloned = ImageMedia.newImageMediaBuilder()
            .setDataId(getDataId())
            .setSource(getSource() == null ? null : Source.newSourceBuilder()
                .setSourceId(getSource().getSourceId())
                .setSourceName(getSource().getSourceName())
                .setDescription(getSource().getDescription())
                .build())
            .setInsertTime(getInsertTime())
            .setUid(getUid())
            .setFilePath(getFilePath())
            .setTags(getTags() == null ? null : new HashSet<>(getTags()))
            .setFileName(getFileName())
            .setSourceUid(getSourceUid())
            .setWidth(getWidth())
            .setHeight(getHeight())
            .setFormat(getFormat())
            .build();

        if (getTags() != null) {
            Set<Tag> cloneTags = new HashSet<>();
            for (Tag tag : getTags()) {
                cloneTags.add(Tag.newTagBuilder()
                    .setTagId(tag.getTagId())
                    .setTagSource(tag.getTagSource())
                    .setTagName(tag.getTagName())
                    .setTagValue(tag.getTagValue())
                    .build());
            }
            cloned.setTags(cloneTags);
        }
        return cloned;
    }

    public ImageMedia merge(ImageMedia other) {
        if (getDataId() == null) setDataId(other.getDataId());
        if (getSource() == null) {
            if (other.getSource() != null) {
                setSource(Source.newSourceBuilder()
                        .setSourceId(other.getSource().getSourceId())
                        .setSourceName(other.getSource().getSourceName())
                        .setDescription(other.getSource().getDescription())
                        .build());
            }
        }
        if (getInsertTime() == null) setInsertTime(other.getInsertTime());
        if (getUid() == null) setUid(other.getUid());
        if (getFilePath() == null) setFilePath(other.getFilePath());
        if (getTags() == null && other.getTags() != null) setTags(new HashSet<>(other.getTags()));
        if (getFileName() == null) setFileName(other.getFileName());
        if (getSourceUid() == null) setSourceUid(other.getSourceUid());
        if (getWidth() == null) setWidth(other.getWidth());
        if (getHeight() == null) setHeight(other.getHeight());
        if (getFormat() == null) setFormat(other.getFormat());

        return this;
    }

    @Override
    public String toString() {
        return "ImageMedia{" +
                "width=" + width +
                ", height=" + height +
                ", format='" + format + ", " +
                super.toString() +
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
        private String sourceUid;
        private String uid;
        private String filePath;
        private String fileName;
        private Set<Tag> tags = new HashSet<>();

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

        public ImageMediaBuilder setSourceUid(String sourceUid) {
            this.sourceUid = sourceUid;
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

        public ImageMediaBuilder setTags(Set<Tag> tags) {
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
            imageMedia.setSourceUid(sourceUid);
            imageMedia.setUid(uid);
            imageMedia.setFilePath(filePath);
            imageMedia.setFileName(fileName);
            imageMedia.setTags(tags);
            return imageMedia;
        }
    }
}
