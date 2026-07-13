package com.ISICOD.ScrumApp.DTOs.Utilisateur;

import com.ISICOD.ScrumApp.Enums.RoleSession;
import com.ISICOD.ScrumApp.Enums.TypeColonne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurRetroDTO {

    private List<UtilisateurRetroNoteDTO> notes;

    private List<UtilisateurVoteRetroDTO> votes;

    private List<UtilisateurRetroActionItemDTO> actionItems;
}