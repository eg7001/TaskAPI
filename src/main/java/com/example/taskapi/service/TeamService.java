package com.example.taskapi.service;

import com.example.taskapi.models.Team;
import com.example.taskapi.models.TeamMembership;
import com.example.taskapi.models.User;
import com.example.taskapi.repository.TeamMembershipRepository;
import com.example.taskapi.repository.TeamRepository;
import com.example.taskapi.repository.UserRepository;

import java.time.LocalDateTime;

public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamMembershipRepository teamMembershipRepository;
    private final UserRepository userRepository;

    public TeamService(TeamRepository teamRepository, TeamMembershipRepository teamMembershipRepository, UserRepository userRepository){
        this.teamRepository = teamRepository;
        this.teamMembershipRepository = teamMembershipRepository;
        this.userRepository = userRepository;
    }
    public Team createTeam(String name, Long creatorId) {

        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Team team = new Team();
        team.setName(name);
        team.setCreatedBy(creator);

        teamRepository.save(team);

        // add creator as TEAM_LEAD
        TeamMembership membership = new TeamMembership();
        membership.setUser(creator);
        membership.setTeam(team);
        membership.setRole("TEAM_LEAD");
        membership.setJoinedAt(LocalDateTime.now());

        teamMembershipRepository.save(membership);

        return team;
    }

    public void addUserToTeam(Long teamId, Long userId) {

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // prevent duplicates
        teamMembershipRepository.findByUserIdAndTeamId(userId, teamId)
                .ifPresent(m -> {
                    throw new RuntimeException("User already in team");
                });

        TeamMembership membership = new TeamMembership();
        membership.setUser(user);
        membership.setTeam(team);
        membership.setRole("MEMBER");
        membership.setJoinedAt(LocalDateTime.now());

        teamMembershipRepository.save(membership);
    }
}
