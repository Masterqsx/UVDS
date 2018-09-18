package com.parabird.uvds.dataLake.onboarding.db.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "MEDIA")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Media {
    @Id
    @GeneratedValue
    @Column(name = "dataId")
    private Long dataId;

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private Source source;

    @Column(name = "insertTime")
    private Timestamp insertTime;

    @Column(name = "sourceUid")
    private String sourceUid;

    @Column(name = "uid")
    private String uid;

    @Column(name = "filePath")
    private String filePath;

    @Column(name = "fileName")
    private String fileName;

    @ElementCollection
    @CollectionTable(name="tags")
    @MapKeyColumn(name="tagName")
    @Column(name="tagValue")
    private Map<String, String> tags = new HashMap<>();

    public Media() {}

    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Timestamp  getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Timestamp  insertTime) {
        this.insertTime = insertTime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSourceUid() {
        return sourceUid;
    }

    public void setSourceUid(String sourceUid) {
        this.sourceUid = sourceUid;
    }

    @Override
    public String toString() {
        return "Media{" +
                "dataId=" + dataId +
                ", source=" + source +
                ", insertTime=" + insertTime +
                ", sourceUid='" + sourceUid + '\'' +
                ", uid='" + uid + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileName='" + fileName + '\'' +
                ", tags=" + tags +
                '}';
    }

    private boolean mapEquals(Map<String, String> l, Map<String, String> r) {
        if (l.size() != r.size()) return false;
        for (Map.Entry<String, String> lEntry: l.entrySet()) {
            if (!r.containsKey(lEntry.getKey())
                || !(r.get(lEntry.getKey()).equals(lEntry.getValue())))
                return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Media)) return false;
        Media media = (Media) o;
        return Objects.equals(dataId, media.dataId) &&
                Objects.equals(source, media.source) &&
                Objects.equals(insertTime, media.insertTime) &&
                Objects.equals(sourceUid, media.sourceUid) &&
                Objects.equals(uid, media.uid) &&
                Objects.equals(filePath, media.filePath) &&
                Objects.equals(fileName, media.fileName) &&
                mapEquals(tags, media.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataId, source, insertTime, sourceUid, uid, filePath, fileName, tags);
    }


    public static MediaBuilder newMediaBuilder() {
        return new MediaBuilder();
    }

    public static final class MediaBuilder {
        private Long dataId;
        private Source source;
        private Timestamp insertTime;
        private String sourceUid;
        private String uid;
        private String filePath;
        private String fileName;
        private Map<String, String> tags = new HashMap<>();

        private MediaBuilder() {
        }

        public static MediaBuilder aMedia() {
            return new MediaBuilder();
        }

        public MediaBuilder setDataId(Long dataId) {
            this.dataId = dataId;
            return this;
        }

        public MediaBuilder setSource(Source source) {
            this.source = source;
            return this;
        }

        public MediaBuilder setInsertTime(Timestamp insertTime) {
            this.insertTime = insertTime;
            return this;
        }

        public MediaBuilder setSourceUid(String sourceUid) {
            this.sourceUid = sourceUid;
            return this;
        }

        public MediaBuilder setUid(String uid) {
            this.uid = uid;
            return this;
        }

        public MediaBuilder setFilePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        public MediaBuilder setFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public MediaBuilder setTags(Map<String, String> tags) {
            this.tags = tags;
            return this;
        }

        public Media build() {
            Media media = new Media();
            media.setDataId(dataId);
            media.setSource(source);
            media.setInsertTime(insertTime);
            media.setSourceUid(sourceUid);
            media.setUid(uid);
            media.setFilePath(filePath);
            media.setFileName(fileName);
            media.setTags(tags);
            return media;
        }
    }
}
