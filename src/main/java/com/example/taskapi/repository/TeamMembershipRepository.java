package com.example.taskapi.repository;

import com.example.taskapi.models.TeamMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamMembershipRepository extends JpaRepository<TeamMembership,Long> {
    Optional<TeamMembership> findByUserIdAndTeamId(Long userId, Long teamId);
    List<TeamMembership> findByUserIdAndRole(Long userId, String role);
    Optional<TeamMembership> findByUserIdAndTeamIdAndRole(Long userId, Long teamId, String role);

}
