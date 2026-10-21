package com.ISICOD.ScrumApp.DTOs.Espace;

public record TransferScrumMasterRequest(
        Integer acteurId,
        Integer nouveauScrumMasterId
) {
}
