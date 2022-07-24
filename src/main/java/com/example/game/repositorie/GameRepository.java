package com.example.game.repositorie;

import com.example.game.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game,Long> {
    Game findByIdPlay(int id);
}