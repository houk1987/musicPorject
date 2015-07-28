package com.github.music.collection.impl;

import com.github.music.collection.inter.MusicCollection;
import com.github.music.tools.HttpClientManager;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import javax.xml.soap.Node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by houk1987 on 15/7/28.
 */
public class Collection51Ape implements MusicCollection {

    public void collectionData(String url) {
        String homePageStr = getHomePage(url);
        if (homePageStr.isEmpty())return;
        List<String> musicDownLoadUrl = getMusicDownloadUrl(homePageStr);
        for (String downLoadUrl:musicDownLoadUrl){
            getDownLoadPageUrl(HttpClientManager.requestGet(downLoadUrl));
        }
    }

    public void saveMusicFile() {

    }

    private String getHomePage(String url){
        return HttpClientManager.requestGet(url);
    }

    private List<String> getMusicDownloadUrl(String homePgeStr){
        List<String> musicUrl = new ArrayList<String>();
        Document doc = Jsoup.parse(homePgeStr);
        Elements elements = doc.getElementsByAttributeValue("class", "wm210 c3b fl f_14 over t20d ml_1");
        if (elements!=null){
            for (Element element: elements){
                String url = element.attr("href");
                System.out.println(url);
                musicUrl.add(url);
            }
        }
        return musicUrl;
    }

    private HashMap<String,String> getDownLoadPageUrl(String html){
        Document document = Jsoup.parse(html);
        Elements urlElement = document.getElementsByAttributeValue("class", "blue a_none");
        String url = "";
        String key = "";
        for (Element e: urlElement)
            url = e.attr("href");

        Elements passwordE = document.getElementsByAttributeValue("class", "mt_1 yh d_b");
        for (Element e1: passwordE){
            org.jsoup.nodes.Node node = e1.childNode(2);
            String S = node.toString();
            key = S.replace("密码：","");
        }

        


        return null;
        //return element.attr("href");
    }

    private void clickDownLoad(){

    }

    public static void main(String[] args) {
        MusicCollection musicCollection = new Collection51Ape();
        musicCollection.collectionData("http://www.51ape.com/");
    }
}
