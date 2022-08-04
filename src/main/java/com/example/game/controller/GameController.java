package com.example.game.controller;

import com.example.game.model.Game;
import com.example.game.model.Word;
import com.example.game.service.GameService;
import com.example.game.service.PlayerService;
import com.example.game.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@RequestMapping(path="/games")
@CrossOrigin(origins="*")
@RequiredArgsConstructor
@RestController
public class GameController {
    private final GameService gameService;

    private final WordService wordService;
    private final PlayerService playerService;

    public boolean ok (String word , int id) throws FileNotFoundException {
        List<Word> ww= gameService.words(id);
        Word word1 = wordService.creatWord(word);
        String str = word1.getWord();
        boolean temp = true;
        for (int i =0; i < ww.size();i++){
            if (ww.get(i).getWord().equals(str)){
                temp = false;
            }
        }
        return temp;
    }

    @PostMapping("/new/{name}")
    public int newGame(@PathVariable("name") String name){
        Game game = gameService.createGame(name);
        return game.getIdPlay();
    }
    @PostMapping("/plus/{name}/{id}")
    public String plusGame(@PathVariable("name") String name,@PathVariable("id") int id){
        gameService.updatePlayer(name,id);
        return "ok";
    }
    @PostMapping("/list")
    public List<Game> neGame(){
        return gameService.games();
    }

    @PostMapping("/play/{id}/{string}")
    public String play (@PathVariable("id") int id,@PathVariable("string") String string) throws FileNotFoundException{
        Word temp = wordService.creatWord(string);
        Game game = gameService.game(id);
        List<Word> ww= gameService.words(id);
        if(ww.size() == 0 && wordService.scanWord(string) == true ) {
            Word word = gameService.play(string, id);
            return "написать слово на букву <<" + word.getLast_char() + ">>";
        } else if (wordService.scanWord(string) == true && ok(string,id) == true){
            Word wordPeek = ww.get(ww.size()-1);
            if(temp.getFirst_char() == wordPeek.getLast_char()){
                Word word = gameService.play(string, id);
                return "написать слово на букву <<" + word.getLast_char() + ">>";
            } else {
                return gameService.endGame(id);
            }
        } else if(ok(string,id) == false){
            Word word1 = ww.get(ww.size()-1);

            return "this word exist \n" + "написать слово на букву <<" + word1.getLast_char() + ">>";
        } else {
            Word word1 = ww.get(ww.size()-1);

            return "no word \n" + "написать слово на букву <<" + word1.getLast_char() + ">>";
        }

    }


}
