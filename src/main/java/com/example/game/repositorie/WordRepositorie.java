package com.example.game.repositorie;

import com.example.game.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepositorie extends JpaRepository<Word, Long> {
    Word findByWord (String word);
}
