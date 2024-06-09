package com.example.TransMarket.web.player;

import com.example.TransMarket.domain.Player;
import com.example.TransMarket.dto.playerDTO;
import com.example.TransMarket.repository.ClubRepository;
import com.example.TransMarket.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerRepository playerRepository;
    private final ClubRepository clubRepository;

    @GetMapping
    public String players(Model model) {
        List<Player> players = playerRepository.findAll();
        List<playerDTO> dtoList = new ArrayList<>();

        for (Player player : players) {
            playerDTO p = new playerDTO(player.getPlayerId());
            p.setPlayerName(player.getPlayerName());
            p.setAge(player.getAge());
            p.setClubName(clubRepository.findClubNameByclubId(player.getClubId()));

            dtoList.add(p);
        }

//        model.addAttribute("players", players);
        model.addAttribute("players", dtoList);
        return "players";
    }

    @GetMapping("/{playerId}")
    public String player(@PathVariable String playerId, Model model) throws SQLException {
        Player player = playerRepository.findById(playerId);
        model.addAttribute("player", player);
        return "player";
    }

    @GetMapping("/add")
    public String addForm() {
        return "addForm";
    }

    @PostMapping("/add")
    public String addPlayer(@ModelAttribute("player") Player player, Model model) throws SQLException {
        playerRepository.save(player);
        return "player";
    }
}
