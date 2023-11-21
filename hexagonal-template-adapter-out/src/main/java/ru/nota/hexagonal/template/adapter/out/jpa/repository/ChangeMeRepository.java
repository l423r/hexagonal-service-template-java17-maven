package ru.nota.hexagonal.template.adapter.out.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nota.hexagonal.template.adapter.out.jpa.entity.ChangeMeEntity;

import java.util.UUID;

@Repository
public interface ChangeMeRepository extends JpaRepository<ChangeMeEntity, UUID> {
}
