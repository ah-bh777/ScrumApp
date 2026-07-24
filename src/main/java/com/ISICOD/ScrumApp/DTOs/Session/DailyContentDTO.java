package com.ISICOD.ScrumApp.DTOs.Session;


import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyContentDTO {

    private List<DailyItemDTO> contenus;
}