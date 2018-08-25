package com.parabird.data_lake.onboarding.db.model;

import javax.persistence.*;
import java.util.Date;

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
    private Date insertTime;

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

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    @Override
    public String toString() {
        return "Media{" +
                "dataId=" + dataId +
                ", source=" + source.toString() +
                ", insertTime=" + insertTime +
                '}';
    }
}
