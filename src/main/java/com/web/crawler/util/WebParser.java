package com.web.crawler.util;
import com.web.crawler.CrawlerApplication;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class WebParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlerApplication.class);
    public static String parse(String page) {


        String result = null;
        try {
            Connection conn = Jsoup.connect(page);
            Document doc = conn.get();
            result = doc.body().text();
        } catch (IOException e) {
            LOGGER.info("Error were occurred during collect data");
        }

        return result;
    }
}
