package handlers;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.primefaces.event.SelectEvent;

import models.Bairro;
import models.Categoria;
import models.Cidade;
import models.Item;
import models.Produto;
import models.Pedido;
import models.ProdutoEspecial;
import models.Status;
import models.StatusPedido;
import models.StatusProduto;
import models.Tipo;
import models.UserTipo;
import models.Usuario;
import service.EmailService;
import util.JPA;
import util.Util;

@ManagedBean
@SessionScoped
public class PedidoBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4200623463565962506L;
	
	private Tipo tipo;
	private Produto produtoSelecionado;
	private Item itemSelecionado;
	private Pedido pedido;
	private Item item;
	private ProdutoEspecial produtoEspecialSelecionado;
	
	private List<Categoria> categorias;
	private Categoria categoria;
	private ArrayList<ProdutoEspecial> listProdutoEspecial;
	
	private Usuario usuario;
	private List<Pedido> pedidosPorUsuario;
	private Cidade cidade;
	private Bairro bairro;
	private Double quantidade;
	private List<Double> quantidades;
	private Integer quantidadeItens;
	
	private StatusPedido statusPedido = StatusPedido.EMANDAMENTO;
	private StatusPedido statusPedidoPara;
	
	private Date dataDe;
	private Date dataPara;
	
	public PedidoBean() {
		usuario =  SessionBean.getUser();
		pedido = buscaUltimoPedido();
		
		item = new Item();
		quantidade = item.getQuantidade();

        cidade = usuario.getCidade();
		bairro = usuario.getBairro();	
		
		categorias = getCategorias();
		if (categorias!=null && categorias.size()>0)
			categoria = getCategorias().get(0); 	 	 	
		
		
		if (pedido!=null){
			quantidadeItens = pedido.getListItens().size();
		}
		else{
			pedido = new Pedido();
	        pedido.setUsuario(usuario);
			carregaCesta(pedido);
			quantidadeItens = 1;
		}
	} 
	
	public Pedido buscaUltimoPedido(){
		
			EntityManager em = JPA.getEM();
			TypedQuery<Pedido> query = em.createQuery("Select c from Pedido c "
															+ "WHERE (c.status = :statusConfirmado "
															+ "OR c.status = :statusAguardando) "
															+ "AND c.usuario.id = :id", Pedido.class);
			query.setParameter("statusAguardando", StatusPedido.EMANDAMENTO);
			query.setParameter("statusConfirmado", StatusPedido.CONFIRMADO);
			query.setParameter("id", usuario.getId());
			List<Pedido> entrega = query.getResultList();
			
			if (entrega!=null && entrega.size() > 0){
				return entrega.get(0);
			}
			else {
				return null;
			}
	}
	
	public void carregaCesta(Pedido pedido){
		EntityManager em = JPA.getEM();
		TypedQuery<Produto> queryEntrega = em.createQuery("Select c from Produto c WHERE c.nome = :nome",
				Produto.class);
		queryEntrega.setParameter("nome", "Entrega");
		Produto entrega = queryEntrega.getSingleResult();
		
		pedido.getListItens().add(new Item(1d, entrega));
		pedido.setTotal(entrega.getValor());
		
		em.getTransaction().begin();
		em.persist(pedido.getListItens().get(0));
		em.getTransaction().commit();
	}
	
	public void addQntdProdutoEspecial(ActionEvent event){
		if (getPedido().getStatus() == null || getPedido().getStatus() != StatusPedido.CONFIRMADO){
			ProdutoEspecial produtoEspecial = (ProdutoEspecial) event.getComponent().getAttributes().get("selected");
			produtoSelecionado = produtoEspecial.getProduto();
			if (getProdutoEspecialSelecionado()!=null){
				if (getProdutoEspecialSelecionado().getProduto().getId() == produtoEspecial.getProduto().getId()){
					
				}
				else 
					setProdutoEspecialSelecionado(produtoEspecial);
			}
			else 
				setProdutoEspecialSelecionado(produtoEspecial);
			
			getProdutoEspecialSelecionado().setQuantidade(getProdutoEspecialSelecionado().getQuantidade() + produtoSelecionado.getTipo().getMinimo());
			quantidade = produtoSelecionado.getTipo().getMinimo();
			System.out.println("Adicionando Qntd do Produto: "+ produtoEspecial.getProduto().getNome()+" Qntd Produto: ");
			addProduto();
		}
	}
	
	public void removeQntdProdutoEspecial(ActionEvent event){
		if (getPedido().getStatus() == null || getPedido().getStatus() != StatusPedido.CONFIRMADO){
				ProdutoEspecial produtoEspecial = (ProdutoEspecial) event.getComponent().getAttributes().get("selected");
				produtoSelecionado = produtoEspecial.getProduto();
				if (getProdutoEspecialSelecionado()!=null){ // se ja removeu ou add
					if (getProdutoEspecialSelecionado().getProduto().getId() == produtoEspecial.getProduto().getId()){
						// se continua removendo do mesmo
						System.out.println(" se continua removendo do mesmo");
					}
					else { // se esta removendo, mas outro produto da lista
						setProdutoEspecialSelecionado(produtoEspecial);
						for (int i=0; i < pedido.getListItens().size(); i++){
							if (pedido.getListItens().get(i).getProduto().getId() == produtoSelecionado.getId()){
								getProdutoEspecialSelecionado().setQuantidade(pedido.getListItens().get(i).getQuantidade());
								System.out.println("se esta removendo, mas outro produto da lista");
							}
						}
					}
				}
				else { //se null - primeiro click
					setProdutoEspecialSelecionado(produtoEspecial);
					for (int i=0; i < pedido.getListItens().size(); i++){
						if (pedido.getListItens().get(i).getProduto().getId() == produtoSelecionado.getId()){
							getProdutoEspecialSelecionado().setQuantidade(pedido.getListItens().get(i).getQuantidade());
							System.out.println("se null - primeiro click");
						}
					}
				}
			
				getProdutoEspecialSelecionado().setQuantidade(getProdutoEspecialSelecionado().getQuantidade() - produtoSelecionado.getTipo().getMinimo());
				quantidade = -produtoSelecionado.getTipo().getMinimo();
				System.out.println("Removendo Qntd do Produto: "+ produtoEspecial.getProduto().getNome()+" Qntd Produto: "+getProdutoEspecialSelecionado().getQuantidade());
				
				if (getProdutoEspecialSelecionado().getQuantidade()>0){
					System.out.println("Add Qntd: "+getProdutoEspecialSelecionado().getQuantidade());
			
					addProduto();
				}
				else {
					System.out.println("Remove Qntd: "+getProdutoEspecialSelecionado().getQuantidade());
					removeProdutoEspecifico(produtoSelecionado);
				}
				
				
			
		}
	}
	
	public void addProduto(){
		Item itemAux = null;
		boolean verificacao = true;
				if (produtoSelecionado !=null){
					for (int i=0; i<pedido.getListItens().size(); i++){
						if (pedido.getListItens().get(i).getProduto().getId() == produtoSelecionado.getId()){					
							
							pedido.getListItens().get(i).setQuantidade(pedido.getListItens().get(i).getQuantidade() + getQuantidade());
							pedido.setTotal(pedido.getTotal() + (getQuantidade() * pedido.getListItens().get(i).getProduto().getValor()) );
							
							itemAux = pedido.getListItens().get(i);
							pedido.getListItens().remove(i);
							
							EntityManager em = JPA.getEM(); 
							em.getTransaction().begin();
							em.merge(item);
							em.getTransaction().commit();
							
							verificacao=false;
						}
					}
					if (itemAux != null)
						pedido.getListItens().add(itemAux);
					if (verificacao){
						item.setProduto(produtoSelecionado);
						if (getQuantidade()!=null)
							item.setQuantidade(getQuantidade());
						
						pedido.getListItens().add(item);
						pedido.setTotal(pedido.getTotal() + item.getTotal());
						
						EntityManager em = JPA.getEM();
						em.getTransaction().begin();
						em.persist(item);
						em.getTransaction().commit();
						
						System.out.println("Add produto "+item.getId()+ " - " + produtoSelecionado.getNome()+" no Pedido "+getPedido().getNome());
						
						item = new Item();
						quantidade = item.getQuantidade();

						this.quantidadeItens++;
					}
					else{
						if (!produtoSelecionado.getNome().equals("Entrega")){
							FacesContext facesContext = FacesContext.getCurrentInstance(); 
							
							facesContext.addMessage(null, new FacesMessage( 
				            FacesMessage.SEVERITY_INFO, "O produto só deve ser cadastrado uma vez, sua quantidade será alterada...", null));
							System.out.println("Nao add, produto ja cadastrado...");
						}
					}
				}
				else
					System.out.println("Nao Add - Produto nao selecionado...");
	}
	

	public void removeProdutoEspecifico(Produto produto){
		boolean verificacao = true;
		 		Item itemSelecionado = null;
				if (produto !=null){
					for (int i=0; i<pedido.getListItens().size(); i++){
						if (pedido.getListItens().get(i).getProduto().getId() == produto.getId() || 
								pedido.getListItens().get(i).getProduto().getNome().equals("Entrega")){
										verificacao=false;
										itemSelecionado = pedido.getListItens().get(i);
						}
					}
					if (!verificacao){
						pedido.setTotal(pedido.getTotal() - itemSelecionado.getTotal());
						
						pedido.getListItens().remove(itemSelecionado);
						System.out.println("Remove produto "+itemSelecionado.getProduto().getNome()+" do Pedido "+getPedido().getNome());
						this.quantidadeItens--;
					}
					else{
						FacesContext facesContext = FacesContext.getCurrentInstance(); 

						facesContext.addMessage(null, new FacesMessage( 
			            FacesMessage.SEVERITY_ERROR, "Ops, erro ao remover produto, tente outra vez...", null));
					
					}
				}
				else
					System.out.println("Nao remove - Produto nao selecionado...");		
	}
	
	public void removeProduto(){
			boolean verificacao = true;
				if (itemSelecionado !=null){
					for (int i=0; i<pedido.getListItens().size(); i++){
						if (pedido.getListItens().get(i).getId() == itemSelecionado.getId() || 
								pedido.getListItens().get(i).getProduto().getNome().equals("Entrega"))
							verificacao=false;
					}
					if (!verificacao){
						pedido.setTotal(pedido.getTotal() - itemSelecionado.getTotal());
						
						pedido.getListItens().remove(getItemSelecionado());
						System.out.println("Remove Produto "+itemSelecionado.getProduto().getNome()+" no Pedido "+getPedido().getNome());
						this.quantidadeItens--;
					}
					else{
						FacesContext facesContext = FacesContext.getCurrentInstance(); 

						facesContext.addMessage(null, new FacesMessage( 
			            FacesMessage.SEVERITY_ERROR, "Ops, erro ao remover produto, tente outra vez...", null));
					
					}
				}
				else
					System.out.println("Nao remove - Produto nao selecionado...");
	}
	
	public String addPedido() {
		if (usuario.getTipo().equals(UserTipo.ADMIN)){
				System.out.println("Novo Pedido "+pedido.getNome());
	
				EntityManager em = JPA.getEM();
				em.getTransaction().begin();
				em.merge(pedido);
				em.getTransaction().commit();
	//			setPedido(new Pedido());
				
				usuario =  SessionBean.getUser();
		        pedido.setUsuario(usuario);
		        cidade = usuario.getCidade();
				bairro = usuario.getBairro();	        
		        
				EmailService ThreadEmail = new EmailService(pedido, "Adm Novo Pedido");
				new Thread(ThreadEmail).start();
		        
				setPedido(new Pedido());
				return "/gerenciador/pedido/listar";
		}
		else if (usuario.getTipo().equals(UserTipo.USER)){
			if (getVerificaDiasDaSemana()){
				if (getPedido().getStatus() != null && getPedido().getStatus().equals(StatusPedido.EMANDAMENTO)){
				
					EntityManager em = JPA.getEM();
					em.getTransaction().begin();
					em.merge(pedido);
					em.getTransaction().commit();
	//				     
					pedido = buscaUltimoPedido();
			        
					EmailService ThreadEmail = new EmailService(pedido, "Novo Pedido");
					new Thread(ThreadEmail).start();
			        
					//setPedido(new Pedido());
			        return "/loja/pedido/listar";
				}
				if (getPedido().getStatus() == null){
					pedido.setStatus(StatusPedido.EMANDAMENTO);
					
					EntityManager em = JPA.getEM();
					em.getTransaction().begin();
					em.merge(pedido);
					em.getTransaction().commit();    
					
					pedido = buscaUltimoPedido();
			        
					EmailService ThreadEmail = new EmailService(pedido, "Novo Pedido");
					new Thread(ThreadEmail).start();
			        
					//setPedido(new Pedido());
			        return "/loja/pedido/listar";
				}
			}
		}
		return null;
	}
	
	public String clearPedidos() {
		System.out.println("Clear All");
		getPedido().setObs("");
		getPedido().getListItens().clear();
		getProdutosCadastrados();
		carregaCesta(pedido);
		quantidadeItens = 1;
		return "";
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

	public String cancelaPedido(){
		System.out.println("Cancelando...");
		pedido.setStatus(StatusPedido.CANCELADO);
		return null;
	}
	
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	public Produto getProdutoSelecionado() {
		if (produtoSelecionado!=null){	
			System.out.println("*** ProdutoSelecionado: "+produtoSelecionado.getNome());
		}
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

	public Double getQuantidade() {

		System.out.println("get" +quantidade);
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {

		System.out.println("set" +quantidade);
		this.quantidade = quantidade;
	}
	

	public void quantidadeChanged(ValueChangeEvent e){
		//assign new value to localeCode
		quantidade = (Double) e.getNewValue();
		
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
	
	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	public Integer getQuantidadeItens() {
		return quantidadeItens;
	}

	public void setQuantidadeItens(Integer quantidadeItens) {
		this.quantidadeItens = quantidadeItens;
	}
	
	public List<Double> getQuantidades() {
		return this.quantidades;
	}
	
	public void setQuantidades(List<Double> quantidades) {
		this.quantidades = quantidades;
	}

	public StatusPedido getStatusPedido() {
		return statusPedido;
	}
	
	public void setStatusPedido(StatusPedido statusPedido) {
		this.statusPedido = statusPedido;
	}
	
	public StatusPedido getStatusPedidoPara() {
		return statusPedidoPara;
	}

	public void setStatusPedidoPara(StatusPedido statusPedidoPara) {
		this.statusPedidoPara = statusPedidoPara;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public ProdutoEspecial getProdutoEspecialSelecionado() {
		return produtoEspecialSelecionado;
	}

	public void setProdutoEspecialSelecionado(ProdutoEspecial produtoEspecialSelecionado) {
		this.produtoEspecialSelecionado = produtoEspecialSelecionado;
	}
	
	public Date getDataDe() {
		return dataDe;
	}

	public void setDataDe(Date dataDe) {
		this.dataDe = dataDe;
	}

	public Date getDataAte() {
		return dataPara;
	}

	public void setDataAte(Date dataPara) {
		this.dataPara = dataPara;
	}

	public String proximaCategoria () {
		
		 
		Integer quantidadeCategorias = categorias.size();
		
		if (categoria.getId()+1 <= quantidadeCategorias)
			categoria = categorias.get(categoria.getId());
		else
			categoria = categorias.get(0);
		return "/loja/pedido/listarProdutos.xhtml";
	}
	
	public List<Categoria> getCategorias() {

		EntityManager em = JPA.getEM();
		TypedQuery<Categoria> query = em.createQuery("Select c from Categoria c",
				Categoria.class);
		return query.getResultList();
	}
	
	public List<ProdutoEspecial> getProdutosPorCategoria() {

		
		categorias = getCategorias();		
		listProdutoEspecial = new ArrayList<ProdutoEspecial>();
		
		EntityManager em = JPA.getEM();
		TypedQuery<Produto> query = em.createQuery("Select p from Produto p left join fetch p.categoria c WHERE c.id = :id AND p.status = :status",
				Produto.class);
		if (categoria!=null)
			query.setParameter("id", categoria.getId());
		query.setParameter("status", StatusProduto.ATIVO);
		
		List<Produto> listProduto = query.getResultList();
		ArrayList<Double> quantidades = new ArrayList<Double>();
		
		
		TypedQuery<Produto> queryEntrega = em.createQuery("Select p from Produto p WHERE p.nome = :nome",
				Produto.class);
		queryEntrega.setParameter("nome", "Entrega");
		Produto entrega = queryEntrega.getSingleResult();
		
		listProduto.remove(entrega);
		
		for (int i= 0; i< listProduto.size(); i++){
			
			Double minimo = 1d;
			Double maximo = 1d;
			Double de = 0.5d;
 		
			if (listProduto.get(i)!=null){
				
				Query queryMinMax = em.createQuery("select t.minimo,t.maximo from Tipo t where t.id = :id");
				queryMinMax.setParameter("id", listProduto.get(i).getTipo().getId());
				List<Object[]> rows = queryMinMax.getResultList();
				
				for (Object[] row: rows) {
				    minimo = (Double) row[0];
				    maximo = (Double) row[1];
				}
				
				if (minimo < 1){
					quantidades = new ArrayList<Double>();
					for (Double contador = minimo; contador <= maximo;){
						quantidades.add(contador);
						contador = contador + de;
					}
				}
				else{
					quantidades = new ArrayList<Double>();
					for (Double contador = minimo; contador <= maximo; contador++){
						quantidades.add(contador);
					}
				}
			}
			
			listProdutoEspecial.add(new ProdutoEspecial(listProduto.get(i), 0d, quantidades));
		}
		return listProdutoEspecial;
	}
	
	public List<Produto> getProdutosCadastrados() {
		EntityManager em = JPA.getEM();
		TypedQuery<Produto> query = em.createQuery("Select p from Produto p WHERE p.status = :status",
				Produto.class);
		query.setParameter("status", StatusProduto.ATIVO);
		List<Produto> listProdutos = (List<Produto>) query.getResultList();
		
		TypedQuery<Produto> queryEntrega = em.createQuery("Select p from Produto p WHERE p.nome = :nome",
				Produto.class);
		queryEntrega.setParameter("nome", "Entrega");
		Produto entrega = queryEntrega.getSingleResult();
		
		listProdutos.remove(entrega);
		
		return listProdutos;
	}
	
	public List<Pedido> getPedidos() {
		EntityManager em = JPA.getEM();
		TypedQuery<Pedido> query;
		if (getStatusPedido()!=null && getDataDe()==null && getDataAte()==null){
			query = em.createQuery("Select p from Pedido p WHERE p.status = :status", Pedido.class);
			query.setParameter("status", getStatusPedido());
			System.out.println("Pedido Status");
		}
		else if (getStatusPedido()!=null && getDataDe()!=null && getDataAte()==null){
			query = em.createQuery("Select p from Pedido p WHERE p.status = :status AND p.data >= :data", Pedido.class);
			query.setParameter("status", getStatusPedido());
			query.setParameter("data", getDataDe());
			System.out.println("Pedido DataDe");
		}
		else if (getStatusPedido()!=null && getDataDe()==null && getDataAte()!=null){
			query = em.createQuery("Select p from Pedido p WHERE p.status = :status AND p.data <= :data", Pedido.class);
			query.setParameter("status", getStatusPedido());
			query.setParameter("data", getDataAte());
			System.out.println("Pedido DataAte");
		}
		else if (getStatusPedido()!=null && getDataDe()!=null && getDataAte()!=null){
			query = em.createQuery("Select p from Pedido p WHERE p.status = :status AND p.data >= :dataDe AND p.data <= :dataAte", Pedido.class);
			query.setParameter("status", getStatusPedido());
			query.setParameter("dataDe", getDataDe());
			query.setParameter("dataAte", getDataAte());
			System.out.println("Pedido DataDe DataAte");
		}
		else if (getStatusPedido()==null && getDataDe()==null && getDataAte()!=null){
			query = em.createQuery("Select p from Pedido p WHERE p.data <= :data", Pedido.class);
			query.setParameter("data", getDataAte());
			System.out.println("DataAte");
		}
		else if (getStatusPedido()==null && getDataDe()!=null && getDataAte()==null){
			query = em.createQuery("Select p from Pedido p WHERE p.data >= :data", Pedido.class);
			query.setParameter("data", getDataDe());
			System.out.println("DataDe");
		}
		else if (getStatusPedido()==null && getDataDe()!=null && getDataAte()!=null){
			query = em.createQuery("Select p from Pedido p WHERE p.data >= :dataDe AND p.data <= :dataAte", Pedido.class);
			query.setParameter("dataDe", getDataDe());
			query.setParameter("dataAte", getDataAte());
			System.out.println("DataDe DataAte");
		}
		else {
			query = em.createQuery("Select p from Pedido p",	Pedido.class);
			System.out.println("Apenas Pedido");
		}
		
		return query.getResultList();
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
	
	public List<Tipo> getTipos() {
		Double minimo = 1d;
		Double maximo = 1d;
		Double de = 0.5d;
//		try{ 			
		if (getProdutoSelecionado()!=null){
			EntityManager em = JPA.getEM();
			TypedQuery<Tipo> query = em.createQuery("Select t from Tipo t where t.id = :id",
					Tipo.class);
			query.setParameter("id", getProdutoSelecionado().getTipo().getId());
			
			Query queryMinMax = em.createQuery("select t.minimo,t.maximo from Tipo t where t.id = :id");
			queryMinMax.setParameter("id", getProdutoSelecionado().getTipo().getId());
			List<Object[]> rows = queryMinMax.getResultList();

			for (Object[] row: rows) {
			    minimo = (Double) row[0];
			    maximo = (Double) row[1];
			}
			if (minimo < 1){
				quantidades = new ArrayList<Double>();
				for (Double contador = minimo; contador <= maximo;){
					quantidades.add(contador);
					contador = contador + de;
				}
			}
			else{
				quantidades = new ArrayList<Double>();
				for (Double contador = minimo; contador <= maximo; contador++){
					quantidades.add(contador);
				}
			}
			return query.getResultList();
		}
		else 
			return new ArrayList<Tipo>();
//		}catch (Exception e){
//			return new ArrayList<Tipo>();
//		}
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
	
	public Integer getNumeroStatus(){
	    if (getPedido() != null && getPedido().getStatus()!=null){
			if(getPedido().getStatus().equals(StatusPedido.EMANDAMENTO))		
					return 1;
			else if(getPedido().getStatus().equals(StatusPedido.CONFIRMADO))
					return 2;
	    }
		return 0;
	}
	
	public List<StatusPedido> getListStatusPedido() {
		 return Util.getListStatusPedido();
	}
	
	public void mudaStatus(){
		System.out.println("mudaStatus");
		List<Pedido> pedidos = getPedidos();
		for (int i=0; i< pedidos.size(); i++){
			pedidos.get(i).setStatus(getStatusPedidoPara());
			EntityManager em = JPA.getEM();
			em.getTransaction().begin();
			em.merge(pedidos.get(i));
			em.getTransaction().commit();
		}
		setStatusPedido(getStatusPedidoPara());
	} 
	
	public String list() {
		return "/gerenciador/pedido/listar";
	}
//
//    public void onRowEdit(RowEditEvent event) {
//    	ProdutoEspecial produtoEspecial = ((ProdutoEspecial) event.getObject());
//        FacesMessage msg = new FacesMessage("Produto Adicionado", produtoEspecial.getProduto().getId().toString());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//        produtoSelecionado = produtoEspecial.getProduto();
//		System.out.println("Add: "+produtoEspecial.getProduto().getNome()+" "+getQuantidade());
//		addProduto();
//    }
//     
//    public void onRowCancel(RowEditEvent event) {
//        FacesMessage msg = new FacesMessage("Adicao Cancelada", ((ProdutoEspecial) event.getObject()).getProduto().getId().toString());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//    }
//     
//    public void onCellEdit(CellEditEvent event) {
//        Object oldValue = event.getOldValue();
//        Object newValue = event.getNewValue();
//         
//        if(newValue != null && !newValue.equals(oldValue)) {
//            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//        }
//    }
//    
    public void sendMessage() {
        FacesContext context = FacesContext.getCurrentInstance();
         
        context.addMessage(null, new FacesMessage("Successful",  "Your message ") );
        context.addMessage(null, new FacesMessage("Second Message", "Additional Message Detail"));
    }
     
    
    public boolean getVerificaDiasDaSemana() {
    	boolean status = false;
    	Integer diaDaSemana = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    	Integer horaAtual = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) * 60;
    	EntityManager em = JPA.getEM();
		TypedQuery<Status> query = em.createQuery("Select s from Status s",
				Status.class);
		
		List<Status> listStatus = query.getResultList();
		for (int i=0; i<listStatus.size(); i++){
			
			if (getPedido().getStatus() != null && listStatus.get(i).getNome().equals( getPedido().getStatus().toString() ) ){
				System.out.println(listStatus.get(i).getNome());
				if (listStatus.get(i).getDiaInicial() < diaDaSemana && listStatus.get(i).getDiaFinal() > diaDaSemana){
					System.out.println("Dentro do dia");
					status = true;
				}
				else if (listStatus.get(i).getDiaInicial() == diaDaSemana && listStatus.get(i).getDiaFinal() > diaDaSemana){
					if (listStatus.get(i).getHrInicial() <= horaAtual ){
						System.out.println("Dentro da hora");
						status = true;
					}
				}
				else if (listStatus.get(i).getDiaInicial() < diaDaSemana && listStatus.get(i).getDiaFinal() == diaDaSemana){
					if (listStatus.get(i).getHrFinal() >= horaAtual ){
						System.out.println("Dentro da hora");
						status = true;
					}
				}
			}
		}
		System.out.println("Dia "+diaDaSemana+" e hora "+horaAtual);
		return status;
	}
    
    public String getVerificaStatus() {
		if (getPedido() != null && getPedido().getStatus() != null){
			if (getPedido().getStatus().equals(StatusPedido.EMANDAMENTO)){
				return "ATUALIZAR PEDIDO";
			}
			else if (getPedido().getStatus().equals(StatusPedido.CONFIRMADO)){
				return "CONFIRMADO, AGUARDANDO ENTREGA";
			}
		}
		return "ENVIAR PEDIDO";
	}
    
    public void onDateDeSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Data Inicial Selecionada", format.format(event.getObject())));
    }
    
    public void onDateParaSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
         facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Data Final Selecionada", format.format(event.getObject())));
    }
}