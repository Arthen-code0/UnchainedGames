package modelos.unchainedgames.services;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.CategoryCreateDTO;
import modelos.unchainedgames.models.Category;
import modelos.unchainedgames.repository.ICategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private ICategoryRepository repository;

    public List<Category> obtenerTodasCategorias(){
        return repository.findAll();
    }

    public Category obtenerCategoriaPorId(Integer id){
        return repository.findById(id).orElse(null);
    }

    public void createCategory(CategoryCreateDTO dto){

        Category newCategory = new Category();

        newCategory.setName(dto.getName());
        newCategory.setProducts(dto.getProducts());

        repository.save(newCategory);
    }

    public void updateCategory(Integer id, CategoryCreateDTO dto){
        Category updatedCategory = repository.findById(id).orElse(null);

        if (updatedCategory != null){
            updatedCategory.setName(dto.getName());
            updatedCategory.setProducts(dto.getProducts());

            repository.save(updatedCategory);
        }
    }

    public void deleteCategory(Integer id){
        repository.deleteById(id);
    }
}