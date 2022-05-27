package ar.com.saile.demojwt.service;

import ar.com.saile.demojwt.domain.AppAboutMe;

import java.util.Optional;

public interface AboutMeService {

    AppAboutMe saveAboutMe(AppAboutMe appAboutMe);

    Optional<AppAboutMe> findById(Long id);

}
