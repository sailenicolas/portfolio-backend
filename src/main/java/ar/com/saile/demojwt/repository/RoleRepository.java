package ar.com.saile.demojwt.repository;

import ar.com.saile.demojwt.domain.AppRole;
import ar.com.saile.demojwt.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<AppRole, Long> {

    AppRole findByName(String name);
}
