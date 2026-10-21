
package com.ISICOD.ScrumApp.DTOs.Espace;

import com.ISICOD.ScrumApp.Enums.RoleEspace;

public record ChangeMemberRoleRequest(
        Integer acteurId,
        RoleEspace role
) {
}
