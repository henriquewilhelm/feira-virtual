package handlers;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.io.File;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import models.Pedido;
import models.Produto;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;

import service.PedidoService;
import service.ProdutoService;
 
@ManagedBean(name = "dataExporterView")
@SessionScoped
public class DataExporterView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4595625100922622983L;

	private List<Pedido> pedidos;
	
    @ManagedProperty("#{pedidoService}")
    private PedidoService pedidoService;
     
    @ManagedProperty("#{produtoService}")
    private ProdutoService produtoService;
    
    @PostConstruct
    public void init() {
    	produtoService.createProdutos();
    	pedidos = pedidoService.createPedidos(100);
    }
    /*
    public DataExporterView() {
    	service = new PedidoService();
    	init();
	}*/
    
	public List<Pedido> getPedidos() {
        return pedidos;
    }
 
	public void setPedidoService(PedidoService service) {
	        this.pedidoService = service;
	}
	
    public void setProdutoService(ProdutoService service) {
        this.produtoService = service;
    }
    /*
    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);
         
        HSSFCellStyle cellStyle = wb.createCellStyle();  
        cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
         
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            HSSFCell cell = header.getCell(i);
             
            cell.setCellStyle(cellStyle);
        }
    }
     
    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);
 
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String logo = servletContext.getRealPath("") + File.separator + "resources"  + File.separator + "image" + File.separator + "pdf.png";
         
        pdf.add(Image.getInstance(logo));
    }
    */
}

