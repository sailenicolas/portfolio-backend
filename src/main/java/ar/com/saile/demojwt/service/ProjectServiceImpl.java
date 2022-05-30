package ar.com.saile.demojwt.service;

import ar.com.saile.demojwt.domain.AppProject;
import ar.com.saile.demojwt.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public AppProject saveProject(AppProject appProject) {

        return projectRepository.save(appProject);
    }

    @Override
    public Optional<AppProject> findById(Long id) {

        return projectRepository.findById(id);
    }

    @Override
    public Boolean deleteById(Long id) {

        return null;
    }
}
