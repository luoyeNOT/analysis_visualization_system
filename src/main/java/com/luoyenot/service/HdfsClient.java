package com.luoyenot.service;

import java.io.IOException;

/**
 * @author luoyenot
 * @create 2020-05-17-19:21
 */
public interface HdfsClient {

    void put() throws IOException, InterruptedException;

    void get() throws IOException, InterruptedException;

    void delete() throws IOException, InterruptedException;
}
