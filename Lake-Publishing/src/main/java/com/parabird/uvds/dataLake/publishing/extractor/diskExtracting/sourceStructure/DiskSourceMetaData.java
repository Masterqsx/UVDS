package com.parabird.uvds.dataLake.publishing.diskPublishing.sourceStructure;

import java.util.List;
import java.util.Map;

public class DiskSourceMetaData extends DiskSourceFile {
    /** This is the meta data source file itself does not have
     * Usually the source meta data file looks like a table.
     */
    private List<String> schema;

    private List<List<String>> records;

    public List<String> getSchema() {
        return schema;
    }

    public void setSchema(List<String> schema) {
        this.schema = schema;
    }

    public List<List<String>> getRecords() {
        return records;
    }

    public void setRecords(List<List<String>> records) {
        this.records = records;
    }

    public static DiskSourceMetaDataBuilder newDiskSourceMetaDataBuilder() {
        return new DiskSourceMetaDataBuilder();
    }

    public static final class DiskSourceMetaDataBuilder {
        protected String fileAbsolutePath;
        protected Map<String, String> tags;
        private List<String> schema;
        private List<List<String>> records;

        private DiskSourceMetaDataBuilder() {
        }

        public static DiskSourceMetaDataBuilder aDiskSourceMetaData() {
            return new DiskSourceMetaDataBuilder();
        }

        public DiskSourceMetaDataBuilder setFileAbsolutePath(String fileAbsolutePath) {
            this.fileAbsolutePath = fileAbsolutePath;
            return this;
        }

        public DiskSourceMetaDataBuilder setSchema(List<String> schema) {
            this.schema = schema;
            return this;
        }

        public DiskSourceMetaDataBuilder setRecords(List<List<String>> records) {
            this.records = records;
            return this;
        }

        public DiskSourceMetaDataBuilder setTags(Map<String, String> tags) {
            this.tags = tags;
            return this;
        }

        public DiskSourceMetaData build() {
            DiskSourceMetaData diskSourceMetaData = new DiskSourceMetaData();
            diskSourceMetaData.setFileAbsolutePath(fileAbsolutePath);
            diskSourceMetaData.setSchema(schema);
            diskSourceMetaData.setRecords(records);
            diskSourceMetaData.setTags(tags);
            return diskSourceMetaData;
        }
    }
}
