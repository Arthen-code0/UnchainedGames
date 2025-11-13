package modelos.unchainedgames.API;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private List<User> users = new ArrayList<>();
    private int nextId = 1;  // Empezar desde 1

    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    public User findById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public User save(User user) {
        if (user.getId() == 0) {
            // Nuevo usuario - asignar ID automáticamente
            user.setId(nextId++);
            users.add(user);
            return user;
        } else {
            // Actualizar usuario existente
            User existingUser = findById(user.getId());
            if (existingUser != null) {
                existingUser.setName(user.getName());
                return existingUser;
            }
            return null;
        }
    }

    public boolean deleteById(int id) {
        return users.removeIf(user -> user.getId() == id);
    }
}
