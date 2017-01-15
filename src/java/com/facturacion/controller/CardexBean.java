/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facturacion.controller;

import com.facturacion.dao.ArticuloDao;
import com.facturacion.dao.ArticuloDetalleDao;
import com.facturacion.dao.ProveedorArticuloDao;
import com.facturacion.dao.ProveedorDao;
import com.facturacion.dao.impl.ArticuloDaoImpl;
import com.facturacion.dao.impl.ArticuloDetalleDaoImpl;
import com.facturacion.dao.impl.ProveedorArticuloDaoImpl;
import com.facturacion.dao.impl.ProveedorDaoImpl;
import com.facturacion.entity.FacArticulo;
import com.facturacion.entity.FacArticuloDetalle;
import com.facturacion.entity.FacProveedor;
import com.facturacion.entity.FacProveedorArticulo;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
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
@ManagedBean(name = "cardexBean")
@ViewScoped
public class CardexBean extends GenericBean {

    private static final long serialVersionUID = 1L;
    private final Logger LOG = LoggerFactory.getLogger(ArticuloBean.class);
    private Integer codigoProveedor;
    private List<FacProveedor> listaProveedor = new ArrayList<FacProveedor>();
    private List<FacProveedorArticulo> listaProveedorArticulos = new ArrayList<FacProveedorArticulo>();
    private List<FacArticuloDetalle> listaArticulosDetalle = new ArrayList<FacArticuloDetalle>();
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
    public CardexBean() {
        proveedor = new FacProveedor();
        articulo = new FacArticulo();
        proveedorArticulo = new FacProveedorArticulo();
        articuloDetalle = new FacArticuloDetalle();
    }

    public void inicializar(ActionEvent actionEvent) {
        proveedor = new FacProveedor();
        articulo = new FacArticulo();
        proveedorArticulo = new FacProveedorArticulo();
        codigoProveedor = null;
        habilitarBusqueda = false;
        habilitar = true;

    }

