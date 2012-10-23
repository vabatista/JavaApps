
public class Estatistica {

	private int contagemAtual;
	private int maximo;
	private float maximoDia;
	private int dias;
	private float media;
	
	public Estatistica() {
		contagemAtual = 0;
		maximo = 0;
		maximoDia = 0;
		dias = 0;
		media = 0;
	}
	
	public void atualizaSomaMedia() {
		media = media + maximoDia;
	}
	
	public void incrementaDias() {
		dias++;
		maximoDia = 0;
	}
	
	public int getContagemAtual() {
		return contagemAtual;
	}
	public void setContagemAtual(int contagemAtual) {
		this.contagemAtual = contagemAtual;
	}
	public int getMaximo() {
		return maximo;
	}
	public void setMaximo(int maximo) {
		this.maximo = maximo;
	}
	public int getDias() {
		return dias;
	}
	public void setDias(int dias) {
		this.dias = dias;
	}
	public float getMedia() {
		return media;
	}
	public void setMedia(float media) {
		this.media = media;
	}

	public void diminuiContagem() {
		this.contagemAtual--;
	}

	public void aumentaContagem() {
		this.contagemAtual++;
		if (this.contagemAtual > this.maximo) {
			this.maximo = this.contagemAtual;
		}
		if (this.contagemAtual > this.maximoDia) {
			this.maximoDia = this.contagemAtual;
		}
	}
	
	
	
}
