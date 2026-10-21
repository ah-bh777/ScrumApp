
package com.ISICOD.ScrumApp.Services;

import com.ISICOD.ScrumApp.Enums.RoleEspace;

public interface AppartenanceService {

    boolean estMembre(
            Integer utilisateurId,
            Integer espaceId
    );

    boolean possedeRole(
            Integer utilisateurId,
            Integer espaceId,
            RoleEspace role
    );

    boolean estScrumMaster(
            Integer utilisateurId,
            Integer espaceId
    );
}
