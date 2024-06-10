package com.example.TransMarket.web.club;

import com.example.TransMarket.domain.Club;
import com.example.TransMarket.domain.Player;
import com.example.TransMarket.dto.clubDTO;
import com.example.TransMarket.dto.playerDTO;
import com.example.TransMarket.repository.ClubRepository;
import com.example.TransMarket.repository.LeagueRepository;
import com.example.TransMarket.repository.RosterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/clubs")
@RequiredArgsConstructor
public class ClubController {

    private final ClubRepository clubRepository;
    private final LeagueRepository leagueRepository;
    private final RosterRepository rosterRepository;

    @GetMapping
    public String clubs(Model model) {
        List<Club> list = clubRepository.findAll();
        List<clubDTO> dtoList = new ArrayList<>();

        for (Club club : list) {
            clubDTO c = new clubDTO(club.getClubId());
            c.setClubName(club.getClubName());
            c.setLeagueName(leagueRepository.findNameById(club.getLeagueId()));

            dtoList.add(c);
        }

//        model.addAttribute("players", players);
        model.addAttribute("clubs", dtoList);
        return "clubs";
    }

    @GetMapping("/{clubId}")
    public String club(@PathVariable String clubId, Model model) {
        String clubName = clubRepository.findClubNameByclubId(clubId);
        List<String> roster = rosterRepository.showAllPlayerName(clubName);
        model.addAttribute("roster", roster);
        model.addAttribute("clubName", clubName);
        return "club";
    }
}
