package com.example.game.repositorie;

import com.example.game.model.Pl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlRepositorie extends JpaRepository<Pl,Long> {

    Pl findByPl (String pl);
}
