import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.util.*;
import java.util.regex.Pattern;

public class HousePriceCrawler extends WebCrawler {
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
            + "|png|mp3|mp4|zip|gz))$");

    private final static Pattern FILTERS2=Pattern.compile("https://fangjia.fang.com/pghouse-c0gz/h315-i3/[0-9]{1,2}/-j320/");

    private static ArrayList<House> houseList;

    public static ArrayList<House> getHouseList() {
        return houseList;
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches()
                && href.startsWith("https://fangjia.fang.com/pghouse-c0gz/")
                && FILTERS2.matcher(href).matches();
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        System.out.println("URL: " + url);
        try {
            Document doc=Jsoup.connect(url).get();
            Elements elements=doc.getElementsByAttributeValue("class", "wid650 floatl").select("div.list").select("div.bkyellow");
            if(elements.size()>0)
            System.out.println("size:"+elements.size()+"进入list    ！！！！！！！！！！！！！！！！！！                                            URL: " + url);
            for(Element ele:elements){
                if(ele.hasClass("bkyellow")){
                    //用来构建house的地址并存入houseList
                    Object[] o;
                    Elements houseEle= ele.getElementsByAttributeValue("class", "bkyellow").select("div.house");
//                    if(houseEle.size()>0)
////                        System.out.println("进入houseEle    ！！！！！！！！！！！！！！！！！！                                            URL: " + url);
                    String houseName=houseEle.select("dl>dt>p>span[class=housetitle]>a").first().text();
                    String housePrice=houseEle.select("dl>dd>p>a>span[class=price]").first().text();
                    System.out.println(houseName+" "+housePrice);
                    o=Coordinate.getCoordinate(houseName);
                    House house=new House(houseName,housePrice,o);
                    System.out.println(house.getLat());
                    if(houseList==null)
                        houseList=new ArrayList<House>();
                    houseList.add(house);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        if (page.getParseData() instanceof HtmlParseData) {
//            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
//            String text = htmlParseData.getText();
//            String html = htmlParseData.getHtml();
//            Set<WebURL> links = htmlParseData.getOutgoingUrls();
//            System.out.println("Text length: " + text.length());
//            System.out.println("Html length: " + html.length());
//            System.out.println("Number of outgoing links: " + links.size());
//        }
    }
}

