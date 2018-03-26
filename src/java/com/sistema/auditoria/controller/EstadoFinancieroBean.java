/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.auditoria.controller;

import com.sistema.auditoria.dao.ClienteDao;
import com.sistema.auditoria.dao.UsuarioDao;
import com.sistema.auditoria.dao.impl.ClienteDaoImpl;
import com.sistema.auditoria.dao.impl.EmpresaDaoImpl;
import com.sistema.auditoria.dao.impl.UsuarioDaoImpl;
import com.sistema.auditoria.entity.CitCita;
import com.sistema.auditoria.entity.AudEmpresa;
import com.sistema.auditoria.entity.CitPaciente;
import com.sistema.auditoria.entity.AudUsuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import com.sistema.auditoria.dao.EmpresaDao;
import com.sistema.auditoria.dao.EstructuraAsignacionDao;
import com.sistema.auditoria.dao.EstructuraAsignacionEmpresaDao;
import com.sistema.auditoria.dao.PlandeCuentasDao;
import com.sistema.auditoria.dao.impl.EstructuraAsignacionDaoImpl;
import com.sistema.auditoria.dao.impl.EstructuraAsignacionEmpresaDaoImpl;
import com.sistema.auditoria.dao.impl.PlandeCuentasDaoImpl;
import com.sistema.auditoria.dto.AudEstadoFinancieroDTO;
import com.sistema.auditoria.entity.AudAsignarEstructuraEmpresa;
import com.sistema.auditoria.entity.AudDetEstadoFinan;
import com.sistema.auditoria.entity.AudEstructuraAsignacion;
import com.sistema.auditoria.entity.AudPlanCuentas;
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
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@ManagedBean(name = "estadoFinancieroBean")
@ViewScoped
public class EstadoFinancieroBean extends GenericBean {
    
    private static final long serialVersionUID = 1L;
    private final Logger LOG = LoggerFactory.getLogger(EstadoFinancieroBean.class);
  

    private String[] selectedColumnas;
    
    
    private CitPaciente paciente;
    private CitPaciente clienteNuevo;
    private CitCita cita;
    private AudEmpresa especialidad;
    private TreeNode nodoTreView;

    private List<AudEstadoFinancieroDTO> listaDetallesPantalla;
    private List<AudUsuario> listaUsuMedicos;
    private List<AudEmpresa> empresas = new ArrayList<AudEmpresa>();
    List<AudEstructuraAsignacion> listEstructuraAsig = new ArrayList<AudEstructuraAsignacion>();
    
    private List<List<String>> dataList;
    private List<String> listaErrores; 
    private List<String> columnas;
    
    private ClienteDao clienteDao = new ClienteDaoImpl();
    private EmpresaDao empresaDAO = new EmpresaDaoImpl();
    private UsuarioDao usuarioDao = new UsuarioDaoImpl();
    private EstructuraAsignacionDao estructuraAsignacionDAO = new EstructuraAsignacionDaoImpl();
    private PlandeCuentasDao plandeCuentasDao= new PlandeCuentasDaoImpl();
    private AudEstadoFinancieroDTO ctaSeleccionado = new AudEstadoFinancieroDTO();
    private EstructuraAsignacionEmpresaDao estructuraAsignacionEmpresaDao = new EstructuraAsignacionEmpresaDaoImpl();
    
    private String tipoExtension;
    private String nombreArchivo;
    
