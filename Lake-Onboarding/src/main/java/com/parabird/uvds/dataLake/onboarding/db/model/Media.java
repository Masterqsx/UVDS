package com.parabird.uvds.dataLake.onboarding.db.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "MEDIA")
public class Media {
    @Id
    @GeneratedValue
    @Column(name = "dataId")
    private int dataId;

    @ManyToOne
    private Source source;

    @Column(name = "insertTime")
    private Timestamp insertTime;

    public Media() {}

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
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

    @Override
    public String toString() {
        return "Media{" +
                "dataId=" + dataId +
                ", source=" + Objects.toString(source) +
                ", insertTime=" + insertTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Media)) return false;
        Media media = (Media) o;
        return dataId == media.dataId &&
                Objects.equals(source, media.source) &&
                Objects.equals(insertTime, media.insertTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataId, source, insertTime);
    }

    public static MediaBuilder newBuilder() {
        return new MediaBuilder();
    }

    public static class MediaBuilder {
        private int dataId;
        private Source source;
        private Timestamp  insertTime;


        public MediaBuilder setDataId(int dataId) {
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

        public Media build() {
            Media media = new Media();
            media.setDataId(dataId);
            media.setSource(source);
            media.setInsertTime(insertTime);
            return media;
        }
    }

}
