package com.gunes.config;

import com.gunes.model.entity.DictionaryWord;
import com.gunes.service.DictionaryWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InsertDictionary {

    @Autowired
    private DictionaryWordService dictionaryWordService;

    @EventListener(ContextRefreshedEvent.class)
    public void insertDictionaryWords() {
        Long count = dictionaryWordService.count();
        if (count == 0) {
            InputStream in = this.getClass().getResourceAsStream("/dictionary/scrabble_turkish_dictionary.txt");
            List<String> result = new BufferedReader(new InputStreamReader(in)).lines().parallel().collect(Collectors.toList());
            result.parallelStream().forEach(r-> dictionaryWordService.persist(new DictionaryWord(r)));
        }
    }
}