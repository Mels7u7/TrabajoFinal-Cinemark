package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Lista_Compra;

@Repository
public interface ListaRepository extends JpaRepository<Lista_Compra, Integer> {

	@Query("select o from Lista_Compra o where o.estadoLista like %?1%")
	public List<Lista_Compra> buscarEstado(String estadoLista);
	
	@Query("select o from Lista_Compra o where o.idLista = ?1")
	public List<Lista_Compra> buscarlistaespecifica(int id);

	@Query("select o from Lista_Compra o where o.proveedorLista.nombreProveedor like %:name%")
	public List<Lista_Compra> buscarProveedor(@Param("name") String nombreProveedor);

	@Query("select o from Lista_Compra o where o.notaLista like ?1")
	public List<Lista_Compra> findByNotaLista(String term);
}
