package modelos.unchainedgames.services;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.ProductCreateDTO;
import modelos.unchainedgames.models.Category;
import modelos.unchainedgames.models.Language;
import modelos.unchainedgames.models.Mechanics;
import modelos.unchainedgames.models.Product;
import modelos.unchainedgames.repository.ICategoryRepository;
import modelos.unchainedgames.repository.ILanguageRepository;
import modelos.unchainedgames.repository.IMechanicsRepository;
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
    private IMechanicsRepository mechanicsRepository;
    private ICategoryRepository categoryRepository;


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

        if (dto.getMechanicsIds() != null && !dto.getMechanicsIds().isEmpty()) {
            Set<Mechanics> mechanics =
                    new HashSet<>(mechanicsRepository.findAllById(dto.getMechanicsIds()));
            newProduct.setMechanics(mechanics);
        }

        if (dto.getCategoriesIds() != null && !dto.getCategoriesIds().isEmpty()) {
            Set<Category> categories =
                    new HashSet<>(categoryRepository.findAllById(dto.getCategoriesIds()));
            newProduct.setCategories(categories);
        }

        if (dto.getLanguagesIds() != null && !dto.getLanguagesIds().isEmpty()) {
            Set<Language> languages =
                    new HashSet<>(languageRepository.findAllById(dto.getLanguagesIds()));
            newProduct.setLanguages(languages);
        }

        repository.save(newProduct);
    }

    // ✅ MÉTODO UPDATE ARREGLADO
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

        if (dto.getMechanicsIds() != null) {
            Set<Mechanics> mechanics =
                    new HashSet<>(mechanicsRepository.findAllById(dto.getMechanicsIds()));
            product.setMechanics(mechanics);
        }

        if (dto.getCategoriesIds() != null) {
            Set<Category> categories =
                    new HashSet<>(categoryRepository.findAllById(dto.getCategoriesIds()));
            product.setCategories(categories);
        }

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
}
