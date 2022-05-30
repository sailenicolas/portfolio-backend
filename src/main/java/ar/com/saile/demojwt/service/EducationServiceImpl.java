package ar.com.saile.demojwt.service;

import ar.com.saile.demojwt.domain.AppEducation;
import ar.com.saile.demojwt.repository.EducationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;

    @Override
    public AppEducation saveEducation(AppEducation appEducation) {

        return educationRepository.save(appEducation);
    }

    public Optional<AppEducation> findById(Long id) {

        return educationRepository.findById(id);
    }

    @Override
    public Boolean deleteById(Long id) {

        educationRepository.deleteById(id);
        return educationRepository.existsById(id);
    }

}
