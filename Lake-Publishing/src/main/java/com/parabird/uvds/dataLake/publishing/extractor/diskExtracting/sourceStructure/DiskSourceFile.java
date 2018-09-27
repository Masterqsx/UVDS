package com.parabird.uvds.dataLake.publishing.extractor.diskExtracting.sourceStructure;


import java.util.Map;

public class DiskSourceFile{

    protected String fileAbsolutePath;

    /** This is the meta data of its format or storage*/
    protected Map<String, String> tags;

    public String getFileAbsolutePath() {
        return fileAbsolutePath;
    }

    public void setFileAbsolutePath(String fileAbsolutePath) {
        this.fileAbsolutePath = fileAbsolutePath;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
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
        protected Map<String, String> tags;

        private DiskSourceFileBuilder() {
        }

        public static DiskSourceFileBuilder aDiskSourceFile() {
            return new DiskSourceFileBuilder();
        }

        public DiskSourceFileBuilder setFileAbsolutePath(String fileAbsolutePath) {
            this.fileAbsolutePath = fileAbsolutePath;
            return this;
        }

        public DiskSourceFileBuilder setTags(Map<String, String> tags) {
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
