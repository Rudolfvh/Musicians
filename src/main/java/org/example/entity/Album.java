package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "albums")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Long id;

    @Column(name = "album_name")
    private String name;

    @Column(name = "album_year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    public void setArtist(Artist artist) {
        this.artist = artist;
        this.artist.getAlbums().add(this);
    }
}