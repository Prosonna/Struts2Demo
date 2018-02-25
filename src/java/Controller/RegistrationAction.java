/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.*;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.tools.ws.wsdl.framework.DuplicateEntityException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

public class RegistrationAction extends ActionSupport implements ServletRequestAware{

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String repassword;
    private String address;
    private String age;
    private String message;
    private String submit;
    //file
    private File image;
    private String imageContentType;
    private String imageFileName;

    private Blob blob;
    private HttpServletRequest servletReqest;

    public Blob getBlob() {
        return blob;
    }

    public void setBlob(Blob blob) {
        this.blob = blob;
    }
    
    
    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

 
    

    private ArrayList<RegistrationAction> list;

    public ArrayList<RegistrationAction> getList() {
        return list;
    }

    public void setList(ArrayList<RegistrationAction> list) {
        this.list = list;
    }
    
    public String getSubmit() {
        return submit;
    }

    public void setSubmit(String submit) {
        this.submit = submit;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public void validate() {
        if (getFirstName().length() == 0) {
            addFieldError("firstName", "First Name Can't be empty");
        }
        if (getLastName().length() == 0) {
            addFieldError("lastName", "Last Name Can't be empty");
        }

        if (getPassword().length() == 0 || getRepassword().length() == 0) {
            addFieldError("password", "Password field can't be empty");
        } else if (!getPassword().equals(getRepassword())) {
            addFieldError("repassword", "Password doesn't macthed");
        }
        if (getEmail().length() != 0) {

            try {
                InternetAddress emaiAddress = new InternetAddress(getEmail());
                emaiAddress.validate();
            } catch (Exception e) {
                e.printStackTrace();
                addFieldError("email", "Not a valid email addrerss");
            }

        }

    }

    @Override
    public String execute() {

        if (getSubmit().equals("Sign up")) {
            try {
                Registration reg = new Registration();
                if (reg.insertintoRegistration(this)) {
                    message = "Registration Successful!!";
                    
                } else {
                    message = "Registration Unsuccessful!!";
                    
                }

            } catch (DuplicateEntityException e) {
                message = "This email address already has an account!";
            } catch (SQLException e) {
                message = e.toString();
            } catch (Exception e) {
                message = e.toString();
            }
            return INPUT;
        } else if(getSubmit().equals("Show Registration List")){
            
            fillInfo();
            return SUCCESS;
        } else if(getSubmit().equals("Show Report")){
            
            String path = servletReqest.getSession().getServletContext().getRealPath("report");
            
            try{
                JasperReport jsr = JasperCompileManager.compileReport(path+"/Report.jrxml");
                fillInfo();
                Map<String,Object> map = new HashMap<String, Object>();
                JasperPrint p = JasperFillManager.fillReport(jsr,map, new JRBeanCollectionDataSource(list));
                
                JasperExportManager.exportReportToPdfFile(p, path+"/report.pdf");
                
            }catch(Exception e){
                message = "Error in Jasper report. "+e.toString();
            }
            return "report";
        }

        return INPUT;
    }

    @Override
    public void setServletRequest(HttpServletRequest hsr) {
         this.servletReqest = hsr;
    }
    
    private void fillInfo(){
        try{
                Registration reg = new Registration();
                ResultSet s = reg.getRegistrationInfo();
                list = new ArrayList<>();
                int i =0;
                while(s.next()){
                    RegistrationAction regA = new RegistrationAction();
                    regA.firstName = s.getString("firstName");
                    regA.lastName = s.getString("lastName");
                    regA.address = s.getString("address");
                    regA.email = s.getString("email");
                    regA.blob = s.getBlob("photo");
                    regA.age = String.valueOf(s.getInt("age"));
                    regA.imageFileName = regA.firstName+i+".jpg";
                    String path = servletReqest.getSession().getServletContext().getRealPath("/");
                    File f = new File(path, regA.imageFileName);
                    i++;
                    
                    InputStream is = regA.blob.getBinaryStream();
                    OutputStream out = new FileOutputStream(f);
                    
                    byte[] buffer = new byte[4 * 1024];
                    int readByte;
                    while(((readByte = is.read(buffer)) != -1 )){
                        out.write(buffer,0,readByte);
                    }
                    
                    IOUtils.closeQuietly(is);
                    IOUtils.closeQuietly(out);
                    
                    
                    
                    list.add(regA);
                    
                }
                
            } catch(Exception e){
                
            }
    }

}
