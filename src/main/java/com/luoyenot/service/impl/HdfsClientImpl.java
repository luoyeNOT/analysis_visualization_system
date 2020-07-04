package com.luoyenot.service.impl;


import com.luoyenot.service.HdfsClient;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;

/**
 * @author luoyenot
 * @create 2020-05-12-10:24
 */
@Service
public class HdfsClientImpl implements HdfsClient {

    private static String HDFSUri = "hdfs://hadoop102:9000";

    @Override
    public void put() throws IOException, InterruptedException {
        Configuration configuration = new Configuration();

        FileSystem fs = FileSystem.get(URI.create(HDFSUri),
                configuration, "wlj");

        fs.copyFromLocalFile(new Path("/opt/module/result"), new Path("/"));

        fs.close();
    }

    @Override
    public void get() throws IOException, InterruptedException {
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(HDFSUri),
                configuration, "wlj");

        fs.copyToLocalFile(new Path("/output"), new Path("/opt/module"));

        fs.close();
    }

    @Override
    public void delete() throws IOException, InterruptedException {
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(HDFSUri),
                configuration, "wlj");

        fs.delete(new Path("/output"), true);
        fs.delete(new Path("/result"), true);

        fs.close();
    }

}
