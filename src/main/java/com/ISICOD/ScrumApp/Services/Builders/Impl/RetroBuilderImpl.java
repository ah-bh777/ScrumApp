package com.ISICOD.ScrumApp.Services.Builders.Impl;

import com.ISICOD.ScrumApp.DTOs.Retro.*;
import com.ISICOD.ScrumApp.DTOs.Retro.ActionItemRetroDTO;
import com.ISICOD.ScrumApp.Entities.*;
import com.ISICOD.ScrumApp.Services.Builders.RetroBuilder;
import com.ISICOD.ScrumApp.Services.Builders.SessionOptionBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RetroBuilderImpl implements RetroBuilder {

    private final SessionOptionBuilder sessionOptionBuilder;

    @Override
    public RetroSessionDTO build(Session session) {

        List<GroupeRetroDTO> groupes =
                session.getGroupesNote()
                        .stream()
                        .map(this::buildGroup)
                        .toList();

        List<ActionItemRetroDTO> actionItems =
                session.getActionItems()
                        .stream()
                        .map(this::buildActionItem)
                        .toList();

        EtatRetroDTO etatRetro =
                session.getEtatsRetro().isEmpty()
                        ? null
                        : buildEtatRetro(session.getEtatsRetro().get(0));

        return RetroSessionDTO.builder()

                .sessionId(session.getId())
                .commenceA(session.getCommenceA())
                .termineA(session.getTermineA())
                .statut(session.getStatus())

                .sprintId(session.getSprint().getId())
                .sprintTitre(session.getSprint().getTitre())

                .etatRetro(etatRetro)

                .groupes(groupes)

                .actionItems(actionItems)

                .options(
                        sessionOptionBuilder.build(session.getConfigurations())
                )

                .build();
    }


    private NoteRetroDTO buildNote(NoteRetro note) {

        List<VoteDotRetroDTO> votes =
                note.getVotesDot()
                        .stream()
                        .map(this::buildVote)
                        .toList();

        return NoteRetroDTO.builder()

                .noteId(note.getId())

                .contenu(note.getContenu())

                .typeColonne(note.getTypeColonne())

                .creeA(note.getCreeA())

                .auteur(
                        buildUtilisateur(note.getUtilisateur())
                )

                .nombreVotes(note.getVotesDot().size())

                .votes(votes)

                .build();
    }

    private UtilisateurRetroDTO buildUtilisateur(Utilisateur utilisateur) {

        return UtilisateurRetroDTO.builder()

                .utilisateurId(utilisateur.getId())

                .nom(utilisateur.getNom())

                .prenom(utilisateur.getPrenom())

                .email(utilisateur.getEmail())

                .build();
    }

    private VoteDotRetroDTO buildVote(VoteDot vote) {

        ParticipantSession participant = vote.getParticipantSession();
        Utilisateur utilisateur = vote.getUtilisateur();

        return VoteDotRetroDTO.builder()

                .voteId(vote.getId())

                .creeA(vote.getCreeA())

                .participantId(participant.getId())

                .pseudo(participant.getPseudo())

                .roleSession(participant.getRoleSession())

                .utilisateurId(utilisateur.getId())

                .nom(utilisateur.getNom())

                .prenom(utilisateur.getPrenom())

                .build();
    }

    private ActionItemRetroDTO buildActionItem(ActionItem actionItem) {

        return ActionItemRetroDTO.builder()

                .actionItemId(actionItem.getId())

                .titre(actionItem.getTitre())

                .description(actionItem.getDescription())

                .statut(actionItem.getStatus().name())

                .creeA(actionItem.getCreeA())

                .assigneAId(actionItem.getAssigneA().getId())

                .assigneANom(actionItem.getAssigneA().getNom())

                .assigneAPrenom(actionItem.getAssigneA().getPrenom())

                .createurId(actionItem.getCreateur().getId())

                .createurNom(actionItem.getCreateur().getNom())

                .createurPrenom(actionItem.getCreateur().getPrenom())

                .build();
    }

    private EtatRetroDTO buildEtatRetro(EtatRetro etatRetro) {

        return EtatRetroDTO.builder()

                .etatRetroId(etatRetro.getId())

                .phaseActuelle(etatRetro.getPhaseActuelle())

                .typeTemplate(etatRetro.getTypeTemplate())

                .commenceA(etatRetro.getCommenceA())

                .termineA(etatRetro.getTermineA())

                .build();
    }
    private GroupeRetroDTO buildGroup(GroupeNote groupe) {

        List<NoteRetroDTO> notes =
                groupe.getNotesRetro()
                        .stream()
                        .map(this::buildNote)
                        .toList();

        return GroupeRetroDTO.builder()

                .groupeId(groupe.getId())

                .label(groupe.getLabel())

                .notes(notes)

                .build();
    }

}