package modelos.unchainedgames.repository;

import modelos.unchainedgames.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;


import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {

//    Optional<Usuario> findTopByUsername(String username);


//    @Query("SELECT u FROM Usuario u WHERE u.email = :email AND u.verificationCode = :verificationCode")
//    Optional<Usuario> findByEmailAndVerificationCode(@Param("email") String email,
//                                                     @Param("verificationCode") String verificationCode);
//
//    @Query("SELECT u FROM Usuario u WHERE u.email = :email AND u.recoveryCode = :recoveryCode")
//    Optional<Usuario> findByEmailAndRecoveryCode(@Param("email") String email,
//                                                 @Param("recoveryCode") String recoveryCode);

}
