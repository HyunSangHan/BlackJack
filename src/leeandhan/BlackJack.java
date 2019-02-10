package leeandhan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

public class BlackJack {

	public static void main(String[] args) {
		BlackJack app = new BlackJack();
		app.mainMethod();

	}

	private void mainMethod() {
		System.out.println("*********************");
		System.out.println("Black Jack (블랙 잭) 게임");
		System.out.println("*********************");
		System.out.println("게임 방법:");
		System.out.println("카드를 뽑아 카드의 숫자 합을 계산하고, 21에 최대한 가깝도록 만드는 게임입니다.");
		System.out.println("딜러와 대결하게 되며, 딜런는 카드 합이 17 이상이 될 때까지 카드를 뽑습니다.");
		System.out.println("유저는 Hit, Stay를 통해 카드를 뽑을 지 여부를 결정할 수 있습니다.");
		System.out
				.println("카드 숫자 합이 21이 초과되면 패배하게 되며, 21이 된다면 이를 BlackJack이라 하고 2배의 배팅액을 받게 됩니다.");
		System.out
				.println("========================================================================");
		start();
	}

	private void start() {
		game();
	}

	private void game() {

		try {
			BufferedReader br = new BufferedReader(new FileReader(
					"bank/money.txt"));
			String line = null;
			line = br.readLine();
			StringTokenizer stk = new StringTokenizer(line);
			int money = Integer.parseInt(stk.nextToken());
			// System.out.printf("당신의 현재 잔고는 $%d입니다.%n", money);//잔고 칸 따로
			// 만들기!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			System.out.println("새로운 게임을 시작합니다.");// dialogue로 구현
			if (money <= 0) {
				int betting = 0;
				restart(betting, money);
			} else if (money > 0) {
				/*
				 * Scanner scan = new Scanner(System.in);
				 * System.out.println("베팅 할 금액을 입력하세요.");//창띄우기 int betting =
				 * scan.nextInt();
				 */
				String a = JOptionPane.showInputDialog("베팅 할 금액을 입력하세요.", 100);
				int betting = Integer.parseInt(a);

				if (betting > 0 && betting <= money) {
					card(betting, money);
				} else {
					System.out.println("올바른 베팅 금액이 아니므로, 다시 입력하여 주세요.");// dialogue로
																		// 구현
					System.out.printf("(당신의 잔고 이하($%d)의 금액만 가능합니다.)%n", money);// 위에꺼랑
																				// 같음
					start();
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("User의 소유 금액 파일을 찾을 수 없습니다. 개발자에게 문의하세요.");// dialogue로
																			// 구현
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void card(int betting, int money) {
		int[] randomnum = new int[22];
		int[] randomdealer = new int[11];
		int[] value = new int[11];
		int[] valuedealer = new int[11];

		for (int i = 0; i < 22; i++) {
			randomnum[i] = (int) (Math.random() * 52 + 1);

			if (i > 0) {
				for (int j = 0; j < i; j++) {
					if (randomnum[i] == randomnum[j]) {
						i = i - 1;
					}

				}
			}
		}
		for (int i = 0; i < 11; i++) {
			randomdealer[i] = randomnum[i + 11];
		}

		for (int i = 0; i < 11; i++) {

			if (randomnum[i] > 13 && randomnum[i] <= 26) {
				value[i] = randomnum[i] - 13;
			} else if (randomnum[i] > 26 && randomnum[i] <= 39) {
				value[i] = randomnum[i] - 26;
			} else if (randomnum[i] > 39 && randomnum[i] <= 52) {
				value[i] = randomnum[i] - 39;
			} else {
				value[i] = randomnum[i];
			}

			if (randomdealer[i] > 13 && randomdealer[i] <= 26) {
				valuedealer[i] = randomdealer[i] - 13;
			} else if (randomdealer[i] > 26 && randomdealer[i] <= 39) {
				valuedealer[i] = randomdealer[i] - 26;
			} else if (randomdealer[i] > 39 && randomdealer[i] <= 52) {
				valuedealer[i] = randomdealer[i] - 39;
			} else {
				valuedealer[i] = randomdealer[i];
			}

		}
		for (int i = 0; i < 11; i++) {
			if (value[i] == 11 || value[i] == 12 || value[i] == 13) {
				value[i] = 10;

			}
			if (valuedealer[i] == 11 || valuedealer[i] == 12
					|| valuedealer[i] == 13) {
				valuedealer[i] = 10;

			}
			if (value[i] == 1) {
				value[i] = 11;
			}
			if (valuedealer[i] == 1) {
				valuedealer[i] = 11;
			}
		}
		//
		for (int m = 0; m < 11; m++) {
			System.out.printf("%d번 째 유저꺼 : %d", m + 1, value[m]);
			System.out.println();
			System.out.printf("%d번 째 딜러꺼 : %d", m + 1, valuedealer[m]);
			System.out.println();
			System.out.println();

		}
		//
		printCard(randomnum, randomdealer, value, valuedealer, betting, money);

	}

	private void printCard(int randomnum[], int randomdealer[], int value[],
			int valuedealer[], int betting, int money) {

		int sum = 0;
		int sumchange = 0;
		int sumdealer = 0;
		int enter = 0;

		System.out.println("==========================");

		System.out.printf("Dealer의 첫번째 카드는 %d 입니다.", randomdealer[0]);
		System.out.println();
		System.out.println("==========================");

		/************************* User ********************************/

		sum = value[0] + value[1];

		if (value[0] == 11 || value[1] == 11) {
			sumchange = sum - 10;
		}
		System.out.println("당신의");
		System.out.printf("[첫 번째 카드]는 %d 입니다.", randomnum[0]);
		System.out.printf("%n[두 번째 카드]는 %d 입니다.", randomnum[1]);
		if (sum > 21) {
			System.out.printf("A를 11에서 1로 바꾸어 현재까지 합은 %d 입니다.%n", sumchange);
		} else {
			System.out.printf("%n합은 %d입니다.%n", sum);
		}
		System.out.println("==========================");

		
		while (true) {

			for (int r = 0; r < 11; r++) {
				sumdealer = sumdealer + valuedealer[r];

				if (sumdealer >= 17) {
					if (sumdealer > 21) {
						for (int m = 0; m < r + 1; m++) {
							if (valuedealer[m] == 11) {
								sumdealer = sumdealer - 10;
							
							}
						}
						if (sumdealer >= 17) {
							break;
						}else{
							continue;
						}
					}else{break;}
				}

			}
			break;

		}
		
		
		
		

			if (sum == 21) {
				System.out.println("BlackJack 입니다!");
				betting(money, betting, sum, sumdealer);
			}
			Scanner scan = new Scanner(System.in);
			System.out.println("hit 하려면 1을, stay하려면 아무거나 입력하시오.☞");
			enter = scan.nextInt();

			if (enter == 1) {
				sum = sum + value[2];
				sumchange = sumchange + value[2];
				if (value[2] == 11) {
					sumchange = sum - 10;
				}
				System.out.printf("[세 번째 카드]는 %d 입니다.", randomnum[2]);

				Scanner scan2 = new Scanner(System.in);
				System.out.println();
				if (sum == 21) {
					System.out.println("BlackJack 입니다!");
					betting(money, betting, sum, sumdealer);
				}
				if (sum > 21) {
					if (value[0] == 11 || value[1] == 11 || value[2] == 11) {
						sum = sumchange;
					}
					if (sum > 21) {
						System.out.printf("현재까지 합은 %d 입니다.%n", sum);
						System.out.println("21을 넘었기 때문에 패배하였습니다.");
						betting(money, betting, sum, sumdealer);
					}
					System.out.printf("A를 11에서 1로 바꾸어 현재까지 합은 %d 입니다.%n", sum);
					if (sum == 21) {
						System.out.println("BlackJack 입니다!");
						betting(money, betting, sum, sumdealer);
					}
				} else {
					System.out.printf("현재까지 합은 %d 입니다.%n", sum);
				}
				System.out.println();
				System.out.println("hit 하려면 1을, stay하려면 아무거나 입력하시오.☞");
				enter = scan2.nextInt();
				if (enter == 1) {
					sum = sum + value[3];
					sumchange = sumchange + value[3];
					if (value[3] == 11) {
						sumchange = sum - 10;
					}
					System.out.printf("[네 번째 카드]는 %d 입니다.", randomnum[3]);

					Scanner scan3 = new Scanner(System.in);
					System.out.println();
					if (sum == 21) {
						System.out.println("BlackJack 입니다!");
						betting(money, betting, sum, sumdealer);
					}
					if (sum > 21) {
						if (value[0] == 11 || value[1] == 11 || value[2] == 11
								|| value[3] == 11) {
							sum = sumchange;
						}
						if (sum > 21) {
							System.out.printf("현재까지 합은 %d 입니다.%n", sum);
							System.out.println("21을 넘었기 때문에 패배하였습니다.");
							betting(money, betting, sum, sumdealer);
						}
						System.out.printf("A를 11에서 1로 바꾸어 현재까지 합은 %d 입니다.%n",
								sum);
						if (sum == 21) {
							System.out.println("BlackJack 입니다!");
							betting(money, betting, sum, sumdealer);
						}
					} else {
						System.out.printf("현재까지 합은 %d 입니다.%n", sum);
					}
					System.out.println();
					System.out.println("hit 하려면 1을, stay하려면 아무거나 입력하시오.☞");
					enter = scan3.nextInt();
					if (enter == 1) {
						sum = sum + value[4];
						sumchange = sumchange + value[4];
						if (value[4] == 11) {
							sumchange = sum - 10;
						}
						System.out.printf("[다섯 번째 카드]는 %d 입니다.", randomnum[4]);

						Scanner scan4 = new Scanner(System.in);
						System.out.println();
						if (sum == 21) {
							System.out.println("BlackJack 입니다!");
							betting(money, betting, sum, sumdealer);
						}
						if (sum > 21) {
							if (value[0] == 11 || value[1] == 11
									|| value[2] == 11 || value[3] == 11
									|| value[4] == 11) {
								sum = sumchange;
							}
							if (sum > 21) {
								System.out.printf("현재까지 합은 %d 입니다.%n", sum);
								System.out.println("21을 넘었기 때문에 패배하였습니다.");
								betting(money, betting, sum, sumdealer);
							}
							System.out.printf(
									"A를 11에서 1로 바꾸어 현재까지 합은 %d 입니다.%n", sum);
							if (sum == 21) {
								System.out.println("BlackJack 입니다!");
								betting(money, betting, sum, sumdealer);
							}
						} else {
							System.out.printf("현재까지 합은 %d 입니다.%n", sum);
						}
						System.out.println();
						System.out.println("hit 하려면 1을, stay하려면 아무거나 입력하시오.☞");
						enter = scan4.nextInt();
						if (enter == 1) {
							sum = sum + value[5];
							sumchange = sumchange + value[5];
							if (value[5] == 11) {
								sumchange = sum - 10;
							}
							System.out.printf("[여섯 번째 카드]는 %d 입니다.",
									randomnum[5]);

							System.out.println();
							if (sum == 21) {
								System.out.println("BlackJack 입니다!");
								betting(money, betting, sum, sumdealer);
							}
							if (sum > 21) {
								if (value[0] == 11 || value[1] == 11
										|| value[2] == 11 || value[3] == 11
										|| value[4] == 11 || value[5] == 11) {
									sum = sumchange;
								}
								if (sum > 21) {
									System.out.printf("현재까지 합은 %d 입니다.%n", sum);
									System.out.println("21을 넘었기 때문에 패배하였습니다.");
									betting(money, betting, sum, sumdealer);
								}
								System.out
										.printf("A를 11에서 1로 바꾸어 현재까지 합은 %d 입니다.%n",
												sum);
								if (sum == 21) {
									System.out.println("BlackJack 입니다!");
									betting(money, betting, sum, sumdealer);
								}
							} else {
								System.out.printf("현재까지 합은 %d 입니다.%n", sum);
							}
							System.out.println();
							System.out
									.println("hit 하려면 1을, stay하려면 아무거나 입력하시오.☞");
							enter = scan4.nextInt();
							if (enter == 1) {
								sum = sum + value[6];
								sumchange = sumchange + value[6];
								if (value[6] == 11) {
									sumchange = sum - 10;
								}
								System.out.printf("[일곱 번째 카드]는 %d 입니다.",
										randomnum[6]);

								System.out.println();
								if (sum == 21) {
									System.out.println("BlackJack 입니다!");
									betting(money, betting, sum, sumdealer);
								}
								if (sum > 21) {
									if (value[0] == 11 || value[1] == 11
											|| value[2] == 11 || value[3] == 11
											|| value[4] == 11 || value[5] == 11
											|| value[6] == 11) {
										sum = sumchange;
									}
									if (sum > 21) {
										System.out.printf("현재까지 합은 %d 입니다.%n",
												sum);
										System.out
												.println("21을 넘었기 때문에 패배하였습니다.");
										betting(money, betting, sum, sumdealer);
									}
									System.out.printf(
											"A를 11에서 1로 바꾸어 현재까지 합은 %d 입니다.%n",
											sum);
									if (sum == 21) {
										System.out.println("BlackJack 입니다!");
										betting(money, betting, sum, sumdealer);
									}
								} else {
									System.out.printf("현재까지 합은 %d 입니다.%n", sum);
								}
								System.out.println();
								System.out
										.println("hit 하려면 1을, stay하려면 아무거나 입력하시오.☞");
								enter = scan4.nextInt();
								if (enter == 1) {
									sum = sum + value[7];
									sum = sumchange + value[7];
									if (value[7] == 11) {
										sumchange = sum - 10;
									}
									System.out.printf("[여덟 번째 카드]는 %d 입니다.",
											randomnum[7]);

									System.out.println();
									if (sum == 21) {
										System.out.println("BlackJack 입니다!");
										betting(money, betting, sum, sumdealer);
									}
									if (sum > 21) {
										if (value[0] == 11 || value[1] == 11
												|| value[2] == 11
												|| value[3] == 11
												|| value[4] == 11
												|| value[5] == 11
												|| value[6] == 11
												|| value[7] == 11) {
											sum = sumchange;
										}
										if (sum > 21) {
											System.out.printf(
													"현재까지 합은 %d 입니다.%n", sum);
											System.out
													.println("21을 넘었기 때문에 패배하였습니다.");
											betting(money, betting, sum, sumdealer);
										}
										System.out
												.printf("A를 11에서 1로 바꾸어 현재까지 합은 %d 입니다.%n",
														sum);
										if (sum == 21) {
											System.out
													.println("BlackJack 입니다!");
											betting(money, betting, sum, sumdealer);
										}
									} else {
										System.out.printf("현재까지 합은 %d 입니다.",
												sum);
									}
									System.out.println();
									System.out
											.println("hit 하려면 1을, stay하려면 아무거나 입력하시오.☞");
									enter = scan4.nextInt();
									if (enter == 1) {
										sum = sum + value[8];
										sum = sumchange + value[8];
										if (value[8] == 11) {
											sumchange = sum - 10;
										}
										System.out.printf(
												"[아홉 번째 카드]는 %d 입니다.",
												randomnum[8]);

										System.out.println();
										if (sum == 21) {
											System.out
													.println("BlackJack 입니다!");
											betting(money, betting, sum, sumdealer);
										}
										if (sum > 21) {
											if (value[0] == 11
													|| value[1] == 11
													|| value[2] == 11
													|| value[3] == 11
													|| value[4] == 11
													|| value[5] == 11
													|| value[6] == 11
													|| value[7] == 11
													|| value[8] == 11) {
												sum = sumchange;
											}
											if (sum > 21) {
												System.out.printf(
														"현재까지 합은 %d 입니다.%n",
														sum);
												System.out
														.println("21을 넘었기 때문에 패배하였습니다.");
												betting(money, betting, sum, sumdealer);
											}
											System.out
													.printf("A를 11에서 1로 바꾸어 현재까지 합은 %d 입니다.%n",
															sum);
											if (sum == 21) {
												System.out
														.println("BlackJack 입니다!");
												betting(money, betting, sum, sumdealer);
											}
										} else {
											System.out.printf(
													"현재까지 합은 %d 입니다.", sum);
										}
										System.out.println();
										System.out
												.println("hit 하려면 1을, stay하려면 아무거나 입력하시오.☞");
										enter = scan4.nextInt();
										if (enter == 1) {
											sum = sum + value[9];
											sumchange = sumchange + value[9];
											if (value[9] == 11) {
												sumchange = sum - 10;
											}
											System.out.printf(
													"[열 번째 카드]는 %d 입니다.",
													randomnum[9]);

											System.out.println();
											if (sum == 21) {
												System.out
														.println("BlackJack 입니다!");
												betting(money, betting, sum, sumdealer);
											}
											if (sum > 21) {
												if (value[0] == 11
														|| value[1] == 11
														|| value[2] == 11
														|| value[3] == 11
														|| value[4] == 11
														|| value[5] == 11
														|| value[6] == 11
														|| value[7] == 11
														|| value[8] == 11
														|| value[9] == 11) {
													sum = sumchange;
												}
												if (sum > 21) {
													System.out
															.printf("현재까지 합은 %d 입니다.%n",
																	sum);
													System.out
															.println("21을 넘었기 때문에 패배하였습니다.");
													betting(money, betting, sum, sumdealer);
												}
												System.out
														.printf("A를 11에서 1로 바꾸어 현재까지 합은 %d 입니다.%n",
																sum);
												if (sum == 21) {
													System.out
															.println("BlackJack 입니다!");
													betting(money, betting, sum, sumdealer);
												}
											} else {
												System.out.printf(
														"현재까지 합은 %d 입니다.%n",
														sum);
											}
											System.out.println();
											System.out
													.println("hit 하려면 1을, stay하려면 아무거나 입력하시오.☞");
											enter = scan4.nextInt();
											if (enter == 1) {
												sum = sum + value[10];
												System.out
														.printf("[열 한 번째 카드]는 %d 입니다.%n",
																randomnum[10]);
												System.out.printf(
														"현재까지 합은 %d 입니다.%n",
														sum);
												if (sum == 21) {
													System.out
															.println("BlackJack 입니다!");
													betting(money, betting, sum, sumdealer);
												}
												if (sum > 21) {
													if (sum > 21) {
														System.out
																.println("21을 넘었기 때문에 패배하였습니다.");
														betting(money, betting, sum, sumdealer);
													}
												}
											}}}}}}}}}}
										
		/************************* dealer ********************************/



										
	

	private void betting(int money, int betting, int sum, int sumdealer) {

		if (sum <= 21) {
			if (sum == 21) {
				money = money + 2 * betting;
			}
			if (sum != 21) {
				if (sum > sumdealer) {
					money = money + betting;
				} else if (sum < sumdealer) {
					money = money - betting;
				}
			}
		} else if (sum > 21) {
			money = money - betting;
		}

		try {
			PrintWriter pw = new PrintWriter(new File("bank/money.txt"));
			pw.println(money);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		determine(sum, sumdealer, betting, money);
	}

	private void determine(int sum, int sumdealer, int betting, int money) {
		System.out.println();

		if (sum < 21) {
			if (sumdealer <= 21) {
				System.out.printf("당신의 결과는 %d 입니다.", sum);
				System.out.println();
				System.out.printf("Dealer의 결과는 %d 입니다.", sumdealer);
				/** 사용자의 결과가*21이 넘었을경우 딜러의결과를 알려주지않기 위해 이곳에 배치 **/
				if (sum > sumdealer) {
					System.out.println("당신의 승리입니다!");
				} else if (sum < sumdealer) {
					System.out.println("당신의 패배입니다!");
				} else {
					System.out.println("동점입니다!");
				}
			}

			else {
				System.out.printf("당신의 결과는 %d 입니다.", sum);
				System.out.printf("Dealer의 결과는 %d 입니다.", sumdealer);
				System.out.println("당신의 승리입니다.");
			}

		}
		restart(betting, money);
	}

	private void restart(int betting, int money) {
		if (money > 0) {
			Scanner scan = new Scanner(System.in);
			System.out.println("게임을 더 진행하시겠습니까? 진행하려면 1, 그만 두려면 아무거나 입력");
			int contin = scan.nextInt();
			if (contin == 1) {
				System.out.printf("당신의 현재 잔고는 $%d입니다.%n", money);// dialogue로 구현
				start();
			} else {
				System.out.println("게임을 종료합니다.");
			}

		} else {
			System.out.println();
			System.out.println("Game Over. 올인 당하셨습니다.");
		}
	}

}
