package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Integer> {

    Optional<Invitation> findByCode(String code);

    List<Invitation> findByUtilisateurId(Integer UtilisateurId);

}