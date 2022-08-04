package com.example.game.service;

import com.example.game.model.Player;
import com.example.game.model.Word;
import com.example.game.repositorie.PlayerRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Data
public class PlayerService {
    private final PlayerRepository playerRepository;


    public int procent (int win , int games){
        if(games != 0){
            return (int) Math.round((double)win/games  * 100);
        } else {
            return 0;
        }
    }



    public void createPlayer (String name){
        Player player = new Player();
        player.setName(name);
        player.setGame(0);
        player.setWin(0);
        player.setProcentwin(procent(player.getWin(),player.getGame()));
        playerRepository.save(player);
    }
    public void updatePlayer(String name ,int win){
        Player player = playerRepository.findByName(name);
        player.setGame(player.getGame() + 1);
        player.setWin(player.getWin() + win);
        player.setProcentwin(procent(player.getWin(),player.getGame()));
        playerRepository.save(player);
    }
    public void deletePlayer(String name){
        Player player = playerRepository.findByName(name);
        playerRepository.deleteById(player.getId());
    }
    public boolean existsPlayer(String name){
        Player player = playerRepository.findByName(name);
        return player != null;
    }
    public List<Player> list(){
        List<Player> players = playerRepository.findAll();
        return players;
    }


}
