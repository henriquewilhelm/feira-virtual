package service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;

import util.JPA;
import models.UserTipo;
import models.Usuario;
	 
@ManagedBean(name = "usuarioService")
@ApplicationScoped
public class UsuarioService implements Serializable{
	     
	/**
	 * 
	 */
	private static final long serialVersionUID = 7506098416499560411L;

	private Integer contador = 0;
	
	private final static String[] nomes;
	
	private final static String[] senhas;
	
	private final static String[] emails;
    
    private final static String[] endereco;
     
    private final static String[] bairro;
    
    private final static String[] cidade;
    
    private final static String[] telefone;
    
    private final static String[] obs;
    
    static {
        nomes = new String[6];
        nomes[0] = "Fulano De Tal";
        nomes[1] = "Fulaninho da Silva";
        nomes[2] = "Ciclano Beltrano";
        nomes[3] = "Leandro Velasquez";
        nomes[4] = "Henrique Pereira";
        nomes[5] = "Gustavo Felipe";
        
        emails = new String[6];
        emails[0] = "borim@hotmail.com";
        emails[1] = "wilhelm@gmail.com";
        emails[2] = "tst@tst.com.br";
        emails[3] = "leandro@teste.br";
        emails[4] = "henrique@tst.com.br";
        emails[5] = "felipe@teste.com";
        
        senhas = new String[6];
        senhas[0] = "123456";
        
        endereco = new String[6];
        endereco[0] = "Rua Coral 205";
        endereco[1] = "Rua Moras 211";
        endereco[2] = "Rua Pinguem Sem Numero";
        endereco[3] = "Av. Pequeno Principe 2005";
        endereco[4] = "SC 401 5000";
        endereco[5] = "Rua da Cabela 1307";
        
        bairro = new String[6];
        bairro[0] = "Campeche";
        bairro[1] = "Areias do Campeche";
        bairro[2] = "Rio Tavres";
        bairro[3] = "Costeira";
        bairro[4] = "Centro";
        bairro[5] = "Itacurubi";
        
        cidade = new String[3];
        cidade[0] = "Florianopolis";
        cidade[1] = "Sao Jose";
        cidade[2] = "Palhoca";
        
        telefone = new String[6];
        telefone[0] = "4833331111";
        telefone[1] = "4833332222";
        telefone[2] = "4833333333";
        telefone[3] = "4833334444";
        telefone[4] = "4833335555";
        telefone[5] = "4833336666";
        
        obs = new String[6];
        obs[0] = "Em frente ao Mercado da Nina";
        obs[1] = "No quebramola a esquerda";
        obs[2] = "Cenoura sem folhas";
        obs[3] = "Tomate cereja";
        obs[4] = "Entrega depois das 18h";
        obs[5] = "Duas cestas";
    }
    
        public List<Usuario> createUsuarios() {
	        List<Usuario> list = new ArrayList<Usuario>(6);
	        
	        Usuario usuario = new Usuario("lnborim@hotmail.com", getRandomSenha(), "Leandro Borim", getRandomEndereco(), 
        			getRandomCidade(), getRandomBairro(), getRandomTelefone(), UserTipo.ADMIN);
        	EntityManager em = JPA.getEM();
    		em.getTransaction().begin();
    		em.persist(usuario);
    		em.getTransaction().commit();

        	list.add(usuario);

        	usuario = new Usuario("admin@admin.com", getRandomSenha(), "Administrador", getRandomEndereco(), 
        			getRandomCidade(), getRandomBairro(), getRandomTelefone(), UserTipo.ADMIN);
        	em = JPA.getEM();
    		em.getTransaction().begin();
    		em.persist(usuario);
    		em.getTransaction().commit();
    		
    		list.add(usuario);
    		
    		usuario = new Usuario("teste@teste.com.br", getRandomSenha(), "Teste Tst", getRandomEndereco(), 
        			getRandomCidade(), getRandomBairro(), getRandomTelefone(), UserTipo.USER);
        	em = JPA.getEM();
    		em.getTransaction().begin();
    		em.persist(usuario);
    		em.getTransaction().commit();

        	list.add(usuario);
    		
        	
	        for(int i = 0 ; i < 6 ; i++) {
	        	usuario = new Usuario(getRandomEmail(), getRandomSenha(), getRandomNome(), getRandomEndereco(), 
	        			getRandomCidade(), getRandomBairro(), getRandomTelefone(), getRandomTipo());
	        	em = JPA.getEM();
	    		em.getTransaction().begin();
	    		em.persist(usuario);
	    		em.getTransaction().commit();
	
	        	list.add(usuario);
	        }
	         
	        return list;
	    }
	     
        private String getRandomEmail() {
	        return emails[contador++];
	    }
	     
        private String getRandomSenha() {
	        return senhas[0];
	    }
        
	    private String getRandomNome() {
	        return nomes[(int) (Math.random() * 6)];
	    }
	     
	    private String getRandomEndereco() {
	        return endereco[(int) (Math.random() * 6)];
	    }
	    
	    private String getRandomBairro() {
	        return bairro[(int) (Math.random() * 6)];
	    }
	     
	    private String getRandomCidade() {
	        return cidade[(int) (Math.random() * 3)];
	    }
	    
	    private long getRandomTelefone() {
	        return Long.parseLong(telefone[(int) (Math.random() * 6)]);
	    }
	    
	    private UserTipo getRandomTipo(){
	    	return UserTipo.USER;
	    }
}
