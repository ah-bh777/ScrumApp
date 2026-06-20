package com.ISICOD.ScrumApp.Services;

import com.ISICOD.ScrumApp.Entities.EtatRetro;

import java.util.List;
import java.util.Optional;

public interface EtatRetroService {

    EtatRetro create(EtatRetro etatRetro);

    Optional<EtatRetro> getById(Integer id);

    List<EtatRetro> getAll();

    EtatRetro update(Integer id, EtatRetro etatRetro);

    void delete(Integer id);
}