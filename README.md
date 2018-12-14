# BMI 지수계산기

* **2018-12-03**
  * Java 학습용 toyProject BMI지수 계산기
  * 키와 몸무게를 입력받아 해당 점수에 해당하는 결과를 출력

![01](https://github.com/younggeun0/younggeun0.github.io/blob/master/_posts/img/toyProjects/bmi01.PNG?raw=true)

---

* **2018-12-10**
  * java AWT를 이용한 GUI 구현(Frame만)
 
![02](https://github.com/younggeun0/younggeun0.github.io/blob/master/_posts/img/toyProjects/bmi02.png?raw=true)

![03](https://github.com/younggeun0/younggeun0.github.io/blob/master/_posts/img/toyProjects/bmi03.png?raw=true)

---

* **2018-12-11**
  * 종료버튼 event처리 구현

---

* **2018-12-13**
  * GUI 디자인(Frame)과 이벤트처리(Listener 인터페이스들) 분리 구현
    * 키, 몸무게 입력 후 계산버튼 클릭시 라벨값 변경, TextArea에 계산결과에 해당하는 결과 
  * 클래스다이어그램과 결과창
  
![05](https://github.com/younggeun0/younggeun0.github.io/blob/master/_posts/img/toyProjects/bmi05.png)

![04](https://github.com/younggeun0/younggeun0.github.io/blob/master/_posts/img/toyProjects/bmi04.png)

---

* **2018-12-13**
  * Dialog를 사용한 결과출력 구현
  * 패키지를 run, view, event로 분리했음
    * Dialog 작업을 위한 클래스 추가 생성(BMIResult, BMIResultEvt)
    * 계산버튼 클릭 시 연산작업 후 결과값을 Dialog에게 전달해서 결과 출력

![06](https://github.com/younggeun0/younggeun0.github.io/blob/master/_posts/img/toyProjects/bmi06.png)

![07](https://github.com/younggeun0/younggeun0.github.io/blob/master/_posts/img/toyProjects/bmi07.png)

---


### 추가구현 과제
* swing을 이용한 GUI 구현 
* 히스토리 구현
* 파일출력 구현
* 메뉴바를 이용 기존 toyProjectLotto와 통합처리

