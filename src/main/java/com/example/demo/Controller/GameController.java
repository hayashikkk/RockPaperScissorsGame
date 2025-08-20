package com.example.demo.Controller;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GameController {

	private static final String[] hands = { "グー", "チョキ", "パー" };

	@GetMapping({"/", "/game"})
	public String showGamePage(Model model) {

		for (int i = 0; i < hands.length; i++) {
			model.addAttribute("hand" + i, hands[i]);
			model.addAttribute("num" + i, i);

		}

		return "game";
	}

	@PostMapping("/result")
	public String shoGameResultPage(@RequestParam(value = "playerChoice", required = false) Integer playerChoice, Model model) {

		if (playerChoice == null || playerChoice < 0 || playerChoice >= hands.length) {
			model.addAttribute("errorMessage", "手を選択してください。");
			for (int i = 0; i < hands.length; i++) {
				model.addAttribute("hand" + i, hands[i]);
				model.addAttribute("num" + i, i);
			}
			return "game";
		}

		String playerHand = hands[playerChoice];
		model.addAttribute("playerHand", playerHand);

		Random random = new Random();
		int computerChoice = random.nextInt(3);
		String computerHand = hands[computerChoice];
		model.addAttribute("computerHand", computerHand);

		String result = gameResult(playerHand, computerHand);
		model.addAttribute("result", result);
		return "result";
	}

	private String gameResult(String playerHand, String computerHand) {
		if (playerHand.equals(computerHand)) {
			return "あいこ";
		} else if ((playerHand.equals("グー") && computerHand.equals("チョキ")) ||
				(playerHand.equals("チョキ") && computerHand.equals("パー")) ||
				(playerHand.equals("パー") && computerHand.equals("グー"))) {
			return "勝ち";
		} else {
			return "負け";
		}
	}
}
