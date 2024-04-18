package ru.fsv67.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fsv67.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
