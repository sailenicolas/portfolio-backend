package ar.com.saile.demojwt.repository;

import ar.com.saile.demojwt.domain.AppSoftSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SoftSkillRepository extends JpaRepository<AppSoftSkill, Long> {

    Optional<AppSoftSkill> findById(Long id);
}
