package com.example.game.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
@Entity
@Table(name = "pl")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pl {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "pl")
    private String pl;
    @Column(name = "place")
    private int place;
    @Column(name = "win")
    private boolean win;

}
