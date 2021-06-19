package io.github.intellijnews.parser;

import com.github.sisyphsu.dateparser.DateParserUtils;
import io.github.intellijnews.logic.RSSChannel;
import io.github.intellijnews.logic.RSSImage;
import io.github.intellijnews.logic.RSSItem;
import io.github.intellijnews.parser.headers.RSSChannelHeader;
import io.github.intellijnews.parser.headers.RSSImageHeader;
import io.github.intellijnews.parser.headers.RSSItemHeader;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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

        Document document = builder.parse(url);

        NodeList list = document.getElementsByTagName(RSSChannelHeader.CHANNEL.getNodeName());

        RSSChannel rssChannel = parseChannel(list);
        rssChannel.setRssUrl(url);

        return rssChannel;
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        Parser parser = new Parser();
        RSSChannel channel = parser.parse("https://rss.nytimes.com/services/xml/rss/nyt/World.xml");
        channel.getItems();
    }

    private RSSChannel parseChannel(NodeList channelList) {
        //TODO: парсим тег <channel>

        RSSChannel channel = null;

        for (int i = 0; i < channelList.getLength(); i++) {

            RSSChannelHeader rssHeader = RSSChannelHeader.valueOfNodeName(channelList.item(i).getNodeName());

            if (rssHeader != RSSChannelHeader.UNKNOWN) {
                if (rssHeader == RSSChannelHeader.CHANNEL) {
                    NodeList channelHeaders = channelList.item(i).getChildNodes();

                    String title = null;
                    String link = null;
                    String description = null;
                    String language = null;
                    String rating = null;
                    String copyright = null;
                    Date pubDate = null;
                    Date lastBuildDate = null;
                    List<String> category = new LinkedList<>();
                    String docs = null;
                    long ttl = -1;
                    String managingEditor = null;
                    String webMaster = null;
                    RSSImage image = null;
                    List<RSSItem> items = new LinkedList<>();

                    for (int j = 0; j < channelHeaders.getLength(); j++) {
                        RSSChannelHeader channelHeader = RSSChannelHeader.valueOfNodeName(channelHeaders.item(j)
                                .getNodeName());

                        switch (channelHeader) {
                            case TITLE: {
                                title = channelHeaders.item(j).getTextContent();
                            }
                            break;
                            case LINK: {
                                link = channelHeaders.item(j).getTextContent();
                            }
                            break;
                            case DESCRIPTION: {
                                description = channelHeaders.item(j).getTextContent();
                            }
                            break;
                            case LANGUAGE: {
                                language = channelHeaders.item(j).getTextContent();
                            }
                            break;
                            case RATING: {
                                rating = channelHeaders.item(j).getTextContent();
                            }
                            break;
                            case COPYRIGHT: {
                                copyright = channelHeaders.item(j).getTextContent();
                            }
                            break;
                            case PUB_DATE: {
                                pubDate = DateParserUtils.parseDate(channelHeaders.item(j).getTextContent());
                            }
                            break;
                            case LAST_BUILD_DATE: {
                                lastBuildDate = DateParserUtils.parseDate(channelHeaders.item(j).getTextContent());
                            }
                            break;
                            case CATEGORY: {
                                category.add(channelHeaders.item(j).getTextContent());
                            }
                            break;
                            case DOCS: {
                                docs = channelHeaders.item(j).getTextContent();
                            }
                            break;
                            case TTL: {
                                ttl = Long.parseLong(channelHeaders.item(j).getTextContent());
                            }
                            break;
                            case MANAGING_EDITOR: {
                                managingEditor = channelHeaders.item(j).getTextContent();
                            }
                            break;
                            case WEB_MASTER: {
                                webMaster = channelHeaders.item(j).getTextContent();
                            }
                            break;
                            case IMAGE: {
                                image = parseImage(channelHeaders.item(j).getChildNodes());
                            }
                            break;
                            case ITEM: {
                                items.add(parseItem(channelHeaders.item(j).getChildNodes()));
                            }
                            break;
                            case UNKNOWN: {

                            }
                        }
                    }

                    channel = RSSChannel.builder()
                            .title(title)
                            .link(link)
                            .description(description)
                            .language(language)
                            .rating(rating)
                            .copyright(copyright)
                            .pubDate(pubDate)
                            .lastBuildDate(lastBuildDate)
                            .category(category)
                            .docs(docs)
                            .ttl(ttl)
                            .managingEditor(managingEditor)
                            .webMaster(webMaster)
                            .image(image)
                            .items(items)
                            .build();

                    for (RSSItem item : items) {
                        item.setChannel(channel);
                    }
                }
            }
        }

        return channel;
    }

    private RSSImage parseImage(NodeList imageHeaders) {
        String imageTitle = null;
        String imageUrl = null;
        String imageLink = null;
        long width = -1;
        long height = -1;
        String imageDescription = null;

        for (int i = 0; i < imageHeaders.getLength(); i++) {
            RSSImageHeader imageHeader = RSSImageHeader.valueOfNodeName(imageHeaders.item(i)
                    .getNodeName());
            switch (imageHeader) {
                case TITLE: {
                    imageTitle = imageHeaders.item(i).getTextContent();
                }
                break;
                case URL: {
                    imageUrl = imageHeaders.item(i).getTextContent();
                }
                break;
                case LINK: {
                    imageLink = imageHeaders.item(i).getTextContent();
                }
                break;
                case WIDTH: {
                    width = Long.parseLong(imageHeaders.item(i).getTextContent());
                }
                break;
                case HEIGHT: {
                    height = Long.parseLong(imageHeaders.item(i).getTextContent());
                }
                break;
                case DESCRIPTION: {
                    imageDescription = imageHeaders.item(i).getTextContent();
                }
                break;
                case UNKNOWN: {

                }
            }
        }

        return RSSImage.builder()
                .title(imageTitle)
                .url(imageUrl)
                .link(imageLink)
                .width(width)
                .height(height)
                .description(imageDescription)
                .build();
    }

    private RSSItem parseItem(NodeList itemHeaders) {
        //TODO: парсим тег <item>

        String itemTitle = null;
        String itemLink = null;
        String itemDescription = null;
        String author = null;
        List<String> itemCategory = new LinkedList<>();
        String comments = null;
        Date itemPubDate = null;

        for (int i = 0; i < itemHeaders.getLength(); i++) {
            RSSItemHeader itemHeader = RSSItemHeader.valueOfNodeName(itemHeaders.item(i)
                    .getNodeName());
            switch (itemHeader) {
                case TITLE: {
                    itemTitle = itemHeaders.item(i).getTextContent();
                }
                break;
                case LINK: {
                    itemLink = itemHeaders.item(i).getTextContent();
                }
                break;
                case DESCRIPTION: {
                    itemDescription = itemHeaders.item(i).getTextContent();
                }
                break;
                case AUTHOR: {
                    author = itemHeaders.item(i).getTextContent();
                }
                break;
                case CATEGORY: {
                    itemCategory.add(itemHeaders.item(i).getTextContent());
                }
                break;
                case COMMENTS: {
                    comments = itemHeaders.item(i).getTextContent();
                }
                break;
                case PUB_DATE: {
                    itemPubDate = DateParserUtils.parseDate(itemHeaders.item(i).getTextContent());
                }
                break;
                case UNKNOWN: {

                }
            }
        }

        return RSSItem.builder()
                .title(itemTitle)
                .link(itemLink)
                .description(itemDescription)
                .author(author)
                .category(itemCategory)
                .comments(comments)
                .pubDate(itemPubDate)
                .build();
    }
}
