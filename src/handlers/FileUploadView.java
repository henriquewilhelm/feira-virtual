package handlers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;

import models.Produto;

import org.primefaces.model.UploadedFile;
 
@ManagedBean
public class FileUploadView {
     
    private UploadedFile file;
 
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }    
    
    public String upload(ActionEvent event) throws IOException {  
    	Produto selected = (Produto) event.getComponent().getAttributes().get("selected");
    	
    	 if(file != null) {
           FacesMessage message = new FacesMessage("Sucesso", file.getFileName() + " upload concluido.");
           FacesContext.getCurrentInstance().addMessage(null, message);
    	 }
        FacesContext ctx = FacesContext.getCurrentInstance();  
  
        ServletContext servletContext = (ServletContext) ctx.getExternalContext().getContext();  
  
        //no contexto web, deverá existir uma pasta chamada temp  
        String path = servletContext.getRealPath("//resources//image//produtos//");  
  
        String arquivo = file.getFileName();  
  
        //local onde será gravado o arquivo no contexto web (o arquivo será renomeado para teste.txt)  
        //isto dentro da pasta (contexto)/temp  
        String fullPath = path +"\\"+ arquivo;  
  
        String contentType = getFile().getContentType();  
  
        ctx.getExternalContext().getApplicationMap().put("fileupload_type", contentType);  
        ctx.getExternalContext().getApplicationMap().put("fileupload_name", arquivo);  
  
        File file = new File(fullPath);  
        System.out.println("path "+fullPath);
        BufferedInputStream bufferedInputStream = null;  
        FileOutputStream fileOutputStream = null;  
        // Salvando o arquivo  
        try {  
            bufferedInputStream = new BufferedInputStream(getFile().getInputstream());  
            fileOutputStream = new FileOutputStream(file);  
            byte[] buffer = new byte[1024];  
            int count;  
            while ((count = bufferedInputStream.read(buffer)) > 0) {  
                fileOutputStream.write(buffer, 0, count);  
            }  
  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                bufferedInputStream.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
            try {  
                fileOutputStream.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        
        selected.setFoto("//resources//image//produtos//"+arquivo);
       
        return null;  
    } 
}
