package modelos.unchainedgames.services;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.ProductCreateDTO;
import modelos.unchainedgames.models.Product;
import modelos.unchainedgames.repository.ICategoryRepository;
import modelos.unchainedgames.repository.ILanguageRepository;
import modelos.unchainedgames.repository.IMechanicsRepository;
import modelos.unchainedgames.repository.IProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

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

    public Product obtenerProductosPorId(@PathVariable Integer id){

        Product product = repository.findById(id).orElse(null);

        return product;
    }

    public void createProduct(ProductCreateDTO dto){

        Product NewProduct = new Product();

        NewProduct.setName(dto.getName());
        NewProduct.setPlayerMin(dto.getPlayerMin());
        NewProduct.setPlayerMax(dto.getPlayerMax());
        NewProduct.setDuration(dto.getDuration());
        NewProduct.setRecommendedAge(dto.getRecommendedAge());
        NewProduct.setPrice(dto.getPrice());
        NewProduct.setStock(dto.getStock());
        NewProduct.setPrice(dto.getPrice());
        NewProduct.setBoxSize(dto.getBoxSize());
        NewProduct.setDifficulty(dto.getDifficulty());
        NewProduct.setDescription(dto.getDescription());
        NewProduct.setMechanics(dto.getMechanics());
        NewProduct.setCategories(dto.getCategories());
        NewProduct.setLanguages(dto.getLanguages());

        repository.save(NewProduct);
    }

    public void updateProduct(Integer id, ProductCreateDTO dto){

        Product updatedProduct = repository.findById(id).orElse(null);

        if (updatedProduct != null){
            updatedProduct.setName(dto.getName());
            updatedProduct.setPlayerMin(dto.getPlayerMin());
            updatedProduct.setPlayerMax(dto.getPlayerMax());
            updatedProduct.setDuration(dto.getDuration());
            updatedProduct.setRecommendedAge(dto.getRecommendedAge());
            updatedProduct.setPrice(dto.getPrice());
            updatedProduct.setStock(dto.getStock());
            updatedProduct.setPrice(dto.getPrice());
            updatedProduct.setBoxSize(dto.getBoxSize());
            updatedProduct.setDifficulty(dto.getDifficulty());
            updatedProduct.setDescription(dto.getDescription());
            updatedProduct.setMechanics(dto.getMechanics());
            updatedProduct.setCategories(dto.getCategories());
            updatedProduct.setLanguages(dto.getLanguages());

            repository.save(updatedProduct);
        }
    }

    public void deleteProduct(Integer id){
        repository.deleteById(id);
    }
}
