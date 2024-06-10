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
        String clubName = clubRepository.findClubNameByclubId(player.getClubId());
        model.addAttribute("player", player);
        model.addAttribute("clubName", clubName);
        return "player";
    }

    @GetMapping("/add")
    public String addForm() {
        return "addForm";
    }

    @PostMapping("/add")
    public String addPlayer(@RequestParam String playerName,
                            @RequestParam int age,
                            @RequestParam String nationality,
                            @RequestParam String clubName,
//                            @RequestParam(required = false) Character importance,
                            Model model
    ) throws SQLException {
        Player player = new Player();
        player.setPlayerName(playerName);
        player.setAge(age);
        player.setNationality(nationality);
        player.setClubId(clubRepository.findClubIdByClubName(clubName));
        if (playerRepository.save(player) == null) {
            model.addAttribute("errorMessage", "Invalid ClubId: Club does not exist. Please try again.");
            return "addForm";  // 다시 입력 페이지로 리다이렉트
        }
        model.addAttribute("player", player);
        model.addAttribute("clubName", clubName);
        return "player";
    }
}
