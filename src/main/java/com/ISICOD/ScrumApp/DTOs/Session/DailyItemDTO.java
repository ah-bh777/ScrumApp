package com.ISICOD.ScrumApp.DTOs.Session;

import com.ISICOD.ScrumApp.Enums.RoleSession;
import com.ISICOD.ScrumApp.Enums.TypeDailyContent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyItemDTO {

    private String pseudo;

    private RoleSession role;

    private String contenu;

    private TypeDailyContent type;

    private String userStory;

    private String sprint;
}