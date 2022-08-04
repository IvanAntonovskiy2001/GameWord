package com.example.game.controller;

import com.example.game.model.Word;
import com.example.game.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

@RequestMapping(path="/word")
@CrossOrigin(origins="*")
@RequiredArgsConstructor
@RestController
public class WordContpoller {
    private final WordService wordService;

    public final Stack<Word> stack = new Stack<>();

    @GetMapping ("/")
    public List<Word> word (){
        return wordService.list();
    }
    @PostMapping("/{string}")
    public String play (@PathVariable("string") String string) throws FileNotFoundException {
        if(stack.empty() && wordService.scanWord(string)){
            wordService.createWord(string);
            Word word = wordService.getWord(string);
            stack.push(word);
            return "написать слово на букву <<" + word.getLast_char() + ">>";
        } else if(wordService.scanWord(string)) {
            wordService.createWord(string);
            Word word = wordService.getWord(string);
            Word word1 = stack.peek();
            if(word.getFirst_char() == word1.getLast_char()){
                stack.push(word);
                return "написать слово на букву <<" + word.getLast_char() + ">>";
            } else {
                wordService.deleteWord(string);
                Word wor = stack.peek();
                wordService.deletePlay();
                stack.clear();
                return "won players hwo word " + wor.getWord();
            }
        } else {
            Word word1 = stack.peek();

            return "no word \n" + "написать слово на букву <<" + word1.getLast_char() + ">>";
        }
    }

}
