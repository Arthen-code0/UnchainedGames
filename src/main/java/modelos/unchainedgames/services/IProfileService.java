package modelos.unchainedgames.services;

import modelos.unchainedgames.dto.ProfileCreateDTO;
import modelos.unchainedgames.models.Profile;

public interface IProfileService {
    ProfileCreateDTO updateProfile(Profile profileLoged, ProfileCreateDTO profileCreateDTO);
}