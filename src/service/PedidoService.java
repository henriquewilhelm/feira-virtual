package service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;

import util.JPA;
import models.Item;
import models.Pedido;
import models.Usuario;
	 
@ManagedBean(name = "pedidoService")
@ApplicationScoped
public class PedidoService implements Serializable{
		
		private ItemService itemService;
		private UsuarioService usuarioService;
		List<Usuario> listUsuario;
		/**
		 * 
		 */
		private static final long serialVersionUID = -3418441511903269362L;
		private final static String[] obs;
		private Double total = 0d;
		
		static {  
			
        obs = new String[6];
        obs[0] = "Em frente ao Mercado da Nina";
        obs[1] = "No quebramola a esquerda";
        obs[2] = "Cenoura sem folhas";
        obs[3] = "Tomate cereja";
        obs[4] = "Entrega depois das 18h";
        obs[5] = "Duas cestas";
		 }
		
	    public List<Pedido> createPedidos(int size) {
	    	usuarioService = new UsuarioService();
	    	listUsuario = usuarioService.createUsuarios();
	    	
	        List<Pedido> list = new ArrayList<Pedido>(size);
	        for(int i = 0 ; i < size ; i++) {
	        	Pedido pedido = new Pedido(Calendar.getInstance().getTime(), getRandomUsuario(), getRandomObs(), getRandomListItens(), total);
	        	EntityManager em = JPA.getEM();
	    		em.getTransaction().begin();
	    		em.persist(pedido);
	    		em.getTransaction().commit();
	
	        	list.add(pedido);
	        }
	         
	        return list;
	    }
	 
	    public List<Item> getRandomListItens() {
        	total = 0d;
	    	itemService = new ItemService();
	    	List<Item> listItem = itemService.createItens((int) (Math.random() * 5)); 
	    	for (int i=0; i< listItem.size(); i++){
	    		total = total + listItem.get(i).getTotal();
	    		
	    		EntityManager em = JPA.getEM();
	    		em.getTransaction().begin();
	    		em.persist(listItem.get(i));
	    		em.getTransaction().commit();
	    	}
	    	
	    	return listItem;
	    }
	    
	    private Usuario getRandomUsuario(){
	    	return listUsuario.get( (int) (Math.random() * listUsuario.size()) );
	    }

	    private String getRandomObs() {
	        return obs[(int) (Math.random() * 6)];
	    }
}
