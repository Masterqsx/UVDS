package com.parabird.uvds.dataLake.onboarding.db.model.builder;

import com.parabird.uvds.dataLake.onboarding.db.model.Media;
import com.parabird.uvds.dataLake.onboarding.db.model.Source;

import java.util.List;

public final class SourceBuilder {
    private int sourceId;
    private String sourceName;
    private String description;
    private List<Media> medias;

    private SourceBuilder() {
    }

    public static SourceBuilder aSource() {
        return new SourceBuilder();
    }

    public SourceBuilder withSourceId(int sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    public SourceBuilder withSourceName(String sourceName) {
        this.sourceName = sourceName;
        return this;
    }

    public SourceBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public SourceBuilder withMedias(List<Media> medias) {
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
