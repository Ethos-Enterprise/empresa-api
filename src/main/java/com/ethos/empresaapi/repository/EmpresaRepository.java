package com.ethos.empresaapi.repository;

import com.ethos.empresaapi.repository.entity.EmpresaEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

public interface EmpresaRepository extends JpaRepository<EmpresaEntity, UUID> {
    @Transactional
    @Modifying
    @Query("update EmpresaEntity e set e.razaoSocial = ?1 where e.id = ?2")
    void updateNome(@NonNull String nome, @NonNull UUID id);

    @Transactional
    @Modifying
    @Query("update EmpresaEntity e set e.telefone = ?1 where e.id = ?2")
    void updateTelefone(@NonNull String telefone, @NonNull UUID id);

    @Transactional
    @Modifying
    @Query("update EmpresaEntity e set e.cnpj = ?1 where e.id = ?2")
    void updateCnpj(@NonNull String cnpj, @NonNull UUID id);
    List<EmpresaEntity> findByCnpj(String cnpj);

    List<EmpresaEntity> findByEmailAndSenha(String email, String senha);
}