    public void buscarArticulo(ActionEvent actionEvent) {
        try {
            if (articuloDao.existePorCampo(articulo.getArtCodigoUnico()) == true) {
                articulo = articuloDao.findXCodUnicoActIna(articulo.getArtCodigoUnico());
                listaArticulosDetalle = articuloDetalleDao.findAllXCod(articulo.getArtCodigo());
                Integer contador = 0;
                Integer cantRegistros = listaArticulosDetalle.size();
                BigDecimal totalExistentes = new BigDecimal(0);
                BigDecimal unitarioSaliente = new BigDecimal(0);
                for (FacArticuloDetalle item : listaArticulosDetalle) {
                    contador++;
                    if (contador == 1) {
                        item.setDetalle("Saldo Anterior");
                        item.setArtCantidadIgresadaInicio(item.getArtSaldo());
                        item.setArtValorUnitarioInicio(item.getArtValorUnitario());
                        totalExistentes = item.getArtCantidadIgresada().multiply(item.getArtValorUnitario());
                        item.setArtValorTotalInicio(totalExistentes);
                        item.setArtCantidadIgresada(null);
                        item.setArtValorTotal(null);
                        item.setArtValorUnitarioIngresado(null);
                        item.setArtValorUnitarioSaliente(null);
                        item.setArtValorTotalSaliente(null);
                        item.setArtCantidadSaliente(null);
                    } else if (item.getArtCantidadIgresada() == null) {
                        
                        item.setDetalle("Venta");
                        item.setArtCantidadSaliente(item.getArtCantidadSaliente());
                        item.setArtValorUnitarioSaliente(unitarioSaliente);
                        item.setArtValorTotalSaliente(item.getArtCantidadSaliente().multiply(item.getArtValorUnitarioSaliente()));
                        item.setArtCantidadIgresadaInicio(item.getArtSaldo());
                        item.setArtValorTotalInicio(totalExistentes.subtract(item.getArtValorTotalSaliente()));
                        totalExistentes = new BigDecimal(0);
                        totalExistentes = item.getArtValorTotalInicio();
                        BigDecimal saldo = new BigDecimal(0);
                        saldo = item.getArtSaldo();
                        BigDecimal bd3 = totalExistentes.divide(saldo, 2, RoundingMode.CEILING );
                        item.setArtValorUnitarioInicio(bd3);
//                        if(contador==cantRegistros){
//                        item.setDetalle("Inventario Final");
//                        item.setArtCantidadSaliente(item.getArtCantidadSaliente());
//                        item.setArtValorUnitarioSaliente(unitarioSaliente);
//                        item.setArtValorTotalSaliente(item.getArtCantidadSaliente().multiply(item.getArtValorUnitarioSaliente()));
//                        item.setArtCantidadIgresadaInicio(item.getArtSaldo());
//                        item.setArtValorTotalInicio(totalExistentes.subtract(item.getArtValorTotalSaliente()));
//                        totalExistentes = new BigDecimal(0);
//                        totalExistentes = item.getArtValorTotalInicio();
//                        BigDecimal saldos = new BigDecimal(0);
//                        saldo = item.getArtSaldo();
//                        BigDecimal bigd3 = totalExistentes.divide(saldos, 2, RoundingMode.CEILING );
//                        item.setArtValorUnitarioInicio(bigd3);
//                        }
                    } else {
                        item.setDetalle("Compra");
                        item.setArtCantidadIgresada(item.getArtCantidadIgresada());
                        item.setArtValorUnitarioIngresado(item.getArtValorUnitario());
                        item.setArtValorTotal(item.getArtCantidadIgresada().multiply(item.getArtValorUnitario()));
                        item.setArtCantidadIgresadaInicio(item.getArtSaldo());
                        item.setArtValorTotalInicio(totalExistentes.add(item.getArtCantidadIgresada().multiply(item.getArtValorUnitario())));
                        totalExistentes = new BigDecimal(0);
                        totalExistentes = item.getArtValorTotalInicio();
                        BigDecimal saldo = new BigDecimal(0);
                        saldo = item.getArtSaldo();
                        BigDecimal bd3 = totalExistentes.divide(saldo, 2, RoundingMode.CEILING );
                        item.setArtValorUnitarioInicio(bd3);
                        unitarioSaliente = bd3;
//                        if(contador==cantRegistros){
//                            item.setDetalle("Inventario Final");
//                        item.setArtCantidadIgresada(item.getArtCantidadIgresada());
//                        item.setArtValorUnitarioIngresado(item.getArtValorUnitario());
//                        item.setArtValorTotal(item.getArtCantidadIgresada().multiply(item.getArtValorUnitario()));
//                        item.setArtCantidadIgresadaInicio(item.getArtSaldo());
//                        item.setArtValorTotalInicio(totalExistentes.add(item.getArtCantidadIgresada().multiply(item.getArtValorUnitario())));
//                        totalExistentes = new BigDecimal(0);
//                        totalExistentes = item.getArtValorTotalInicio();
//                        BigDecimal saldos = new BigDecimal(0);
//                        saldo = item.getArtSaldo();
//                        BigDecimal bigd3 = totalExistentes.divide(saldos, 2, RoundingMode.CEILING );
//                        item.setArtValorUnitarioInicio(bigd3);
//                        unitarioSaliente = bigd3;
//                        }

                    }
                    item.setFacArticulo(articuloDao.findActIna(item.getFacArticulo().getArtCodigo()));
                }
            } else {
                saveMessageInfoDetail("Artículo", "Artículo no existe");
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }

    }

    public void create(ActionEvent actionEvent) {

    }

    public void edit(ActionEvent event) {
       
        
    }

    public void remove(ActionEvent event) {
      
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

    public List<FacArticuloDetalle> getListaArticulosDetalle() {
        return listaArticulosDetalle;
    }

    public void setListaArticulosDetalle(List<FacArticuloDetalle> listaArticulosDetalle) {
        this.listaArticulosDetalle = listaArticulosDetalle;
    }

}
