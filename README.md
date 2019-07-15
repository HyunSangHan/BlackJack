# BlackJack
Card game for an assignment when I was in the university

# Overview
![overview](https://user-images.githubusercontent.com/44132406/61220899-44816b80-a752-11e9-8195-b9304cf5ac24.jpg)

# Details
1. Random함수를 통하여 52개의 숫자를 무작위로 뽑고, 각각의 숫자에 카드 1 장씩을 일대일 대응시켰음
  (eg: 1 = clover A,  13 = clover K, 14 = clover A, 15 = clover 2 ...)
2. 각 무늬의 2, 3 등의 숫자는 그 자체의 값을 갖지만, K, Q, J를 10의 값을, A는 1 혹은 11의 값을 갖는 다는 블랙잭의 rule 을 적용시키기 위하여 각 카드에 대응 된 숫자를 13으로 나눈 나머지를 그 카드의 값으로 하였음
  (eg: clover 2 의 값 = R(15/13) = 2)
3. 각 무늬의 A 카드는 일단 11의 값을 가진다고 가정하고 게임을 진행. 그리고 나서 값의 합이 21이 넘어갈 경우 전체 합에서 10을 빼도록 하여 A를 1로 변환함
4. 값이 21이 되었을 경우 BlackJack이라 하고, 배팅액의 2배를 가져가도록 함
5. 배팅액으로 인해 사용자의 금전 잔고가 변하고 그 내역이 txt파일로 저장되기 때문에, 종료 후 재실행시켜도 금전 잔고는 유지됨
6. 카드의 중복을 방지하였음
