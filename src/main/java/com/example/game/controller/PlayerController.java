package com.example.game.controller;

import com.example.game.model.Player;
import com.example.game.model.Word;
import com.example.game.service.PlayerService;
import com.example.game.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Stack;

@RequestMapping(path="/player")
@CrossOrigin(origins="*")
@RequiredArgsConstructor
@RestController
public class PlayerController {
    public final Stack<Word> stack = new Stack<>();
    private final WordService wordService;
    private final PlayerService playerService;
    @PostMapping("/create-player/{name}")
    public String create(@PathVariable("name") String name){
        if(!playerService.existsPlayer(name)){
            playerService.createPlayer(name);
            return "player create";
        } else {
            return "player exist";
        }
    }
    @PostMapping("/list")
    public List<Player> listPlayer(){
        return playerService.list();
    }


}
