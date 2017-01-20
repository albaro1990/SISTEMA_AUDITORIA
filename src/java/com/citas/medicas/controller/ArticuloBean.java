/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citas.medicas.controller;

import com.citas.medicas.dao.ArticuloDao;
import com.citas.medicas.dao.ArticuloDetalleDao;
import com.citas.medicas.dao.ProveedorArticuloDao;
import com.citas.medicas.dao.ProveedorDao;
import com.citas.medicas.dao.impl.ArticuloDaoImpl;
import com.citas.medicas.dao.impl.ArticuloDetalleDaoImpl;
import com.citas.medicas.dao.impl.ProveedorArticuloDaoImpl;
import com.citas.medicas.dao.impl.ProveedorDaoImpl;
import com.citas.medicas.entity.FacArticulo;
import com.citas.medicas.entity.FacArticuloDetalle;
import com.citas.medicas.entity.FacProveedor;
import com.citas.medicas.entity.FacProveedorArticulo;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rene.travez
 */
@ManagedBean(name = "articuloBean")
@ViewScoped
public class ArticuloBean extends GenericBean {

    private static final long serialVersionUID = 1L;
    private final Logger LOG = LoggerFactory.getLogger(ArticuloBean.class);
    private Integer codigoProveedor;
    private List<FacProveedor> listaProveedor = new ArrayList<FacProveedor>();
    private List<FacProveedorArticulo> listaProveedorArticulos = new ArrayList<FacProveedorArticulo>();
    private FacProveedor proveedor;
    private FacArticulo articulo;
    private FacArticuloDetalle articuloDetalle;
    private FacProveedorArticulo proveedorArticulo;

    private ProveedorDao proveedorDao = new ProveedorDaoImpl();
    private ArticuloDao articuloDao = new ArticuloDaoImpl();
    private ArticuloDetalleDao articuloDetalleDao = new ArticuloDetalleDaoImpl();
    private ProveedorArticuloDao proveedorArticuloDao = new ProveedorArticuloDaoImpl();
    private boolean existArticulo = false;
    private boolean editarArticulo = false;
    private boolean habilitar = true;
    private boolean habilitarBusqueda = false;
    private BigDecimal cantidadAnterior = new BigDecimal(0);
    private String articuloEncontrado = "";

    /**
     * Creates a new instance of JsfManejadorUsuarioBean
     */
    public ArticuloBean() {
        proveedor = new FacProveedor();
        articulo = new FacArticulo();
        proveedorArticulo = new FacProveedorArticulo();
        articuloDetalle = new FacArticuloDetalle();
        cargarCombos();
        cargarDependencias();
    }

    public void inicializar(ActionEvent actionEvent) {
        proveedor = new FacProveedor();
        articulo = new FacArticulo();
        proveedorArticulo = new FacProveedorArticulo();
        codigoProveedor = null;
        habilitarBusqueda=false;
        habilitar=true;

    }

