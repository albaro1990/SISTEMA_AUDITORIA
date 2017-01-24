/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citas.medicas.controller;

import com.citas.medicas.dao.ArticuloDao;
import com.citas.medicas.dao.ArticuloDetalleDao;
import com.citas.medicas.dao.CabeceraFacturaDao;
import com.citas.medicas.dao.CiudadDao;
import com.citas.medicas.dao.ClienteDao;
import com.citas.medicas.dao.DetalleFacturaDao;
import com.citas.medicas.dao.impl.ArticuloDaoImpl;
import com.citas.medicas.dao.impl.ArticuloDetalleDaoImpl;
import com.citas.medicas.dao.impl.CabeceraFacturaDaoImpl;
import com.citas.medicas.dao.impl.CiudadDaoImpl;
import com.citas.medicas.dao.impl.ClienteDaoImpl;
import com.citas.medicas.dao.impl.DetalleFacturaDaoImpl;
import com.citas.medicas.entity.FacArticulo;
import com.citas.medicas.entity.FacArticuloDetalle;
import com.citas.medicas.entity.FacCitaFactura;
import com.citas.medicas.entity.CitPaciente;
import com.citas.medicas.entity.FacCiudad;
import com.citas.medicas.entity.FacDetalleFactura;
import com.citas.medicas.entity.FacRol;
import com.citas.medicas.utilitarios.Utils;
import com.citas.medicas.utilitarios.ValidadorCedulaRuc;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean(name = "citasBean")
@ViewScoped
public class CitaBean extends GenericBean {

    private static final long serialVersionUID = 1L;
    private final Logger LOG = LoggerFactory.getLogger(CitaBean.class);

    private CitPaciente cliente;
    private CitPaciente clienteNuevo;
    private FacArticuloDetalle articuloDetalle;
    private FacCitaFactura cabeceraFactura;
    private List<FacDetalleFactura> listaDetalleFacturas;
    private List<FacArticulo> listaArticulos;
    private ClienteDao clienteDao = new ClienteDaoImpl();
    private CabeceraFacturaDao cabeceraFacturaDao = new CabeceraFacturaDaoImpl();
    private DetalleFacturaDao detalleFacturaDao = new DetalleFacturaDaoImpl();
    private ArticuloDao articuloDao = new ArticuloDaoImpl();
    private ArticuloDetalleDao articuloDetalleDao = new ArticuloDetalleDaoImpl();
    private FacArticulo articuloSeleccionado;
    private FacDetalleFactura detalleFactura;
    private List<FacCiudad> ciudades = new ArrayList<FacCiudad>();
    private Integer codigoCiudad;
    private CiudadDao ciudadDAO = new CiudadDaoImpl();
    public CitaBean() {
        try {
            ciudades = ciudadDAO.findAll();
            cliente = new CitPaciente();
            clienteNuevo = new CitPaciente();
            articuloDetalle = new FacArticuloDetalle();
            detalleFactura = new FacDetalleFactura();
            articuloSeleccionado = new FacArticulo();
            cabeceraFactura = new FacCitaFactura();
            cabeceraFactura.setCabSubtotal(BigDecimal.ZERO);
            cabeceraFactura.setCabTotal(BigDecimal.ZERO);
            cabeceraFactura.setCabIva(BigDecimal.ZERO);
            cabeceraFactura.setCabFechaCreacion(new Date());
            cabeceraFactura.setCabAutorizacion(String.valueOf(Random.class.newInstance().nextInt()));
        } catch (InstantiationException | IllegalAccessException ex) {
            LOG.error(ex.getMessage(), ex);
        }catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }

