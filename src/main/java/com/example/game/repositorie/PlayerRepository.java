package com.example.game.repositorie;

import com.example.game.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Player findByName (String name);
    //List<Player> findByName(String name);
}
