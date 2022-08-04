package com.example.game.service;


import com.example.game.model.Game;
import com.example.game.model.Pl;
import com.example.game.model.Player;
import com.example.game.model.Word;
import com.example.game.repositorie.GameRepository;
import com.example.game.repositorie.PlRepositorie;
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
    private final PlRepositorie plRepositorie;

    public static int playerID(){
        int max = 9999999;
        int min = 1000000;
        double x = (Math.random()*((max-min)+1))+min;
        return (int)x;
    }

    public Pl createPL (String name){
        Player player = playerRepository.findByName(name);
        return new Pl(player.getId(),name,1,false);
    }

    public Game createGame(String name){
        Game game = new Game();
        game.setIdPlay(playerID());
        game.setCountword(0);
        game.setPlayers(1);
        game.setLooser(null);
        List<Pl> pl= new ArrayList<>();
        pl.add(createPL(name));
        game.setPl_id(pl);
        Player player = playerRepository.findByName(name);
        List<Game> games = new ArrayList<>();
        games.add(game);
        player.setGame_id(games);
        playerRepository.save(player);
        return game;

    }
    public void updatePlayer(String name , int id)  {
        Game game = gameRepository.findByIdPlay(id);
        List<Pl> pls = game.getPl_id();
        Pl temp = createPL(name);
        int ch = 0;
        for (int i = 0 ; i < pls.size(); i++){
            if (pls.get(i).getPl().equals(temp.getPl())){
                ch++;
            }
        }
        if (ch == 0){
            temp.setPlace(pls.size() + 1);
            pls.add(temp);
            game.setPl_id(pls);
            game.setPlayers(game.getPl_id().size());
            gameRepository.save(game);
        }
        Player player = playerRepository.findByName(name);
        if (game.getWord_id().size() == 0) {
            List<Game> games;
            if(player.getGame_id() == null) {
                games = new ArrayList<>();
                games.add(game);
            } else  {
                games = player.getGame_id();
                games.add(game);
            }
            player.setGame_id(games);
            playerRepository.save(player);
        }
    }

    public boolean ex (String name , int id){
        Player player = playerRepository.findByName(name);
        Game game = gameRepository.findByIdPlay(id);
        List<Game> games = player.getGame_id();

        int ch = 0;
        for (Game value : games) {
            if (value.equals(game)) {
                ch = 0;
            } else {
                ch = 1;
            }
        }
        return ch == 0;
    }



    public Word play(String word , int id) throws FileNotFoundException {
        Game game = gameRepository.findByIdPlay(id);
        Word word1 = wordService.creatWord(word);
        if(!game.getStop() && word1 != null) {
            List<Word> words;
            if (game.getWord_id() == null) {
                words = new ArrayList<>();
                words.add(word1);
                game.setWord_id(words);
            } else {
                words = game.getWord_id();
                words.add(word1);
                game.setWord_id(words);
                game.setCountword(game.getCountword() + 1);
            }
            gameRepository.save(game);
            return word1;
        } else {
            return null;
        }

    }

    public String endGame(int id){
        Game game = gameRepository.findByIdPlay(id);
        if(!game.getStop()) {

            List<Pl> pls = game.getPl_id();
            int countPlayers = game.getPlayers();
            int countWord = game.getCountword();
            int numberLooser = countWord % countPlayers + 1;
            Pl playerLooser = game.getPl_id().get(numberLooser - 1);
            game.setLooser(playerLooser.getPl());
            for (int i = 0; i < pls.size(); i++) {
                if (i != numberLooser - 1) {
                    pls.get(i).setWin(true);
                    game.setPl_id(pls);
                    game.setStop(true);
                    gameRepository.save(game);
                }
            }
            List<Pl> temp = game.getPl_id();
            for (Pl pl : temp) {
                Player player = playerRepository.findByName(pl.getPl());
                player.setGame(player.getGame() + 1);
                if (pl.isWin()) {
                    player.setWin(player.getWin() + 1);
                }
                player.setProcentwin(playerService.procent(player.getWin(), player.getGame()));
                playerRepository.save(player);
            }


            return playerLooser.getPl() + " - looser";
        } else {
            return "play stop ";
        }
    }

    public List<Game> games (){
        return gameRepository.findAll();
    }
    public List<Word> words (int id){

        return gameRepository.findByIdPlay(id).getWord_id();
    }
    public Game game (int id){
        return gameRepository.findByIdPlay(id);
    }

    public void saveGame(Game game){
        gameRepository.save(game);
    }

}