        listaDetalleFacturas = new ArrayList<>();
        listaArticulos = new ArrayList<>();
        cargarCombos();
    }

    public void inicializar(ActionEvent actionEvent) {
        try {
            cliente = new CitPaciente();
            cabeceraFactura = new FacCitaFactura();
            cabeceraFactura.setCabFechaCreacion(new Date());
            cabeceraFactura.setCabAutorizacion(String.valueOf(Random.class.newInstance().nextInt()));
            detalleFactura = new FacDetalleFactura();
            listaDetalleFacturas = new ArrayList<>();
            listaArticulos = new ArrayList<>();
        } catch (InstantiationException | IllegalAccessException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    public void procesarArticulo() {
        cabeceraFactura.setCabSubtotal(BigDecimal.ZERO);
        cabeceraFactura.setCabTotal(BigDecimal.ZERO);
        cabeceraFactura.setCabIva(BigDecimal.ZERO);
        Double subtotal = new Double(0);
        try {
            if (!listaDetalleFacturas.isEmpty()) {
                for (FacDetalleFactura item : listaDetalleFacturas) {
                    if (item.getDetCatidad() != null) {
                        FacArticulo art = articuloDao.find(item.getArtCodigo().getArtCodigo());
                        if (art != null) {
                            if (item.getDetCatidad() <= art.getArtCantidadIngresada().intValue()) {
                                if (item.getDetValorUnitario().doubleValue() > item.getArtCodigo().getArtValorUnitario().doubleValue()) {
                                    item.setDetSubtotal(item.getDetValorUnitario().multiply(BigDecimal.valueOf(item.getDetCatidad())));
                                    BigDecimal i = item.getDetValorUnitario().multiply(BigDecimal.valueOf(item.getDetCatidad()));
                                    subtotal += i.doubleValue();
                                } else {
                                    saveMessageErrorDetail("Artículo", "El Artículo " + item.getArtCodigo().getArtDescripcion() + "debe tener un valor unitario mayor " + item.getArtCodigo().getArtValorUnitario().doubleValue());
                                }
                            } else {
                                saveMessageErrorDetail("Artículo", "El Artículo " + item.getArtCodigo().getArtDescripcion() + " no tiene la cantidad suficiente para la venta");
                            }
                        } else {
                        }
                    } else {
                    }
                }
                cabeceraFactura.setCabSubtotal(BigDecimal.valueOf(Utils.redondearNumero(BigDecimal.valueOf(subtotal).divide(BigDecimal.valueOf(1.14), 2, BigDecimal.ROUND_HALF_UP), 2, true)));
                cabeceraFactura.setCabIva(BigDecimal.valueOf(Utils.redondearNumero(cabeceraFactura.getCabSubtotal().multiply(BigDecimal.valueOf(0.14)), 2, true)));
                cabeceraFactura.setCabTotal(cabeceraFactura.getCabSubtotal().add(cabeceraFactura.getCabIva()));
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    private void cargarCombos() {
        try {
            listaArticulos = articuloDao.findAll();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }

    }

    public void addArticulo(ActionEvent actionEvent) {
        listaArticulos = new ArrayList<>();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        try {
            if (cliente.getPacIdentificacin() != null) {
                cargarCombos();
                requestContext.execute("PF('dlListaArticulo').show()");
            } else {
                agregarDialogMensaje(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Factura", "Ingresar cliente", RequestContext.getCurrentInstance());
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    public void addCliente(ActionEvent actionEvent) {
        clienteNuevo = new CitPaciente();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        try {
            requestContext.execute("PF('dlListaCliente').show()");
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    private boolean estaEnSeleccion(Integer codigoArticulo) {
        for (int i = 0; i < listaDetalleFacturas.size(); i++) {
            if (listaDetalleFacturas.get(i).getArtCodigo().getArtCodigo().equals(codigoArticulo)) {
                return true;
            }
        }
        return false;
    }

    public void onRowSelect(SelectEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        detalleFactura = new FacDetalleFactura();
        detalleFactura.setArtCodigo(articuloSeleccionado);
        detalleFactura.setDetCatidad(Double.valueOf(0));
        detalleFactura.setDetValorUnitario(BigDecimal.ZERO);

        listaDetalleFacturas.add(detalleFactura);

        if (detalleFactura.getArtCodigo().getArtCodigo() != null) {
            requestContext.execute("PF('dlListaArticulo').hide()");
        } else {
            saveMessageWarnDetail("Artículo", "Seleccione un artículo");
        }
    }

    public void onRowUnselect(UnselectEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        detalleFactura = new FacDetalleFactura();
        detalleFactura.setArtCodigo(articuloSeleccionado);
        detalleFactura.setDetCatidad(Double.valueOf(0));
        detalleFactura.setDetValorUnitario(BigDecimal.ZERO);

        listaDetalleFacturas.add(detalleFactura);

        if (detalleFactura.getArtCodigo().getArtCodigo() != null) {
            requestContext.execute("PF('dlListaArticulo').hide()");
        } else {
            saveMessageWarnDetail("Artículo", "Seleccione un artículo");
        }
    }

    public void create(ActionEvent actionEvent) {
        try {
            if (cabeceraFactura.getCabTotal().intValue() > 0) {
                if (cabeceraFactura.getCabCodigo() == null) {
                    cabeceraFactura.setCliCodigo(cliente);
                    cabeceraFactura.setCabEstado(1);
                    int idc = cabeceraFacturaDao.save(cabeceraFactura);
                    if (idc > 0) {
                        for (FacDetalleFactura item : listaDetalleFacturas) {
                            item.setDetAplicaIva(1);
                            item.setCabCodigo(cabeceraFacturaDao.find(idc));
                            detalleFacturaDao.save(item);
                        }
                        saveMessageInfoDetail("Factura", "La factura se creo correctamente");
                        createKardex(listaDetalleFacturas);
                        inicializar(actionEvent);
                    } else {
                        saveMessageWarnDetail("Factura", "Error al crear la factura");
                    }
                }
            } else {
                saveMessageWarnDetail("Factura", "Ingrese detalles de la factura");
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    private void createKardex(List<FacDetalleFactura> listaDetalleFacturas) {
        try {
            if (!listaDetalleFacturas.isEmpty()) {
                for (FacDetalleFactura item : listaDetalleFacturas) {
                    articuloDetalle.setFacArticulo(articuloDao.find(item.getArtCodigo().getArtCodigo()));
                    articuloDetalle.setArtCantidadIgresada(null);
                    articuloDetalle.setArtCantidadSaliente(BigDecimal.valueOf(item.getDetCatidad()));
                    articuloDetalle.setArtValorUnitario(item.getDetValorUnitario());
                    articuloDetalle.setArtAutorizacion(item.getCabCodigo().getCabAutorizacion());
                    articuloDetalle.setArtSaldo(item.getArtCodigo().getArtCantidadIngresada().subtract(BigDecimal.valueOf(item.getDetCatidad())));
                    articuloDetalleDao.save(articuloDetalle);
                    item.getArtCodigo().setArtCantidadIngresada(articuloDetalle.getArtSaldo());
                    articuloDao.update(item.getArtCodigo());
                }
            }
        } catch (Exception e) {
        }
    }

    public void createCliente(ActionEvent actionEvent) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        try {
            if (clienteNuevo.getPacCodigo() == null) {
                if (!clienteDao.existePorCampo(clienteNuevo.getPacIdentificacin())) {
                    if (ValidadorCedulaRuc.isRucCedulaValido(clienteNuevo.getPacIdentificacin())) {
                        clienteNuevo.setPacEstado(1);
                        int idc = clienteDao.save(clienteNuevo);
                        if (idc > 0) {
                            saveMessageInfoDetail("Cliente", "Cliente " + clienteNuevo.getPacIdentificacin() + " creado correctamente");
                            requestContext.execute("PF('dlListaCliente').hide()");
                        }
                    } else {
                        saveMessageErrorDetail("Cliente", "La cédula o ruc es incorrecta");
                    }
                } else {
                    saveMessageErrorDetail("Cliente", "Cliente " + clienteNuevo.getPacIdentificacin() + " ya existe");
                }
            }

        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }

    }

    public void findCliente(ActionEvent actionEvent) {
        try {
            if (cliente.getPacIdentificacin() != null) {
                if (ValidadorCedulaRuc.isRucCedulaValido(cliente.getPacIdentificacin().trim())) {
                    cliente = clienteDao.find(cliente.getPacIdentificacin());
                    if (cliente != null) {
                        saveMessageInfoDetail("Cliente", "Cliente encontrado con exito");
                    } else {
                        cliente = new CitPaciente();
                        saveMessageWarnDetail("Cliente", "El cliente no existe");
                    }
                } else {
                    cliente = new CitPaciente();
                    saveMessageWarnDetail("Cliente", "El ruc o la cédula es incorrecta");
                }
            }
        } catch (Exception e) {
        }

    }

    public void edit(ActionEvent event) {

    }

    public void remove(ActionEvent event) {
        detalleFactura = new FacDetalleFactura();
        try {
            detalleFactura = (FacDetalleFactura) event.getComponent().getAttributes().get("objetoRemover");
            if (detalleFactura != null) {
                listaDetalleFacturas.remove(detalleFactura);
                procesarArticulo();
            }
        } catch (Exception e) {
        }

    }

    public CitPaciente getCliente() {
        return cliente;
    }

    public void setCliente(CitPaciente cliente) {
        this.cliente = cliente;
    }

    public FacCitaFactura getCabeceraFactura() {
        return cabeceraFactura;
    }

    public void setCabeceraFactura(FacCitaFactura cabeceraFactura) {
        this.cabeceraFactura = cabeceraFactura;
    }

    public List<FacDetalleFactura> getListaDetalleFacturas() {
        return listaDetalleFacturas;
    }

    public void setListaDetalleFacturas(List<FacDetalleFactura> listaDetalleFacturas) {
        this.listaDetalleFacturas = listaDetalleFacturas;
    }

    public List<FacArticulo> getListaArticulos() {
        return listaArticulos;
    }

    public void setListaArticulos(List<FacArticulo> listaArticulos) {
        this.listaArticulos = listaArticulos;
    }

    public ClienteDao getClienteDao() {
        return clienteDao;
    }

    public void setClienteDao(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    public FacArticulo getArticuloSeleccionado() {
        return articuloSeleccionado;
    }

    public void setArticuloSeleccionado(FacArticulo articuloSeleccionado) {
        this.articuloSeleccionado = articuloSeleccionado;
    }

    public CitPaciente getClienteNuevo() {
        return clienteNuevo;
    }

    public void setClienteNuevo(CitPaciente clienteNuevo) {
        this.clienteNuevo = clienteNuevo;
    }

    public List<FacCiudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<FacCiudad> ciudades) {
        this.ciudades = ciudades;
    }

    public Integer getCodigoCiudad() {
        return codigoCiudad;
    }

    public void setCodigoCiudad(Integer codigoCiudad) {
        this.codigoCiudad = codigoCiudad;
    }

    public CiudadDao getCiudadDAO() {
        return ciudadDAO;
    }

    public void setCiudadDAO(CiudadDao ciudadDAO) {
        this.ciudadDAO = ciudadDAO;
    }

}
