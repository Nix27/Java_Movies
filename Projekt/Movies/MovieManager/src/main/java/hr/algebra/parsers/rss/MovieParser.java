/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.parsers.rss;

import hr.algebra.factory.ParserFactory;
import hr.algebra.factory.UrlConnectionFactory;
import hr.algebra.view.models.MovieVM;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author Nix
 */
public class MovieParser {

    private MovieParser() {
    }
    
    private static final String RSS_URL = "https://www.blitz-cinestar-bh.ba/rss.aspx?id=2682";
    private static final String ATTRIBUTE_URL = "url";
    private static final String EXT = ".jpg";
    private static final String DIR = "assets";
    
    public static List<MovieVM> parse() throws IOException, XMLStreamException{
        List<MovieVM> movies = new ArrayList<>();
        
        HttpURLConnection conn = UrlConnectionFactory.getHttpUrlConnection(RSS_URL);
        
        try(InputStream is = conn.getInputStream();){
            XMLEventReader reader = ParserFactory.createStaxParser(is);
            
            MovieVM movie = null;
            Optional<TagType> tagType = Optional.empty();
            StartElement startElement = null;
            
            while(reader.hasNext()){
                XMLEvent event = reader.nextEvent();
                
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT -> {
                        startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        tagType = TagType.from(qName);
                        
                        if(tagType.isPresent() && tagType.get().equals(TagType.ITEM)){
                            movie = new MovieVM();
                            movies.add(movie);
                        }
                    }
                    case XMLStreamConstants.CHARACTERS -> {
                        if(tagType.isPresent() && movie != null){
                            Characters characters = event.asCharacters();
                            String data = characters.getData().trim();
                            
                            switch (tagType.get()) {
                                case TITLE:
                                    break;
                                case PUB_DATE:
                                    break;
                                case DESCRIPTION:
                                    break;
                                case ORIGINAL_NAME:
                                    break;
                                case REDATELJ:
                                    break;
                                case GLUMCI:
                                    break;
                                case TRAJANJE:
                                    break;
                                case GODINA:
                                    break;
                                case ZANR:
                                    break;
                                case PLAKAT:
                                    break;
                                case VRSTA:
                                    break;
                                case LINK:
                                    break;
                                case REZERVACIJA:
                                    break;
                                case DATUM_PRIKAZIVANJA:
                                    break;
                                case SORT:
                                    break;
                                case TRAILER:
                                    break;
                            }
                        }
                    }
                }
            }
        }
        
        return movies;
    }
    
    private enum TagType{
        ITEM("item"),
        TITLE("title"),
        PUB_DATE("pubDate"),
        DESCRIPTION("description"),
        ORIGINAL_NAME("orignaziv"),
        REDATELJ("redatelj"),
        GLUMCI("glumci"),
        TRAJANJE("trajanje"),
        GODINA("godina"),
        ZANR("zanr"),
        PLAKAT("plakat"),
        VRSTA("vrsta"),
        LINK("link"),
        REZERVACIJA("rezervacija"),
        DATUM_PRIKAZIVANJA("datumprikazivanja"),
        SORT("sort"),
        TRAILER("trailer")
        ;
        
        private final String name;

        private TagType(String name) {
            this.name = name;
        }
        
        private static Optional<TagType> from(String name){
            for (TagType value : values()) {
                if(value.name.equals(name)){
                    return Optional.of(value);
                }
            }
            
            return Optional.empty();
        }
    }
}
