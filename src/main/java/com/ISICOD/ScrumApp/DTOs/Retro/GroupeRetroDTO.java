package com.ISICOD.ScrumApp.DTOs.Retro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupeRetroDTO {

    private Integer groupeId;

    private String label;

    private List<NoteRetroDTO> notes;
}