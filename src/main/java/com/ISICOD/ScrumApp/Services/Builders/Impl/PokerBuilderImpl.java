package com.ISICOD.ScrumApp.Services.Builders.Impl;

import com.ISICOD.ScrumApp.DTOs.Poker.*;
import com.ISICOD.ScrumApp.Entities.*;
import com.ISICOD.ScrumApp.Services.Builders.PokerBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PokerBuilderImpl implements PokerBuilder {

    @Override
    public PokerSessionDTO build(Session session) {

        return PokerSessionDTO.builder()

                .sessionId(session.getId())
                .commenceA(session.getCommenceA())
                .termineA(session.getTermineA())
                .statut(session.getStatus())

                .sprintId(session.getSprint().getId())
                .sprintTitre(session.getSprint().getTitre())

                .build();
    }

    private SprintUserStoryPokerDTO buildSprintUserStory(
            SprintUserStory sprintUserStory) {

        return SprintUserStoryPokerDTO.builder()

                .sprintUserStoryId(
                        sprintUserStory.getId()
                )

                .userStoryId(
                        sprintUserStory.getUserStory().getId()
                )

                .titre(
                        sprintUserStory.getUserStory().getTitre()
                )

                .description(
                        sprintUserStory.getUserStory().getDescription()
                )

                .priorite(
                        sprintUserStory.getUserStory().getPriorite()
                )

                .storyPoints(
                        sprintUserStory.getUserStory().getStoryPoints()
                )

                .estimationFinale(
                        sprintUserStory.getEstimationFinale()
                )

                .retenue(
                        sprintUserStory.getRetenue()
                )

                .statut(
                        sprintUserStory.getStatut()
                )

                .build();
    }

    private VotePokerDTO buildVote(VotePoker vote) {

        ParticipantSession participant =
                vote.getParticipantSession();

        Utilisateur utilisateur =
                participant.getUtilisateur();

        return VotePokerDTO.builder()

                .voteId(vote.getId())

                .valeur(vote.getValeur())

                .statut(vote.getStatut())

                .creeA(vote.getCreeA())

                .participantId(participant.getId())

                .pseudo(participant.getPseudo())

                .roleSession(participant.getRoleSession())

                .utilisateurId(utilisateur.getId())

                .nom(utilisateur.getNom())

                .prenom(utilisateur.getPrenom())

                .build();
    }

    private TourPokerDTO buildTour(TourEstimation tour) {

        return TourPokerDTO.builder()

                .tourId(tour.getId())

                .numeroTour(tour.getNumeroTour())

                .revele(tour.getRevele())

                .valeurFinale(tour.getValeurFinale())

                .votes(
                        tour.getVotePokers()
                                .stream()
                                .map(this::buildVote)
                                .toList()
                )

                .build();
    }


    private SelectionPokerDTO buildSelection(
            SelectionUserStorySession selection) {

        return SelectionPokerDTO.builder()

                .selectionId(selection.getId())

                .selectionneA(selection.getSelectionneA())

                .sprintUserStory(
                        buildSprintUserStory(
                                selection.getSprintUserStory()
                        )
                )

                .tours(
                        selection.getTourEstimations()
                                .stream()
                                .map(this::buildTour)
                                .toList()
                )

                .build();
    }


}