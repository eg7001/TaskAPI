package com.example.taskapi.service;

import com.example.taskapi.dto.team.TeamResponseDto;
import com.example.taskapi.exceptions.ResourceNotFoundException;
import com.example.taskapi.exceptions.UnauthorizedException;
import com.example.taskapi.mappers.TeamMapper;
import com.example.taskapi.models.Team;
import com.example.taskapi.models.TeamMembership;
import com.example.taskapi.models.User;
import com.example.taskapi.repository.TeamMembershipRepository;
import com.example.taskapi.repository.TeamRepository;
import com.example.taskapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamMembershipRepository teamMembershipRepository;
    private final UserRepository userRepository;

    public TeamService(TeamRepository teamRepository, TeamMembershipRepository teamMembershipRepository, UserRepository userRepository){
        this.teamRepository = teamRepository;
        this.teamMembershipRepository = teamMembershipRepository;
        this.userRepository = userRepository;
    }
    public TeamResponseDto createTeam(String name, Long creatorId) {

        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Team team = new Team();
        team.setName(name);
        team.setCreatedBy(creator);

        teamRepository.save(team);

        TeamMembership membership = new TeamMembership();
        membership.setUser(creator);
        membership.setTeam(team);
        membership.setRole("TEAM_LEAD");
        membership.setJoinedAt(LocalDateTime.now());

        teamMembershipRepository.save(membership);

        return TeamMapper.toDto(team);
    }

    public void addUserToTeam(Long teamId, Long userId, User currentUser) {

        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(r -> r.getName().equals("ADMIN"));

        boolean isTeamLead = teamMembershipRepository
                .findByUserIdAndTeamIdAndRole(currentUser.getId(), teamId, "TEAM_LEAD")
                .isPresent();

        if (!isAdmin && !isTeamLead) {
            throw new UnauthorizedException("Only admins or team leads can add members");
        }

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        teamMembershipRepository.findByUserIdAndTeamId(userId, teamId)
                .ifPresent(m -> { throw new RuntimeException("User already in team"); });

        TeamMembership membership = new TeamMembership();
        membership.setUser(user);
        membership.setTeam(team);
        membership.setRole("MEMBER");
        membership.setJoinedAt(LocalDateTime.now());

        teamMembershipRepository.save(membership);
    }
    public List<TeamResponseDto> getAll(){
        return teamRepository.findAll()
                .stream()
                .map(TeamMapper::toDto)
                .toList();

    }

    public void deleteTeam(Long teamId){
        teamRepository.deleteById(teamId);
    }
}
