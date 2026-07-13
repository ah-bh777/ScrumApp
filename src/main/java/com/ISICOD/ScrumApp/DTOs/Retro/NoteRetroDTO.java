package com.ISICOD.ScrumApp.DTOs.Retro;

    import com.ISICOD.ScrumApp.DTOs.Retro.UtilisateurRetroDTO;
import com.ISICOD.ScrumApp.Enums.TypeColonne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteRetroDTO {

    private Integer noteId;

    private String contenu;

    private TypeColonne typeColonne;

    private LocalDateTime creeA;

    private UtilisateurRetroDTO auteur;

    private Integer nombreVotes;

    private List<VoteDotRetroDTO> votes;

}