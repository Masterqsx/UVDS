package com.parabird.data_lake.onboarding.db.model;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Source")
public class Source {
    @Id
    @GeneratedValue
    @Column(name = "sourceId")
    private int sourceId;

    @Column(name = "sourceName")
    private String sourceName;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "source")
    private List<Media> medias;

    public Source() {}

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }

    @Override
    public String toString() {
        return "Source{" +
                "sourceId=" + sourceId +
                ", sourceName='" + sourceName + '\'' +
                ", description='" + description + '\'' +
                ", medias=" + StringUtils.join(medias) +
                '}';
    }
}
