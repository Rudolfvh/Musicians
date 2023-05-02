package org.example.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "artists")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(exclude = "albums")
@EqualsAndHashCode(exclude = "albums")
@Builder
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_id")
    private Long id;

    @Column(name = "artist_name")
    private String name;

    @OneToMany(mappedBy = "artist")
    private List<Album> albums;

}
