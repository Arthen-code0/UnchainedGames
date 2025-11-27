package modelos.unchainedgames.controllers;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import modelos.unchainedgames.dto.ProfileCreateDTO;
import modelos.unchainedgames.models.Profile;
import modelos.unchainedgames.services.ProfileService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//import modelos.unchainedgames.dto.ProfileCreateDTO;
//import modelos.unchainedgames.models.Profile;
//import modelos.unchainedgames.security.JWTService;
//import modelos.unchainedgames.security.ProfileServices;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
@RestController
@RequestMapping("/profile")
@AllArgsConstructor
public class ProfileController {

    private ProfileService service;

    @GetMapping("/all")
    public List<Profile> obtenerTodasCategorias(){
        return service.obtenerTodosProfiles();
    }

    @GetMapping("/{id}")
    public Profile obtenerCategoriaPorId(@PathVariable Integer id){
        return service.obtenerProfilesPorId(id);
    }

    @PostMapping("/create")
    public void createProfile(@RequestBody ProfileCreateDTO dto) {
        service.createProfile(dto);
    }

    @PutMapping("/update/{id}")
    public void updateProfile(@PathVariable Integer id, @RequestBody ProfileCreateDTO dto) {
        service.updateProfile(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteProfile(@PathVariable Integer id) {
        service.deleteProfile(id);
    }







//
//    @Autowired
//    private ProfileServices perfilService;
//
//    @Autowired
//   private JWTService jwtService;
//
//    @GetMapping("/all")
//    public ResponseEntity<List<ProfileCreateDTO>> getAllPerfiles(){
//        try {
//            List<ProfileCreateDTO> profiles = perfilService.getAll();
//            return ResponseEntity.ok(profiles);
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().build();
//        }
//    }
//
//    @PutMapping("/update")
//    public ResponseEntity<?> updatePerfil(@RequestHeader("Authorization") String token,
//                                          @RequestBody ProfileCreateDTO profileCreateDTO) {
//        try {
//            // Manejar el token Bearer
//            String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;
//            Profile profileLoged = jwtService.extraerPerfilToken(jwtToken);
//
//            if (profileLoged == null) {
//                return ResponseEntity.status(401).body("Token inválido o perfil no encontrado");
//            }
//
//            ProfileCreateDTO updatedProfile = perfilService.updateProfile(profileLoged, profileCreateDTO);
//            return ResponseEntity.ok(updatedProfile);
//
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Error al actualizar perfil: " + e.getMessage());
//        }
//    }
//
}