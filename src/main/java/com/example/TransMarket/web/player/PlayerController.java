package com.example.TransMarket.web.player;

import com.example.TransMarket.domain.Player;
import com.example.TransMarket.dto.playerDTO;
import com.example.TransMarket.dto.trophyDTO;
import com.example.TransMarket.repository.AvailablePositionRepository;
import com.example.TransMarket.repository.ClubRepository;
import com.example.TransMarket.repository.PlayerRepository;
import com.example.TransMarket.repository.TrophyRepository;
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
    private final TrophyRepository trophyRepository;
    private final AvailablePositionRepository positionRepository;

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
        List<trophyDTO> trophys = trophyRepository.findTrophyByPlayerId(playerId);
        List<String> positions = positionRepository.displayAvailablePostionByPlayerId(playerId);

        model.addAttribute("player", player);
        model.addAttribute("clubName", clubName);
        model.addAttribute("trophys", trophys);
        model.addAttribute("positions", positions);

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
                            @RequestParam(required = false) String clubName,
//                            @RequestParam(required = false) Character importance,
                            Model model
    ) throws SQLException {

        Player player = new Player();
        player.setPlayerName(playerName);
        player.setAge(age);
        player.setNationality(nationality);

        if (clubName != "" && clubRepository.findClubIdByClubName(clubName) == null) {
            model.addAttribute("errorMessage", "Club not null but does not exist. Please try again.");
            return "addForm";
        }
        player.setClubId(clubRepository.findClubIdByClubName(clubName));
        if (playerRepository.save(player) == null) {
            model.addAttribute("errorMessage", "Club does not exist. Please try again.");
            return "addForm";
        }

        model.addAttribute("player", player);
        model.addAttribute("clubName", clubName);
        return "player";
    }
}
