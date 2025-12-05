package modelos.unchainedgames.repository;

import modelos.unchainedgames.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product, Integer> {

    // Buscar solo por nombre
    @Query("""
           select distinct p
           from Product p
           where lower(p.name) like lower(concat('%', :name, '%'))
           """)
    List<Product> searchByName(@Param("name") String name);


    // Buscar solo por idiomas
    @Query("""
           select distinct p
           from Product p
           join p.languages l
           where l.name in :languages
           """)
    List<Product> searchByLanguages(@Param("languages") List<String> languages);


    // Buscar por nombre + idiomas
    @Query("""
           select distinct p
           from Product p
           join p.languages l
           where lower(p.name) like lower(concat('%', :name, '%'))
             and l.name in :languages
           """)
    List<Product> searchByNameAndLanguages(
            @Param("name") String name,
            @Param("languages") List<String> languages
    );
}
