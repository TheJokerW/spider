import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    public static void main(String[] args) throws Exception {
        String crawlStorageFolder = "/data/crawl/root/test";
        int numberOfCrawlers = 7;

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);



        // Instantiate the controller for this crawl.
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        // For each crawl, you need to add some seed urls. These are the first
        // URLs that are fetched and then the crawler starts following links
        // which are found in these pages
        controller.addSeed("https://fangjia.fang.com/pghouse-c0gz/");
        for(int i=1;i<21;i++){
            controller.addSeed("https://fangjia.fang.com/pghouse-c0gz/h315-i3"+i+"-j320/");
        }
//         controller.addSeed("https://fangjia.fang.com/pghouse-c0gz/h315-i31-j320/");
//         controller.addSeed("https://fangjia.fang.com/pghouse-c0gz/h315-i34-j320/");
//         controller.addSeed("https://fangjia.fang.com/pghouse-c0gz/h315-i314-j320/");
//         controller.addSeed("https://fangjia.fang.com/pghouse-c0gz/h315-i317-j320//");

        // The factory which creates instances of crawlers.
        CrawlController.WebCrawlerFactory<HousePriceCrawler> factory = HousePriceCrawler::new;

        // Start the crawl. This is a blocking operation, meaning that your code
        // will reach the line after this only when crawling is finished.
        controller.start(factory, numberOfCrawlers);

        List<House> houseList = HousePriceCrawler.getHouseList();
        /*jxl测试失败*/

//        Object[] ot= {"3", "4"};
//        House h=new House("1","2",ot);
//        houseList.add(h);
//
        WritableWorkbook writeBook = Workbook.createWorkbook(new File(
                "C:/Users/Administrator/Desktop/data/test/test.xls"));

        // 2、新建工作表(sheet)对象
        WritableSheet Sheet = writeBook.createSheet("test", 0);

     // 3、创建表头,以及录入数据
        Sheet.addCell(new Label(0, 0, "HouseName"));
        Sheet.addCell(new Label(1, 0, "HousePrice"));
        Sheet.addCell(new Label(2, 0, "LNG"));
        Sheet.addCell(new Label(3, 0, "LAT"));

        try{
            for (int i = 0, j = 1; i < houseList.size(); i++, j++) {
                House h= houseList.get(i);
                System.out.println(h.getHouseName());
                Sheet.addCell(new Label(0,j,h.getHouseName()));
                Sheet.addCell(new Label(1,j,h.getHousePrice()));
                Sheet.addCell(new Label(2,j,h.getLng()));
                Sheet.addCell(new Label(3,j,h.getLat()));
            }}catch (Exception e){
            e.printStackTrace();
        }

        // 4、打开流，开始写文件
        writeBook.write();

        // 5、关闭流
        writeBook.close();

//        String filePath="C:/Users/Administrator/Desktop/data/main.xls";
//        List<House> houseList = HousePriceCrawler.getHouseList();
//        HSSFWorkbook workbook = new HSSFWorkbook();//创建Excel文件(Workbook)
//        //新建表格
//        HSSFSheet sheet = workbook.createSheet("main");
//        HSSFRow row0=sheet.createRow(0);
//        row0.createCell(0).setCellValue("HouseName");
//        row0.createCell(1).setCellValue("HousePrice");
//        row0.createCell(2).setCellValue("LNG");
//        row0.createCell(3).setCellValue("LAT");
//        try{
//            for (int i = 0, j = 1; i < houseList.size(); i++, j++) {
//                HSSFRow row=sheet.createRow(i+1);
//                House h= houseList.get(i);
//                System.out.println(h.getHouseName());
//                row.createCell(0).setCellValue(h.getHouseName());
//                row.createCell(1).setCellValue(h.getHousePrice());
//                row.createCell(2).setCellValue(h.getLng());
//                row.createCell(3).setCellValue(h.getLat());
//            }}catch (Exception e){
//            e.printStackTrace();
//        }
//        workbook.write();
//        workbook.close();
    }
}
