package handlers;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;

import models.Bairro;
import models.Cidade;
import models.Item;
import models.Mail;
import models.Produto;
import models.Pedido;
import models.UserTipo;
import models.Usuario;
import util.JPA;

@ManagedBean
@SessionScoped
public class PedidoBean {
	
	private Produto produtoSelecionado;
	private Item itemSelecionado;
	private Pedido pedido;
	private Item item;
	private Integer quantidade;
	private Usuario usuario;
	private List<Pedido> pedidosPorUsuario;
	private Mail mail;
	private Cidade cidade;
	private Bairro bairro;
	 
	public PedidoBean() {
		pedido = new Pedido();
		item = new Item();
		quantidade = item.getQuantidade();
		
        usuario =  SessionBean.getUser();
        pedido.setUsuario(usuario);
        cidade = usuario.getCidade();
		bairro = usuario.getBairro();	
   
	} 
	
	public void addProduto(){
		boolean verificacao = true;
				if (produtoSelecionado !=null){
					for (int i=0; i<pedido.getListItens().size(); i++){
						if (pedido.getListItens().get(i).getProduto().getId() == produtoSelecionado.getId()){

							pedido.getListItens().get(i).setQuantidade(getQuantidade());
							EntityManager em = JPA.getEM();
							em.getTransaction().begin();
							em.persist(pedido.getListItens().get(i));
							em.getTransaction().commit();
							
							verificacao=false;
						}
					}
					if (verificacao){
						item.setProduto(produtoSelecionado);
						if (getQuantidade()!=null)
							item.setQuantidade(getQuantidade());
						pedido.getListItens().add(item);
						EntityManager em = JPA.getEM();
						em.getTransaction().begin();
						em.persist(item);
						em.getTransaction().commit();
						
						System.out.println("Add "+item.getId()+ " - " + produtoSelecionado.getNome()+" no Pedido "+getPedido().getNome());

						item = new Item();
						quantidade = item.getQuantidade();
					}
					else{
						FacesContext facesContext = FacesContext.getCurrentInstance(); 
						
						facesContext.addMessage(null, new FacesMessage( 
			            FacesMessage.SEVERITY_ERROR, "Ops, produto só deve ser cadastrado uma vez...", null));
						System.out.println("Nao add, produto ja cadastrado...");
					}
				}
				else
					System.out.println("Nao add...");
	}
	public void removeProduto(){
		boolean verificacao = true;
				if (itemSelecionado !=null){
					for (int i=0; i<pedido.getListItens().size(); i++){
						if (pedido.getListItens().get(i).getId() == itemSelecionado.getId())
							verificacao=false;
					}
					if (!verificacao){
						pedido.getListItens().remove(getItemSelecionado());
						System.out.println("Remove "+itemSelecionado.getProduto().getNome()+" no Pedido "+getPedido().getNome());
					}
					else{
						FacesContext facesContext = FacesContext.getCurrentInstance(); 

						facesContext.addMessage(null, new FacesMessage( 
			            FacesMessage.SEVERITY_ERROR, "Ops, produto só deve ser cadastrado uma vez...", null));
						System.out.println("Nao add, produto ja cadastrado...");
					}
				}
				else
					System.out.println("Nao remove...");
	}
	
	public String addPedido() {
		System.out.println("Add Pedido "+pedido.getNome());

		EntityManager em = JPA.getEM();
		em.getTransaction().begin();
		em.merge(pedido);
		em.getTransaction().commit();
//		setPedido(new Pedido());
		
		usuario =  SessionBean.getUser();
        pedido.setUsuario(usuario);
        cidade = usuario.getCidade();
		bairro = usuario.getBairro();	        
        
		mail = new Mail();
        mail.setAssunto("LNB - Pedido realizado");
		mail.setDestino(pedido.getUsuario().getEmail());
		
		String msg = "Olá "+pedido.getNome()+", seu pedido foi realizado com sucesso!\n\nQuantidade de Produtos: "+pedido.getListItens().size() +" \n";
		for (int i = 0; i< pedido.getListItens().size(); i++){
			msg = msg + " " + pedido.getListItens().get(i).getProduto().getNome() + " - " + pedido.getListItens().get(i).getQuantidade() + "x " + pedido.getListItens().get(i).getProduto().getTipo() + " = " + pedido.getListItens().get(i).getTotal()+ "\n";
		}
		msg = msg + "Até a entrega, facilete o troco! Qualquer dúvida entraremos em contato!";
		mail.setMsg(msg);
		mail.setNomeDestino(pedido.getNome());
		mail.sendMail();
        
		//setPedido(new Pedido());
		if (usuario.getTipo().equals(UserTipo.ADMIN))
			return "/gerenciador/pedido/listar";
		else
			return "/loja/pedido/listar";
	}
	
