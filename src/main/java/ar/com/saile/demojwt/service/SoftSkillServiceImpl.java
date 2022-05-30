package ar.com.saile.demojwt.service;

import ar.com.saile.demojwt.domain.AppSoftSkill;
import ar.com.saile.demojwt.repository.SoftSkillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SoftSkillServiceImpl implements SoftSkillService {


    private final SoftSkillRepository softSkillRepository;


    @Override
    public AppSoftSkill saveSoftSkills(AppSoftSkill appSoftSkill) {

        return softSkillRepository.save(appSoftSkill);
    }

    @Override
    public Optional<AppSoftSkill> findById(Long id) {

        return softSkillRepository.findById(id);
    }

    @Override
    public Boolean deleteById(Long id) {

        return null;
    }
}
