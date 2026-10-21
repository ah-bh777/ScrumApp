
package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.Invitation;
import com.ISICOD.ScrumApp.Enums.StatutInvitation;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Integer> {

    // Existing methods
    Optional<Invitation> findByCode(String code);

    List<Invitation> findByUtilisateurId(Integer utilisateurId);

    boolean existsByUtilisateurIdAndEspaceIdAndStatut(
            Integer utilisateurId,
            Integer espaceId,
            StatutInvitation statut
    );

    // New: retrieve invitations by recipient and status
    List<Invitation> findByUtilisateurIdAndStatut(
            Integer utilisateurId,
            StatutInvitation statut
    );


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM Invitation i WHERE i.id = :invitationId")
    Optional<Invitation> findByIdForUpdate(
            @Param("invitationId") Integer invitationId
    );

}
