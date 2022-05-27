package ar.com.saile.demojwt.service;

import ar.com.saile.demojwt.domain.AppExperience;
import ar.com.saile.demojwt.repository.ExperienceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;

    @Override
    public AppExperience saveExperience(AppExperience appExperience) {

        return experienceRepository.save(appExperience);
    }

    @Override
    public Optional<AppExperience> findById(Long id) {

        return experienceRepository.findById(id);
    }
}
