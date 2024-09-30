package org.community.service;

import org.community.dto.GameDtoList;
import org.community.dto.GameDtoName;
import org.community.dto.GameDtoRegister;
import org.community.entities.Game;

import java.util.List;
import java.util.Set;

public interface GameService {

    void save(GameDtoRegister gameDtoRegister);

    List<GameDtoList> listGames();

    Set<GameDtoName> recommendGamesByUserFeedback(int userId);

    Game findByName(String name);

    Game findById(int id);
}
