package com.parabird.uvds.dataLake.publishing.extractor.diskExtracting.sourceStructure;


import com.parabird.uvds.dataLake.onboarding.db.model.Tag;

import java.util.Set;

public class DiskSourceFile{

    protected String fileAbsolutePath;

    /** This is the meta data of its format or storage*/
    protected Set<Tag> tags;

    public String getFileAbsolutePath() {
        return fileAbsolutePath;
    }

    public void setFileAbsolutePath(String fileAbsolutePath) {
        this.fileAbsolutePath = fileAbsolutePath;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "DiskSourceFile{" +
                "fileAbsolutePath='" + fileAbsolutePath + '\'' +
                ", tags=" + tags +
                '}';
    }

    public static DiskSourceFileBuilder newDiskSourceFileBuilder() {
        return new DiskSourceFileBuilder();
    }

    public static final class DiskSourceFileBuilder {
        protected String fileAbsolutePath;
        protected Set<Tag> tags;

        private DiskSourceFileBuilder() {
        }

        public static DiskSourceFileBuilder aDiskSourceFile() {
            return new DiskSourceFileBuilder();
        }

        public DiskSourceFileBuilder setFileAbsolutePath(String fileAbsolutePath) {
            this.fileAbsolutePath = fileAbsolutePath;
            return this;
        }

        public DiskSourceFileBuilder setTags(Set<Tag> tags) {
            this.tags = tags;
            return this;
        }

        public DiskSourceFile build() {
            DiskSourceFile diskSourceFile = new DiskSourceFile();
            diskSourceFile.setFileAbsolutePath(fileAbsolutePath);
            diskSourceFile.setTags(tags);
            return diskSourceFile;
        }
    }
}
