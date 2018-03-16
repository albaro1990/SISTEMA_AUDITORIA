/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.auditoria.controller;

import com.sistema.auditoria.dao.CiudadDao;
import com.sistema.auditoria.dao.ClienteDao;
import com.sistema.auditoria.dao.UsuarioDao;
import com.sistema.auditoria.dao.impl.CitaDaoImpl;
import com.sistema.auditoria.dao.impl.CiudadDaoImpl;
import com.sistema.auditoria.dao.impl.ClienteDaoImpl;
import com.sistema.auditoria.dao.impl.EmpresaDaoImpl;
import com.sistema.auditoria.dao.impl.UsuarioDaoImpl;
import com.sistema.auditoria.entity.CitCita;
import com.sistema.auditoria.entity.AudEmpresa;
import com.sistema.auditoria.entity.CitPaciente;
import com.sistema.auditoria.entity.AudCiudad;
import com.sistema.auditoria.entity.AudUsuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sistema.auditoria.dao.CitaDao;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import com.sistema.auditoria.dao.EmpresaDao;
import com.sistema.auditoria.entity.AudDetEstadoFinan;
import com.sistema.auditoria.entity.AudEstadoFinanciero;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.Iterator;
import javax.faces.bean.ViewScoped;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.primefaces.event.FileUploadEvent;

@ManagedBean(name = "estadoFinancieroBean")
@ViewScoped
public class EstadoFinancieroBean extends GenericBean {
    
    private static final long serialVersionUID = 1L;
    private final Logger LOG = LoggerFactory.getLogger(CitaBean.class);
    
    private List<CitCita> listaCitas;
    
    private CitaDao citaDao = new CitaDaoImpl();
    private List<String> listaErrores; 
    private List<AudDetEstadoFinan> listaDetallesPantalla;
    private ScheduleModel lazyEventModel;
 
    private ScheduleEvent event = new DefaultScheduleEvent();
    
    private CitPaciente paciente;
    private CitPaciente clienteNuevo;

    private CitCita cita;
    private AudEmpresa especialidad;


    private List<AudUsuario> listaUsuMedicos;
    private List<AudCiudad> ciudades = new ArrayList<AudCiudad>();
    private List<AudEmpresa> empresas = new ArrayList<AudEmpresa>();
    private ClienteDao clienteDao = new ClienteDaoImpl();
    private CiudadDao ciudadDAO = new CiudadDaoImpl();
    private EmpresaDao empresaDAO = new EmpresaDaoImpl();
    private List<List<String>> dataList;
    private String tipoExtension;
    private String nombreArchivo;

