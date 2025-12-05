package modelos.unchainedgames.services;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.ProductCreateDTO;
import modelos.unchainedgames.models.Language;
import modelos.unchainedgames.models.Product;
import modelos.unchainedgames.repository.ILanguageRepository;
import modelos.unchainedgames.repository.IProductRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProductService {

    private IProductRepository repository;
    private ILanguageRepository languageRepository;


    public List<Product> obtenerTodosProductos() {
        return repository.findAll();
    }

    public Product obtenerProductosPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void createProduct(ProductCreateDTO dto) {

        Product newProduct = new Product();

        newProduct.setName(dto.getName());
        newProduct.setPicture(dto.getPicture());
        newProduct.setPlayerMin(dto.getPlayerMin());
        newProduct.setPlayerMax(dto.getPlayerMax());
        newProduct.setDuration(dto.getDuration());
        newProduct.setRecommendedAge(dto.getRecommendedAge());
        newProduct.setPrice(dto.getPrice());
        newProduct.setStock(dto.getStock());
        newProduct.setBoxSize(dto.getBoxSize());
        newProduct.setDifficulty(dto.getDifficulty());
        newProduct.setDescription(dto.getDescription());

        // 👇 SOLO cargamos relaciones si el DTO trae IDs
        if (dto.getLanguagesIds() != null && !dto.getLanguagesIds().isEmpty()) {
            Set<Language> languages =
                    new HashSet<>(languageRepository.findAllById(dto.getLanguagesIds()));
            newProduct.setLanguages(languages);
        }

        repository.save(newProduct);
    }

    public Product updateProduct(Integer id, ProductCreateDTO dto) {

        // Usamos el atributo "repository", no la interfaz
        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setRecommendedAge(dto.getRecommendedAge());
        product.setPlayerMin(dto.getPlayerMin());
        product.setPlayerMax(dto.getPlayerMax());
        product.setDuration(dto.getDuration());
        product.setPicture(dto.getPicture());
        product.setBoxSize(dto.getBoxSize());
        product.setDifficulty(dto.getDifficulty());

        // 👇 MUY IMPORTANTE: proteger las listas de IDs
        // y convertir a Set como en createProduct
        if (dto.getLanguagesIds() != null) {
            Set<Language> languages =
                    new HashSet<>(languageRepository.findAllById(dto.getLanguagesIds()));
            product.setLanguages(languages);
        }

        return repository.save(product);
    }

    public void deleteProduct(Integer id) {
        repository.deleteById(id);
    }

    public List<Product> searchProducts(String name, List<String> languages) {

        String normalizedName = (name == null || name.trim().isEmpty())
                ? null
                : name.trim().toLowerCase();

        boolean hasName = normalizedName != null;
        boolean hasLanguages = (languages != null && !languages.isEmpty());

        // ⛔ Sin filtros → todo catálogo
        if (!hasName && !hasLanguages) {
            return repository.findAll();
        }

        // 🔍 Nombre + idiomas
        if (hasName && hasLanguages) {
            return repository.searchByNameAndLanguages(normalizedName, languages);
        }

        // 🔍 Solo nombre
        if (hasName) {
            return repository.searchByName(normalizedName);
        }

        // 🔍 Solo idiomas
        return repository.searchByLanguages(languages);
    }
}
