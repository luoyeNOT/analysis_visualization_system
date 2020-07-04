package com.luoyenot.service.impl;

import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author luoyenot
 * @create 2020-05-17-17:23
 */
@Service
public class JobProcessorImpl implements PageProcessor {

    @Override
    public void process(Page page) {
        page.putField("json", page.getJson().toString());
    }

    @Override
    public Site getSite() {
        return site;
    }

    private Site site = Site.me()
            .setDomain("club.jd.com")
            .setSleepTime(1000)
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36")
            .addHeader("Referer", "https://item.jd.com/39925655208.html#comment")
            .addHeader("Connection", "keep-alive")
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
            .addHeader("Accept-Encoding", "gzip, deflate")
            .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
            .addHeader("Host", "club.jd.com");
}
