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


    public List<Product> obtenerTodosProductos(){
        return repository.findAll();
    }

    public Product obtenerProductosPorId(Integer id){
        return repository.findById(id).orElse(null);
    }

    public void createProduct(ProductCreateDTO dto){

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


    public void updateProduct(Integer id, ProductCreateDTO dto){

        Product updatedProduct = repository.findById(id).orElse(null);

        if (updatedProduct == null) return;

        // Campos simples
        updatedProduct.setName(dto.getName());
        updatedProduct.setPicture(dto.getPicture());
        updatedProduct.setPlayerMin(dto.getPlayerMin());
        updatedProduct.setPlayerMax(dto.getPlayerMax());
        updatedProduct.setDuration(dto.getDuration());
        updatedProduct.setRecommendedAge(dto.getRecommendedAge());
        updatedProduct.setPrice(dto.getPrice());
        updatedProduct.setStock(dto.getStock());
        updatedProduct.setBoxSize(dto.getBoxSize());
        updatedProduct.setDifficulty(dto.getDifficulty());
        updatedProduct.setDescription(dto.getDescription());

        // Relaciones
        Set<Mechanics> mechanics =
                new HashSet<>(mechanicsRepository.findAllById(dto.getMechanicsIds()));

        Set<Category> categories =
                new HashSet<>(categoryRepository.findAllById(dto.getCategoriesIds()));

        Set<Language> languages =
                new HashSet<>(languageRepository.findAllById(dto.getLanguagesIds()));

        updatedProduct.setMechanics(mechanics);
        updatedProduct.setCategories(categories);
        updatedProduct.setLanguages(languages);

        repository.save(updatedProduct);
    }

    public void deleteProduct(Integer id){
        repository.deleteById(id);
    }
}
