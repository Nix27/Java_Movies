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
import hr.algebra.view.models.MovieVM;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
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
                            movie = new MovieVM(
                             new Movie(),
                             new ArrayList<>(),
                             new ArrayList<>()
                            );
                            movies.add(movie);
                        }
                    }
                    case XMLStreamConstants.CHARACTERS -> {
                        if(tagType.isPresent() && movie != null){
                            Characters characters = event.asCharacters();
                            String data = characters.getData().trim();
                            
                            switch (tagType.get()) {
                                case TITLE:
                                    if(!data.isEmpty()){
                                        movie.getMovie().setTitle(data);
                                    }
                                    break;
                                case PUB_DATE:
                                    if(!data.isEmpty()){
                                        LocalDateTime publishedDate = LocalDateTime.parse(data, DateTimeFormatter.RFC_1123_DATE_TIME);
                                        movie.getMovie().setPublishedDate(publishedDate);
                                    }
                                    break;
                                case DESCRIPTION:
                                    if(!data.isEmpty()){
                                        movie.getMovie().setDescription(data);
                                    }
                                    break;
                                case ORIGINAL_NAME:
                                    if(!data.isEmpty()){
                                        movie.getMovie().setOriginalTitle(data);
                                    }
                                    break;
                                case REDATELJ:
                                    if(!data.isEmpty()){
                                        List<Director> directors = getDirectorsFromData(data);
                                        for (Director director : directors) {
                                            movie.getDirectors().add(director);
                                        }
                                    }
                                    break;
                                case GLUMCI:
                                    if(!data.isEmpty()){
                                        List<Actor> actors = getActorsFromData(data);
                                        for (Actor actor : actors) {
                                            movie.getActors().add(actor);
                                        }
                                    }
                                    break;
                                case TRAJANJE:
                                    if(!data.isEmpty()){
                                        movie.getMovie().setDuration(Integer.parseInt(data));
                                    }
                                    break;
                                case GODINA:
                                    if(!data.isEmpty()){
                                        movie.getMovie().setYearOfRelease(Integer.parseInt(data));
                                    }
                                    break;
                                case ZANR:
                                    if(!data.isEmpty()){
                                        movie.getMovie().setGenre(data);
                                    }
                                    break;
                                case PLAKAT:
                                    if (!data.isEmpty() && startElement != null && movie.getMovie().getPoster() == null) {
                                        handlePicture(movie.getMovie(), data);
                                    }
                                    break;
                                case VRSTA:
                                    if(!data.isEmpty()){
                                        movie.getMovie().setTypeOfMovie(data);
                                    }
                                    break;
                                case LINK:
                                    if(!data.isEmpty()){
                                        movie.getMovie().setLink(data);
                                    }
                                    break;
                                case REZERVACIJA:
                                    if(!data.isEmpty()){
                                        movie.getMovie().setReservation(data);
                                    }
                                    break;
                                case DATUM_PRIKAZIVANJA:
                                    if(!data.isEmpty()){
                                        LocalDate dateOfDisplay = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                                        movie.getMovie().setDateOfDisplay(dateOfDisplay);
                                    }
                                    break;
                                case SORT:
                                    if(!data.isEmpty()){
                                        movie.getMovie().setSort(Integer.parseInt(data));
                                    }
                                    break;
                                case TRAILER:
                                    if(!data.isEmpty()){
                                        movie.getMovie().setTrailer(data);
                                    }
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
    
    private static void handlePicture(Movie movie, String data) {
        try {
            String ext = data.substring(data.lastIndexOf("."));
            
            if(ext.length() > 4){
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
