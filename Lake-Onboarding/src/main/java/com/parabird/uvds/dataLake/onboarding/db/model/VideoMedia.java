package com.parabird.uvds.dataLake.onboarding.db.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
public class VideoMedia extends Media{
    @Column(name = "timeLen")
    private Integer timeLen;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @Column(name = "codec")
    private String codec;

    public Integer getTimeLen() {
        return timeLen;
    }

    public void setTimeLen(Integer timeLen) {
        this.timeLen = timeLen;
    }

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

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    @Override
    public String toString() {
        return "VideoMedia{" +
                "timeLen=" + timeLen +
                ", width=" + width +
                ", height=" + height +
                ", codec=" + codec + ", " +
                super.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VideoMedia)) return false;
        if (!super.equals(o)) return false;
        VideoMedia that = (VideoMedia) o;
        return Objects.equals(timeLen, that.timeLen) &&
                Objects.equals(width, that.width) &&
                Objects.equals(height, that.height) &&
                Objects.equals(codec, that.codec);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), timeLen, width, height, codec);
    }

    public static VideoMediaBuilder newVideoMediaBuilder() {
        return new VideoMediaBuilder();
    }

    public static final class VideoMediaBuilder {
        private Integer timeLen;
        private Integer width;
        private Integer height;
        private Long dataId;
        private String codec;
        private Source source;
        private Timestamp insertTime;
        private String sourceUid;
        private String uid;
        private String filePath;
        private String fileName;
        private Map<String, String> tags = new HashMap<>();

        private VideoMediaBuilder() {
        }

        public static VideoMediaBuilder aVideoMedia() {
            return new VideoMediaBuilder();
        }

        public VideoMediaBuilder setTimeLen(Integer timeLen) {
            this.timeLen = timeLen;
            return this;
        }

        public VideoMediaBuilder setWidth(Integer width) {
            this.width = width;
            return this;
        }

        public VideoMediaBuilder setHeight(Integer height) {
            this.height = height;
            return this;
        }

        public VideoMediaBuilder setDataId(Long dataId) {
            this.dataId = dataId;
            return this;
        }

        public VideoMediaBuilder setCodec(String codec) {
            this.codec = codec;
            return this;
        }

        public VideoMediaBuilder setSource(Source source) {
            this.source = source;
            return this;
        }

        public VideoMediaBuilder setInsertTime(Timestamp insertTime) {
            this.insertTime = insertTime;
            return this;
        }

        public VideoMediaBuilder setSourceUid(String sourceUid) {
            this.sourceUid = sourceUid;
            return this;
        }

        public VideoMediaBuilder setUid(String uid) {
            this.uid = uid;
            return this;
        }

        public VideoMediaBuilder setFilePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        public VideoMediaBuilder setFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public VideoMediaBuilder setTags(Map<String, String> tags) {
            this.tags = tags;
            return this;
        }

        public VideoMedia build() {
            VideoMedia videoMedia = new VideoMedia();
            videoMedia.setTimeLen(timeLen);
            videoMedia.setWidth(width);
            videoMedia.setHeight(height);
            videoMedia.setDataId(dataId);
            videoMedia.setCodec(codec);
            videoMedia.setSource(source);
            videoMedia.setInsertTime(insertTime);
            videoMedia.setSourceUid(sourceUid);
            videoMedia.setUid(uid);
            videoMedia.setFilePath(filePath);
            videoMedia.setFileName(fileName);
            videoMedia.setTags(tags);
            return videoMedia;
        }
    }
}
