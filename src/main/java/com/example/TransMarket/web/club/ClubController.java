package com.example.TransMarket.web.club;

import com.example.TransMarket.domain.Club;
import com.example.TransMarket.domain.Player;
import com.example.TransMarket.dto.clubDTO;
import com.example.TransMarket.dto.playerDTO;
import com.example.TransMarket.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/clubs")
@RequiredArgsConstructor
public class ClubController {

    private final ClubRepository clubRepository;

    @GetMapping
    public String clubs(Model model) {
        List<Club> list = clubRepository.findAll();
        List<clubDTO> dtoList = new ArrayList<>();

        for (Club club : list) {
            clubDTO c = new clubDTO(club.getClubId());
            c.setClubName(club.getClubName());
            //todo : leagueName -> leagueRepository
            dtoList.add(c);
        }

//        model.addAttribute("players", players);
        model.addAttribute("clubs", dtoList);
        return "clubs";
    }
}