	public String clearPedidos() {
		System.out.println("Clear All");
		setPedido(new Pedido());
		return "/gerenciador/pedido/registrar";
	}
	
	public void  deletePedido(ActionEvent event){
		Pedido selected = (Pedido) event.getComponent().getAttributes().get("selected");
		
		EntityManager em = JPA.getEM();
		em.getTransaction().begin();
			for (int j=0; j<selected.getListItens().size(); j++){
				
				//em.remove(selected.getListItens().get(j).getProduto());
				selected.getListItens().get(j).setProduto(null);
				em.remove(selected.getListItens().get(j));
				selected.getListItens().remove(j);
			}
			selected.setListItens(null);
			selected.setUsuario(null);
			em.remove(selected);
		em.getTransaction().commit();
		
		System.out.println("Delete Pedido "+ selected.getNome());
	}
	
	public void deleteProduto(ActionEvent event){
		Pedido selected = (Pedido) event.getComponent().getAttributes().get("selected");

		boolean aux = selected.getListItens().remove(getItemSelecionado());
		if (aux){
			System.out.println("Deletando do "+selected.getNome()+" o "
					+ "Produto Selecionado "+getItemSelecionado().getProduto().getNome()+" !");
			EntityManager em = JPA.getEM();
			em.getTransaction().begin();
			em.persist(selected);
			em.getTransaction().commit();
		}
		else
			System.out.println("Nao Deletou Produto do Pedido: "+selected.getNome());
	}

	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	public Produto getProdutoSelecionado() {
		if (produtoSelecionado!=null)
			System.out.println("*** ProdutoSelecionado: "+produtoSelecionado.getNome());
		return produtoSelecionado;
	}
	
	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
		
	}
	
	public Item getItemSelecionado() {
		if (itemSelecionado!=null)
			System.out.println("*** ItemSelecionado: "+itemSelecionado.getId());
		return itemSelecionado;
	}

	public void setItemSelecionado(Item itemSelecionado) {
		this.itemSelecionado = itemSelecionado;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	public List<Produto> getProdutosCadastrados() {
		EntityManager em = JPA.getEM();
		TypedQuery<Produto> query = em.createQuery("Select c from Produto c",
				Produto.class);
		
		return query.getResultList();
	}
	
	public List<Pedido> getPedidos() {
		EntityManager em = JPA.getEM();
		TypedQuery<Pedido> query = em.createQuery("Select v from Pedido v",
				Pedido.class);
		
		return query.getResultList();
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	
	public Mail getMail() {
		return mail;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}
	

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
		getBairros();
	}
	
	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}
	
	public List<Cidade> getCidades() {

		EntityManager em = JPA.getEM();
		TypedQuery<Cidade> query = em.createQuery("Select c from Cidade c",
				Cidade.class);
		
		return query.getResultList();
	}
	
	public List<Bairro> getBairros() {
		try{ 				
			EntityManager em = JPA.getEM();
			TypedQuery<Bairro> query = em.createQuery("Select b from Bairro b where b.cidade.id = :id",
					Bairro.class);
			query.setParameter("id", cidade.getId());
			
			return query.getResultList();
		
		}catch (Exception e){
			return new ArrayList<Bairro>();
		}
	}
	
	public Usuario getUsuarioPorEmail(String email) {
		EntityManager em = JPA.getEM();
		TypedQuery<Usuario> query = em.createQuery("Select u from Usuario u where u.email = :email",
				Usuario.class);
		query.setParameter("email", email);
		Usuario usuario = query.getSingleResult();
		return usuario;
	}
	
	public List<Pedido> getPedidosPorUsuario() {

		EntityManager em = JPA.getEM();
		TypedQuery<Pedido> query = em.createQuery("Select p from Pedido p left join fetch p.usuario u where u.email = :email",
				Pedido.class);
		query.setParameter("email", SessionBean.getUser().getEmail());
		pedidosPorUsuario = query.getResultList();
		return pedidosPorUsuario;
	}

	public String list() {
		return "/gerenciador/pedido/listar";
	}


}