package com.ISICOD.ScrumApp.Services.DailyMeeting;

import com.ISICOD.ScrumApp.DTOs.DailyMeeting.DailyContentRequestDTO;
import com.ISICOD.ScrumApp.DTOs.DailyMeeting.DailyContentUpdateDTO;
import com.ISICOD.ScrumApp.DTOs.DailyMeeting.DailyDTO;
import com.ISICOD.ScrumApp.DTOs.DailyMeeting.DailySessionRequestDTO;
import com.ISICOD.ScrumApp.DTOs.DailyMeeting.DailyStoryOptionDTO;
import com.ISICOD.ScrumApp.DTOs.DailyMeeting.ParticipantJoinRequestDTO;
import com.ISICOD.ScrumApp.DTOs.DailyMeeting.SessionStatusUpdateDTO;
import com.ISICOD.ScrumApp.Entities.Appartenance;
import com.ISICOD.ScrumApp.Entities.DailyContent;
import com.ISICOD.ScrumApp.Entities.Espace;
import com.ISICOD.ScrumApp.Entities.ParticipantSession;
import com.ISICOD.ScrumApp.Entities.Session;
import com.ISICOD.ScrumApp.Entities.Sprint;
import com.ISICOD.ScrumApp.Entities.SprintUserStory;
import com.ISICOD.ScrumApp.Entities.TypeSession;
import com.ISICOD.ScrumApp.Entities.Utilisateur;
import com.ISICOD.ScrumApp.Enums.RoleEspace;
import com.ISICOD.ScrumApp.Enums.RoleSession;
import com.ISICOD.ScrumApp.Enums.StatutSession;
import com.ISICOD.ScrumApp.Enums.TypeSessionCode;
import com.ISICOD.ScrumApp.Exceptions.BadRequestException;
import com.ISICOD.ScrumApp.Exceptions.ConflictException;
import com.ISICOD.ScrumApp.Exceptions.ForbiddenException;
import com.ISICOD.ScrumApp.Exceptions.ResourceNotFoundException;
import com.ISICOD.ScrumApp.Repositories.AppartenanceRepository;
import com.ISICOD.ScrumApp.Repositories.DailyContentRepository;
import com.ISICOD.ScrumApp.Repositories.EspaceRepository;
import com.ISICOD.ScrumApp.Repositories.ParticipantSessionRepository;
import com.ISICOD.ScrumApp.Repositories.SessionRepository;
import com.ISICOD.ScrumApp.Repositories.SprintRepository;
import com.ISICOD.ScrumApp.Repositories.SprintUserStoryRepository;
import com.ISICOD.ScrumApp.Repositories.TypeSessionRepository;
import com.ISICOD.ScrumApp.Repositories.UtilisateurRepository;
import com.ISICOD.ScrumApp.Services.Builders.DailyMeeting.DailyMeetingBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DailyServiceImpl implements DailyService {

    private final SessionRepository sessionRepository;
    private final EspaceRepository espaceRepository;
    private final SprintRepository sprintRepository;
    private final TypeSessionRepository typeSessionRepository;
    private final ParticipantSessionRepository participantSessionRepository;
    private final DailyContentRepository dailyContentRepository;
    private final SprintUserStoryRepository sprintUserStoryRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final AppartenanceRepository appartenanceRepository;
    private final DailyMeetingBuilder dailyMeetingBuilder;

    @Override
    @Transactional(readOnly = true)
    public DailyDTO getDaily(Integer sessionId) {

        Session session = getSession(sessionId);

        validateDailySession(session);

        List<ParticipantSession> participants =
                participantSessionRepository.findAll()
                        .stream()
                        .filter(p ->
                                p.getSession() != null
                                        && p.getSession().getId().equals(sessionId)
                        )
                        .toList();

        List<DailyContent> contents =
                dailyContentRepository.findBySessionId(sessionId);

        return dailyMeetingBuilder.buildDaily(
                session,
                participants,
                contents
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<DailyStoryOptionDTO> getDailyStories(Integer sprintId) {

        Sprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Sprint introuvable : " + sprintId
                        )
                );

        return sprintUserStoryRepository
                .findBySprintId(sprint.getId())
                .stream()
                .map(dailyMeetingBuilder::buildStoryOption)
                .toList();
    }

    @Override
    public DailyDTO createDaily(
            Integer espaceId,
            Integer sprintId,
            Integer utilisateurId,
            DailySessionRequestDTO request) {

        Espace espace = espaceRepository.findById(espaceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Workspace introuvable : " + espaceId
                        )
                );

        Sprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Sprint introuvable : " + sprintId
                        )
                );

        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Utilisateur introuvable : " + utilisateurId
                        )
                );

        /*
         * Vérifie que le sprint appartient bien au workspace.
         */
        if (sprint.getEspace() == null
                || !sprint.getEspace().getId().equals(espaceId)) {

            throw new BadRequestException(
                    "Le sprint n'appartient pas à ce workspace."
            );
        }

        /*
         * Vérifie que l'utilisateur appartient au workspace.
         */
        Appartenance appartenance =
                appartenanceRepository
                        .findByUtilisateurIdAndEspaceId(
                                utilisateurId,
                                espaceId
                        )
                        .orElseThrow(() ->
                                new ForbiddenException(
                                        "L'utilisateur n'est pas membre de ce workspace."
                                )
                        );

        /*
         * Seul le SCRUM_MASTER peut créer une Daily.
         */
        if (appartenance.getRoleAttribue()
                != RoleEspace.SCRUM_MASTER) {

            throw new ForbiddenException(
                    "Seul le SCRUM_MASTER peut créer une Daily."
            );
        }

        /*
         * Récupération du type DAILY.
         */
        TypeSession typeDaily =
                typeSessionRepository
                        .findByCode(TypeSessionCode.DAILY)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Le type de session DAILY n'existe pas."
                                )
                        );

        /*
         * Création de la session Daily.
         */
        Session session = Session.builder()
                .titre(request.getTitre())
                .commenceA(LocalDateTime.now())
                .status(StatutSession.ACTIVE)
                .creaA(LocalDateTime.now())
                .espace(espace)
                .sprint(sprint)
                .typeSession(typeDaily)
                .build();

        session = sessionRepository.save(session);

        /*
         * Le créateur devient automatiquement ANIMATEUR.
         */
        ParticipantSession participant =
                ParticipantSession.builder()
                        .pseudo(
                                utilisateur.getPrenom()
                                        + " "
                                        + utilisateur.getNom()
                        )
                        .estInvite(false)
                        .roleSession(RoleSession.ANIMATEUR)
                        .utilisateur(utilisateur)
                        .session(session)
                        .build();

        participantSessionRepository.save(participant);

        return getDaily(session.getId());
    }

    @Override
    public DailyDTO joinDaily(
            Integer sessionId,
            ParticipantJoinRequestDTO request) {

        Session session = getSession(sessionId);

        validateDailySession(session);

        Utilisateur utilisateur =
                utilisateurRepository
                        .findById(request.getUtilisateurId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Utilisateur introuvable : "
                                                + request.getUtilisateurId()
                                )
                        );

        /*
         * Vérifie que l'utilisateur appartient au workspace
         * de la Daily.
         */
        Appartenance appartenance =
                appartenanceRepository
                        .findByUtilisateurIdAndEspaceId(
                                request.getUtilisateurId(),
                                session.getEspace().getId()
                        )
                        .orElseThrow(() ->
                                new ForbiddenException(
                                        "L'utilisateur n'est pas membre de ce workspace."
                                )
                        );

        /*
         * Empêche un utilisateur de rejoindre deux fois
         * la même Daily.
         */
        if (participantSessionRepository
                .findBySessionIdAndUtilisateurId(
                        sessionId,
                        request.getUtilisateurId()
                )
                .isPresent()) {

            throw new ConflictException(
                    "L'utilisateur participe déjà à cette Daily."
            );
        }

        /*
         * Par défaut, un utilisateur rejoint comme PARTICIPANT.
         */
        RoleSession role = request.getRoleSession();

        if (role == null) {
            role = RoleSession.PARTICIPANT;
        }

        /*
         * Seul un SCRUM_MASTER peut être ANIMATEUR.
         */
        if (role == RoleSession.ANIMATEUR
                && appartenance.getRoleAttribue()
                != RoleEspace.SCRUM_MASTER) {

            throw new ForbiddenException(
                    "Seul le SCRUM_MASTER peut être ANIMATEUR."
            );
        }

        /*
         * Si aucun pseudo n'est fourni,
         * on utilise prénom + nom.
         */
        String pseudo = request.getPseudo();

        if (pseudo == null || pseudo.isBlank()) {
            pseudo = utilisateur.getPrenom()
                    + " "
                    + utilisateur.getNom();
        }

        ParticipantSession participant =
                ParticipantSession.builder()
                        .pseudo(pseudo)
                        .estInvite(false)
                        .roleSession(role)
                        .utilisateur(utilisateur)
                        .session(session)
                        .build();

        participantSessionRepository.save(participant);

        return getDaily(sessionId);
    }

    @Override
    public DailyDTO addContent(
            Integer sessionId,
            DailyContentRequestDTO request) {

        Session session = getSession(sessionId);

        validateActiveDaily(session);

        if (request.getUtilisateurId() == null) {

            throw new BadRequestException(
                    "utilisateurId est obligatoire."
            );
        }

        /*
         * Vérifie que l'utilisateur participe à cette Daily.
         */
        ParticipantSession participant =
                participantSessionRepository
                        .findBySessionIdAndUtilisateurId(
                                sessionId,
                                request.getUtilisateurId()
                        )
                        .orElseThrow(() ->
                                new ForbiddenException(
                                        "L'utilisateur ne participe pas à cette Daily."
                                )
                        );

        /*
         * Vérifie que le participantId envoyé
         * correspond bien à l'utilisateur.
         */
        if (!participant.getId()
                .equals(request.getParticipantSessionId())) {

            throw new BadRequestException(
                    "Le participantSessionId ne correspond pas à l'utilisateur."
            );
        }

        /*
         * Les OBSERVATEURS ne peuvent pas écrire.
         */
        validateCanWrite(participant);

        SprintUserStory sprintUserStory =
                sprintUserStoryRepository
                        .findById(request.getSprintUserStoryId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User Story du sprint introuvable."
                                )
                        );

        /*
         * Vérifie que la User Story appartient
         * au sprint de la Daily.
         */
        if (!sprintUserStory.getSprint().getId()
                .equals(session.getSprint().getId())) {

            throw new BadRequestException(
                    "La User Story n'appartient pas au sprint de cette Daily."
            );
        }

        DailyContent content =
                DailyContent.builder()
                        .contenu(request.getDescription())
                        .typeContenu(request.getType())
                        .session(session)
                        .participantSession(participant)
                        .sprintUserStory(sprintUserStory)
                        .build();

        dailyContentRepository.save(content);

        return getDaily(sessionId);
    }

    @Override
    public DailyDTO updateContent(
            Integer sessionId,
            Integer dailyContentId,
            DailyContentUpdateDTO request) {

        Session session = getSession(sessionId);

        validateActiveDaily(session);

        if (request.getUtilisateurId() == null) {

            throw new BadRequestException(
                    "utilisateurId est obligatoire."
            );
        }

        DailyContent content =
                dailyContentRepository
                        .findById(dailyContentId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Contenu Daily introuvable : "
                                                + dailyContentId
                                )
                        );

        /*
         * Le contenu doit appartenir à la Daily.
         */
        if (!content.getSession().getId()
                .equals(sessionId)) {

            throw new BadRequestException(
                    "Ce contenu n'appartient pas à cette Daily."
            );
        }

        /*
         * Récupération du participant correspondant
         * à l'utilisateur.
         */
        ParticipantSession participant =
                participantSessionRepository
                        .findBySessionIdAndUtilisateurId(
                                sessionId,
                                request.getUtilisateurId()
                        )
                        .orElseThrow(() ->
                                new ForbiddenException(
                                        "L'utilisateur ne participe pas à cette Daily."
                                )
                        );

        /*
         * Un participant ne peut modifier
         * que son propre contenu.
         */
        if (!content.getParticipantSession()
                .getId()
                .equals(participant.getId())) {

            throw new ForbiddenException(
                    "Vous ne pouvez modifier que votre propre contenu."
            );
        }

        validateCanWrite(participant);

        /*
         * Modification éventuelle de la User Story.
         */
        if (request.getSprintUserStoryId() != null) {

            SprintUserStory sprintUserStory =
                    sprintUserStoryRepository
                            .findById(request.getSprintUserStoryId())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "User Story du sprint introuvable."
                                    )
                            );

            if (!sprintUserStory.getSprint().getId()
                    .equals(session.getSprint().getId())) {

                throw new BadRequestException(
                        "La User Story n'appartient pas au sprint de cette Daily."
                );
            }

            content.setSprintUserStory(sprintUserStory);
        }

        /*
         * Modification du type.
         */
        if (request.getType() != null) {
            content.setTypeContenu(request.getType());
        }

        /*
         * Modification de la description.
         */
        if (request.getDescription() != null
                && !request.getDescription().isBlank()) {

            content.setContenu(request.getDescription());
        }

        dailyContentRepository.save(content);

        return getDaily(sessionId);
    }

    @Override
    public void deleteContent(
            Integer sessionId,
            Integer dailyContentId,
            Integer utilisateurId) {

        Session session = getSession(sessionId);

        validateActiveDaily(session);

        DailyContent content =
                dailyContentRepository
                        .findById(dailyContentId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Contenu Daily introuvable : "
                                                + dailyContentId
                                )
                        );

        /*
         * Vérifie que le contenu appartient à cette Daily.
         */
        if (!content.getSession().getId()
                .equals(sessionId)) {

            throw new BadRequestException(
                    "Ce contenu n'appartient pas à cette Daily."
            );
        }

        /*
         * Récupération du participant correspondant
         * à l'utilisateur.
         */
        ParticipantSession participant =
                participantSessionRepository
                        .findBySessionIdAndUtilisateurId(
                                sessionId,
                                utilisateurId
                        )
                        .orElseThrow(() ->
                                new ForbiddenException(
                                        "L'utilisateur ne participe pas à cette Daily."
                                )
                        );

        /*
         * Un utilisateur ne peut supprimer
         * que son propre contenu.
         */
        if (!content.getParticipantSession()
                .getId()
                .equals(participant.getId())) {

            throw new ForbiddenException(
                    "Vous ne pouvez supprimer que votre propre contenu."
            );
        }

        validateCanWrite(participant);

        dailyContentRepository.delete(content);
    }

    @Override
    public DailyDTO updateSessionStatus(
            Integer sessionId,
            Integer utilisateurId,
            SessionStatusUpdateDTO request) {

        Session session = getSession(sessionId);

        validateDailySession(session);

        /*
         * Vérifie que l'utilisateur participe à la Daily.
         */
        ParticipantSession participant =
                participantSessionRepository
                        .findBySessionIdAndUtilisateurId(
                                sessionId,
                                utilisateurId
                        )
                        .orElseThrow(() ->
                                new ForbiddenException(
                                        "L'utilisateur ne participe pas à cette Daily."
                                )
                        );

        /*
         * Seul l'ANIMATEUR peut modifier
         * le statut de la Daily.
         */
        if (participant.getRoleSession()
                != RoleSession.ANIMATEUR) {

            throw new ForbiddenException(
                    "Seul l'ANIMATEUR peut modifier le statut de la Daily."
            );
        }

        if (request.getStatut() == null) {

            throw new BadRequestException(
                    "Le statut est obligatoire."
            );
        }

        session.setStatus(request.getStatut());

        /*
         * Si la Daily est terminée,
         * on enregistre la date de fin.
         */
        if (request.getStatut()
                == StatutSession.TERMINEE) {

            session.setTermineA(LocalDateTime.now());
        }

        sessionRepository.save(session);

        return getDaily(sessionId);
    }

    /*
     * =========================================================
     *                  PRIVATE METHODS
     * =========================================================
     */

    private Session getSession(Integer sessionId) {

        return sessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Session introuvable : " + sessionId
                        )
                );
    }

    private void validateDailySession(Session session) {

        if (session.getTypeSession() == null
                || session.getTypeSession().getCode()
                != TypeSessionCode.DAILY) {

            throw new BadRequestException(
                    "Cette session n'est pas une Daily."
            );
        }
    }

    private void validateActiveDaily(Session session) {

        validateDailySession(session);

        if (session.getStatus()
                != StatutSession.ACTIVE) {

            throw new BadRequestException(
                    "La Daily n'est pas active."
            );
        }
    }

    private void validateCanWrite(
            ParticipantSession participant) {

        if (participant.getRoleSession()
                == RoleSession.OBSERVATEUR) {

            throw new ForbiddenException(
                    "Un observateur ne peut pas modifier le contenu de la Daily."
            );
        }
    }
}
