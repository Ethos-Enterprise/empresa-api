package com.ethos.empresaapi.repository;

import com.ethos.empresaapi.repository.entity.EmpresaEntity;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Transactional
    @Modifying
    @Query("update EmpresaEntity e set e.email = ?1 where e.id = ?2")
    void updateEmail(@NonNull String email, @NonNull UUID id);

    @Transactional
    @Modifying
    @Query("update EmpresaEntity e set e.senha = ?1 where e.id = ?2")
    void updateSenha(@NonNull String senha, @NonNull UUID id);

    @Transactional
    @Modifying
    @Query("update EmpresaEntity e set e.setor = ?1 where e.id = ?2")
    void updateSetor(@NonNull String setor, @NonNull UUID id);

    @Transactional
    @Modifying
    @Query("update EmpresaEntity e set e.qtdFuncionarios = ?1 where e.id = ?2")
    void updateQtdFuncionarios(@NonNull Integer qtdFuncionarios, @NonNull UUID id);

    @Transactional
    @Modifying
    @Query("update EmpresaEntity e set e.email = ?1 where e.id = ?2")
    List<EmpresaEntity> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update EmpresaEntity e set e.fkPlano = ?1 where e.id = ?2")
    void updatePlano(@Param("fkPlano") int plano, @Param("id") UUID id);

    List<EmpresaEntity> findByRazaoSocial(String razaoSocial);

    List<EmpresaEntity> findByCnpj(String cnpj);

    List<EmpresaEntity> findByEmailAndSenha(String email, String senha);

    List<EmpresaEntity> findBySetor(String setor);

    List<EmpresaEntity> findByTelefone(String telefone);

    List<EmpresaEntity> findByAssinanteNewsletterOrderByAssinanteNewsletterAsc(Boolean assinanteNewsletter);
}
