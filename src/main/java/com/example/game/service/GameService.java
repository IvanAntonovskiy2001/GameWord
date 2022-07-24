package com.example.game.service;


import com.example.game.model.Game;
import com.example.game.model.Player;
import com.example.game.model.Word;
import com.example.game.repositorie.GameRepository;
import com.example.game.repositorie.PlayerRepository;
import com.example.game.repositorie.WordRepositorie;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Data
public class GameService {
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final WordRepositorie wordRepositorie;
    private final WordService wordService;
    private final PlayerService playerService;

    public static int playerID(){
        int max = 9999999;
        int min = 1000000;
        double x = (Math.random()*((max-min)+1))+min;
        return (int)x;
    }

    public void createGame(String name){
        Game game = new Game();
        game.setIdPlay(playerID());
        game.setCountword(0);
        game.setPlayers(1);
        game.setLooser(null);
        Player player = playerRepository.findByName(name);
        List<Player> pl = new ArrayList<>();
        pl.add(player);
        game.setPlayer_id(pl);
        gameRepository.save(game);

    }
    public void updatePlayer(String name , int id){
        Game game = gameRepository.findByIdPlay(id);
        Player player = playerRepository.findByName(name);
        game.setPlayers(game.getPlayers() + 1);
        List<Player> pl = game.getPlayer_id();
        pl.add(player);
        game.setPlayer_id(pl);
        gameRepository.save(game);
    }

    public Word play(String word , int id) throws FileNotFoundException {
        Game game = gameRepository.findByIdPlay(id);
        Word word1 = wordService.creatWord(word);
        if(game.getWord_id() == null) {
        List<Word> words = new ArrayList<>();
        words.add(word1);
        game.setWord_id(words);
        gameRepository.save(game);
        } else {
            List<Word> words = game.getWord_id();
            words.add(word1);
            game.setWord_id(words);
            game.setCountword(game.getCountword()+1);
            gameRepository.save(game);
        }
        return word1;

    }

    public String endGame(int id){
        Game game = gameRepository.findByIdPlay(id);
        int countPlayers = game.getPlayers();
        int countWord = game.getCountword();
        int numberLooser = countWord % countPlayers + 1;
        Player playerLooser = game.getPlayer_id().get(numberLooser-1);
        game.setLooser(playerLooser.getName());
        gameRepository.save(game);
        List<Player> players = game.getPlayer_id();
        for(int i = 0 ; i < players.size();i++){
            if(players.get(i) == playerLooser){
                Player player = playerRepository.findByName(players.get(i).getName());
                player.setGame(player.getGame() + 1);
                player.setWin(player.getWin() + 0);
                if(player.getWin() == 0) {
                    player.setProcentwin(0);
                } else {
                    player.setProcentwin(player.getWin()/player.getGame());
                }
                playerRepository.save(player);
            } else {
                Player player = playerRepository.findByName(players.get(i).getName());
                player.setGame(player.getGame() + 1);
                player.setWin(player.getWin() + 1);
                double procent = player.getWin()/player.getGame();
                player.setProcentwin(procent);
                playerRepository.save(player);
            }
        }
        return playerLooser.getName() +" - looser";
    }

    public List<Game> games (){
        return gameRepository.findAll();
    }
    public Game game (int id){
        return gameRepository.findByIdPlay(id);
    }
    public void saveGame(Game game){
        gameRepository.save(game);
    }
}
