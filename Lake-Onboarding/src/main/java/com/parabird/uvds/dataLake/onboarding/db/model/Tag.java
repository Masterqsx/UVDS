package com.parabird.uvds.dataLake.onboarding.db.model;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Tag {
    private String tagSource;
    private Integer tagId;
    private String tagName;
    private String tagValue;

    public String getTagSource() {
        return tagSource;
    }

    public void setTagSource(String tagSource) {
        this.tagSource = tagSource;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;
        Tag tag = (Tag) o;
        return Objects.equals(tagSource, tag.tagSource) &&
                Objects.equals(tagId, tag.tagId) &&
                Objects.equals(tagName, tag.tagName) &&
                Objects.equals(tagValue, tag.tagValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagSource, tagId, tagName, tagValue);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tagSource='" + tagSource + '\'' +
                ", tagId=" + tagId +
                ", tagName='" + tagName + '\'' +
                ", tagValue='" + tagValue + '\'' +
                '}';
    }

    public static TagBuilder newTagBuilder() {
        return new TagBuilder();
    }


    public static final class TagBuilder {
        private String tagSource;
        private Integer tagId;
        private String tagName;
        private String tagValue;

        private TagBuilder() {
        }

        public static TagBuilder aTag() {
            return new TagBuilder();
        }

        public TagBuilder setTagSource(String tagSource) {
            this.tagSource = tagSource;
            return this;
        }

        public TagBuilder setTagId(Integer tagId) {
            this.tagId = tagId;
            return this;
        }

        public TagBuilder setTagName(String tagName) {
            this.tagName = tagName;
            return this;
        }

        public TagBuilder setTagValue(String tagValue) {
            this.tagValue = tagValue;
            return this;
        }

        public Tag build() {
            Tag tag = new Tag();
            tag.setTagSource(tagSource);
            tag.setTagId(tagId);
            tag.setTagName(tagName);
            tag.setTagValue(tagValue);
            return tag;
        }
    }
}
