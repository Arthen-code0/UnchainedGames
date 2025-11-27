package modelos.unchainedgames.repository;

import modelos.unchainedgames.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {

    /*
    * Primer método de consulta:
    *
    * List<Product> findByName(String name); //Para solo buscar por nombre
    *
    * Consulta directa con la base de datos
    *
    * Segunda método de consulta:
    *
    * @Query(value = "select p from producto p where ciudad = ?1 o :"producto", nativeQuery = true)
    * List<Product> buscarPorProductos(@Param("producto"));
    *
    * En el query directamente se mete la consulta que se quiera preguntar
    *
    * = ?! Este método está pendiente de ver si esta correctamente escrito
    *
    * Tercer método de consulta:
    *
    * @Query("select p from product t where p.product = :product")
    * List<Product> buscarPorProducto (@Param ("producto") string nombreProducto)
    *
    * Cuarto método de consulta:
    *
    * List<Product> findAllByProductEqualsAnsCodigoProductLike(String nombre, Integer codigo)
    *
    * Este es solo un ejemplo ya que codigo no esta en la base de datos
    *
    * Estos métodos están por comprobar
    *
    * */


}
