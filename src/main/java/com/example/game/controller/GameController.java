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
    public final Stack<Word> stack = new Stack<>();
    private final WordService wordService;
    private final PlayerService playerService;

    @PostMapping("/new/{name}")
    public String newGame(@PathVariable("name") String name){
        gameService.createGame(name);
        return "ok";
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
        if(stack.empty() == true && wordService.scanWord(string) == true) {
            Word word = gameService.play(string, id);
            stack.push(word);
            return "написать слово на букву <<" + word.getLast_char() + ">>";
        } else if (wordService.scanWord(string) == true){
            Word wordPeek = stack.peek();
            if(temp.getFirst_char() == wordPeek.getLast_char()){
                Word word = gameService.play(string, id);
                stack.push(word);
                return "написать слово на букву <<" + word.getLast_char() + ">>";
            } else {
                return gameService.endGame(id);
            }
        } else {
            Word word1 = stack.peek();

            return "no word \n" + "написать слово на букву <<" + word1.getLast_char() + ">>";
        }

    }

}
