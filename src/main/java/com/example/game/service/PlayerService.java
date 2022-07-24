package com.example.game.service;

import com.example.game.model.Player;
import com.example.game.repositorie.PlayerRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Data
public class PlayerService {
    private final PlayerRepository playerRepository;

    private double procent (int win , int games){
        if(games != 0){
            return win/games;
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
        if(player == null){
            return false;
        } else {
            return true;
        }
    }
    public List<Player> list(){
        List<Player> players = playerRepository.findAll();
        return players;
    }


}
