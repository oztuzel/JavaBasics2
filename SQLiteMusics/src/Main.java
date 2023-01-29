import model.Artist;
import model.Datasource;
import model.SongArtist;

import java.util.List;

public class Main {
    public static void main(String[] args) {
// creating datasource and try to opening
        Datasource datasource = new Datasource();
        if(!datasource.open()){
            System.out.println("Cant open datasource");
            return;
        }
// first part
        List<Artist> artists = datasource.queryArtist(Datasource.ORDER_BY_ASC);
        if(artists ==null) {
            System.out.println("No artists");
        }
        for(Artist artist : artists) {
            System.out.println("ID = " + artist.getId() + ", Name = " + artist.getName());
        }


// second part
        List<String> albumsForArtist = datasource.queryAlbumsForArtist("Iron Maiden", Datasource.ORDER_BY_ASC);
        for (String album : albumsForArtist){
            System.out.println(album);
        }


// third part
        List<SongArtist> songArtists = datasource.queryArtistForSong("Heartless",Datasource.ORDER_BY_ASC);
        if(songArtists == null) {
            System.out.println("Couldn't find the artist for the song");
            return;
        }
        for(SongArtist artist : songArtists){
            System.out.println("Artist name = " + artist.getArtistName() + " Album name = " + artist.getAlbumName()
                    + "Track = " + artist.getTrack());
        }

// metadata for songs table (getting .schema info)
        datasource.querySongsMetadata();


// we try to sql functions in function of Datasource class ( COUNT(*) function example in there )
        int count = datasource.getCount(Datasource.TABLE_SONGS);
        System.out.println("Number of songs is : " + count);


// creating view
        datasource.createViewForSongArtists();

// query song information in view we created (artist_list)
        songArtists = datasource.querySongInfoView("Go Your Own Way");
        // trying sql injection:
        // songArtists = datasource.querySongInfoView("Go Your Own Way /" or 1=1 or /"")
        if(songArtists.isEmpty()){
            System.out.println("Couldn't find the artist for the song");
            return;
        }
        for(SongArtist artist : songArtists){
            System.out.println("FROM VIEW - ARTIST Name = " + artist.getArtistName() +
                    " ALBUM name = " + artist.getAlbumName() +
                    "Track number = " + artist.getTrack());
        }

// inserting song with manuel commit

        datasource.insertSong("Touch of Grey", "Grateful Dead", "In The Dark", 1);

// datasource closing
        datasource.close();
    }
}