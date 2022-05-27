package ar.com.saile.demojwt.service;

import ar.com.saile.demojwt.domain.AppProject;

import java.util.Optional;

public interface ProjectService {

    AppProject saveProject(AppProject appProject);

    Optional<AppProject> findById(Long id);
}
