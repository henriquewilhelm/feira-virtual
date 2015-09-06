package models;

public class Relacao {

	public String nome = "";
	public String valor = "";
	public String quantidade = "";

	public Relacao(){
		
	}
	
	public Relacao(String nome, String valor, String quantidade) {
		super();
		this.nome = nome;
		this.valor = valor;
		this.quantidade = quantidade;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}
}
