/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.parsers.rss;

import hr.algebra.factory.ParserFactory;
import hr.algebra.factory.UrlConnectionFactory;
import hr.algebra.models.Actor;
import hr.algebra.models.Movie;
import hr.algebra.models.Director;
import hr.algebra.utilities.FileUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public static Set<Movie> parse() throws IOException, XMLStreamException {
        Set<Movie> movies = new HashSet<>();

        HttpURLConnection conn = UrlConnectionFactory.getHttpUrlConnection(RSS_URL);

        try (InputStream is = conn.getInputStream();) {
            XMLEventReader reader = ParserFactory.createStaxParser(is);

            Movie movie = null;
            Optional<TagType> tagType = Optional.empty();
            StartElement startElement = null;

            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();

                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT -> {
                        startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        tagType = TagType.from(qName);

                        if (tagType.isPresent() && tagType.get().equals(TagType.ITEM)) {
                            movie = new Movie();
                        }
                    }
                    case XMLStreamConstants.CHARACTERS -> {
                        if (tagType.isPresent() && movie != null) {
                            Characters characters = event.asCharacters();
                            String data = characters.getData().trim();

                            switch (tagType.get()) {
                                case TITLE:
                                    if (!data.isEmpty()) {
                                        movie.setTitle(data);
                                    }
                                    break;
                                case PUB_DATE:
                                    if (!data.isEmpty()) {
                                        LocalDateTime publishedDate = LocalDateTime.parse(data, DateTimeFormatter.RFC_1123_DATE_TIME);
                                        movie.setPublishedDate(publishedDate);
                                    }
                                    break;
                                case DESCRIPTION:
                                    if (!data.isEmpty()) {
                                        String text = getTextFromData(data);
                                        movie.setDescription(text);
                                    }
                                    break;
                                case ORIGINAL_NAME:
                                    if (!data.isEmpty()) {
                                        movie.setOriginalTitle(data);
                                    }
                                    break;
                                case REDATELJ:
                                    if (!data.isEmpty()) {
                                        List<Director> directors = getDirectorsFromData(data);
                                        for (Director director : directors) {
                                            movie.getDirectors().add(director);
                                        }
                                    }
                                    break;
                                case GLUMCI:
                                    if (!data.isEmpty()) {
                                        List<Actor> actors = getActorsFromData(data);
                                        for (Actor actor : actors) {
                                            movie.getActors().add(actor);
                                        }
                                    }
                                    break;
                                case TRAJANJE:
                                    if (!data.isEmpty()) {
                                        movie.setDuration(Integer.parseInt(data));
                                    }
                                    break;
                                case GODINA:
                                    if (!data.isEmpty()) {
                                        movie.setYearOfRelease(Integer.parseInt(data));
                                    }
                                    break;
                                case ZANR:
                                    if (!data.isEmpty()) {
                                        movie.setGenre(data);
                                    }
                                    break;
                                case PLAKAT:
                                    if (!data.isEmpty() && startElement != null && movie.getPoster() == null) {
                                        handlePicture(movie, data);
                                    }
                                    break;
                                case LINK:
                                    if (!data.isEmpty()) {
                                        movie.setLink(data);
                                    }
                                    break;
                                case REZERVACIJA:
                                    if (!data.isEmpty()) {
                                        movie.setReservation(data);
                                    }
                                    break;
                                case TRAILER:
                                    if (!data.isEmpty()) {
                                        movie.setTrailer(data);
                                    }
                                    movies.add(movie);
                                    break;
                            }
                        }
                    }
                }
            }
        }

        return movies;
    }

    private enum TagType {
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
        LINK("link"),
        REZERVACIJA("rezervacija"),
        TRAILER("trailer");

        private final String name;

        private TagType(String name) {
            this.name = name;
        }

        private static Optional<TagType> from(String name) {
            for (TagType value : values()) {
                if (value.name.equals(name)) {
                    return Optional.of(value);
                }
            }

            return Optional.empty();
        }
    }

    private static String getTextFromData(String data) {
        StringBuilder sb = new StringBuilder();
        boolean isText = false;
        
        for(int i = 0; i < data.length(); i++){
            char c = data.charAt(i);
            
            if (c == '<') {
                if(data.charAt(i + 1) == 'a') break;
                
                isText = false;
                continue;
            }
            
            if (c == '>') {
                isText = true;
                continue;
            }

            if (isText) {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    private static void handlePicture(Movie movie, String data) {
        try {
            String ext = data.substring(data.lastIndexOf("."));

            if (ext.length() > 4) {
                ext = EXT;
            }

            String pictureName = UUID.randomUUID() + ext;
            String localPicturePath = DIR + File.separator + pictureName;

            FileUtils.copyFromUrl(data, localPicturePath);

            movie.setPoster(localPicturePath);
        } catch (IOException ex) {
            Logger.getLogger(MovieParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static List<Director> getDirectorsFromData(String data) {
        List<Director> directors = new ArrayList<>();

        String[] directorsData = data.split(",");
        for (String directorData : directorsData) {
            String[] firstAndLastName = directorData.trim().split(" ", 2);
            directors.add(new Director(firstAndLastName[0], firstAndLastName[1]));
        }

        return directors;
    }

    private static List<Actor> getActorsFromData(String data) {
        List<Actor> actors = new ArrayList<>();

        String[] actorsData = data.split(",");
        for (String actorData : actorsData) {
            String[] firstAndLastName = actorData.trim().split(" ", 2);
            actors.add(new Actor(firstAndLastName[0], firstAndLastName[1]));
        }

        return actors;
    }
}
