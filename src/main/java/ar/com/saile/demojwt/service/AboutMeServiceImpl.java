package ar.com.saile.demojwt.service;

import ar.com.saile.demojwt.domain.AppAboutMe;
import ar.com.saile.demojwt.repository.AboutMeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AboutMeServiceImpl implements AboutMeService {

    private final AboutMeRepository aboutMeRepository;

    @Override
    public AppAboutMe saveAboutMe(AppAboutMe appAboutMe) {

        return aboutMeRepository.save(appAboutMe);
    }

    @Override
    public Optional<AppAboutMe> findById(Long id) {

        return aboutMeRepository.findById(id);
    }
}