    private Integer codigoEmp;
    private Integer estrCodigo;

     
     public EstadoFinancieroBean() {
         empresas = new ArrayList<AudEmpresa>();
         listEstructuraAsig = new ArrayList<AudEstructuraAsignacion>();
         nodoTreView = new DefaultTreeNode("Root",null);
         cargarDependencias();   
        
    }
    @PostConstruct
    public void init() {
        
    }  
    
    
    private void cargarDependencias() {
        try{
           
        empresas = empresaDAO.findAll();
        listEstructuraAsig = estructuraAsignacionDAO.findAll();
        
        
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }
    
     public void asignarEstructura(ActionEvent event) {
        ctaSeleccionado = (AudEstadoFinancieroDTO) event.getComponent().getAttributes().get("objetoAsignar");
    }
    
     public void getCodEmpresa(){
         setCodigoEmp(codigoEmp);
     }
    @SuppressWarnings("rawtypes")
	public void handleFileUpload(FileUploadEvent event) {
		try {
			// Date currentDate = new Date(); PARA LA COMPARACION
                        System.out.print(": " + codigoEmp);
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
                    System.out.print("cod empresa: "+codigoEmp);
			if (dataList != null && !dataList.isEmpty()) {
				
				listaDetallesPantalla = new ArrayList<AudEstadoFinancieroDTO>();
				
				// PROCESAR PEDIDOS DEL DATA LIST
				Integer contadorLinea=1;
				for (int i = 1; i < dataList.size(); i++) {
					AudDetEstadoFinan pedidoArchivoDet = new AudDetEstadoFinan();
					AudEstadoFinancieroDTO pedidoArchivoCap = new AudEstadoFinancieroDTO();
						// Secuencial(nuemro de orden)
						if (dataList.get(i).get(1) != null) {
							try {
								pedidoArchivoCap.setNumeroCuenta(dataList.get(i).get(1));
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
						if (dataList.get(i).get(2) != null) {
							try {
								pedidoArchivoCap.setDescCuenta(dataList.get(i).get(2));
								//se valida si el cleinte existe o esta ectivo
								
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
						if (dataList.get(i).get(3) != null) {
							try {
								//se valida si el item existe o esta activo
                                                                pedidoArchivoCap.setSaldoInicial(Double.valueOf(dataList.get(i).get(3)));
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
						if (dataList.get(i).get(4) != null) {
							try {
								pedidoArchivoCap.setDebe(Double.valueOf(dataList.get(i).get(4)));
							} catch (Exception e) {
								listaErrores.add("FILA: " + i + " --> EL CODIGO DE VENDEDOR TIENE UN DATO INVÁLIDO");
								//pedidoArchivoCap.setErrorCabecera(true);

								//saveMessageFatalDetail(getStringResourceBundle("app.fatal"), getStringResourceBundle("common.fatal.general"));
								LOG.error("validarInfoArchivo() {}", e);
								break;
							}
						} else {
							listaErrores.add("FILA: " + i + " --> EL CODIGO DE VENDEDOR NO EXISTE");
							//pedidoArchivoCap.setErrorCabecera(true);
							break;
						}
						// Numero Orden Compra
						if (dataList.get(i).get(5) != null) {
							try {
								pedidoArchivoCap.setHaber(Double.valueOf(dataList.get(i).get(5)));
							} catch (Exception e) {
								listaErrores.add("FILA: " + i + " --> EL NUMERO DE ORDEN TIENE UN DATO INVÁLIDO");
								//pedidoArchivoCap.setErrorCabecera(true);

								//saveMessageFatalDetail(getStringResourceBundle("app.fatal"), getStringResourceBundle("common.fatal.general"));
								LOG.error("validarInfoArchivo() {}", e);
								break;
							}
						} else {
							listaErrores.add("FILA: " + i + " --> EL NUMERO DE ORDEN NO EXISTE");
							//pedidoArchivoCap.setErrorCabecera(true);
							break;
						}
					if(pedidoArchivoCap!=null&&pedidoArchivoCap.getNumeroCuenta()!=null) {
						listaDetallesPantalla.add(pedidoArchivoCap);
					}
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
    
public void crearEstructuraAsignacion(ActionEvent event){
    try {
        AudPlanCuentas planSave = new AudPlanCuentas();
        AudAsignarEstructuraEmpresa audAsignarEstructuraEmpresa = new AudAsignarEstructuraEmpresa();
        planSave.setNumeroCta(ctaSeleccionado.getNumeroCuenta()); 
        planSave.setDescCta(ctaSeleccionado.getDescCuenta());
        planSave.setEmpresa(empresaDAO.find(codigoEmp));
        planSave.setEstado(1);
        Integer codigoCta = plandeCuentasDao.save(planSave);
        planSave.setCodPlanCta(Long.valueOf(codigoCta));
        audAsignarEstructuraEmpresa.setCodigoEmpresa(Long.valueOf(codigoEmp));
        audAsignarEstructuraEmpresa.setDescCta(ctaSeleccionado.getDescCuenta());
        audAsignarEstructuraEmpresa.setEstructuraAsig(estructuraAsignacionDAO.find(estrCodigo));
        audAsignarEstructuraEmpresa.setPlanCuentas(planSave);
        estructuraAsignacionEmpresaDao.save(audAsignarEstructuraEmpresa);
        List<AudEstructuraAsignacion> listEstructuraAsignada = estructuraAsignacionEmpresaDao.findByEmp(codigoEmp);
        TreeNode nodo1 = new DefaultTreeNode("",null);
        TreeNode node00 = new DefaultTreeNode("",null);
        new DefaultTreeNode("Root",null);
        Integer cont=0;
        if(listEstructuraAsignada!=null&&listEstructuraAsignada.size()>0){
            for (AudEstructuraAsignacion audEstructuraAsignacion : listEstructuraAsignada) {
                 nodo1 = new DefaultTreeNode(audEstructuraAsignacion.getEstrDescripcion(), nodoTreView);   
                List<AudAsignarEstructuraEmpresa> listAsig= estructuraAsignacionEmpresaDao.findByEmpYEst(codigoEmp, audEstructuraAsignacion.getEstrCodigo());
                for (AudAsignarEstructuraEmpresa audAsignarEstructuraEmpresa1 : listAsig) {
                     if(Long.valueOf(estrCodigo)==audEstructuraAsignacion.getEstrCodigo()){
                        node00 = new DefaultTreeNode(ctaSeleccionado.getDescCuenta(), nodo1);
                       }
                }
              
            }
        }
        cont++;
        
        
        listaDetallesPantalla.remove(ctaSeleccionado);
    } catch (Exception e) {
    }
}
     
    public Date getInitialDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);
         
        return calendar.getTime();
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
          
            
            paciente = clienteDao.findXId(cita.getCliCodigo().getPacCodigo().intValue());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }

    }

    public void remove(ActionEvent event) {
        cita = new CitCita();
        try {
            cita = (CitCita) event.getComponent().getAttributes().get("objetoRemover");
           
//            if (detalleFactura != null) {
//              //  listaDetalleFacturas.remove(detalleFactura);
//                procesarArticulo();
//            }
        } catch (Exception e) {
        }

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

    public UsuarioDao getUsuarioDao() {
        return usuarioDao;
    }

    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
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

    public List<AudEstadoFinancieroDTO> getListaDetallesPantalla() {
        return listaDetallesPantalla;
    }

    public void setListaDetallesPantalla(List<AudEstadoFinancieroDTO> listaDetallesPantalla) {
        this.listaDetallesPantalla = listaDetallesPantalla;
    }

    public List<String> getColumnas() {
        return columnas;
    }

    public void setColumnas(List<String> columnas) {
        this.columnas = columnas;
    }

    public String[] getSelectedColumnas() {
        return selectedColumnas;
    }

    public void setSelectedColumnas(String[] selectedColumnas) {
        this.selectedColumnas = selectedColumnas;
    }

    public List<AudEstructuraAsignacion> getListEstructuraAsig() {
        return listEstructuraAsig;
    }

    public void setListEstructuraAsig(List<AudEstructuraAsignacion> listEstructuraAsig) {
        this.listEstructuraAsig = listEstructuraAsig;
    }

    public Integer getEstrCodigo() {
        return estrCodigo;
    }

    public void setEstrCodigo(Integer estrCodigo) {
        this.estrCodigo = estrCodigo;
    }

    public Integer getCodigoEmp() {
        return codigoEmp;
    }

    public void setCodigoEmp(Integer codigoEmp) {
        this.codigoEmp = codigoEmp;
    }

    public EmpresaDao getEmpresaDAO() {
        return empresaDAO;
    }

    public void setEmpresaDAO(EmpresaDao empresaDAO) {
        this.empresaDAO = empresaDAO;
    }

    public EstructuraAsignacionDao getEstructuraAsignacionDAO() {
        return estructuraAsignacionDAO;
    }

    public void setEstructuraAsignacionDAO(EstructuraAsignacionDao estructuraAsignacionDAO) {
        this.estructuraAsignacionDAO = estructuraAsignacionDAO;
    }

    public TreeNode getNodoTreView() {
        return nodoTreView;
    }

    public void setNodoTreView(TreeNode nodoTreView) {
        this.nodoTreView = nodoTreView;
    }
    
    
}
