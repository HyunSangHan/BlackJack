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
		System.out.println("Black Jack (�� ��) ����");
		System.out.println("*********************");
		System.out.println("���� ���:");
		System.out.println("ī�带 �̾� ī���� ���� ���� ����ϰ�, 21�� �ִ��� �������� ����� �����Դϴ�.");
		System.out.println("������ ����ϰ� �Ǹ�, ������ ī�� ���� 17 �̻��� �� ������ ī�带 �̽��ϴ�.");
		System.out.println("������ Hit, Stay�� ���� ī�带 ���� �� ���θ� ������ �� �ֽ��ϴ�.");
		System.out
				.println("ī�� ���� ���� 21�� �ʰ��Ǹ� �й��ϰ� �Ǹ�, 21�� �ȴٸ� �̸� BlackJack�̶� �ϰ� 2���� ���þ��� �ް� �˴ϴ�.");
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
			// System.out.printf("����� ���� �ܰ�� $%d�Դϴ�.%n", money);//�ܰ� ĭ ����
			// �����!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			System.out.println("���ο� ������ �����մϴ�.");// dialogue�� ����
			if (money <= 0) {
				int betting = 0;
				restart(betting, money);
			} else if (money > 0) {
				/*
				 * Scanner scan = new Scanner(System.in);
				 * System.out.println("���� �� �ݾ��� �Է��ϼ���.");//â���� int betting =
				 * scan.nextInt();
				 */
				String a = JOptionPane.showInputDialog("���� �� �ݾ��� �Է��ϼ���.", 100);
				int betting = Integer.parseInt(a);

				if (betting > 0 && betting <= money) {
					card(betting, money);
				} else {
					System.out.println("�ùٸ� ���� �ݾ��� �ƴϹǷ�, �ٽ� �Է��Ͽ� �ּ���.");// dialogue��
																		// ����
					System.out.printf("(����� �ܰ� ����($%d)�� �ݾ׸� �����մϴ�.)%n", money);// ��������
																				// ����
					start();
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("User�� ���� �ݾ� ������ ã�� �� �����ϴ�. �����ڿ��� �����ϼ���.");// dialogue��
																			// ����
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
			System.out.printf("%d�� ° ������ : %d", m + 1, value[m]);
			System.out.println();
			System.out.printf("%d�� ° ������ : %d", m + 1, valuedealer[m]);
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

		System.out.printf("Dealer�� ù��° ī��� %d �Դϴ�.", randomdealer[0]);
		System.out.println();
		System.out.println("==========================");

		/************************* User ********************************/

		sum = value[0] + value[1];

		if (value[0] == 11 || value[1] == 11) {
			sumchange = sum - 10;
		}
		System.out.println("�����");
		System.out.printf("[ù ��° ī��]�� %d �Դϴ�.", randomnum[0]);
		System.out.printf("%n[�� ��° ī��]�� %d �Դϴ�.", randomnum[1]);
		if (sum > 21) {
			System.out.printf("A�� 11���� 1�� �ٲپ� ������� ���� %d �Դϴ�.%n", sumchange);
		} else {
			System.out.printf("%n���� %d�Դϴ�.%n", sum);
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
				System.out.println("BlackJack �Դϴ�!");
				betting(money, betting, sum, sumdealer);
			}
			Scanner scan = new Scanner(System.in);
			System.out.println("hit �Ϸ��� 1��, stay�Ϸ��� �ƹ��ų� �Է��Ͻÿ�.��");
			enter = scan.nextInt();

			if (enter == 1) {
				sum = sum + value[2];
				sumchange = sumchange + value[2];
				if (value[2] == 11) {
					sumchange = sum - 10;
				}
				System.out.printf("[�� ��° ī��]�� %d �Դϴ�.", randomnum[2]);

				Scanner scan2 = new Scanner(System.in);
				System.out.println();
				if (sum == 21) {
					System.out.println("BlackJack �Դϴ�!");
					betting(money, betting, sum, sumdealer);
				}
				if (sum > 21) {
					if (value[0] == 11 || value[1] == 11 || value[2] == 11) {
						sum = sumchange;
					}
					if (sum > 21) {
						System.out.printf("������� ���� %d �Դϴ�.%n", sum);
						System.out.println("21�� �Ѿ��� ������ �й��Ͽ����ϴ�.");
						betting(money, betting, sum, sumdealer);
					}
					System.out.printf("A�� 11���� 1�� �ٲپ� ������� ���� %d �Դϴ�.%n", sum);
					if (sum == 21) {
						System.out.println("BlackJack �Դϴ�!");
						betting(money, betting, sum, sumdealer);
					}
				} else {
					System.out.printf("������� ���� %d �Դϴ�.%n", sum);
				}
				System.out.println();
				System.out.println("hit �Ϸ��� 1��, stay�Ϸ��� �ƹ��ų� �Է��Ͻÿ�.��");
				enter = scan2.nextInt();
				if (enter == 1) {
					sum = sum + value[3];
					sumchange = sumchange + value[3];
					if (value[3] == 11) {
						sumchange = sum - 10;
					}
					System.out.printf("[�� ��° ī��]�� %d �Դϴ�.", randomnum[3]);

					Scanner scan3 = new Scanner(System.in);
					System.out.println();
					if (sum == 21) {
						System.out.println("BlackJack �Դϴ�!");
						betting(money, betting, sum, sumdealer);
					}
					if (sum > 21) {
						if (value[0] == 11 || value[1] == 11 || value[2] == 11
								|| value[3] == 11) {
							sum = sumchange;
						}
						if (sum > 21) {
							System.out.printf("������� ���� %d �Դϴ�.%n", sum);
							System.out.println("21�� �Ѿ��� ������ �й��Ͽ����ϴ�.");
							betting(money, betting, sum, sumdealer);
						}
						System.out.printf("A�� 11���� 1�� �ٲپ� ������� ���� %d �Դϴ�.%n",
								sum);
						if (sum == 21) {
							System.out.println("BlackJack �Դϴ�!");
							betting(money, betting, sum, sumdealer);
						}
					} else {
						System.out.printf("������� ���� %d �Դϴ�.%n", sum);
					}
					System.out.println();
					System.out.println("hit �Ϸ��� 1��, stay�Ϸ��� �ƹ��ų� �Է��Ͻÿ�.��");
					enter = scan3.nextInt();
					if (enter == 1) {
						sum = sum + value[4];
						sumchange = sumchange + value[4];
						if (value[4] == 11) {
							sumchange = sum - 10;
						}
						System.out.printf("[�ټ� ��° ī��]�� %d �Դϴ�.", randomnum[4]);

						Scanner scan4 = new Scanner(System.in);
						System.out.println();
						if (sum == 21) {
							System.out.println("BlackJack �Դϴ�!");
							betting(money, betting, sum, sumdealer);
						}
						if (sum > 21) {
							if (value[0] == 11 || value[1] == 11
									|| value[2] == 11 || value[3] == 11
									|| value[4] == 11) {
								sum = sumchange;
							}
							if (sum > 21) {
								System.out.printf("������� ���� %d �Դϴ�.%n", sum);
								System.out.println("21�� �Ѿ��� ������ �й��Ͽ����ϴ�.");
								betting(money, betting, sum, sumdealer);
							}
							System.out.printf(
									"A�� 11���� 1�� �ٲپ� ������� ���� %d �Դϴ�.%n", sum);
							if (sum == 21) {
								System.out.println("BlackJack �Դϴ�!");
								betting(money, betting, sum, sumdealer);
							}
						} else {
							System.out.printf("������� ���� %d �Դϴ�.%n", sum);
						}
						System.out.println();
						System.out.println("hit �Ϸ��� 1��, stay�Ϸ��� �ƹ��ų� �Է��Ͻÿ�.��");
						enter = scan4.nextInt();
						if (enter == 1) {
							sum = sum + value[5];
							sumchange = sumchange + value[5];
							if (value[5] == 11) {
								sumchange = sum - 10;
							}
							System.out.printf("[���� ��° ī��]�� %d �Դϴ�.",
									randomnum[5]);

							System.out.println();
							if (sum == 21) {
								System.out.println("BlackJack �Դϴ�!");
								betting(money, betting, sum, sumdealer);
							}
							if (sum > 21) {
								if (value[0] == 11 || value[1] == 11
										|| value[2] == 11 || value[3] == 11
										|| value[4] == 11 || value[5] == 11) {
									sum = sumchange;
								}
								if (sum > 21) {
									System.out.printf("������� ���� %d �Դϴ�.%n", sum);
									System.out.println("21�� �Ѿ��� ������ �й��Ͽ����ϴ�.");
									betting(money, betting, sum, sumdealer);
								}
								System.out
										.printf("A�� 11���� 1�� �ٲپ� ������� ���� %d �Դϴ�.%n",
												sum);
								if (sum == 21) {
									System.out.println("BlackJack �Դϴ�!");
									betting(money, betting, sum, sumdealer);
								}
							} else {
								System.out.printf("������� ���� %d �Դϴ�.%n", sum);
							}
							System.out.println();
							System.out
									.println("hit �Ϸ��� 1��, stay�Ϸ��� �ƹ��ų� �Է��Ͻÿ�.��");
							enter = scan4.nextInt();
							if (enter == 1) {
								sum = sum + value[6];
								sumchange = sumchange + value[6];
								if (value[6] == 11) {
									sumchange = sum - 10;
								}
								System.out.printf("[�ϰ� ��° ī��]�� %d �Դϴ�.",
										randomnum[6]);

								System.out.println();
								if (sum == 21) {
									System.out.println("BlackJack �Դϴ�!");
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
										System.out.printf("������� ���� %d �Դϴ�.%n",
												sum);
										System.out
												.println("21�� �Ѿ��� ������ �й��Ͽ����ϴ�.");
										betting(money, betting, sum, sumdealer);
									}
									System.out.printf(
											"A�� 11���� 1�� �ٲپ� ������� ���� %d �Դϴ�.%n",
											sum);
									if (sum == 21) {
										System.out.println("BlackJack �Դϴ�!");
										betting(money, betting, sum, sumdealer);
									}
								} else {
									System.out.printf("������� ���� %d �Դϴ�.%n", sum);
								}
								System.out.println();
								System.out
										.println("hit �Ϸ��� 1��, stay�Ϸ��� �ƹ��ų� �Է��Ͻÿ�.��");
								enter = scan4.nextInt();
								if (enter == 1) {
									sum = sum + value[7];
									sum = sumchange + value[7];
									if (value[7] == 11) {
										sumchange = sum - 10;
									}
									System.out.printf("[���� ��° ī��]�� %d �Դϴ�.",
											randomnum[7]);

									System.out.println();
									if (sum == 21) {
										System.out.println("BlackJack �Դϴ�!");
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
													"������� ���� %d �Դϴ�.%n", sum);
											System.out
													.println("21�� �Ѿ��� ������ �й��Ͽ����ϴ�.");
											betting(money, betting, sum, sumdealer);
										}
										System.out
												.printf("A�� 11���� 1�� �ٲپ� ������� ���� %d �Դϴ�.%n",
														sum);
										if (sum == 21) {
											System.out
													.println("BlackJack �Դϴ�!");
											betting(money, betting, sum, sumdealer);
										}
									} else {
										System.out.printf("������� ���� %d �Դϴ�.",
												sum);
									}
									System.out.println();
									System.out
											.println("hit �Ϸ��� 1��, stay�Ϸ��� �ƹ��ų� �Է��Ͻÿ�.��");
									enter = scan4.nextInt();
									if (enter == 1) {
										sum = sum + value[8];
										sum = sumchange + value[8];
										if (value[8] == 11) {
											sumchange = sum - 10;
										}
										System.out.printf(
												"[��ȩ ��° ī��]�� %d �Դϴ�.",
												randomnum[8]);

										System.out.println();
										if (sum == 21) {
											System.out
													.println("BlackJack �Դϴ�!");
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
														"������� ���� %d �Դϴ�.%n",
														sum);
												System.out
														.println("21�� �Ѿ��� ������ �й��Ͽ����ϴ�.");
												betting(money, betting, sum, sumdealer);
											}
											System.out
													.printf("A�� 11���� 1�� �ٲپ� ������� ���� %d �Դϴ�.%n",
															sum);
											if (sum == 21) {
												System.out
														.println("BlackJack �Դϴ�!");
												betting(money, betting, sum, sumdealer);
											}
										} else {
											System.out.printf(
													"������� ���� %d �Դϴ�.", sum);
										}
										System.out.println();
										System.out
												.println("hit �Ϸ��� 1��, stay�Ϸ��� �ƹ��ų� �Է��Ͻÿ�.��");
										enter = scan4.nextInt();
										if (enter == 1) {
											sum = sum + value[9];
											sumchange = sumchange + value[9];
											if (value[9] == 11) {
												sumchange = sum - 10;
											}
											System.out.printf(
													"[�� ��° ī��]�� %d �Դϴ�.",
													randomnum[9]);

											System.out.println();
											if (sum == 21) {
												System.out
														.println("BlackJack �Դϴ�!");
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
															.printf("������� ���� %d �Դϴ�.%n",
																	sum);
													System.out
															.println("21�� �Ѿ��� ������ �й��Ͽ����ϴ�.");
													betting(money, betting, sum, sumdealer);
												}
												System.out
														.printf("A�� 11���� 1�� �ٲپ� ������� ���� %d �Դϴ�.%n",
																sum);
												if (sum == 21) {
													System.out
															.println("BlackJack �Դϴ�!");
													betting(money, betting, sum, sumdealer);
												}
											} else {
												System.out.printf(
														"������� ���� %d �Դϴ�.%n",
														sum);
											}
											System.out.println();
											System.out
													.println("hit �Ϸ��� 1��, stay�Ϸ��� �ƹ��ų� �Է��Ͻÿ�.��");
											enter = scan4.nextInt();
											if (enter == 1) {
												sum = sum + value[10];
												System.out
														.printf("[�� �� ��° ī��]�� %d �Դϴ�.%n",
																randomnum[10]);
												System.out.printf(
														"������� ���� %d �Դϴ�.%n",
														sum);
												if (sum == 21) {
													System.out
															.println("BlackJack �Դϴ�!");
													betting(money, betting, sum, sumdealer);
												}
												if (sum > 21) {
													if (sum > 21) {
														System.out
																.println("21�� �Ѿ��� ������ �й��Ͽ����ϴ�.");
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
				System.out.printf("����� ����� %d �Դϴ�.", sum);
				System.out.println();
				System.out.printf("Dealer�� ����� %d �Դϴ�.", sumdealer);
				/** ������� �����*21�� �Ѿ������ �����ǰ���� �˷������ʱ� ���� �̰��� ��ġ **/
				if (sum > sumdealer) {
					System.out.println("����� �¸��Դϴ�!");
				} else if (sum < sumdealer) {
					System.out.println("����� �й��Դϴ�!");
				} else {
					System.out.println("�����Դϴ�!");
				}
			}

			else {
				System.out.printf("����� ����� %d �Դϴ�.", sum);
				System.out.printf("Dealer�� ����� %d �Դϴ�.", sumdealer);
				System.out.println("����� �¸��Դϴ�.");
			}

		}
		restart(betting, money);
	}

	private void restart(int betting, int money) {
		if (money > 0) {
			Scanner scan = new Scanner(System.in);
			System.out.println("������ �� �����Ͻðڽ��ϱ�? �����Ϸ��� 1, �׸� �η��� �ƹ��ų� �Է�");
			int contin = scan.nextInt();
			if (contin == 1) {
				System.out.printf("����� ���� �ܰ�� $%d�Դϴ�.%n", money);// dialogue�� ����
				start();
			} else {
				System.out.println("������ �����մϴ�.");
			}

		} else {
			System.out.println();
			System.out.println("Game Over. ���� ���ϼ̽��ϴ�.");
		}
	}

}
