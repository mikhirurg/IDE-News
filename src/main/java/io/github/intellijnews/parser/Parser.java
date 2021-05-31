package io.github.intellijnews.parser;

import io.github.intellijnews.logic.RSSChannel;
import io.github.intellijnews.logic.RSSContainer;
import io.github.intellijnews.logic.RSSImage;
import io.github.intellijnews.logic.RSSItem;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;

public class Parser {

    private final DocumentBuilder builder;

    public Parser() throws ParserConfigurationException {
        this.builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    /**
     * @param url
     * @return
     */
    public RSSChannel parse(String url) throws IOException, SAXException {
        //TODO: кидаем свои исключения
        //TODO: Подключаемся к url по HttpURLConnection
        //TODO: передаем в билдер InputStream
        //TODO: Делаем документ
        //TODO: Парсим все.
        Document doc = builder.parse(new URL(url).openConnection().getInputStream());
        return null;
    }

    private RSSChannel parseChannel(Node node) {
        //TODO: парсим тег <channel>
        return null;
    }

    private RSSImage parseImage(Node node) {
        //TODO: парсим тег <image>
        return null;
    }

    private RSSItem parseItem(Node node) {
        //TODO: парсим тег <item>
        return null;
    }
}
