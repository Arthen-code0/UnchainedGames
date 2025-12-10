package modelos.unchainedgames.services;

import lombok.RequiredArgsConstructor;
import modelos.unchainedgames.dto.PedidoCreateDTO;
import modelos.unchainedgames.dto.PedidoLineCreateDTO;
import modelos.unchainedgames.dto.PedidoMostrarDTO;
import modelos.unchainedgames.listed.Rol;
import modelos.unchainedgames.mapper.PedidoMapper;
import modelos.unchainedgames.models.Pedido;
import modelos.unchainedgames.models.PedidoLine;
import modelos.unchainedgames.models.Product;
import modelos.unchainedgames.models.Usuario;
import modelos.unchainedgames.repository.IPedidoRepository;
import modelos.unchainedgames.repository.IProductRepository;
import modelos.unchainedgames.repository.IUsuarioRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final IPedidoRepository pedidoRepository;
    private final IProductRepository productRepository;
    private final IUsuarioRepository usuarioRepository;

    //Crear pedido desde DTO (carrito borrador del front)
    public PedidoMostrarDTO createPedido(PedidoCreateDTO dto) {

        if (dto.getLineas() == null || dto.getLineas().isEmpty()) {
            throw new RuntimeException("El pedido debe tener al menos una línea");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Usuario no autenticado");
        }

        String email = auth.getName();
        Usuario usuario = usuarioRepository.findTopByEmailEquals(email);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Pedido pedido = new Pedido();
        pedido.setDatetime(Timestamp.from(Instant.now()));
        pedido.setStatus(0); // 0 = PENDIENTE, por ejemplo
        pedido.setUsuario(usuario);

        List<PedidoLine> lineas = new ArrayList<>();

        for (PedidoLineCreateDTO lineaDTO : dto.getLineas()) {

            Product product = productRepository.findById(lineaDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + lineaDTO.getProductId()));

            PedidoLine linea = new PedidoLine();
            linea.setAmount(lineaDTO.getAmount());
            linea.setProduct(product);
            linea.setPedido(pedido); // 👈 asociamos la línea al pedido

            lineas.add(linea);
        }

        pedido.setLineas(lineas);

        Pedido saved = pedidoRepository.save(pedido);

        // devolvemos DTO
        return PedidoMapper.toPedidoMostrarDTO(saved);
    }

    //Pedidos del usuario actual
    public List<PedidoMostrarDTO> getPedidosUsuarioActual() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Usuario no autenticado");
        }

        String email = auth.getName();
        Usuario usuario = usuarioRepository.findTopByEmailEquals(email);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        List<Pedido> pedidos = pedidoRepository
                .findByUsuarioIdOrderByDatetimeDesc(usuario.getId());

        return pedidos.stream()
                .map(PedidoMapper::toPedidoMostrarDTO)
                .toList();
    }

    public PedidoMostrarDTO getPedidoById(Integer id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Usuario no autenticado");
        }

        Usuario actual = (Usuario) auth.getPrincipal();

        Pedido p = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        // Si NO eres ADMIN y el pedido NO es tuyo -> fuera
        if (!actual.getRol().equals(Rol.ADMIN)
                && !p.getUsuario().getId().equals(actual.getId())) {
            throw new AccessDeniedException("No tienes permiso para ver este pedido");
        }

        return PedidoMapper.toPedidoMostrarDTO(p);
    }
}
