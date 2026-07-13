package com.ISICOD.ScrumApp.DTOs.Session;

import com.ISICOD.ScrumApp.Enums.TypeColonne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetroNoteDTO {

    private Integer noteId;

    private String auteur;

    private String contenu;

    private TypeColonne colonne;

    private Integer nombreVotes;
}