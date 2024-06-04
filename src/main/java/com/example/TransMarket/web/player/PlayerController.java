package com.example.TransMarket.web.player;

import com.example.TransMarket.domain.Player;
import com.example.TransMarket.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerRepository playerRepository;

    @GetMapping
    public String players(Model model) {
        List<Player> players = playerRepository.findAll();
        model.addAttribute("players", players);
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
