package com.codeoftheweb.salvo;

import com.codeoftheweb.salvo.models.*;
import com.codeoftheweb.salvo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class SalvoApplication { //extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository,
									  GameRepository gameRepository,
									  GamePlayerRepository gamePlayerRepository,
									  ShipRepository shipRepository,
									  SalvoRepository salvoRepository){
		return (args) -> {

		//Adding Games
		Game g1 = new Game();
		Game g2 = new Game();
		Game g3 = new Game();

		gameRepository.save(g1);
		gameRepository.save(g2);
		gameRepository.save(g3);

		//Adding PLayers
		Player p1 = new Player("Peter", passwordEncoder.encode("123123123"));
		Player p2 = new Player("Wendy",passwordEncoder.encode("123123123"));
		Player p3 = new Player("Captain", passwordEncoder.encode("123123123"));

		Score score1 = new Score(g1, p1, 0.5);
		Score score2 = new Score(g1, p2, 0.5);
		Score score3 = new Score(g2, p2, 1);
		Score score4 = new Score(g2, p3, 0);

		p1.addScore(score1);
		p2.addScore(score2);
		p2.addScore(score3);
		p3.addScore(score4);



		playerRepository.save(p1);
		playerRepository.save(p2);
		playerRepository.save(p3);

		//Adding GamePlayers
		GamePlayer gp1 = new GamePlayer(g1, p1);
		GamePlayer gp2 = new GamePlayer(g1, p2);
		GamePlayer gp3 = new GamePlayer(g2, p2);
		GamePlayer gp4 = new GamePlayer(g2, p3);


		Ship sh1 = new Ship("battleShip",  new ArrayList<>(Arrays.asList("H2","H3","H4")));
		Ship sh2 = new Ship("landingShip",new ArrayList<>(Arrays.asList("J3","J4","J5" )));
		Ship sh3 = new Ship("lifeRaft", new ArrayList<>(Arrays.asList("A1","A2","A3")));
		Ship sh4 = new Ship("battleShip", new ArrayList<>(Arrays.asList("K3","K2","K1")));
		Ship sh5 = new Ship("landingShip", new ArrayList<>(Arrays.asList("J3","J4","J5")));
		Ship sh6 = new Ship("lifeRaft",new ArrayList<>(Arrays.asList("C1","C2","C3")));
		Ship sh7 = new Ship("battleShip",  new ArrayList<>(Arrays.asList("I1","J1","K1")));
		Ship sh8 = new Ship("landingShip",new ArrayList<>(Arrays.asList("A1","A2","A3" )));
		Ship sh9 = new Ship("lifeRaft", new ArrayList<>(Arrays.asList("K3","K2","K1")));

		/*
		shipRepository.save(sh1);
		shipRepository.save(sh2);
		shipRepository.save(sh3);
		shipRepository.save(sh4);
		shipRepository.save(sh5);
		shipRepository.save(sh6);
		shipRepository.save(sh7);
		shipRepository.save(sh8);
		shipRepository.save(sh9);
*/
        gp1.addShip(sh1);
        gp1.addShip(sh2);
        gp1.addShip(sh3);
        gp2.addShip(sh4);
        gp2.addShip(sh5);
        gp2.addShip(sh6);
        gp3.addShip(sh7);
        gp3.addShip(sh8);
        gp3.addShip(sh9);

		Salvo s1 = new Salvo(1, new ArrayList<>(Arrays.asList("H1","B6")));
		Salvo s2 = new Salvo(1, new ArrayList<>(Arrays.asList("I2","K2")));
		Salvo s3 = new Salvo(2, new ArrayList<>(Arrays.asList("A4","B9")));
		Salvo s4 = new Salvo(2, new ArrayList<>(Arrays.asList("C5","B7")));
		Salvo s5 = new Salvo(3, new ArrayList<>(Arrays.asList("D8","D2")));
		Salvo s6 = new Salvo(3, new ArrayList<>(Arrays.asList("K9","H2")));
		Salvo s7 = new Salvo(4, new ArrayList<>(Arrays.asList("A8","B5")));
		Salvo s8 = new Salvo(4, new ArrayList<>(Arrays.asList("K8","E1")));
		Salvo s9 = new Salvo(5, new ArrayList<>(Arrays.asList("F3","H9")));
		Salvo s10 = new Salvo(5, new ArrayList<>(Arrays.asList("F9","C1")));
		Salvo s11 = new Salvo(6, new ArrayList<>(Arrays.asList("D6","B5")));
		Salvo s12 = new Salvo(6, new ArrayList<>(Arrays.asList("A3","H5")));
		Salvo s13 = new Salvo(7, new ArrayList<>(Arrays.asList("C7","J9")));
		Salvo s14 = new Salvo(7, new ArrayList<>(Arrays.asList("D2","F7")));
/*
		salvoRepository.save(s1);
		salvoRepository.save(s2);
		salvoRepository.save(s3);
		salvoRepository.save(s4);
		salvoRepository.save(s5);
		salvoRepository.save(s6);
		salvoRepository.save(s7);
		salvoRepository.save(s8);
		salvoRepository.save(s9);
		salvoRepository.save(s10);
		salvoRepository.save(s11);
		salvoRepository.save(s12);
		salvoRepository.save(s13);
		salvoRepository.save(s14);
*/

		gp1.addSalvo(s1);
		gp2.addSalvo(s2);
		gp1.addSalvo(s3);
		gp2.addSalvo(s4);
		gp1.addSalvo(s5);
		gp2.addSalvo(s6);
		gp1.addSalvo(s7);
		gp2.addSalvo(s8);
		gp1.addSalvo(s9);
		gp2.addSalvo(s10);
		gp1.addSalvo(s11);
		gp2.addSalvo(s12);
	    gp1.addSalvo(s13);
		gp2.addSalvo(s14);


		gamePlayerRepository.save(gp1);
		gamePlayerRepository.save(gp2);
		gamePlayerRepository.save(gp3);
		gamePlayerRepository.save(gp4);



	};
	}
}



