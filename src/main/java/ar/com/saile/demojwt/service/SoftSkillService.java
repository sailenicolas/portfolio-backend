package ar.com.saile.demojwt.service;

import ar.com.saile.demojwt.domain.AppSoftSkill;

import java.util.Optional;

public interface SoftSkillService {

    AppSoftSkill saveSoftSkills(AppSoftSkill appSoftSkill);

    Optional<AppSoftSkill> findById(Long id);
}
