package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.DTOs.Utilisateur.*;
import com.ISICOD.ScrumApp.Entities.*;
import com.ISICOD.ScrumApp.Repositories.*;
import com.ISICOD.ScrumApp.Services.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final AppartenanceRepository appartenanceRepository;
    private final InvitationRepository invitationRepository;
    private final NotificationRepository notificationRepository;
    private final ParticipantSessionRepository participantSessionRepository;
    private final DailyContentRepository dailyContentRepository;
    private final NoteRetroRepository noteRetroRepository;
    private final VoteDotRepository voteDotRepository;
    private final ActionItemRepository actionItemRepository;
    private final VotePokerRepository votePokerRepository;


    @Override
    public Utilisateur createUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public Optional<Utilisateur> getUtilisateurById(Integer id) {
        return utilisateurRepository.findById(id);
    }

    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    @Override
    public Utilisateur updateUtilisateur(Integer id, Utilisateur utilisateur) {

        Utilisateur existingUtilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Utilisateur introuvable avec l'id : " + id));


        if (utilisateur.getNom() != null) {
            existingUtilisateur.setNom(utilisateur.getNom());
        }

        if (utilisateur.getPrenom() != null) {
            existingUtilisateur.setPrenom(utilisateur.getPrenom());
        }

        if (utilisateur.getTelephone() != null) {
            existingUtilisateur.setTelephone(utilisateur.getTelephone());
        }

        if (utilisateur.getEmail() != null) {
            existingUtilisateur.setEmail(utilisateur.getEmail());
        }

        return utilisateurRepository.save(existingUtilisateur);
    }

    @Override
    public void deleteUtilisateur(Integer id) {

        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Utilisateur introuvable avec l'id : " + id));

        utilisateurRepository.delete(utilisateur);
    }

    @Override
    public List<UtilisateurEspaceDTO> getUtilisateursEspace(Integer id){
        List<Appartenance> appartenances =
                appartenanceRepository.getAppartenanceByUtilisateurId(id);

        return  appartenances.stream().map(app -> {
            return  UtilisateurEspaceDTO.builder()
                    .espaceId(app.getEspace().getId())
                    .nom(app.getEspace().getNom())
                    .nomEquipe(app.getEspace().getNomEquipe())
                    .capacite(app.getEspace().getCapacite())
                    .estActive(app.getEspace().getEstActive())
                    .role(app.getRoleAttribue())
                    .rejointA(app.getRejointA())
                    .build();
        } ).toList();
    }


    @Override
    public List<UtilisateurInvitationDTO> getInvitationByUtilisateur(Integer utilisateurId) {

        List<Invitation> invitations =
                invitationRepository.findByUtilisateurId(utilisateurId);

        return invitations.stream()
                .map(invitation -> UtilisateurInvitationDTO.builder()
                        .code(invitation.getCode())
                        .expireA(invitation.getExpireA())
                        .estValide(invitation.getEstValide())
                        .role(invitation.getRole())
                        .espaceNom(invitation.getEspace().getNom())
                        .nomEquipe(invitation.getEspace().getNomEquipe())
                        .build())
                .toList();
    }

    @Override
    public List<UtilisateurNotificationDTO> getNotificationsByUtilisateur(Integer utilisateurId) {

        List<Notification> notifications =
                notificationRepository.findByUtilisateurId(utilisateurId);

        return notifications.stream()
                .map(notification -> {

                    ActionItem actionItem = notification.getActionItem();
                    Utilisateur assigneA = actionItem.getAssigneA();
                    Utilisateur createur = actionItem.getCreateur();
                    Session session = actionItem.getSession();

                    return UtilisateurNotificationDTO.builder()

                            .notificationId(notification.getId())
                            .description(notification.getDescription())
                            .envoyeA(notification.getEnvoyeA())
                            .luA(notification.getLuA())

                            .actionItemId(actionItem.getId())
                            .titre(actionItem.getTitre())
                            .actionDescription(actionItem.getDescription())
                            .echeance(actionItem.getEcheance())
                            .status(actionItem.getStatus())
                            .creeA(actionItem.getCreeA())

                            .assigneAId(assigneA != null ? assigneA.getId() : null)
                            .assigneANom(assigneA != null ? assigneA.getNom() : null)
                            .assigneAPrenom(assigneA != null ? assigneA.getPrenom() : null)
                            .assigneAEmail(assigneA != null ? assigneA.getEmail() : null)

                            .createurId(createur != null ? createur.getId() : null)
                            .createurPrenom(createur != null ? createur.getPrenom() : null)
                            .createurNom(createur != null ? createur.getNom() : null)
                            .createurEmail(createur != null ? createur.getEmail() : null)

                            .sessionId(session != null ? session.getId() : null)
                            .statusSession(session != null ? session.getStatus() : null)
                            .commencerA(session != null ? session.getCommenceA() : null)
                            .termineA(session != null ? session.getTermineA() : null)

                            .build();
                })
                .toList();
    }

    @Override
    public List<UtilisateurSessionDTO> getSessionsByUtilisateur(Integer utilisateurId) {

        List<ParticipantSession> participants =
                participantSessionRepository.findByUtilisateurId(utilisateurId);

        return participants.stream()
                .map(participant -> {

                    Espace espace = participant.getSession().getEspace();
                    TypeSession typeSession = participant.getSession().getTypeSession();
                    Sprint sprint = participant.getSession().getSprint();

                    Appartenance appartenance =
                            appartenanceRepository
                                    .findByUtilisateurIdAndEspaceId(
                                            utilisateurId,
                                            espace.getId()
                                    )
                                    .orElseThrow(() ->
                                            new RuntimeException(
                                                    "Appartenance introuvable pour l'utilisateur "
                                                            + utilisateurId
                                                            + " dans l'espace "
                                                            + espace.getId()
                                            ));

                    return UtilisateurSessionDTO.builder()


                            .participantId(participant.getId())
                            .pseudo(participant.getPseudo())
                            .roleSession(participant.getRoleSession())


                            .utilisateurId(participant.getUtilisateur().getId())
                            .prenom(participant.getUtilisateur().getPrenom())
                            .nom(participant.getUtilisateur().getNom())
                            .email(participant.getUtilisateur().getEmail())


                            .sessionId(participant.getSession().getId())


                            .espaceId(espace.getId())
                            .nomEspace(espace.getNom())
                            .nomEquipe(espace.getNomEquipe())
                            .capacite(espace.getCapacite())


                            .roleAttribue(appartenance.getRoleAttribue())

                            .typeSessionId(typeSession.getId())
                            .typeSessionTitre(typeSession.getName())

                            .sprintId(sprint.getId())
                            .sprintTitre(sprint.getTitre())

                            .build();

                })
                .toList();
    }


    @Override
    public List<UtilisateurDailyContentDTO> getDailyContentsByUtilisateur(Integer utilisateurId) {

        List<DailyContent> dailyContents =
                dailyContentRepository
                        .findByParticipantSessionUtilisateurId(utilisateurId);

        return dailyContents.stream()
                .map(dc -> {

                    SprintUserStory sprintUserStory = dc.getSprintUserStory();
                    Sprint sprint = sprintUserStory.getSprint();
                    UserStory userStory = sprintUserStory.getUserStory();

                    return UtilisateurDailyContentDTO.builder()

                            // DailyContent
                            .dailyContentId(dc.getId())
                            .contenu(dc.getContenu())
                            .typeContenu(dc.getTypeContenu())
                            .creeA(dc.getCreeA())

                            // ParticipantSession
                            .participantId(dc.getParticipantSession().getId())
                            .pseudo(dc.getParticipantSession().getPseudo())
                            .roleSession(dc.getParticipantSession().getRoleSession())

                            // Sprint
                            .sprintId(sprint.getId())
                            .sprintTitre(sprint.getTitre())
                            .commenceDe(sprint.getCommFinanceDeDate())

                            // User Story
                            .userStoryId(userStory.getId())
                            .userStoryTitre(userStory.getTitre())

                            .build();
                })
                .toList();
    }

    @Override
    public UtilisateurRetroDTO getRetroByUtilisateur(Integer utilisateurId) {

        // ----------------------------
        // Load data
        // ----------------------------

        List<NoteRetro> notes =
                noteRetroRepository.findByUtilisateurId(utilisateurId);

        List<VoteDot> votes =
                voteDotRepository.findByUtilisateurId(utilisateurId);

        List<ActionItem> actionItems =
                actionItemRepository.findByCreateurId(utilisateurId);



        // ----------------------------
        // Notes
        // ----------------------------

        List<UtilisateurRetroNoteDTO> notesDTO =
                notes.stream()
                        .map(note -> {

                            Session session =
                                    note.getGroupeNote().getSession();

                            ParticipantSession participant =
                                    participantSessionRepository
                                            .findBySessionIdAndUtilisateurId(
                                                    session.getId(),
                                                    utilisateurId
                                            )
                                            .orElseThrow(() ->
                                                    new RuntimeException("Participant introuvable."));

                            Sprint sprint = session.getSprint();

                            return UtilisateurRetroNoteDTO.builder()

                                    .noteId(note.getId())
                                    .contenu(note.getContenu())
                                    .typeColonne(note.getTypeColonne())
                                    .creeA(note.getCreeA())

                                    .participantId(participant.getId())
                                    .pseudo(participant.getPseudo())
                                    .roleSession(participant.getRoleSession())

                                    .sessionId(session.getId())

                                    .sprintId(sprint.getId())
                                    .sprintTitre(sprint.getTitre())

                                    .build();

                        })
                        .toList();



        // ----------------------------
        // Votes
        // ----------------------------

        List<UtilisateurVoteRetroDTO> votesDTO =
                votes.stream()
                        .map(vote -> {

                            NoteRetro note =
                                    vote.getNoteRetro();

                            Sprint sprint =
                                    vote.getSession()
                                            .getSprint();

                            return UtilisateurVoteRetroDTO.builder()

                                    .voteId(vote.getId())
                                    .voteA(vote.getCreeA())

                                    .noteId(note.getId())
                                    .contenu(note.getContenu())
                                    .typeColonne(note.getTypeColonne())

                                    .auteurId(note.getUtilisateur().getId())
                                    .auteurNom(note.getUtilisateur().getNom())
                                    .auteurPrenom(note.getUtilisateur().getPrenom())

                                    .participantId(
                                            vote.getParticipantSession().getId()
                                    )

                                    .pseudo(
                                            vote.getParticipantSession().getPseudo()
                                    )

                                    .roleSession(
                                            vote.getParticipantSession().getRoleSession()
                                    )

                                    .sprintId(sprint.getId())
                                    .sprintTitre(sprint.getTitre())

                                    .build();

                        })
                        .toList();



        // ----------------------------
        // Action Items
        // ----------------------------

        List<UtilisateurRetroActionItemDTO> actionItemsDTO =
                actionItems.stream()
                        .map(action -> {

                            Sprint sprint =
                                    action.getSession().getSprint();

                            return UtilisateurRetroActionItemDTO.builder()

                                    .actionItemId(action.getId())

                                    .titre(action.getTitre())

                                    .description(action.getDescription())

                                    .echeance(action.getEcheance())

                                    .status(action.getStatus())

                                    .creeA(action.getCreeA())

                                    .createurId(action.getCreateur().getId())
                                    .createurNom(action.getCreateur().getNom())

                                    .assigneId(action.getAssigneA().getId())
                                    .assigneNom(action.getAssigneA().getNom())

                                    .sessionId(action.getSession().getId())

                                    .sprintId(sprint.getId())
                                    .sprintTitre(sprint.getTitre())

                                    .build();

                        })
                        .toList();



        // ----------------------------
        // Final DTO
        // ----------------------------

        return UtilisateurRetroDTO.builder()

                .notes(notesDTO)

                .votes(votesDTO)

                .actionItems(actionItemsDTO)

                .build();
    }


    @Override
    public UtilisateurPokerDTO getPokerByUtilisateur(Integer utilisateurId) {

        List<VotePoker> votes =
                votePokerRepository.findByUtilisateurId(utilisateurId);

        List<UtilisateurPokerEstimationDTO> estimations =
                votes.stream()
                        .map(vote -> {


                            TourEstimation tour = vote.getTourEstimation();


                            SelectionUserStorySession selection =
                                    tour.getSelectionUserStorySession();


                            SprintUserStory sprintUserStory =
                                    selection.getSprintUserStory();


                            Sprint sprint =
                                    sprintUserStory.getSprint();


                            UserStory userStory =
                                    sprintUserStory.getUserStory();


                            ParticipantSession participant =
                                    vote.getParticipantSession();


                            Session session =
                                    participant.getSession();

                            return UtilisateurPokerEstimationDTO.builder()


                                    .participantId(participant.getId())
                                    .pseudo(participant.getPseudo())
                                    .roleSession(participant.getRoleSession())

                                    .sessionId(session.getId())
                                    .sessionCommenceA(session.getCommenceA())
                                    .sessionTermineA(session.getTermineA())
                                    .statutSession(session.getStatus())


                                    .sprintId(sprint.getId())
                                    .sprintTitre(sprint.getTitre())
                                    .objectifSprint(sprint.getObjectif())


                                    .sprintUserStoryId(sprintUserStory.getId())
                                    .retenue(sprintUserStory.getRetenue())
                                    .statutSprintUserStory(
                                            sprintUserStory.getStatut()
                                    )
                                    .commitA(
                                            sprintUserStory.getCommitA()
                                    )
                                    .sprintUserStoryTermineA(
                                            sprintUserStory.getTermineA()
                                    )
                                    .estimationFinale(
                                            sprintUserStory.getEstimationFinale()
                                    )


                                    .userStoryId(userStory.getId())
                                    .titre(userStory.getTitre())
                                    .description(userStory.getDescription())
                                    .priorite(userStory.getPriorite())
                                    .storyPoints(userStory.getStoryPoints())


                                    .tourId(tour.getId())
                                    .numeroTour(tour.getNumeroTour())
                                    .revele(tour.getRevele())
                                    .valeurFinale(tour.getValeurFinale())


                                    .voteId(vote.getId())
                                    .valeurVote(vote.getValeur())
                                    .statutVote(vote.getStatut())
                                    .voteA(vote.getCreeA())

                                    .build();

                        })
                        .toList();

        return UtilisateurPokerDTO.builder()
                .estimations(estimations)
                .build();
    }
}