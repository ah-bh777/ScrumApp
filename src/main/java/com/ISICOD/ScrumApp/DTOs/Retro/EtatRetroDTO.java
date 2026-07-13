package com.ISICOD.ScrumApp.DTOs.Retro;

import com.ISICOD.ScrumApp.Enums.PhaseRetro;
import com.ISICOD.ScrumApp.Enums.TypeTemplateRetro;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EtatRetroDTO {

    private Integer etatRetroId;

    private PhaseRetro phaseActuelle;

    private TypeTemplateRetro typeTemplate;

    private LocalDateTime commenceA;

    private LocalDateTime termineA;

}