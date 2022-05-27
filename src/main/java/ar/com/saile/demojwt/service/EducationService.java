package ar.com.saile.demojwt.service;

import ar.com.saile.demojwt.domain.AppEducation;

import java.util.Optional;

public interface EducationService {

    AppEducation saveEducation(AppEducation appEducation);

    Optional<AppEducation> findById(Long id);
}
