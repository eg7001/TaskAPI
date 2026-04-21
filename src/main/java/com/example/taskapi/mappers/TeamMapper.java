package com.example.taskapi.mappers;

import com.example.taskapi.dto.team.TeamResponseDto;
import com.example.taskapi.models.Team;

import java.util.List;

public class TeamMapper {
    public static TeamResponseDto toDto(Team team){
        TeamResponseDto dto = new TeamResponseDto();
        dto.setId(team.getId());
        dto.setName(team.getName());

        List<String> members = team.getMemberships()
                .stream()
                .map(m -> m.getUser().getUsername())
                .toList();
        dto.setMembers(members);

        return dto;
    }
}
