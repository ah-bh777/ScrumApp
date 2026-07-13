package com.ISICOD.ScrumApp.DTOs.Session;

import com.ISICOD.ScrumApp.Enums.TypeTemplateRetro;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetroContentDTO {

    private TypeTemplateRetro template;

    private List<RetroNoteDTO> notes;

    private List<ActionItemDTO> actionItems;
}