    private void cargarCombos() {
        try {
            listaProveedor = proveedorDao.findAll();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    private void cargarDependencias() {
        try {
            listaProveedorArticulos = proveedorArticuloDao.findAll();
            for (FacProveedorArticulo item : listaProveedorArticulos) {
                item.setFacArticulo(articuloDao.find(item.getFacArticulo().getArtCodigo()));
                item.setFacProveedor(proveedorDao.find(item.getFacProveedor().getProCodigo().intValue()));
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }

    }

    public void buscarArticulo(ActionEvent actionEvent) {
        proveedorArticulo = new FacProveedorArticulo();
        try {

            if (articuloDao.existePorCampo(articulo.getArtCodigoUnico()) == true) {
                String coidgoUnicoArticulo = articulo.getArtCodigoUnico();
                articulo = new FacArticulo();
                articulo = articuloDao.findXCodUnico(coidgoUnicoArticulo);
                cantidadAnterior = articulo.getArtCantidadIngresada();
                articuloEncontrado = articulo.getArtCodigoUnico();
                setCantidadAnterior(articulo.getArtCantidadIngresada());
                articulo.setArtCantidadIngresada(new BigDecimal(0));
                existArticulo = true;
                editarArticulo=false;
                cargarDependencias();
            } else {
                cargarDependencias();
                saveMessageInfoDetail("Artículo", "Artículo nuevo crearlo");
            }
            habilitar=false;
            habilitarBusqueda=true;
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }

    }

    public void create(ActionEvent actionEvent) {
        try {

            if (existArticulo == true && articuloEncontrado == articulo.getArtCodigoUnico()) {
                BigDecimal nuevaCantidad = articulo.getArtCantidadIngresada();
                BigDecimal nuevaCantidadFinal = cantidadAnterior.add(nuevaCantidad);
                articulo.setArtCantidadIngresada(nuevaCantidadFinal);
                Integer aplicaIva= 1;
                articulo.setArtAplicaIvaInt(aplicaIva);
                articuloDao.update(articulo);
                articuloDetalle.setArtCantidadIgresada(nuevaCantidad);
                articuloDetalle.setFacArticulo(articuloDao.find(articulo.getArtCodigo()));
                articuloDetalle.setArtValorUnitario(articulo.getArtValorUnitario());
                articuloDetalle.setArtSaldo(nuevaCantidadFinal);
                articuloDetalle.setArtAutorizacion(articulo.getArtCodigo().toString());
                articuloDetalleDao.save(articuloDetalle);
                if (!(proveedorArticuloDao.existePorCampo(articulo.getArtCodigo(), codigoProveedor))) {
                    proveedorArticulo.setFacArticulo(articuloDao.find(articulo.getArtCodigo()));
                    proveedorArticulo.setFacProveedor(proveedorDao.find(codigoProveedor));
                    proveedorArticuloDao.save(proveedorArticulo);
                }
                cargarDependencias();
                inicializar(actionEvent);
                saveMessageInfoDetail("Artículo", "Artículo " + articulo.getArtCodigoUnico() + " creado correctamente");
            } else if (editarArticulo == true) {
                Integer aplicaIva= 1;
                articulo.setArtAplicaIvaInt(aplicaIva);
                articuloDao.update(articulo);
                if (!(proveedorArticuloDao.existePorCampo(articulo.getArtCodigo(), codigoProveedor))) {
                    proveedorArticulo.setFacArticulo(articuloDao.find(articulo.getArtCodigo()));
                    proveedorArticulo.setFacProveedor(proveedorDao.find(codigoProveedor));
                    proveedorArticuloDao.save(proveedorArticulo);
                }
                cargarDependencias();
                inicializar(actionEvent);
                saveMessageInfoDetail("Artículo", "Artículo " + articulo.getArtCodigoUnico() + " actualizado correctamente");
            } else {
                articulo.setArtEstado(1);
                Integer aplicaIva= 1;
                articulo.setArtAplicaIvaInt(aplicaIva);
                int id = articuloDao.save(articulo);
                Integer codigo = new Integer(id);
                articuloDetalle.setArtSaldo(articulo.getArtCantidadIngresada());
                articuloDetalle.setArtAutorizacion(codigo.toString());
                articuloDetalle.setArtCantidadIgresada(articulo.getArtCantidadIngresada());
                articuloDetalle.setFacArticulo(articuloDao.find(id));
                articuloDetalle.setArtValorUnitario(articulo.getArtValorUnitario());
                articuloDetalleDao.save(articuloDetalle);
                proveedorArticulo.setFacArticulo(articuloDao.find(id));
                proveedorArticulo.setFacProveedor(proveedorDao.find(codigoProveedor));
                proveedorArticuloDao.save(proveedorArticulo);
                cargarDependencias();
                inicializar(actionEvent);
                saveMessageInfoDetail("Artículo", "Artículo " + articulo.getArtCodigoUnico() + " Creado correctamente");
            }
        } catch (SQLException e) {
            saveMessageErrorDetail("Articulo", e.getMessage());
            LOG.error(e.getMessage(), e);
        }

    }

    public void edit(ActionEvent event) {
        proveedorArticulo = new FacProveedorArticulo();
        articulo = new FacArticulo();
        try {
            existArticulo = false;
            editarArticulo = true;
            habilitar=false;
            habilitarBusqueda=true;
            proveedorArticulo = (FacProveedorArticulo) event.getComponent().getAttributes().get("objetoEditar");
            articulo = proveedorArticulo.getFacArticulo();
            codigoProveedor = proveedorArticulo.getFacProveedor().getProCodigo().intValue();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public void remove(ActionEvent event) {
        try {
            proveedorArticulo = (FacProveedorArticulo) event.getComponent().getAttributes().get("objetoEliminar");
            articuloDao.delete(proveedorArticulo.getFacArticulo().getArtCodigo());
            cargarDependencias();
            this.inicializar(event);

        } catch (Exception e) {
        }
    }

    public Integer getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(Integer codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public List<FacProveedor> getListaProveedor() {
        return listaProveedor;
    }

    public void setListaProveedor(List<FacProveedor> listaProveedor) {
        this.listaProveedor = listaProveedor;
    }

    public FacProveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(FacProveedor proveedor) {
        this.proveedor = proveedor;
    }

    public FacArticulo getArticulo() {
        return articulo;
    }

    public void setArticulo(FacArticulo articulo) {
        this.articulo = articulo;
    }

    public FacProveedorArticulo getProveedorArticulo() {
        return proveedorArticulo;
    }

    public void setProveedorArticulo(FacProveedorArticulo proveedorArticulo) {
        this.proveedorArticulo = proveedorArticulo;
    }

    public List<FacProveedorArticulo> getListaProveedorArticulos() {
        return listaProveedorArticulos;
    }

    public void setListaProveedorArticulos(List<FacProveedorArticulo> listaProveedorArticulos) {
        this.listaProveedorArticulos = listaProveedorArticulos;
    }

    public FacArticuloDetalle getArticuloDetalle() {
        return articuloDetalle;
    }

    public void setArticuloDetalle(FacArticuloDetalle articuloDetalle) {
        this.articuloDetalle = articuloDetalle;
    }

    public boolean isExistArticulo() {
        return existArticulo;
    }

    public void setExistArticulo(boolean existArticulo) {
        this.existArticulo = existArticulo;
    }

    public BigDecimal getCantidadAnterior() {
        return cantidadAnterior;
    }

    public void setCantidadAnterior(BigDecimal cantidadAnterior) {
        this.cantidadAnterior = cantidadAnterior;
    }

    public boolean isEditarArticulo() {
        return editarArticulo;
    }

    public void setEditarArticulo(boolean editarArticulo) {
        this.editarArticulo = editarArticulo;
    }

    public String getArticuloEncontrado() {
        return articuloEncontrado;
    }

    public void setArticuloEncontrado(String articuloEncontrado) {
        this.articuloEncontrado = articuloEncontrado;
    }

    public boolean isHabilitar() {
        return habilitar;
    }

    public void setHabilitar(boolean habilitar) {
        this.habilitar = habilitar;
    }

    public boolean isHabilitarBusqueda() {
        return habilitarBusqueda;
    }

    public void setHabilitarBusqueda(boolean habilitarBusqueda) {
        this.habilitarBusqueda = habilitarBusqueda;
    }

}