    private UsuarioDao usuarioDao = new UsuarioDaoImpl();
    private Integer codigoCiudad;
    private Integer codigoEmp;
    private Integer codigoMedico;
    private Integer codigoPaciente;
    private Integer codigoCita;
    
 
     public EstadoFinancieroBean() {
         empresas = new ArrayList<AudEmpresa>();
         cargarDependencias();   
        
    }
    @PostConstruct
    public void init() {
  
    }  
    
    
    private void cargarDependencias() {
        try{
           
        empresas = empresaDAO.findAll();
        
        
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }
    
    @SuppressWarnings("rawtypes")
	public void handleFileUpload(FileUploadEvent event) {
		try {
			// Date currentDate = new Date(); PARA LA COMPARACION
			if (null == null) {
				// configuramos fecha de entrega
				// fechaEntregaString = sdf.format(fechaEntrega);

				dataList = new ArrayList<List<String>>();
				List<String> cellTempList = new ArrayList<String>();
				setTipoExtension("");
				
				

				if (event.getFile() != null) {
					System.out.println("Nombre Archivo Pedido Manual: "+event.getFile().getFileName());
					nombreArchivo = event.getFile().getFileName();
					if (event.getFile().getFileName().endsWith("xls")) {
						// LEER VALORES DE ARCHIVO XLS
						setTipoExtension("XLS");
						FileInputStream fileInputStream = (FileInputStream) event.getFile().getInputstream();
						Workbook workbook = Workbook.getWorkbook(fileInputStream);
						Sheet sheet = workbook.getSheet(0);
						Cell[] row = null;
						Cell cell = null;

						for (int rowIndex = 0; rowIndex < sheet.getRows(); rowIndex++) {
							row = sheet.getRow(rowIndex);
							for (int colIndex = 0; colIndex < sheet.getRow(rowIndex).length; colIndex++) {
								cell = row[colIndex];
								if (!cell.getContents().isEmpty()) {
									cellTempList.add(cell.getContents().toString());
								} else {
									cellTempList.add("");
								}
							}
							dataList.add(cellTempList);
							cellTempList = new ArrayList<String>();
						}
						fileInputStream.close();
						workbook.close();
					} else if (event.getFile().getFileName().endsWith("xlsx")) {
						// LEER VALORES DE ARCHIVO XLSX
						setTipoExtension("XLSX");
						byte[] b = event.getFile().getContents();
						ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(b);
						XSSFWorkbook wb = new XSSFWorkbook(byteArrayInputStream);
						XSSFSheet sheet = wb.getSheetAt(0);
						Iterator rowIterator = sheet.rowIterator();

						while (rowIterator.hasNext()) {
							XSSFRow xSSFRow = (XSSFRow) rowIterator.next();
							Iterator iterator = xSSFRow.cellIterator();

							while (iterator.hasNext()) {
								XSSFCell xSSFCell = (XSSFCell) iterator.next();
								if(xSSFCell!=null && (xSSFCell.getCellType()==0)){
									cellTempList.add(String.valueOf(Double.valueOf(xSSFCell.toString()).intValue()));
								}else{
									cellTempList.add(xSSFCell.toString());
								}
							}
							dataList.add(cellTempList);
							cellTempList = new ArrayList<String>();
						}
						wb.close();
					}
				} else {
					saveMessageFatalDetail("app.fatal", "carga.archivos.llenar.formato");
				}
			} else {
				saveMessageInfoDetail("app.fatal", "error.fecha.invalida");
			}
		} catch (Exception e) {
			saveMessageFatalDetail("app.fatal","common.fatal.general");
			LOG.error("handleFileUpload () {}", e);

		}
		validarInfoArchivo();

	}
    
   private void validarInfoArchivo() {
		try {
			if (dataList != null && !dataList.isEmpty()) {
				listaErrores = new ArrayList<String>();
				listaDetallesPantalla = new ArrayList<AudDetEstadoFinan>();
				
				// PROCESAR PEDIDOS DEL DATA LIST
				Integer contadorLinea=1;
				for (int i = 1; i < dataList.size(); i++) {
					AudDetEstadoFinan pedidoArchivoDet = new AudDetEstadoFinan();
					AudEstadoFinanciero pedidoArchivoCap = new AudEstadoFinanciero();
					for (int j = 0; j < dataList.get(i).size(); j++) {
						// Secuencial(nuemro de orden)
						if (dataList.get(i).get(1 - 1) != null) {
							try {
								//pedidoArchivoCap.setSecuencial(Integer.valueOf((int) Double.parseDouble(dataList.get(i).get(formatoPedidoManual.getSecuencial() - 1))));
							} catch (Exception e) {
								listaErrores.add("FILA: " + i + " --> EL SECUENCIAL TIENE UN DATO INVÁLIDO");
								//pedidoArchivoCap.setErrorCabecera(true);

								//saveMessageFatalDetail(getStringResourceBundle("app.fatal"), getStringResourceBundle("common.fatal.general"));
								LOG.error("validarInfoArchivo() {}", e);
								break;
							}
						} else {
							listaErrores.add("FILA: " + i + " --> EL SECUENCIAL NO EXISTE");
							//pedidoArchivoCap.setErrorCabecera(true);
							break;
						}
						// CodigoCliente
						if (dataList.get(i).get(2 - 1) != null) {
							try {
								
								//se valida si el cleinte existe o esta ectivo
								
							/*	if(clienteDir!=null && clienteDir.getEstado().equalsIgnoreCase(Constantes.ESTADO_ACTIVO)){
								   autoservicioCliente.setCodigoCliente(dataList.get(i).get(formatoPedidoManual.getCodigoCliente() - 1));
								   pedidoArchivoCap.setAutoservicioCliente(autoservicioCliente);
								}else if(clienteDir!=null && clienteDir.getEstado().equalsIgnoreCase(Constantes.ESTADO_INACTIVO)){
									 autoservicioCliente.setCodigoCliente(dataList.get(i).get(formatoPedidoManual.getCodigoCliente() - 1));
									   pedidoArchivoCap.setAutoservicioCliente(autoservicioCliente);
								}else{
									listaErrores.add("FILA: " + i + " --> EL CODIGO CLIENTE NO EXISTE");
									pedidoArchivoCap.setErrorCabecera(true);
									break;
								}*/
								
							} catch (Exception e) {
								//saveMessageFatalDetail(getStringResourceBundle("app.fatal"), getStringResourceBundle("common.fatal.general"));
								LOG.error("validarInfoArchivo() {}", e);
								break;
							}
						} else {
							listaErrores.add("FILA: " + i + " --> EL CAMPO CLIENTE NO ESTA LLENO");
							//pedidoArchivoCap.setErrorCabecera(true);
							break;
						}
						// CodigoItem
						if (dataList.get(i).get(3 - 1) != null) {
							try {
								//se valida si el item existe o esta activo
//								Producto item = productoService.findArticulo(dataList.get(i).get(formatoPedidoManual.getCodigoItem() - 1).trim().toUpperCase());
//								if(item!=null&&item.getEstado().equalsIgnoreCase(Constantes.ESTADO_ACT)){
//									pedidoArchivoDet.setItemCodigo(dataList.get(i).get(formatoPedidoManual.getCodigoItem() - 1).trim().toUpperCase());
//								}else if(item!=null&&item.getEstado().equalsIgnoreCase(Constantes.ESTADO_INA)){
//									pedidoArchivoDet.setItemCodigo(dataList.get(i).get(formatoPedidoManual.getCodigoItem() - 1).trim().toUpperCase());
//								}else {
//								listaErrores.add("FILA: " + i + " --> EL CODIGO DE ITEM NO EXISTE");
//								pedidoArchivoDet.setError(true);
//								break;
//								}
							} catch (Exception e) {
								//saveMessageFatalDetail(getStringResourceBundle("app.fatal"), getStringResourceBundle("common.fatal.general"));
								LOG.error("validarInfoArchivo() {}", e);
								break;
							}
						} else {
							listaErrores.add("FILA: " + i + " --> EL CAPO ITEM NO ESTA LLENO");
							//pedidoArchivoDet.setError(true);
							break;
						}
						// CodigoVendedor
//						if (dataList.get(i).get(formatoPedidoManual.getCodigoVendedor() - 1) != null) {
//							try {
//								pedidoArchivoCap.setCodigoVendedor(String.valueOf((int) Double.parseDouble(dataList.get(i).get(formatoPedidoManual.getCodigoVendedor() - 1).trim().toUpperCase())));
//							} catch (Exception e) {
//								listaErrores.add("FILA: " + i + " --> EL CODIGO DE VENDEDOR TIENE UN DATO INVÁLIDO");
//								pedidoArchivoCap.setErrorCabecera(true);
//
//								saveMessageFatalDetail(getStringResourceBundle("app.fatal"), getStringResourceBundle("common.fatal.general"));
//								LOG.error("validarInfoArchivo() {}", e);
//								break;
//							}
//						} else {
//							listaErrores.add("FILA: " + i + " --> EL CODIGO DE VENDEDOR NO EXISTE");
//							pedidoArchivoCap.setErrorCabecera(true);
//							break;
//						}
						// Numero Orden Compra
//						if (dataList.get(i).get(formatoPedidoManual.getObservacion() - 1) != null) {
//							try {
//								pedidoArchivoCap.setNumeroOrdenCompra(dataList.get(i).get(formatoPedidoManual.getObservacion() - 1));
//							} catch (Exception e) {
//								listaErrores.add("FILA: " + i + " --> EL NUMERO DE ORDEN TIENE UN DATO INVÁLIDO");
//								pedidoArchivoCap.setErrorCabecera(true);
//
//								saveMessageFatalDetail(getStringResourceBundle("app.fatal"), getStringResourceBundle("common.fatal.general"));
//								LOG.error("validarInfoArchivo() {}", e);
//								break;
//							}
//						} else {
//							listaErrores.add("FILA: " + i + " --> EL NUMERO DE ORDEN NO EXISTE");
//							pedidoArchivoCap.setErrorCabecera(true);
//							break;
//						}
						// Cantidad
//						if (dataList.get(i).get(formatoPedidoManual.getCantidad() - 1) != null) {
//							try {
//								pedidoArchivoDet.setItemCantidad(String.valueOf((int) Double.parseDouble(dataList.get(i).get(formatoPedidoManual.getCantidad() - 1))));
//							} catch (Exception e) {
//								listaErrores.add("FILA: " + i + " --> LA CANTIDAD TIENE UN DATO INVÁLIDO");
//								pedidoArchivoDet.setError(true);
//
//								saveMessageFatalDetail(getStringResourceBundle("app.fatal"), getStringResourceBundle("common.fatal.general"));
//								LOG.error("validarInfoArchivo() {}", e);
//								break;
//							}
//						} else {
//							listaErrores.add("FILA: " + i + " --> LA CANTIDAD NO EXISTE");
//							pedidoArchivoDet.setError(true);
//							break;
//						}
						// direccion
//						if (dataList.get(i).get(formatoPedidoManual.getDireccion() - 1) != null) {
//							try {
//								pedidoArchivoCap.setDireccionCliente(String.valueOf(dataList.get(i).get(formatoPedidoManual.getDireccion() - 1)));
//							} catch (Exception e) {
//								listaErrores.add("FILA: " + i + " --> LA DIRECCION DE CLIENTE TIENE UN DATO INVÁLIDO");
//								pedidoArchivoCap.setErrorCabecera(true);
//
//								saveMessageFatalDetail(getStringResourceBundle("app.fatal"), getStringResourceBundle("common.fatal.general"));
//								LOG.error("validarInfoArchivo() {}", e);
//								break;
//							}
//						} else {
//							listaErrores.add("FILA: " + i + " --> LA DIRECCION DE CLIENTE NO EXISTE");
//							pedidoArchivoCap.setErrorCabecera(true);
//							break;
//						}
						// tipoOrden
//						if (dataList.get(i).get(formatoPedidoManual.getTipoOrden() - 1) != null) {
//							try {
//								pedidoArchivoCap.setTipoOrden(String.valueOf(dataList.get(i).get(formatoPedidoManual.getTipoOrden() - 1)));
//							} catch (Exception e) {
//								listaErrores.add("FILA: " + i + " --> EL TIPO DE ORDEN TIENE UN DATO INVÁLIDO");
//								pedidoArchivoCap.setErrorCabecera(true);
//
//								saveMessageFatalDetail(getStringResourceBundle("app.fatal"), getStringResourceBundle("common.fatal.general"));
//								LOG.error("validarInfoArchivo() {}", e);
//								break;
//							}
//						} else {
//							listaErrores.add("FILA: " + i + " --> EL TIPO DE ORDEN NO EXISTE");
//							pedidoArchivoCap.setErrorCabecera(true);
//							break;
//						}
						// contrato
//						if (dataList.get(i).get(formatoPedidoManual.getContrato() - 1) != null && !dataList.get(i).get(formatoPedidoManual.getContrato() - 1).equalsIgnoreCase("")) {
//							try {
//								pedidoArchivoCap.setContratoVenta(String.valueOf(dataList.get(i).get(formatoPedidoManual.getContrato() - 1)));
//							} catch (Exception e) {
//								listaErrores.add("FILA: " + i + " --> EL CONTRATO TIENE UN DATO INVÁLIDO");
//								pedidoArchivoCap.setErrorCabecera(true);
//
//								saveMessageFatalDetail(getStringResourceBundle("app.fatal"), getStringResourceBundle("common.fatal.general"));
//								LOG.error("validarInfoArchivo() {}", e);
//								break;
//							}
//						} else {
//							if(pedidoArchivoCap.getTipoOrden()!=null && pedidoArchivoCap.getTipoOrden().trim().contains(Constantes.TIPO_PEDIDO_CONTRATO)){
//							listaErrores.add("FILA: " + i + " --> EL CONTRATO NO EXISTE");
//							pedidoArchivoCap.setErrorCabecera(true);
//							break;
//						  }
//						}
					 }
//					if (!pedidoArchivoCap.isErrorCabecera() && !pedidoArchivoDet.isError()) {
//						contadorLinea++;
//						// seteamos fecha entrega
//						pedidoArchivoCap.setFechaEntrega(fechaEntregaString);
//						// seteamos la cabecera en el detalle
//						pedidoArchivoDet.setPedidoCab(pedidoArchivoCap);
//						pedidoArchivoDet.setLineaDetalle(contadorLinea);
//						listaDetallesPantalla.add(pedidoArchivoDet);
//					}
//					System.out.println("Contador registro= " + contadotPruebReg++ );
				}

			}
		} catch (Exception e) {
			if(e.getMessage().contains("Index:") || e.getMessage().contains("Size:")){
				saveMessageFatalDetail("app.fatal", "FORMATO DE ARCHIVO INCORRECTO.");
			}else{
			saveMessageFatalDetail("app.fatal", "common.fatal.general");
			}
			LOG.error("validarInfoArchivo() {}", e);
		}
	}
    
    
        
        public Date getRandomDate(Date base) {
        Calendar date = Calendar.getInstance();
        date.setTime(base);
        date.add(Calendar.DATE, ((int) (Math.random()*30)) + 1);    //set random day of month
         
        return date.getTime();
    }
     
    public Date getInitialDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);
         
