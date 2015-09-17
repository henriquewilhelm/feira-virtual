package service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;

import util.JPA;
import models.Bairro;
import models.Cidade;
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
     
    private final static Bairro[] bairros;
    
    private final static Cidade[] cidades;
    
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
        endereco[0] = "Rua Coral";
        endereco[1] = "Rua Moras ";
        endereco[2] = "Rua Pinguem";
        endereco[3] = "Av. Pequeno Principe";
        endereco[4] = "SC 401";
        endereco[5] = "Rua da Cabela";
        
        cidades = new Cidade[2];
        cidades[0] = new Cidade("Florianopolis");
        cidades[1] = new Cidade("Sao Jose");
        
        bairros = new Bairro[6];
        bairros[0] = new Bairro("Campeche",cidades[0]);
        bairros[1] = new Bairro("Rio Tavares",cidades[0]);
        bairros[2] = new Bairro("Costeira",cidades[0]);
        bairros[3] = new Bairro("Campinas",cidades[1]);
        bairros[4] = new Bairro("Barreiros",cidades[1]);
        bairros[5] = new Bairro("Centro",cidades[1]);
        
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
	      
	        EntityManager em = JPA.getEM();
	        em = JPA.getEM();
    		em.getTransaction().begin();
    		em.persist(cidades[0]);
    		em.persist(cidades[1]);
    		for(int i = 0 ; i < 6 ; i++) 
    			em.persist(bairros[i]);
    		em.getTransaction().commit();
	        
	        
	        Usuario usuario = new Usuario("lnborim@hotmail.com", getRandomSenha(), "Leandro Borim", getRandomEndereco(), (int) (Math.random() * 600), "",
	        		cidades[0], bairros[1], getRandomTelefone(), UserTipo.ADMIN);
            em = JPA.getEM();
    		em.getTransaction().begin();
    		em.persist(usuario);
    		em.getTransaction().commit();

        	list.add(usuario);

        	usuario = new Usuario("admin@admin.com", getRandomSenha(), "Administrador", getRandomEndereco(), (int) (Math.random() * 600), "", 
        			cidades[0], bairros[0], getRandomTelefone(), UserTipo.ADMIN);
        	em = JPA.getEM();
    		em.getTransaction().begin();
    		em.persist(usuario);
    		em.getTransaction().commit();
    		
    		list.add(usuario);
    		
    		usuario = new Usuario("teste@teste.com.br", getRandomSenha(), "Teste Tst", getRandomEndereco(), (int) (Math.random() * 600), "", 
    				cidades[1], bairros[4], getRandomTelefone(), UserTipo.USER);
        	em = JPA.getEM();
    		em.getTransaction().begin();
    		em.persist(usuario);
    		em.getTransaction().commit();

        	list.add(usuario);
    		
        	
	        for(int i = 0 ; i < 3 ; i++) {
	        	usuario = new Usuario(getRandomEmail(), getRandomSenha(), getRandomNome(), getRandomEndereco(), (int) (Math.random() * 600), "", 
	        			cidades[0], bairros[i], getRandomTelefone(), getRandomTipo());
	        	em = JPA.getEM();
	    		em.getTransaction().begin();
	    		em.persist(usuario);
	    		em.getTransaction().commit();
	
	        	list.add(usuario);
	        }
	        
	        for(int i = 3 ; i < 6 ; i++) {
	        	usuario = new Usuario(getRandomEmail(), getRandomSenha(), getRandomNome(), getRandomEndereco(), (int) (Math.random() * 600), "", 
	        			cidades[1], bairros[i], getRandomTelefone(), getRandomTipo());
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
	    
	    private long getRandomTelefone() {
	        return Long.parseLong(telefone[(int) (Math.random() * 6)]);
	    }
	    
	    private UserTipo getRandomTipo(){
	    	return UserTipo.USER;
	    }
}
