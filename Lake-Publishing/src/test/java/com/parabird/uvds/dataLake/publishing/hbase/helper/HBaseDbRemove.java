package com.parabird.uvds.dataLake.publishing.hbase.helper;

import com.google.protobuf.ServiceException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class HBaseDbRemove {
    private static Logger logger = LoggerFactory.getLogger(HBaseDbRemove.class);

    public static void main(String[] args) throws IOException, ServiceException {
        logger.warn("Start Remove HBase Tables");

        Configuration config = HBaseConfiguration.create();
        config.addResource("test/hbase-site.xml");
        HBaseAdmin.available(config);

        Connection hbaseConnection = ConnectionFactory.createConnection(config);
        Admin admin = hbaseConnection.getAdmin();

        TableName table = TableName.valueOf("media");

        logger.warn("Start Deleting hbase table : media");

        if (admin.tableExists(table)) {
            logger.warn("Deleting hbase table : media");

            admin.disableTable(table);
            admin.deleteTable(table);
        }

        admin.close();
        logger.warn("hbase table : media Has Been Removed");
    }
}
