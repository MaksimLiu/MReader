package com.maksimliu.mreader.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by MaksimLiu on 2017/3/9.
 * <h3>Jsoup 工具类</h3>
 */

public class JsoupUtil {


    public static String pickupImageForGank(String html) {

        String img_url = null;


        Document document = Jsoup.parse(html);

        Elements elements = document.select("img");


        return elements.get(0).attr("src");


    }
}
