package com.parabird.uvds.dataLake.onboarding.db.model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Source")
public class Source implements Serializable {
    private static final long serialVersionUID = 4577341449072703351L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "sourceId")
    private Long sourceId;

    @Column(name = "sourceName", unique = true)
    private String sourceName;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "source", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<Media> medias;

    public Source() {}

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
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
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Source)) return false;
        Source source = (Source) o;
        return Objects.equals(sourceId, source.sourceId) &&
                Objects.equals(sourceName, source.sourceName) &&
                Objects.equals(description, source.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceId, sourceName, description);
    }

    public static SourceBuilder newSourceBuilder() {
        return new SourceBuilder();
    }

    public static final class SourceBuilder {
        private Long sourceId;
        private String sourceName;
        private String description;
        private List<Media> medias;

        private SourceBuilder() {
        }

        public static SourceBuilder aSource() {
            return new SourceBuilder();
        }

        public SourceBuilder setSourceId(Long sourceId) {
            this.sourceId = sourceId;
            return this;
        }

        public SourceBuilder setSourceName(String sourceName) {
            this.sourceName = sourceName;
            return this;
        }

        public SourceBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public SourceBuilder setMedias(List<Media> medias) {
            this.medias = medias;
            return this;
        }

        public Source build() {
            Source source = new Source();
            source.setSourceId(sourceId);
            source.setSourceName(sourceName);
            source.setDescription(description);
            source.setMedias(medias);
            return source;
        }
    }
}
