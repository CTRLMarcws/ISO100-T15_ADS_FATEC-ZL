package controller;

import java.util.concurrent.Semaphore;

public class ThreadCarro extends Thread
{
	private Semaphore semaforo;
	private int idCarro;
	private int equipe;
	private int melhorVolta;
	private static int contador;
	private static int escuderias [][] = new int [14][3];

	public ThreadCarro(int idCarro, int equipe, Semaphore semaforo)
	{
		this.idCarro = idCarro;
		this.semaforo = semaforo;
		this.equipe = equipe;
	}

	@Override
	public void run()
	{
		try
		{
			semaforo.acquire();
			volta();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			semaforo.release();
		}

		salvarTempos();

		if (contador == 14)
		{
			dormir(1000);
			ordenarResultados();
			exibirResultados();
			exibirGrid();
		}
	}


	private void volta()
	{
		melhorVolta = 10000;
		System.out.println("Carro #" + idCarro + " da equipe " + equipe + " entrou na pista.");

		for (int i = 0; i < 3; i++)
		{
			int lap = (int) ((Math.random() * 2001) + 1000); //ajustar tempo
			
			dormir(lap);
			
			if (lap < melhorVolta)
			{
				melhorVolta = lap;
			}
			
			System.out.println("Carro #" + idCarro + " da equipe " + equipe + " completou sua "
					+ (i+1) + "º volta em " + (lap / Math.pow(10, 3)) + "s.");
		}
		System.out.println("Carro #" + idCarro + " da equipe " + equipe + " completou as 3 voltas do circuito - "
				+ "Melhor tempo: " + paraSegundos(melhorVolta) + "s.");
	}


	private void salvarTempos()
	{
		escuderias [contador][0] = idCarro;
		escuderias [contador][1] = equipe;
		escuderias [contador][2] = melhorVolta;
		contador ++;
	}


	private void dormir(int tempo)
	{
		try
		{
			sleep(tempo);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}


	private void ordenarResultados()
	{
		int auxId, auxEqp, auxTmp;
		
		for (int i = 0; i <= 13; i++)
		{
			for (int j = 0; j <= 12; j++)
			{
				if(escuderias[i][2] < escuderias[j][2])
				{
					auxId = escuderias[i][0];
					auxEqp = escuderias[i][1];
					auxTmp = escuderias[i][2];
					
					escuderias[i][0] = escuderias[j][0];
					escuderias[i][1] = escuderias[j][1];
					escuderias[i][2] = escuderias[j][2];
					
					escuderias[j][0] = auxId; 
					escuderias[j][1] = auxEqp;
					escuderias[j][2] = auxTmp; 
				}
			}
		}
	}
	private void exibirResultados()
	{
		System.out.println("\n----------------\nMelhores tempos:");
		for (int i = 0; i <= 13; i++)
		{
			System.out.println("\n" + (i + 1) + "º colocado:\nCarro:        " + escuderias[i][0]
					+ "\nEquipe:       " + escuderias[i][1] + "\nMelhor tempo: " + paraSegundos(escuderias[i][2]) + "s.");
		}
	}
	
	
	private void exibirGrid()
	{
		System.out.println("\n\nGRID DE LARGADA\n carro-equipe\n---------------");
		for (int i = 0; i <= 13; i=i+2)
		{
			System.out.println("   "+escuderias[i][0] + "-" + escuderias[i][1]
					+ "\n         " + escuderias[(i+1)][0] + "-" + escuderias[(i+1)][1]);
		}
	}
	
	private double paraSegundos(int tempo)
	{
		return (tempo / Math.pow(10, 3));
	}
}
