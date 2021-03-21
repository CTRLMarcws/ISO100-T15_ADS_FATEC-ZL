package view;

import java.util.concurrent.Semaphore;

import controller.ThreadCarro;

public class Treino {

	public static void main(String[] args)
	{
		int permissoes = 5;
		Semaphore semaforo = new Semaphore(permissoes);
		
		for (int idCarro = 1; idCarro < 3; idCarro++)
		{
			for (int equipe = 1; equipe < 8; equipe ++)
			{
				Thread t = new ThreadCarro(idCarro, equipe, semaforo);
				t.start();				
			}
		}
	}

}
