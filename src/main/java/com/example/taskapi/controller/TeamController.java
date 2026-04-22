package com.example.taskapi.controller;

import com.example.taskapi.dto.team.TeamRequestDto;
import com.example.taskapi.dto.team.TeamResponseDto;
import com.example.taskapi.service.TeamService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/team/")
public class TeamController {
    private final TeamService teamService;
    public TeamController(TeamService teamService){
        this.teamService = teamService;
    }
    @GetMapping
    public List<TeamResponseDto> getAll(){
       return teamService.getAll();
    }

    @PostMapping("/{userId}/")
    public TeamResponseDto createTeam(@RequestBody TeamRequestDto requestDto,@PathVariable Long userId){
        return teamService.createTeam(requestDto.getName(),userId);
    }
    @PostMapping("/{teamId}/users/{userId}")
    public void addUserToTeam(@PathVariable Long teamId,@PathVariable Long userId){
        teamService.addUserToTeam(teamId,userId);
    }
}
