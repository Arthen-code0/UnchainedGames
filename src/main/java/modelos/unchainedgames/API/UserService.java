package modelos.unchainedgames.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(int id, User user) {
        User existingUser = userRepository.findById(id);
        if (existingUser != null) {
            user.setId(id); // Asegurar que el ID sea el correcto
            return userRepository.save(user);
        }
        return null;
    }

    public boolean deleteUser(int id) {
        return userRepository.deleteById(id);
    }
}