package leeandhan;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BlackGUI {

	private JFrame frmblackjack;
	private JTextField bank;
	private JTextField jack;
	private Image cardimage[] = new Image[52];
	private Image photo[] = new Image[2];
	private JPanel[] panelCard1;
	private JPanel[] panelCard2;
	private JPanel panelPhoto1;
	private JPanel panelPhoto2;
	private JTextField txtDealer;
	private JTextField txtYou;
	private int enter;
	private int[] randomnum = new int[22];

	private int[] value = new int[11];
	private int[] randomdealer = new int[11];
	private int sum;
	private int sumchange;
	private int sumdealer;
	private int betting;
	private int money;
	// private int vision;

	private int r;
	int ans;
	private JTextField victory;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BlackGUI window = new BlackGUI();
					window.frmblackjack.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	public BlackGUI() {

		buildGUI();
		// mainMethod();

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
		sum = 0;
		// enter = 0;

		boolean isGameStart = false;
		do {
			isGameStart = game();
		} while (isGameStart == false);

		// dealer();
		// updateUSER();
	}

	private boolean game() {

		try {
			BufferedReader br = new BufferedReader(new FileReader(
					"bank/money.txt"));
			String line = null;
			line = br.readLine();
			StringTokenizer stk = new StringTokenizer(line);
			money = Integer.parseInt(stk.nextToken());
			// ////////////////////////////////////
			String yourbank = String.format("현재 잔고 : %d", money);
			bank.setText(yourbank);

			// System.out.printf("당신의 현재 잔고는 $%d입니다.%n", money);//잔고 칸 따로
			// 만들기!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			System.out.println("새로운 게임을 시작합니다.");// dialogue로 구현

			/*
			 * if (money <= 0)
			 * {/////////////////////////////////////////////////
			 * /////////////////////////////////////////////////////////
			 * 
			 * restart();
			 * 
			 * } else
			 */
			if (money > 0) {
				/*
				 * Scanner scan = new Scanner(System.in);
				 * System.out.println("베팅 할 금액을 입력하세요.");//창띄우기 int betting =
				 * scan.nextInt();
				 */
				String a = JOptionPane.showInputDialog("베팅 할 금액을 입력하세요.", 100);
				betting = Integer.parseInt(a);

				if (betting > 0 && betting <= money) {
					card();
					return true;
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
			System.out.println("=========================="); // 구현
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private void card() {
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
			// betting();
			// /////////////////초기화단계//////////////////////
			rearrange();
			// ///////////////////////
			printCard(sumdealer, valuedealer);

		}
	}

	private void rearrange() {
		image1();
		enter = 0;
		updateUSER();
		int vision = 1;
		updateDEALER(vision);
		String vic = String.format("");
		victory.setText(vic);
		String bjack = String.format("");
		jack.setText(bjack);
	}

	private void updateUSER() {
		if (frmblackjack != null) {
			for (int i = 0; i < panelCard2.length; i++) {
				if (i < enter + 2) {
					panelCard2[i].setVisible(true);
				} else {
					panelCard2[i].setVisible(false);
				}
			}
			frmblackjack.repaint();
		}
	}

	private void updateDEALER(int vision) {
		if (frmblackjack != null) {
			for (int i = 0; i < panelCard1.length; i++) {
				if (i < vision) {
					panelCard1[i].setVisible(true);
				} else {
					panelCard1[i].setVisible(false);
				}
			}
			frmblackjack.repaint();
		}
	}

	@SuppressWarnings("serial")
	private void buildGUI() {

		for (int i = 0; i < photo.length; i++) {
			int j = i + 1;
			photo[i] = Toolkit.getDefaultToolkit().getImage(
					"photo/photo" + j + ".JPG");
		}
		frmblackjack = new JFrame();
		frmblackjack.getContentPane().setBackground(new Color(0, 51, 0));
		frmblackjack.getContentPane().setForeground(new Color(0, 153, 102));
		frmblackjack.setTitle("[BlackJack \uAC8C\uC784] (\uCD5C\uC2E0\uD310) made by \uD55C\uD604\uC0C1");
		frmblackjack.setBounds(100, 100, 750, 630);
		frmblackjack.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmblackjack.getContentPane().setLayout(null);

		panelPhoto1 = new JPanel() {
			public void paintComponent(Graphics g) {
				// super.paintComponents(g);
				g.drawImage(photo[0], 0, 0, 91, 177, panelPhoto1);
			}
		};
		panelPhoto1.setBackground(new Color(0, 51, 0));
		panelPhoto1.setBounds(12, 40, 91, 177);
		frmblackjack.getContentPane().add(panelPhoto1);

		panelPhoto2 = new JPanel() {
			public void paintComponent(Graphics g) {
				// super.paintComponents(g);
				g.drawImage(photo[1], 0, 0, 91, 177, panelPhoto2);
			}
		};
		panelPhoto2.setBackground(new Color(0, 51, 0));
		panelPhoto2.setBounds(12, 318, 91, 177);
		frmblackjack.getContentPane().add(panelPhoto2);

		txtDealer = new JTextField();
		txtDealer.setForeground(new Color(255, 255, 0));
		txtDealer.setBackground(new Color(0, 51, 0));
		txtDealer.setFont(new Font("HY크리스탈M", Font.PLAIN, 12));
		txtDealer.setEditable(false);
		txtDealer.setText("Dealer(\uB51C\uB7EC)");
		txtDealer.setBounds(12, 227, 91, 32);
		frmblackjack.getContentPane().add(txtDealer);
		txtDealer.setColumns(10);

		txtYou = new JTextField();
		txtYou.setForeground(new Color(255, 255, 0));
		txtYou.setBackground(new Color(0, 51, 0));
		txtYou.setFont(new Font("HY크리스탈M", Font.PLAIN, 13));
		txtYou.setEditable(false);
		txtYou.setText("You(\uC0AC\uC6A9\uC790)");
		txtYou.setBounds(12, 503, 91, 32);
		frmblackjack.getContentPane().add(txtYou);
		txtYou.setColumns(10);

		bank = new JTextField();
		bank.setFont(new Font("돋움", Font.BOLD, 14));
		bank.setEditable(false);
		bank.setBounds(119, 271, 120, 32);
		frmblackjack.getContentPane().add(bank);
		bank.setColumns(10);

		jack = new JTextField();
		jack.setForeground(new Color(255, 255, 0));
		jack.setBackground(new Color(0, 51, 0));
		jack.setFont(new Font("돋움체", Font.BOLD, 15));
		jack.setBounds(404, 271, 114, 32);
		frmblackjack.getContentPane().add(jack);
		jack.setColumns(10);

		victory = new JTextField();
		victory.setBackground(new Color(0, 51, 0));
		victory.setForeground(new Color(255, 255, 0));
		victory.setFont(new Font("굴림", Font.BOLD, 14));
		victory.setBounds(247, 271, 145, 32);
		frmblackjack.getContentPane().add(victory);
		victory.setColumns(10);

		JMenuBar menuBar = new JMenuBar();
		frmblackjack.setJMenuBar(menuBar);

		JMenu menu = new JMenu("Menu");
		menuBar.add(menu);

		JMenuItem mntmNewGame = new JMenuItem("New game");
		menu.add(mntmNewGame);
		mntmNewGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				mainMethod();
			}
		});
		JMenuItem mntmExit = new JMenuItem("Exit");

		menu.add(mntmExit);
		
		
		
		mntmExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				System.exit(0);
			}
		});
		/*
		 * enter = 0; buttonHit.addActionListener(new ActionListener() { public
		 * void actionPerformed(ActionEvent e) { // eoeo(); // printCard();
		 * System.out.printf("현재까지 합은 %d 입니다.ㅋㅋ%n", sum); enter = enter + 1;
		 * printCard(enter); } });
		 */
		// image1();
	}

	/*
	 * private void eoeo() { enter++; printCard(enter); }
	 */

	private void printCard(int sumdealer, int[] valuedealer) {
		sum = value[0] + value[1];

		if (value[0] == 11 || value[1] == 11) {
			sumchange = sum - 10;
		}/*
		 * System.out.println("당신의"); System.out.printf("[첫 번째 카드]는 %d 입니다.",
		 * randomnum[0]); System.out.printf("%n[두 번째 카드]는 %d 입니다.",
		 * randomnum[1]); if (sum > 21) {
		 * System.out.printf("A를 11에서 1로 바꾸어 현재까지 합은 %d 입니다.%n", sumchange); }
		 * else { System.out.printf("%n합은 %d입니다.%n", sum); }
		 * System.out.println("==========================");
		 */
		while (true) {

			if (sum == 21) {
				System.out.println("BlackJack 입니다!");
				String vic = String.format("승리하셨습니다!");
				victory.setText(vic);
				String bjack = String.format("BlackJack!");
				jack.setText(bjack);
				break;

			}
			do {
				ans = JOptionPane.showConfirmDialog(null, "카드를 더 받으시겠습니까?",
						"Hit 여부를 선택", JOptionPane.YES_NO_OPTION);
			} while (ans != JOptionPane.YES_OPTION
					&& ans != JOptionPane.NO_OPTION);

			if (ans == JOptionPane.YES_OPTION) {
				enter = 1;
				updateUSER();
				sum = sum + value[2];
				sumchange = sumchange + value[2];
				if (value[2] == 11) {
					sumchange = sum - 10;
				}
				System.out.printf("[세 번째 카드]는 %d 입니다.", randomnum[2]);

				System.out.println();
				if (sum == 21) {
					System.out.println("BlackJack 입니다!");
					String vic = String.format("승리하셨습니다!");
					victory.setText(vic);
					String bjack = String.format("BlackJack!");
					jack.setText(bjack);
					break;
				}
				if (sum > 21) {
					if (value[0] == 11 || value[1] == 11 || value[2] == 11) {
						sum = sumchange;
					}
					if (sum > 21) {
						System.out.printf("현재까지 합은 %d 입니다.%n", sum);
						String vic = String.format("패배하셨습니다!");
						victory.setText(vic);
						System.out.println("21을 넘었기 때문에 패배하였습니다.");
						break;
					}
					System.out.printf("A를 11에서 1로 바꾸어 현재까지 합은 %d 입니다.%n", sum);
					if (sum == 21) {
						System.out.println("BlackJack 입니다!");
						String vic = String.format("승리하셨습니다!");
						victory.setText(vic);
						String bjack = String.format("BlackJack!");
						jack.setText(bjack);
						break;
					}
				} else {
					System.out.printf("현재까지 합은 %d 입니다.%n", sum);
				}
				System.out.println();
				do {
					ans = JOptionPane.showConfirmDialog(null, "카드를 더 받으시겠습니까?",
							"Hit 여부를 선택", JOptionPane.YES_NO_OPTION);
				} while (ans != JOptionPane.YES_OPTION
						&& ans != JOptionPane.NO_OPTION);

				if (ans == JOptionPane.YES_OPTION) {
					enter = 2;
					updateUSER();
					sum = sum + value[3];
					sumchange = sumchange + value[3];
					if (value[3] == 11) {
						sumchange = sum - 10;
					}
					System.out.printf("[네 번째 카드]는 %d 입니다.", randomnum[3]);

					System.out.println();
					if (sum == 21) {
						System.out.println("BlackJack 입니다!");
						String vic = String.format("승리하셨습니다!");
						victory.setText(vic);
						String bjack = String.format("BlackJack!");
						jack.setText(bjack);
						break;
					}
					if (sum > 21) {
						if (value[0] == 11 || value[1] == 11 || value[2] == 11
								|| value[3] == 11) {
							sum = sumchange;
						}
						if (sum > 21) {
							System.out.printf("현재까지 합은 %d 입니다.%n", sum);
							String vic = String.format("패배하셨습니다!");
							victory.setText(vic);
							System.out.println("21을 넘었기 때문에 패배하였습니다.");
							break;
						}
						System.out.printf("A를 11에서 1로 바꾸어 현재까지 합은 %d 입니다.%n",
								sum);
						if (sum == 21) {
							System.out.println("BlackJack 입니다!");
							String vic = String.format("승리하셨습니다!");
							victory.setText(vic);
							String bjack = String.format("BlackJack!");
							jack.setText(bjack);
							break;
						}
					} else {
						System.out.printf("현재까지 합은 %d 입니다.%n", sum);
					}
					System.out.println();
					do {
						ans = JOptionPane.showConfirmDialog(null,
								"카드를 더 받으시겠습니까?", "Hit 여부를 선택",
								JOptionPane.YES_NO_OPTION);
					} while (ans != JOptionPane.YES_OPTION
							&& ans != JOptionPane.NO_OPTION);

					if (ans == JOptionPane.YES_OPTION) {
						enter = 3;
						updateUSER();
						sum = sum + value[4];
						sumchange = sumchange + value[4];
						if (value[4] == 11) {
							sumchange = sum - 10;
						}
						System.out.printf("[다섯 번째 카드]는 %d 입니다.", randomnum[4]);

						System.out.println();
						if (sum == 21) {
							System.out.println("BlackJack 입니다!");
							String vic = String.format("승리하셨습니다!");
							victory.setText(vic);
							String bjack = String.format("BlackJack!");
							jack.setText(bjack);
							break;
						}
						if (sum > 21) {
							if (value[0] == 11 || value[1] == 11
									|| value[2] == 11 || value[3] == 11
									|| value[4] == 11) {
								sum = sumchange;
							}
							if (sum > 21) {
								System.out.printf("현재까지 합은 %d 입니다.%n", sum);
								String vic = String.format("패배하셨습니다!");
								victory.setText(vic);
								System.out.println("21을 넘었기 때문에 패배하였습니다.");
								break;
							}
							System.out.printf(
									"A를 11에서 1로 바꾸어 현재까지 합은 %d 입니다.%n", sum);
							if (sum == 21) {
								System.out.println("BlackJack 입니다!");
								String vic = String.format("승리하셨습니다!");
								victory.setText(vic);
								String bjack = String.format("BlackJack!");
								jack.setText(bjack);
								break;
							}
						} else {
							System.out.printf("현재까지 합은 %d 입니다.%n", sum);
						}
						System.out.println();
						do {
							ans = JOptionPane.showConfirmDialog(null,
									"카드를 더 받으시겠습니까?", "Hit 여부를 선택",
									JOptionPane.YES_NO_OPTION);
						} while (ans != JOptionPane.YES_OPTION
								&& ans != JOptionPane.NO_OPTION);

						if (ans == JOptionPane.YES_OPTION) {
							enter = 4;
							updateUSER();
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
								String vic = String.format("승리하셨습니다!");
								victory.setText(vic);
								String bjack = String.format("BlackJack!");
								jack.setText(bjack);
								break;
							}
							if (sum > 21) {
								if (value[0] == 11 || value[1] == 11
										|| value[2] == 11 || value[3] == 11
										|| value[4] == 11 || value[5] == 11) {
									sum = sumchange;
								}
								if (sum > 21) {
									System.out.printf("현재까지 합은 %d 입니다.%n", sum);
									String vic = String.format("패배하셨습니다!");
									victory.setText(vic);
									System.out.println("21을 넘었기 때문에 패배하였습니다.");
									break;
								}
								System.out
										.printf("A를 11에서 1로 바꾸어 현재까지 합은 %d 입니다.%n",
												sum);
								if (sum == 21) {
									System.out.println("BlackJack 입니다!");
									String vic = String.format("승리하셨습니다!");
									victory.setText(vic);
									String bjack = String.format("BlackJack!");
									jack.setText(bjack);
									break;
								}
							} else {
								System.out.printf("현재까지 합은 %d 입니다.%n", sum);
							}
							System.out.println();
							do {
								ans = JOptionPane.showConfirmDialog(null,
										"카드를 더 받으시겠습니까?", "Hit 여부를 선택",
										JOptionPane.YES_NO_OPTION);
							} while (ans != JOptionPane.YES_OPTION
									&& ans != JOptionPane.NO_OPTION);

							if (ans == JOptionPane.YES_OPTION) {
								enter = 5;
								updateUSER();
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
									String vic = String.format("승리하셨습니다!");
									victory.setText(vic);
									String bjack = String.format("BlackJack!");
									jack.setText(bjack);
									break;
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
										String vic = String.format("패배하셨습니다!");
										victory.setText(vic);
										System.out
												.println("21을 넘었기 때문에 패배하였습니다.");
										break;
									}
									System.out.printf(
											"A를 11에서 1로 바꾸어 현재까지 합은 %d 입니다.%n",
											sum);
									if (sum == 21) {
										System.out.println("BlackJack 입니다!");
										String vic = String.format("승리하셨습니다!");
										victory.setText(vic);
										String bjack = String
												.format("BlackJack!");
										jack.setText(bjack);
										break;
									}
								} else {
									System.out.printf("현재까지 합은 %d 입니다.%n", sum);
								}
								System.out.println();
								do {
									ans = JOptionPane.showConfirmDialog(null,
											"카드를 더 받으시겠습니까?", "Hit 여부를 선택",
											JOptionPane.YES_NO_OPTION);
								} while (ans != JOptionPane.YES_OPTION
										&& ans != JOptionPane.NO_OPTION);

								if (ans == JOptionPane.YES_OPTION) {
									enter = 6;
									updateUSER();
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
										String vic = String.format("승리하셨습니다!");
										victory.setText(vic);
										String bjack = String
												.format("BlackJack!");
										jack.setText(bjack);
										break;
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
											String vic = String
													.format("패배하셨습니다!");
											victory.setText(vic);
											System.out
													.println("21을 넘었기 때문에 패배하였습니다.");
											break;
										}
										System.out
												.printf("A를 11에서 1로 바꾸어 현재까지 합은 %d 입니다.%n",
														sum);
										if (sum == 21) {
											System.out
													.println("BlackJack 입니다!");
											String vic = String
													.format("승리하셨습니다!");
											victory.setText(vic);
											String bjack = String
													.format("BlackJack!");
											jack.setText(bjack);
											break;
										}
									} else {
										System.out.printf("현재까지 합은 %d 입니다.",
												sum);
									}
									System.out.println();
									do {
										ans = JOptionPane.showConfirmDialog(
												null, "카드를 더 받으시겠습니까?",
												"Hit 여부를 선택",
												JOptionPane.YES_NO_OPTION);
									} while (ans != JOptionPane.YES_OPTION
											&& ans != JOptionPane.NO_OPTION);

									if (ans == JOptionPane.YES_OPTION) {
										enter = 7;
										updateUSER();
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
											String vic = String
													.format("승리하셨습니다!");
											victory.setText(vic);
											String bjack = String
													.format("BlackJack!");
											jack.setText(bjack);
											break;
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
												String vic = String
														.format("패배하셨습니다!");
												victory.setText(vic);
												System.out
														.println("21을 넘었기 때문에 패배하였습니다.");
												break;
											}
											System.out
													.printf("A를 11에서 1로 바꾸어 현재까지 합은 %d 입니다.%n",
															sum);
											if (sum == 21) {
												System.out
														.println("BlackJack 입니다!");
												String vic = String
														.format("승리하셨습니다!");
												victory.setText(vic);
												String bjack = String
														.format("BlackJack!");
												jack.setText(bjack);
												break;
											}
										} else {
											System.out.printf(
													"현재까지 합은 %d 입니다.", sum);
										}
										System.out.println();
										do {
											ans = JOptionPane
													.showConfirmDialog(
															null,
															"카드를 더 받으시겠습니까?",
															"Hit 여부를 선택",
															JOptionPane.YES_NO_OPTION);
										} while (ans != JOptionPane.YES_OPTION
												&& ans != JOptionPane.NO_OPTION);

										if (ans == JOptionPane.YES_OPTION) {
											enter = 8;
											updateUSER();
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
												String vic = String
														.format("승리하셨습니다!");
												victory.setText(vic);
												String bjack = String
														.format("BlackJack!");
												jack.setText(bjack);
												break;
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
													String vic = String
															.format("패배하셨습니다!");
													victory.setText(vic);
													System.out
															.println("21을 넘었기 때문에 패배하였습니다.");
													break;
												}
												System.out
														.printf("A를 11에서 1로 바꾸어 현재까지 합은 %d 입니다.%n",
																sum);
												if (sum == 21) {
													System.out
															.println("BlackJack 입니다!");
													String vic = String
															.format("승리하셨습니다!");
													victory.setText(vic);
													String bjack = String
															.format("BlackJack!");
													jack.setText(bjack);
													break;
												}
											} else {
												System.out.printf(
														"현재까지 합은 %d 입니다.%n",
														sum);
											}
											System.out.println();
											do {
												ans = JOptionPane
														.showConfirmDialog(
																null,
																"카드를 더 받으시겠습니까?",
																"Hit 여부를 선택",
																JOptionPane.YES_NO_OPTION);
											} while (ans != JOptionPane.YES_OPTION
													&& ans != JOptionPane.NO_OPTION);

											if (ans == JOptionPane.YES_OPTION) {
												enter = 9;
												updateUSER();
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
													String vic = String
															.format("승리하셨습니다!");
													victory.setText(vic);
													String bjack = String
															.format("BlackJack!");
													jack.setText(bjack);
													break;
												}
												if (sum > 21) {
													if (sum > 21) {
														System.out
																.println("21을 넘었기 때문에 패배하였습니다.");
														String vic = String
																.format("패배하셨습니다!");
														victory.setText(vic);
														break;
													}
												}

											} else {
												break;
											}
										} else {
											break;
										}
									} else {
										break;
									}
								}

								else {
									break;
								}

							} else {
								break;
							}
						} else {
							break;
						}
					} else {
						break;
					}
				} else {
					break;
				}
			} else {
				break;
			}

		}
		next(sumdealer, valuedealer);
	}

	private void image1() {
		/*
		 * for (int i = 0; i < cardimage.length; i++) { int j = i +1;
		 * cardimage[i] = Toolkit.getDefaultToolkit().getImage( "cardimage/" + j
		 * + ".JPG"); }
		 */
		cardimage[0] = Toolkit.getDefaultToolkit().getImage(
				"cardimage/" + randomnum[11] + ".JPG");
		cardimage[1] = Toolkit.getDefaultToolkit().getImage(
				"cardimage/" + randomnum[12] + ".JPG");
		cardimage[2] = Toolkit.getDefaultToolkit().getImage(
				"cardimage/" + randomnum[13] + ".JPG");
		cardimage[3] = Toolkit.getDefaultToolkit().getImage(
				"cardimage/" + randomnum[14] + ".JPG");
		cardimage[4] = Toolkit.getDefaultToolkit().getImage(
				"cardimage/" + randomnum[15] + ".JPG");
		cardimage[5] = Toolkit.getDefaultToolkit().getImage(
				"cardimage/" + randomnum[16] + ".JPG");
		cardimage[11] = Toolkit.getDefaultToolkit().getImage(
				"cardimage/" + randomnum[0] + ".JPG");
		cardimage[12] = Toolkit.getDefaultToolkit().getImage(
				"cardimage/" + randomnum[1] + ".JPG");
		cardimage[13] = Toolkit.getDefaultToolkit().getImage(
				"cardimage/" + randomnum[2] + ".JPG");
		cardimage[14] = Toolkit.getDefaultToolkit().getImage(
				"cardimage/" + randomnum[3] + ".JPG");
		cardimage[15] = Toolkit.getDefaultToolkit().getImage(
				"cardimage/" + randomnum[4] + ".JPG");
		cardimage[16] = Toolkit.getDefaultToolkit().getImage(
				"cardimage/" + randomnum[5] + ".JPG");

		panelCard1 = new JPanel[6];
		panelCard1[0] = new JPanel() {
			public void paintComponent(Graphics g) {
				// super.paintComponents(g);
				g.drawImage(cardimage[0], 0, 0, 67, 79, panelCard1[0]);
			}
		};
		panelCard1[0].setBackground(new Color(0, 51, 0));
		panelCard1[0].setBounds(130, 85, 67, 79);
		frmblackjack.getContentPane().add(panelCard1[0]);

		panelCard1[1] = new JPanel() {
			public void paintComponent(Graphics g) {
				// super.paintComponents(g);
				g.drawImage(cardimage[1], 0, 0, 67, 79, panelCard1[1]);
			}
		};
		panelCard1[1].setBackground(new Color(0, 51, 0));
		panelCard1[1].setBounds(220, 85, 67, 79);
		frmblackjack.getContentPane().add(panelCard1[1]);

		panelCard1[2] = new JPanel() {
			public void paintComponent(Graphics g) {
				// super.paintComponents(g);
				g.drawImage(cardimage[2], 0, 0, 67, 79, panelCard1[2]);
			}
		};
		panelCard1[2].setBackground(new Color(0, 51, 0));
		panelCard1[2].setBounds(310, 85, 67, 79);
		frmblackjack.getContentPane().add(panelCard1[2]);

		panelCard1[3] = new JPanel() {
			public void paintComponent(Graphics g) {
				// super.paintComponents(g);
				g.drawImage(cardimage[3], 0, 0, 67, 79, panelCard1[3]);
			}
		};
		panelCard1[3].setBackground(new Color(0, 51, 0));
		panelCard1[3].setBounds(400, 85, 67, 79);
		frmblackjack.getContentPane().add(panelCard1[3]);

		panelCard1[4] = new JPanel() {
			public void paintComponent(Graphics g) {
				// super.paintComponents(g);
				g.drawImage(cardimage[4], 0, 0, 67, 79, panelCard1[4]);
			}
		};
		panelCard1[4].setBackground(new Color(0, 51, 0));
		panelCard1[4].setBounds(490, 85, 67, 79);
		frmblackjack.getContentPane().add(panelCard1[4]);

		panelCard1[5] = new JPanel() {
			public void paintComponent(Graphics g) {
				// super.paintComponents(g);
				g.drawImage(cardimage[5], 0, 0, 67, 79, panelCard1[5]);
			}
		};
		panelCard1[5].setBackground(new Color(0, 51, 0));
		panelCard1[5].setBounds(580, 85, 67, 79);
		frmblackjack.getContentPane().add(panelCard1[5]);

		panelCard2 = new JPanel[6];
		panelCard2[0] = new JPanel() {
			public void paintComponent(Graphics g) {
				// super.paintComponents(g);
				g.drawImage(cardimage[11], 0, 0, 67, 79, panelCard2[0]);
			}
		};
		panelCard2[0].setBackground(new Color(0, 51, 0));
		panelCard2[0].setBounds(130, 370, 67, 79);
		frmblackjack.getContentPane().add(panelCard2[0]);

		panelCard2[1] = new JPanel() {
			public void paintComponent(Graphics g) {
				// super.paintComponents(g);
				g.drawImage(cardimage[12], 0, 0, 67, 79, panelCard2[1]);
			}
		};
		panelCard2[1].setBackground(new Color(0, 51, 0));
		panelCard2[1].setBounds(220, 370, 67, 79);
		frmblackjack.getContentPane().add(panelCard2[1]);

		panelCard2[2] = new JPanel() {
			public void paintComponent(Graphics g) {
				// super.paintComponents(g);
				g.drawImage(cardimage[13], 0, 0, 67, 79, panelCard2[2]);
			}
		};
		panelCard2[2].setBackground(new Color(0, 51, 0));
		panelCard2[2].setBounds(310, 370, 67, 79);
		frmblackjack.getContentPane().add(panelCard2[2]);

		panelCard2[3] = new JPanel() {
			public void paintComponent(Graphics g) {
				// super.paintComponents(g);
				g.drawImage(cardimage[14], 0, 0, 67, 79, panelCard2[3]);
			}
		};
		panelCard2[3].setBackground(new Color(0, 51, 0));
		panelCard2[3].setBounds(400, 370, 67, 79);
		frmblackjack.getContentPane().add(panelCard2[3]);

		panelCard2[4] = new JPanel() {
			public void paintComponent(Graphics g) {
				// super.paintComponents(g);
				g.drawImage(cardimage[15], 0, 0, 67, 79, panelCard2[4]);
			}
		};
		panelCard2[4].setBackground(new Color(0, 51, 0));
		panelCard2[4].setBounds(490, 370, 67, 79);
		frmblackjack.getContentPane().add(panelCard2[4]);

		panelCard2[5] = new JPanel() {
			public void paintComponent(Graphics g) {
				// super.paintComponents(g);
				g.drawImage(cardimage[16], 0, 0, 67, 79, panelCard2[5]);
			}
		};
		panelCard2[5].setBackground(new Color(0, 51, 0));
		panelCard2[5].setBounds(580, 370, 67, 79);
		frmblackjack.getContentPane().add(panelCard2[5]);

	}

	private void next(int sumdealer, int[] valuedealer) {
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
						} else {
							continue;
						}
					} else {
						break;
					}
				}
				try {
					PrintWriter pw2 = new PrintWriter(new File("input/r.txt"));
					pw2.println(r);
					pw2.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

			}
			break;

		}
		betting(sumdealer, valuedealer);
	}

	private void betting(int sumdealer, int[] valuedealer) {

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

		determine(sumdealer, valuedealer);
	}

	private void determine(int sumdealer, int[] valuedealer) {
		System.out.println();

		try {
			BufferedReader br = new BufferedReader(
					new FileReader("input/r.txt"));
			String line = null;
			line = br.readLine();
			StringTokenizer stk = new StringTokenizer(line);
			r = Integer.parseInt(stk.nextToken());
			int vision = r + 2;
			updateDEALER(vision);

			if (sum < 21) {
				if (sumdealer <= 21) {
					System.out.printf("당신의 결과는 %d 입니다.", sum);
					System.out.println();
					System.out.printf("Dealer의 결과는 %d 입니다.", sumdealer);

					// updateDEALER();
					/** 사용자의 결과가*21이 넘었을경우 딜러의결과를 알려주지않기 위해 이곳에 배치 **/
					if (sum > sumdealer) {
						System.out.println("당신의 승리입니다!");
						String vic = String.format("승리하셨습니다!");
						victory.setText(vic);
					} else if (sum < sumdealer) {
						System.out.println("당신의 패배입니다!");
						String vic = String.format("패배하셨습니다!");
						victory.setText(vic);
					} else {
						System.out.println("동점입니다!");
						String vic = String.format("동점입니다!");
						victory.setText(vic);
					}
				}

				else {
					System.out.printf("당신의 결과는 %d 입니다.", sum);
					System.out.printf("Dealer의 결과는 %d 입니다.", sumdealer);
					System.out.println("당신의 승리입니다.");
					String vic = String.format("승리하셨습니다!");
					victory.setText(vic);
				}

			}
			restart();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void restart() {
		if (money > 0) {
			// Scanner scan = new Scanner(System.in);
			// System.out.println("게임을 더 진행하시겠습니까? 진행하려면 1, 그만 두려면 아무거나 입력");
			// int contin = scan.nextInt();
			int contin;

			do {
				contin = JOptionPane.showConfirmDialog(null, "게임을 더 진행하시겠습니까?",
						"진행 여부를 선택하세요.", JOptionPane.YES_NO_OPTION);
			} while (contin != JOptionPane.YES_OPTION
					&& contin != JOptionPane.NO_OPTION);

			if (contin == JOptionPane.YES_OPTION) {
				System.out.printf("당신의 현재 잔고는 $%d입니다.%n", money);// dialogue로 구현

				if (frmblackjack != null) {
					for (int i = 0; i < 5; i++) {
						panelCard1[i].setVisible(false);
						panelCard2[i].setVisible(false);
					}
				}
				start();
			} else {
				System.out.println("게임을 종료합니다.");
				System.exit(0);
			}

			// if (contin == 1) {
			// System.out.printf("당신의 현재 잔고는 $%d입니다.%n", money);// dialogue로 구현
			// start();
			// } else {
			// System.out.println("게임을 종료합니다.");
			// }

		} else {
			String vic = String
					.format("올인 당하셨습니다!");
			victory.setText(vic);
			System.out.println();
			System.out.printf("당신의 현재 잔고는 $%d입니다.%n", money);
			System.out.println("Game Over. 올인 당하셨습니다.");
		}
	}
}
