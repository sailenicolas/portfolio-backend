package ar.com.saile.demojwt.service;

import ar.com.saile.demojwt.domain.AppExperience;

import java.util.Optional;

public interface ExperienceService {

    AppExperience saveExperience(AppExperience appExperience);

    Optional<AppExperience> findById(Long id);
}
