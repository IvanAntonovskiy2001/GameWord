package com.example.game.service;

import com.example.game.model.Word;
import com.example.game.repositorie.WordRepositorie;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Stack;

@Service
@Slf4j
@RequiredArgsConstructor
@Data
public class WordService {
    private final WordRepositorie wordRepositorie;
    private final PlayerService playerService;


    public boolean scanWord (String string) throws FileNotFoundException {
        String word = string.toLowerCase();
        File file = new File("dictionary.txt");
        Scanner scanner = new Scanner(file);
        int ch = 0;
        while (scanner.hasNextLine()){
            if(word.equals(scanner.nextLine())){
                ch++;
            }
        }
        return ch == 1;
    }

    public boolean createWord (String temp) throws FileNotFoundException {
        Word word = new Word();
        word.setWord(temp);
        word.setFirst_char(temp.charAt(0));
        if(temp.charAt(temp.length() - 1) != 'ы' && temp.charAt(temp.length() - 1)!='ь'&& temp.charAt(temp.length() - 1)!='ъ') {
            word.setLast_char(temp.charAt(temp.length() - 1));
        } else {
            word.setLast_char(temp.charAt(temp.length() - 2));
        }
        boolean wordExist = scanWord(temp);
        if(wordExist){
            wordRepositorie.save(word);
            return true;
        } else {
            return false;
        }

    }
    public Word creatWord (String temp) throws FileNotFoundException {
        Word word = new Word();
        word.setWord(temp);
        word.setFirst_char(temp.charAt(0));
        if(temp.charAt(temp.length() - 1) != 'ы' && temp.charAt(temp.length() - 1)!='ь'&& temp.charAt(temp.length() - 1)!='ъ' && temp.charAt(temp.length() - 1)!='й') {
            word.setLast_char(temp.charAt(temp.length() - 1));
        } else {
            word.setLast_char(temp.charAt(temp.length() - 2));
        }
        boolean wordExist = scanWord(temp);
        if(wordExist){
            return word;
        } else {
            return null;
        }

    }




    public List<Word> list(){
        List<Word> temp = wordRepositorie.findAll();
        return temp;
    }
    public Word getWord (String str){
        return wordRepositorie.findByWord(str);
    }


}