        return calendar.getTime();
    }
     
   
     
    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }
 
    private Calendar today() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
 
        return calendar;
    }
     
    private Date previousDay8Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) - 1);
        t.set(Calendar.HOUR, 8);
         
        return t.getTime();
    }
     
    private Date previousDay11Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) - 1);
        t.set(Calendar.HOUR, 11);
         
        return t.getTime();
    }
     
    private Date today1Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.HOUR, 1);
         
        return t.getTime();
    }
     
    private Date theDayAfter3Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 2);     
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.HOUR, 3);
         
        return t.getTime();
    }
 
    private Date today6Pm() {
        Calendar t = (Calendar) today().clone(); 
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.HOUR, 6);
         
        return t.getTime();
    }
     
    private Date nextDay9Am() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.AM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 1);
        t.set(Calendar.HOUR, 9);
         
        return t.getTime();
    }
     
    private Date nextDay11Am() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.AM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 1);
        t.set(Calendar.HOUR, 11);
         
        return t.getTime();
    }
     
    private Date fourDaysLater3pm() {
        Calendar t = (Calendar) today().clone(); 
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 4);
        t.set(Calendar.HOUR, 3);
         
        return t.getTime();
    }
     
    public ScheduleEvent getEvent() {
        return event;
    }
 
    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }     
    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
        setCita((CitCita) event.getData());
        codigoPaciente = getCita().getCliCodigo().getPacCodigo().intValue();
        try {
           paciente =  clienteDao.findXId(codigoPaciente); 
        } catch (Exception e) {
        }
        
    }
    
     public void onEventIngresar(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
        FacesContext context = FacesContext.getCurrentInstance();
        HistoriaClinicaBean historiaBean= context.getApplication().evaluateExpressionGet(context, "#{historiaClinicaBean}", HistoriaClinicaBean.class);
        historiaBean.setCita((CitCita) event.getData());
    }
     
    public void onDateSelect(SelectEvent selectEvent) {
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject(), (CitCita) selectEvent.getObject());
    }
     
    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
         
        addMessage(message);
    }
     
    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
         
        addMessage(message);
    }
     
    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public void edit(ActionEvent event) {
        cita = new CitCita();
        especialidad = new AudEmpresa();
        try {
            cita = (CitCita) event.getComponent().getAttributes().get("objetoEditar");
            especialidad = cita.getUsuario().getCitEspecialidad();
            codigoEmp = cita.getUsuario().getCitEspecialidad().getEmpCodigo().intValue();
            codigoMedico = cita.getUsuario().getUsuCodigo().intValue();
            paciente = clienteDao.findXId(cita.getCliCodigo().getPacCodigo().intValue());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }

    }

    public void remove(ActionEvent event) {
        cita = new CitCita();
        try {
            cita = (CitCita) event.getComponent().getAttributes().get("objetoRemover");
           int ndel = citaDao.cacelar(cita.getCitCodigo().intValue());
//            if (detalleFactura != null) {
//              //  listaDetalleFacturas.remove(detalleFactura);
//                procesarArticulo();
//            }
        } catch (Exception e) {
        }

    }

    public List<CitCita> getListaCitas() {
        return listaCitas;
    }

    public void setListaCitas(List<CitCita> listaCitas) {
        this.listaCitas = listaCitas;
    }

    public CitaDao getCitaDao() {
        return citaDao;
    }

    public void setCitaDao(CitaDao citaDao) {
        this.citaDao = citaDao;
    }

    public CitPaciente getPaciente() {
        return paciente;
    }

    public void setPaciente(CitPaciente paciente) {
        this.paciente = paciente;
    }

    public CitPaciente getClienteNuevo() {
        return clienteNuevo;
    }

    public void setClienteNuevo(CitPaciente clienteNuevo) {
        this.clienteNuevo = clienteNuevo;
    }

    public CitCita getCita() {
        return cita;
    }

    public void setCita(CitCita cita) {
        this.cita = cita;
    }

    public AudEmpresa getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(AudEmpresa especialidad) {
        this.especialidad = especialidad;
    }

    public List<AudUsuario> getListaUsuMedicos() {
        return listaUsuMedicos;
    }

    public void setListaUsuMedicos(List<AudUsuario> listaUsuMedicos) {
        this.listaUsuMedicos = listaUsuMedicos;
    }

    public List<AudCiudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<AudCiudad> ciudades) {
        this.ciudades = ciudades;
    }

    public List<AudEmpresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<AudEmpresa> empresas) {
        this.empresas = empresas;
    }

    public ClienteDao getClienteDao() {
        return clienteDao;
    }

    public void setClienteDao(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    public CiudadDao getCiudadDAO() {
        return ciudadDAO;
    }

    public void setCiudadDAO(CiudadDao ciudadDAO) {
        this.ciudadDAO = ciudadDAO;
    }

    public UsuarioDao getUsuarioDao() {
        return usuarioDao;
    }

    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    public Integer getCodigoCiudad() {
        return codigoCiudad;
    }

    public void setCodigoCiudad(Integer codigoCiudad) {
        this.codigoCiudad = codigoCiudad;
    }

    public Integer getCodigoEmp() {
        return codigoEmp;
    }

    public void setCodigoEsp(Integer codigoEmp) {
        this.codigoEmp = codigoEmp;
    }

    public Integer getCodigoMedico() {
        return codigoMedico;
    }

    public void setCodigoMedico(Integer codigoMedico) {
        this.codigoMedico = codigoMedico;
    }

    public Integer getCodigoPaciente() {
        return codigoPaciente;
    }

    public void setCodigoPaciente(Integer codigoPaciente) {
        this.codigoPaciente = codigoPaciente;
    }

    public Integer getCodigoCita() {
        return codigoCita;
    }

    public void setCodigoCita(Integer codigoCita) {
        this.codigoCita = codigoCita;
    }

    public List<List<String>> getDataList() {
        return dataList;
    }

    public void setDataList(List<List<String>> dataList) {
        this.dataList = dataList;
    }

    public String getTipoExtension() {
        return tipoExtension;
    }

    public void setTipoExtension(String tipoExtension) {
        this.tipoExtension = tipoExtension;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public List<String> getListaErrores() {
        return listaErrores;
    }

    public void setListaErrores(List<String> listaErrores) {
        this.listaErrores = listaErrores;
    }

    public List<AudDetEstadoFinan> getListaDetallesPantalla() {
        return listaDetallesPantalla;
    }

    public void setListaDetallesPantalla(List<AudDetEstadoFinan> listaDetallesPantalla) {
        this.listaDetallesPantalla = listaDetallesPantalla;
    }
    
    
}
