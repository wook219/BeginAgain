# BeginAgain 게시판 프로젝트
<hr>

### Notion (작업일지)
https://www.notion.so/elice-track/BeginAgain-6016c02cceb64c30b6120290a1c2aca3

![image](https://github.com/user-attachments/assets/6aeef5ae-7056-4675-b042-fdabaa5b377b)
![image](https://github.com/user-attachments/assets/84093d43-4410-48e7-a45b-ac76105457bc)
![image](https://github.com/user-attachments/assets/b6561f9c-f0ed-4391-8e78-7a899f701c12)
![image](https://github.com/user-attachments/assets/b6c801ed-47d3-407c-a936-ca9fd4fa3d11)
![image](https://github.com/user-attachments/assets/2f2e3150-a484-4cd6-9603-482ba1b15090)
![image](https://github.com/user-attachments/assets/86225b4a-9d65-4c28-bb3f-640c30f96517)
![image](https://github.com/user-attachments/assets/4bb1c874-5762-4095-894a-6e02410e7a1a)
![image](https://github.com/user-attachments/assets/9a6e6a96-61fd-4135-9afb-13697f4eda5d)

<hr>

### Refactoring
09-19 MapStruct 라이브러리를 이용하여 Dto, Entity간 변환 코드 간소화  
09-20 컨트롤러단에서 Entity를 활용하는 부분은 Dto를 활용하여 로직작성  
09-20 수정 버튼 누를 때 메시지 안나오는 버그 수정, 수정완료 버튼 클릭시 confirm 추가  
09-24 게시글 목록 내 페이지네이션 제대로 적용되지 않던 오류 해결  
09-27 Mac OS을 고려하지 않고 이미지 업로드 경로를 설정, 사용자의 컴퓨터에 파일을 저장하는 방식을 수정.  
이미지 업로드 시 static폴더에 이미지 저장되도록 하여 운영체제에 상관없이 게시글 조회시 이미지도 문제없이 조회 가능. 
하지만 애플리케이션의 확장성을 고려했을 때 이미지 파일을 static폴더에 저장하는 것은 좋은 방법이 아닌 것 같음.. 고민 필요 