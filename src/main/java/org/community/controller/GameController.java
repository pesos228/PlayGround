package org.community.controller;

import org.community.dto.GameDtoList;
import org.community.dto.GameDtoName;
import org.community.dto.GameDtoRegister;
import org.community.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public void saveGame(@RequestBody GameDtoRegister gameDtoRegister){
        gameService.save(gameDtoRegister);
    }

    @GetMapping
    List<GameDtoList> getListOfGames(){
        return gameService.listGames();
    }

    @GetMapping("/{userId}/recommend")
    public Set<GameDtoName> recommendGames(@PathVariable int userId){
        return gameService.recommendGamesByUserFeedback(userId);
    }
}
